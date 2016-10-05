/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myDarkDiary.service.service;

import myDarkDiary.service.model.Image;
import myDarkDiary.service.model.User;

/**
 *
 * @author Dell
 */
public interface ImageService{
   
    Image findByUserId(User userId);
    public void save(Image uploadFile);
}
