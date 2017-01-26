/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.services;

import java.util.Arrays;
import java.util.HashSet;
import myDarkDiary.service.model.Role;
import myDarkDiary.service.model.User;
import myDarkDiary.service.service.UserService;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


/**
 *
 * @author Dell
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/test-configuration.xml")
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    
    

    @Before
    public void createFakeUsers(){
        
            userService.saveRegisteredUser(new User("user1","pass2","email3",false,false,false,new HashSet<Role>(Arrays.asList(userService.findRoleByName("ROLE_USER")))));
            
    }
    
    @Test
    public void testIsAdmin() {
        assertTrue("User is not admin",!userService.IsAdmin(userService.findByUsername("user1")));
    }
    
    @After
    public void destroyFakeUsers(){
        userService.deleteUser("user1");
    }

}
