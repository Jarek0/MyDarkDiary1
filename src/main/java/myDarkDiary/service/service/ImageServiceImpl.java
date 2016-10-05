/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import myDarkDiary.service.model.Image;
import myDarkDiary.service.model.User;
import myDarkDiary.service.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
 
@Service
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageRepository imageRespository;
    
    @Override
    public Image findByUserId(User userId){
        return imageRespository.findByUserId(userId);
    }
    
 
    
    @Override
    @Transactional
    public void save(Image uploadFile) {
        imageRespository.save(uploadFile);
    }
}
