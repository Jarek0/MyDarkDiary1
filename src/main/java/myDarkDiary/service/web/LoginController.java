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

import java.util.Locale;
import myDarkDiary.service.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import myDarkDiary.service.GenericResponse;
import myDarkDiary.service.model.VerificationToken;
import myDarkDiary.service.service.MailServiceImpl;
import myDarkDiary.service.notUsed.SecurityServiceImpl;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//this controler redirect to login page and supports a lot of another functions which are not suitable to another controllers
@Controller
public class LoginController {
    
    //this is object which can save user, find it by id,username, verification token or e-mail,delete, change role and more
    @Autowired
    private UserService userService;
    
    //this object can auto login user but now this possibility is not used
    @Autowired
    private SecurityServiceImpl securityService;
    //this object give access to messages which are saved in special file
    @Autowired
    private MessageSource messages;

    
    //this is mail sender
    @Autowired
    private MailServiceImpl mailService;
    
    
//when user session is expired by admin (banUser and deleteUser functions from admin controler) appconfig-security.xml file redirect it to this method
        @RequestMapping(value = "/youarebanned", method = RequestMethod.GET)
        public String youAreBanned(Model model)
        {
            model.addAttribute("msg", "You are banned by admin");
            return "login";
        }
        
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
