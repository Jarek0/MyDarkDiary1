package myDarkDiary.service.service;

import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.model.User;
import myDarkDiary.service.model.VerificationToken;

public interface UserService {
    void saveRegisteredUser(User user);
    
    User registerNewUserAccount(User accountDto)  throws EmailExistsException;

    User findByUsername(String username);
    
    User getUser(String verificationToken);
    
    User findByEmail(String email);
    
    boolean emailExist(String email);
    
    void createVerificationToken(User user, String token);
    
    VerificationToken generateNewVerificationToken(String existingToken);
 
    VerificationToken getVerificationToken(String VerificationToken);
}
