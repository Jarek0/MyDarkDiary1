/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.web;

import java.util.Iterator;
import java.util.List;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Dell
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
      private UserService userService;
    
     @RequestMapping( method = RequestMethod.GET)
    public String search(Model model,Authentication auth) {
        
        return "search";
    }
    @RequestMapping( method = RequestMethod.POST)
    public String search(@RequestParam("text") String text,@RequestParam("searchBy") String searchBy,@RequestParam("online") int online,@RequestParam("role") String role,Model model,Authentication auth) {
        model.addAttribute("usersList",getUserList(userService.findAll(),text,searchBy,online,role));
        model.addAttribute("authUser", userService.findByUsername(auth.getName()));
        return "search";
    }

	
        
     
       
        public List<User> getUserList(List<User> usersList,String text,String searchBy,int online,String role){
            
           
        for(Iterator<User> iter = usersList.iterator(); iter.hasNext(); )
        {
            User user = iter.next();
            
            if(searchBy.equals("username") && !user.getUsername().contains(text))
            {
                
                    iter.remove();
                    continue;
                
            }
            else if(searchBy.equals("e-mail") && !user.getEmail().contains(text))
            {
                    iter.remove();
                    continue;
               
            }
            if(online==0 && !user.getOnline())
            {
                iter.remove();
                    continue;
            }
            if(online==1 && user.getOnline())
            {
                iter.remove();
                    continue;
            }
            if(userService.IsAdmin(user) && role.equals("ROLE_USER"))
            {
                iter.remove();
                    continue;
            }
            if(!userService.IsAdmin(user) && role.equals("ROLE_ADMIN"))
            {
                iter.remove();
            }
        }
        return usersList;
        }
    
}
