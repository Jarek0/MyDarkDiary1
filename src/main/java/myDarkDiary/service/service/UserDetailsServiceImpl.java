package myDarkDiary.service.service;

import myDarkDiary.service.repository.UserRepository;
import myDarkDiary.service.model.User;
import myDarkDiary.service.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;


public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;
  
    @Autowired
    private HttpServletRequest request;
    
    private String getClientIP() {
    String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null){
        return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
}
    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,DisabledException,BadCredentialsException,RuntimeException {
        
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
        
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
              "no user found with username");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        boolean enabled = true;
       if(!user.getEnabled())
        {
            enabled = false;
            throw new DisabledException(
              "your account is not verificated");
        }
        
        
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = !user.getBanned();
        if (!accountNonLocked) {
            throw new RuntimeException("banned");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),enabled, accountNonExpired, 
          credentialsNonExpired, accountNonLocked, grantedAuthorities);
    }
}
    
    //remember about this place

//tomorow http://www.baeldung.com/registration-verify-user-by-email
