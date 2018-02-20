package com.shop.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private List<byte[]> listImages = new ArrayList<>();


    public void addImage(byte[] image){
        listImages.add(image);
    }

    public List<byte[]> getListImages() {
        return listImages;
    }

    public void clearListImages(){
        listImages.clear();
    }
}