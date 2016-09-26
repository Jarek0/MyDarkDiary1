/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.handlers;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

/**
 *
 * @author Dell
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
 
    @Autowired
    private MessageSource messages;
 
    @Autowired
    private LocaleResolver localeResolver;
 
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, 
      HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
        setDefaultFailureUrl("/login.html?error=true");
        super.onAuthenticationFailure(request, response, exception);
 
        Locale locale = localeResolver.resolveLocale(request);
 
        String errorMessage = messages.getMessage("message.error", null, locale);
 
        if (exception.getMessage().equalsIgnoreCase("blocked")) {
            errorMessage = messages.getMessage("auth.message.blocked", null, locale);
        }
        else if (exception.getMessage().equalsIgnoreCase("your account is not verificated")) {
            errorMessage = messages.getMessage("auth.message.disabled", null, locale);
        } else if (exception.getMessage().equalsIgnoreCase("user account has expired")) {
            errorMessage = messages.getMessage("auth.message.expired", null, locale);
        }else if (exception.getMessage().equalsIgnoreCase("bad credentials")) {
            errorMessage = messages.getMessage("message.badCredentials", null, locale);
        }else if (exception.getMessage().equalsIgnoreCase("no user found with username")) {
            errorMessage = messages.getMessage("message.noUser", null, locale);
        }else if (exception.getMessage().equalsIgnoreCase("banned")) {
            errorMessage = messages.getMessage("message.userBanned", null, locale);
        }
 
        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}
