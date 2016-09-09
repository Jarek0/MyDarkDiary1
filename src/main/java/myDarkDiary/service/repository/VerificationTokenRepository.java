/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.repository;

import myDarkDiary.service.model.User;
import myDarkDiary.service.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Dell
 */
public interface VerificationTokenRepository 
  extends JpaRepository<VerificationToken, Long> {
 
    VerificationToken findByToken(String token);
 
    VerificationToken findByUser(User user);
}
