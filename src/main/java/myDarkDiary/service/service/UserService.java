package myDarkDiary.service.service;

import java.util.List;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.model.Role;
import myDarkDiary.service.model.User;
import myDarkDiary.service.model.VerificationToken;

public interface UserService {
    void deleteFromFriends(User user1,User user2);
    
    void saveRegisteredUser(User user);
    
    User registerNewUserAccount(User accountDto)  throws EmailExistsException;

    User findByUsername(String username);
    
    User getUser(String verificationToken);
    
    User findByEmail(String email);
    
    public Role findRoleByName(String roleName);
    
    public User deleteUser(String userName);
    
    public User findById(Long id) throws UserNotFoundException;
    
    public User deleteUser(Long id) throws UserNotFoundException;
    
    public void changeRoleOfUser(User user,String role);
    
    public boolean IsAdmin(User user);
    
    boolean emailExist(String email);
    
    List<User> findAll();
    
    void createVerificationToken(User user, String token);
    
    VerificationToken generateNewVerificationToken(String existingToken);
 
    VerificationToken getVerificationToken(String VerificationToken);
    
    public List<User> getUserList(List<User> usersList,String text,String searchBy,int online,int enabled,int banned,String role);
    
    public List<User> getUserList(List<User> usersList,String text,String searchBy,int online,String role);
}
