/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.web;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import myDarkDiary.service.extras.ImageConverter;
import myDarkDiary.service.model.Image;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.ImageService;
import myDarkDiary.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Dell
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
      private UserService userService;
    
    @Autowired
    private ImageService imageService;
    
    
    @Autowired
    private ImageConverter imageConverter;
    
       @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String welcome(@PathVariable(value="userId") Long id,Model model,Authentication auth) {
        if(id==null)
        {
            User user=userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
            return "profile";
        }
        else{
            User user=userService.findById(id);
            model.addAttribute("user", user);
            model.addAttribute("authUser", userService.findByUsername(auth.getName()));
            return "displayProfile";
        }
        
    }
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String displayFriends(Model model,Authentication auth) {
        model.addAttribute("usersList",new ArrayList<User>(userService.findByUsername(auth.getName()).getUsers()));
        return "displayFriends";
    }
    
    @RequestMapping(value = "/addFriend/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity addFriend(@PathVariable(value="userId") Long id,Model model,Authentication auth) {
        User user1=userService.findByUsername(auth.getName());
        User user2=userService.findById(id);
        user1.getUsers().add(user2);
        user2.getUsers().add(user1);
        userService.saveRegisteredUser(user1);
        userService.saveRegisteredUser(user2);
        return new ResponseEntity<>("User with name "+user2.getUsername()+" is added to friends", new HttpHeaders(), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(value = "/deleteFriend/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteFriend(@PathVariable(value="userId") Long id,Model model,Authentication auth) {
        User user1=userService.findByUsername(auth.getName());
        User user2=userService.findById(id);
        userService.deleteFromFriends(user1, user2);
        userService.saveRegisteredUser(user1);
        userService.saveRegisteredUser(user2);
        return new ResponseEntity<>("User with name "+user2.getUsername()+" is deleted from friends", new HttpHeaders(), HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String welcome(Model model,Authentication auth) {
        
            User user=userService.findByUsername(auth.getName());
            model.addAttribute("user", user);
            return "profile";
        
    }
    
    @RequestMapping(value = "/changeDiscription", method = RequestMethod.POST)
    public String changeDiscription(@RequestParam("discription") String discription,Model model,Authentication auth) {
        User user=userService.findByUsername(auth.getName());
        user.setDiscription(discription);
        userService.saveRegisteredUser(user);
        model.addAttribute("user",user);
        return "profile";
    }
    
    @RequestMapping(value = "/imageDisplay/{width}/{height}/{userId}", method = RequestMethod.GET)
  public void showImage(@PathVariable(value="userId") Long id,@PathVariable(value="width") int width,@PathVariable(value="height") int height,HttpServletResponse response,HttpServletRequest request) 
          throws ServletException, IOException{


    User user=userService.findById(id);
    Image avatar = imageService.findByUserId(user);        
    avatar=imageConverter.convertImage(avatar,width,height);
    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
    response.getOutputStream().write(avatar.getData());


    response.getOutputStream().close();
  }
  
  @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String handleFileUpload(HttpServletRequest request,
            @RequestParam("file") MultipartFile fileUpload,Authentication auth,Model model) throws IOException{
          User user=userService.findByUsername(auth.getName());
          
        if (fileUpload != null) {
                
                
                if(fileUpload.getContentType().toLowerCase().equals("image/jpg") || fileUpload.getContentType().toLowerCase().equals("image/jpeg") || fileUpload.getContentType().toLowerCase().equals("image/png") || fileUpload.getContentType().toLowerCase().equals("image/gif"))
                {
                    Image uploadFile = imageService.findByUserId(user);
                    uploadFile.setFileName(fileUpload.getOriginalFilename());
                uploadFile.setData(fileUpload.getBytes());
                imageService.save(uploadFile); 
                }
                
        }
        model.addAttribute("user",user);
        return "profile";
    }  
}
