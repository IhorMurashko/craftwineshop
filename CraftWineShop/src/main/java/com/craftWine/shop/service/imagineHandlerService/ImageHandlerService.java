package com.craftWine.shop.service.imagineHandlerService;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageHandlerService {
    String saveImageIntoServerAndReturnPath(MultipartFile multipartFile, String wineID)
            throws IOException;


    boolean deleteWineImage(String publicId) throws IOException;


}
