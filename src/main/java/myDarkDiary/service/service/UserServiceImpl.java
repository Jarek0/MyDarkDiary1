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
package myDarkDiary.service.service;

/**
 *
 * @author Dell
 */
import java.io.IOException;
import java.util.Arrays;
import myDarkDiary.service.repository.RoleRepository;
import myDarkDiary.service.repository.UserRepository;
import myDarkDiary.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.model.Image;
import myDarkDiary.service.model.Role;
import myDarkDiary.service.model.VerificationToken;
import myDarkDiary.service.repository.ImageRepository;
import myDarkDiary.service.repository.VerificationTokenRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public void saveRegisteredUser(User user) {
        
        userRepository.save(user);
    }
    @Override
    public Role findRoleByName(String roleName){
        return roleRepository.findByName(roleName);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public User findById(Long id){
        User user=userRepository.findById(id);
        if(user==null){
            throw new UserNotFoundException("this user does not exist");
        }
        return user;
        
    }
    
   
    @Override
    public boolean emailExist(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    @Transactional
    @Override
    public User registerNewUserAccount(User accountDto) 
      throws EmailExistsException {
         
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
              "There is an account with that email adress: "
              + accountDto.getEmail());
        }
        accountDto.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        accountDto.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
        accountDto.setUsers(new HashSet<>());
        Image avatar;
        try {
            avatar=new Image(accountDto,"defaultAvatar","/images/default.png");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            avatar=new Image();
        }
        
        accountDto.setEnabled(false);
        accountDto.setBanned(false);
        accountDto.setDiscription("New user of \"My dark diary\"");
        User savedUser=userRepository.save(accountDto);
        imageRepository.save(avatar);
        return savedUser;
    }
    
    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    @Override
    public VerificationToken generateNewVerificationToken(String existingToken)
    {
        return new VerificationToken(UUID.randomUUID().toString(),(tokenRepository.findByToken(existingToken)).getUser());
    }
    
    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
    
    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users=userRepository.findAll();
        for(User user:users)
        {
            user.setPassword(user.getPassword());
        }
        return users;
    }

    @Override
    public User deleteUser(Long id) {
        User deletedUser = userRepository.findById(id);
         
        if (deletedUser == null)
            throw new UserNotFoundException("this user does not exist");
         
        userRepository.delete(deletedUser);
        return deletedUser;
    }
    
    @Override
    public User deleteUser(String userName) {
        User deletedUser = userRepository.findByUsername(userName);
         
        if (deletedUser == null)
            throw new UserNotFoundException("this user does not exist");
         
        userRepository.delete(deletedUser);
        return deletedUser;
    }
    
    @Override
    public void changeRoleOfUser(User user,String role) {
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_ADMIN"))));
    }
    
    @Override
    public boolean IsAdmin(User user) {
        
        for(User searchedUser:roleRepository.findByName("ROLE_ADMIN").getUsers())
        {
            if(searchedUser.getId()==user.getId())
            {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void deleteFromFriends(User user1,User user2) {
        Iterator<User> iterator = user1.getUsers().iterator();
        while (iterator.hasNext()) {
        User element = iterator.next();
        if (Objects.equals(element.getId(), user2.getId())) {
        iterator.remove();
        }
        }
        iterator = user2.getUsers().iterator();
        while (iterator.hasNext()) {
        User element = iterator.next();
        if (Objects.equals(element.getId(), user1.getId())) {
        iterator.remove();
        }
        }
    }
    
    @Override
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
            if(IsAdmin(user) && role.equals("ROLE_USER"))
            {
                iter.remove();
                    continue;
            }
            if(!IsAdmin(user) && role.equals("ROLE_ADMIN"))
            {
                iter.remove();
            }
        }
        return usersList;
        }
    
    @Override
     public List<User> getUserList(List<User> usersList,String text,String searchBy,int online,int enabled,int banned,String role){
            
           
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
            if(enabled==0 && !user.getEnabled())
            {
                iter.remove();
                    continue;
            }
            if(enabled==1 && user.getEnabled())
            {
                iter.remove();
                    continue;
            }
            if(banned==0 && !user.getBanned())
            {
                iter.remove();
                    continue;
            }
            if(banned==1 && user.getBanned())
            {
                iter.remove();
                    continue;
            }
            if(IsAdmin(user) && role.equals("ROLE_USER"))
            {
                iter.remove();
                    continue;
            }
            if(!IsAdmin(user) && role.equals("ROLE_ADMIN"))
            {
                iter.remove();
            }
        }
        return usersList;
        }

}
