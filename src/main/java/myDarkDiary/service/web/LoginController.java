/*
 * Copyright 2016 Dell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package myDarkDiary.service.web;

/**
 *
 * @author Dell
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import javax.annotation.Resource;
import myDarkDiary.service.validator.UserValidator;
import myDarkDiary.service.service.SecurityServiceImpl;
import myDarkDiary.service.service.UserServiceImpl;
import myDarkDiary.service.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.GenericResponse;
import myDarkDiary.service.events.BanUserEvent;
import myDarkDiary.service.model.VerificationToken;
import myDarkDiary.service.events.OnRegistrationCompleteEvent;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.service.MailServiceImpl;
import myDarkDiary.service.service.SecurityServiceImpl;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


@Controller
public class LoginController {
    @Autowired
    private SessionRegistry sessionRegistry;
    
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityServiceImpl securityService;
    
    @Autowired
    private MessageSource messages;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private MailServiceImpl mailService;
    
    

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model,WebRequest request) throws EmailExistsException {
        
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User registered = userService.registerNewUserAccount(userForm);
        

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        //try {
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
        (userForm, request.getLocale(), appUrl));
        /** } catch (Exception me) {
         model.addAttribute("error", "Error :(");
         return "login";
         }**/
        model.addAttribute("msg", "You've been registered, check your email to verificate your account.");
        return "login";
    }
    
    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
    Locale locale = request.getLocale();
     
    VerificationToken verificationToken = userService.getVerificationToken(token);
    if (verificationToken == null) {
        String message = messages.getMessage("auth.message.invalidToken", null, locale);
        model.addAttribute("message", message);
        return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }
     
    User user = verificationToken.getUser();
    Calendar cal = Calendar.getInstance();
    if ((verificationToken.getDate() - cal.getTime().getTime()) <= 0) {
        String messageValue = messages.getMessage("auth.message.expired", null, locale);
        model.addAttribute("message", messageValue);
        model.addAttribute("expired", true);
        model.addAttribute("token", token);
        return "badUser";
    } 
     
    user.setEnabled(true); 
    userService.saveRegisteredUser(user); 
    String messageValue = messages.getMessage("message.register.succes", null, locale);
    model.addAttribute("msg", messageValue);
    return "login"; 
}

    
        @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("title", "Spring Security Remember Me");
	model.addAttribute("message", "This is default page!");
        return "hello";
    }

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model) {

		
		model.addAttribute("title", "Spring Security Remember Me");
		model.addAttribute("message", "This page is for ROLE_ADMIN only!");
		
		return "admin";

	}
        
        @RequestMapping(value = "/admin/console", method = RequestMethod.GET)
	public String adminConsole(Model model, Authentication authentication) {
//userService.findAll()
                List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
		return "console";

	}
        
        @RequestMapping(value = "/admin/console/delete/{userId}", method = RequestMethod.GET)
        public String delateUser(@PathVariable(value="userId") Long id,Model model, Authentication authentication)
        {
            try{
            User deletedUser=userService.deleteUser(id);
            if(deletedUser.getUsername().equalsIgnoreCase(authentication.getName()))
            {
                model.addAttribute("message", "You can not delete yourself");
                List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
                return "console";
            }
            else if(!userService.IsAdmin(deletedUser))
            {
                model.addAttribute("message", "You can not delete admin");
                List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
                return "console";
            }
            else
            {
            model.addAttribute("message", "User with name "+deletedUser.getUsername()+" is deleted");
            List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
            return "console";
            }
            
            }
            catch(UserNotFoundException e)
            {
            model.addAttribute("message", e.getMessage());
            List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
            return "console";
            }
        }
        
        @RequestMapping(value = "/admin/console/ban/{userId}", method = RequestMethod.GET)
        public String banUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            
            User bannedUser=userService.findById(id);
            if(bannedUser.getUsername().equalsIgnoreCase(authentication.getName()))
            {
                model.addAttribute("message", "You can not delete yourself");
                List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
                return "console";
            }
            else if(!userService.IsAdmin(bannedUser))
            {
                model.addAttribute("message", "You can not ban admin");
                List<User> usersList=getUserList(authentication.getName());
		model.addAttribute("usersList",usersList);
                return "console";
            }
            else
            {
            bannedUser.setBanned(true);
            userService.saveRegisteredUser(bannedUser);
                eventPublisher.publishEvent(new BanUserEvent(bannedUser.getUsername(),bannedUser.getOnline()));
            List<User> usersList=getUserList(authentication.getName());
	    model.addAttribute("usersList",usersList);
            model.addAttribute("message", "User with name "+bannedUser.getUsername()+" is banned");
            
            return "console";
            }
        }
        
        @RequestMapping(value = "/admin/console/unban/{userId}", method = RequestMethod.GET)
        public String unbanUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            User unbannedUser=userService.findById(id);
            unbannedUser.setBanned(false);
            userService.saveRegisteredUser(unbannedUser);
            List<User> usersList=getUserList(authentication.getName());
	    model.addAttribute("usersList",usersList);
            model.addAttribute("message", "User with name "+unbannedUser.getUsername()+" is unbanned");
            return "console";
        }
        
        @RequestMapping(value = "/admin/console/upgrade/{userId}", method = RequestMethod.GET)
        public String upgradeUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            User unbannedUser=userService.findById(id);
            userService.changeRoleOfUser(unbannedUser, "ROLE_ADMIN");
            userService.saveRegisteredUser(unbannedUser);
            List<User> usersList=getUserList(authentication.getName());
	    model.addAttribute("usersList",usersList);
            model.addAttribute("message", "User with name "+unbannedUser.getUsername()+" is unbanned");
            return "console";
        }
        
        public List<User> getUserList(String userName){
            List<User> usersList=userService.findAll();
            
        List<Object> onlineUserList=sessionRegistry.getAllPrincipals();
        for(User user:usersList)
        {
            if(user.getUsername().equalsIgnoreCase(userName))
            {
                usersList.remove(user);
                break;
            }
            for(Object onlineUser:onlineUserList)
            {
                if(user.getUsername().equalsIgnoreCase(((org.springframework.security.core.userdetails.User)onlineUser).getUsername()))
                {
                    user.setOnline(true);
                    userService.saveRegisteredUser(user);
                    break;
                }
                else
                {
                    user.setOnline(false);
                    userService.saveRegisteredUser(user);
                }
            }
        }
        return usersList;
        }

	/**
	 * This update page is for user login with password only.
	 * If user is login via remember me cookie, send login to ask for password again.
	 * To avoid stolen remember me cookie to update info
	 */
	@RequestMapping(value = "/admin/update**", method = RequestMethod.GET)
	public String updatePage(HttpServletRequest request,Model model) {

		

		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request);
			model.addAttribute("loginUpdate", true);
			
                        return "login";

		} else {
                        return "update";
		}

		

	}

	/**
	 * both "normal login" and "login for update" shared this form.
	 *
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
	  @RequestParam(value = "logout", required = false) String logout,
          HttpServletRequest request,Model model){

		
		if (error != null) {
                    

			//login form for update page
                        //if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			System.out.println(targetUrl);
			if(StringUtils.hasText(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
			}

		}
                

		if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}
		

		return "login";

	}
        
        @RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied(Model model) {

	  

	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		model.addAttribute("username", userDetail.getUsername());
	  }

	  
	  return "403";

	}

	
	private boolean isRememberMeAuthenticated() {

		Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}

	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", "/admin/update");
		}
	}

	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null?""
                             :session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}

        @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
        @ResponseBody
        public GenericResponse resendRegistrationToken(
        HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
     
        User user = userService.getUser(newToken.getToken());
        String appUrl = 
        "http://" + request.getServerName() + 
        ":" + request.getServerPort() + 
        request.getContextPath();
        SimpleMailMessage email = 
        constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailService.sendMail(email);
        
        return new GenericResponse(
        messages.getMessage("message.resendToken", null, request.getLocale()));
}
        
        private SimpleMailMessage constructResendVerificationTokenEmail
  (String contextPath, Locale locale, VerificationToken newToken, User user) {
    String confirmationUrl = 
      contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
    String message = messages.getMessage("message.resendToken", null, locale);
    SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject("Resend Registration Token");
    email.setText(message + " rn" + confirmationUrl);
    email.setFrom("from@no-spam.com");
    email.setTo(user.getEmail());
    return email;
}
    
}
