package myDarkDiary.service.service;

import java.util.List;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.model.User;
import myDarkDiary.service.model.VerificationToken;

public interface UserService {
    void saveRegisteredUser(User user);
    
    User registerNewUserAccount(User accountDto)  throws EmailExistsException;

    User findByUsername(String username);
    
    User getUser(String verificationToken);
    
    User findByEmail(String email);
    
    public User findById(Long id);
    
    public User deleteUser(Long id) throws UserNotFoundException;
    
    boolean emailExist(String email);
    
    List<User> findAll();
    
    void createVerificationToken(User user, String token);
    
    VerificationToken generateNewVerificationToken(String existingToken);
 
    VerificationToken getVerificationToken(String VerificationToken);
}
