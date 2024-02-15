package com.craftWine.shop.service.imagineHandlerService;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageHandlerServiceImpl implements ImageHandlerService {

    @Autowired
    private Cloudinary cloudinary;

    public String saveImageIntoServerAndReturnPath(MultipartFile multipartFile, String wineID)
            throws IOException {

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new MultipartException("image file cannot be empty");
        }

        Map upload = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap(
                "public_id", "wine-" + wineID,
                "folder", "/craft-wine-shop",
                "use_filename", "false",
                "unique_filename", "true"
                , "transformation", new Transformation()
                        .width(1000).crop("scale").chain()
                        .quality("auto:best").chain()
                        .fetchFormat("auto")
        ));

        return (String) upload.get("url");
    }


}
