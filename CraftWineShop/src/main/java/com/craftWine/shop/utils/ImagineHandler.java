package com.craftWine.shop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagineHandler {


    public static String saveImageIntoServerAndReturnPath(MultipartFile imageFile, String wineArticle) throws IOException {


        Path destination = Paths.get("/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/"
                + "wine-" + wineArticle + ".jpg");


        Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return destination.toString();

    }


}
