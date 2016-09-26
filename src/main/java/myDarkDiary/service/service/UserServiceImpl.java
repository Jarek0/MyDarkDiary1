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
import java.util.UUID;
import java.util.Arrays;
import myDarkDiary.service.repository.RoleRepository;
import myDarkDiary.service.repository.UserRepository;
import myDarkDiary.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import myDarkDiary.service.exceptions.EmailExistsException;
import myDarkDiary.service.exceptions.UserNotFoundException;
import myDarkDiary.service.model.VerificationToken;
import myDarkDiary.service.repository.VerificationTokenRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    
    @Override
    public void saveRegisteredUser(User user) {
        
        userRepository.save(user);
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
    public User findById(Long id) {
        return userRepository.findById(id);
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
        accountDto.setEnabled(false);
        accountDto.setBanned(false);
        return userRepository.save(accountDto);
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
    public void changeRoleOfUser(User user,String role) {
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName("ROLE_ADMIN"))));
    }
    
    @Override
    public boolean IsAdmin(User user) {
        if(roleRepository.findByName("ROLE_ADMIN").getUsers().contains(user))
        return true;
        
        return false;
    }
}
