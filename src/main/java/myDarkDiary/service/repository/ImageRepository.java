/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.repository;

/**
 *
 * @author Dell
 */
import myDarkDiary.service.model.Image;
import myDarkDiary.service.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
 
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
   Image findByUserId(User userId);
 
}
