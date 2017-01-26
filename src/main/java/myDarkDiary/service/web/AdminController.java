/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import myDarkDiary.service.events.BanUserEvent;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author Dell
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
      @Autowired
      private UserService userService;
      
      @Autowired
      ApplicationEventPublisher eventPublisher;
    
       @RequestMapping(value = "/console", method = RequestMethod.GET)
	public String adminConsole(Model model, Authentication auth) {
//userService.findAll()
                List<User> usersList=userService.findAll();
		model.addAttribute("usersList",usersList);
                model.addAttribute("authUser",userService.findByUsername(auth.getName()));
		return "console";

	}
        
        @RequestMapping(value = "/console/delete/{userId}", method = RequestMethod.GET)
        @ResponseBody
        public ResponseEntity delateUser(@PathVariable(value="userId") Long id,Model model, Authentication authentication)
        {
            try{
            User deletedUser=userService.findById(id);
            if(deletedUser.getUsername().equalsIgnoreCase(authentication.getName()))
            {
                return new ResponseEntity<>("You can not delete yourself", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else if(userService.IsAdmin(deletedUser))
            {
                return new ResponseEntity<>("You can not delete admin", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else
            {
            userService.deleteUser(id);
            return new ResponseEntity<>("User with name "+deletedUser.getUsername()+" is deleted", new HttpHeaders(), HttpStatus.ACCEPTED);
            }
            
            }
            catch(UserNotFoundException e)
            {
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
        }
        
        @RequestMapping(value = "/console/ban/{userId}", method = RequestMethod.GET)
        @ResponseBody
        public ResponseEntity banUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            User bannedUser=userService.findById(id);
            if(bannedUser.getUsername().equalsIgnoreCase(authentication.getName()))
            {
                 return new ResponseEntity<>("You can not ban yourself", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else if(userService.IsAdmin(bannedUser))
            {
                 return new ResponseEntity<>("You can not ban admin", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            else
            {
                
            bannedUser.setBanned(true);
            userService.saveRegisteredUser(bannedUser);
            eventPublisher.publishEvent(new BanUserEvent(bannedUser.getUsername(),bannedUser.getOnline()));
            return new ResponseEntity<>("User "+bannedUser.getUsername()+" is banned", new HttpHeaders(), HttpStatus.ACCEPTED);
            }
        }
        
        @RequestMapping(value = "/console/unban/{userId}", method = RequestMethod.GET)
        @ResponseBody
        public ResponseEntity unbanUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            User unbannedUser=userService.findById(id);
            unbannedUser.setBanned(false);
            userService.saveRegisteredUser(unbannedUser);
            return new ResponseEntity<>("User "+unbannedUser.getUsername()+" is unbanned", new HttpHeaders(), HttpStatus.ACCEPTED);
        }
        
        @RequestMapping(value = "/console/upgrade/{userId}", method = RequestMethod.GET)
        @ResponseBody
        public ResponseEntity upgradeUser(@PathVariable(value="userId") Long id,Model model,WebRequest request, Authentication authentication)
        {
            
            User upgradedUser=userService.findById(id);
            if(userService.IsAdmin(upgradedUser))
            {
                model.addAttribute("message", "You can not upgrade admin");
                return new ResponseEntity<>("You can not upgrade admin", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            userService.changeRoleOfUser(upgradedUser, "ROLE_ADMIN");
            userService.saveRegisteredUser(upgradedUser);
            return new ResponseEntity<>("User "+upgradedUser.getUsername()+" is upgrade", new HttpHeaders(), HttpStatus.ACCEPTED);
        }
        
        @RequestMapping(value="/console/search", method=RequestMethod.POST)
        public String search(@RequestParam("text") String text,@RequestParam("searchBy") String searchBy,@RequestParam("online") int online,@RequestParam("verificated") int enabled,@RequestParam("banned") int banned,@RequestParam("role") String role,Model model,Authentication auth){
            System.out.println(text+" "+searchBy+" "+online+" "+enabled+" "+banned+" "+role);
            model.addAttribute("usersList",userService.getUserList(userService.findAll(),text,searchBy,online,enabled,banned,role));
            return "console";
        }
        
        @RequestMapping(value = "/update**", method = RequestMethod.GET)
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
        
        private boolean isRememberMeAuthenticated() {

		Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}

		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}
        
        private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.setAttribute("targetUrl", "/admin/update");
		}
	}
        
}
