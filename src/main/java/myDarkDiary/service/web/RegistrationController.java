/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.web;

import java.util.Calendar;
import java.util.Locale;
import myDarkDiary.service.events.OnRegistrationCompleteEvent;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.model.User;
import myDarkDiary.service.model.VerificationToken;
import myDarkDiary.service.service.UserService;
import myDarkDiary.service.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

/**
 *
 * @author Dell
 */
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    
    @Autowired
    private MessageSource messages;
    
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
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
    
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
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
}
