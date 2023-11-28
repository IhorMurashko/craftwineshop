package com.craftWine.shop.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ImagineHandler {
    /**
     * Saves an image file into the server directory and returns the path.
     *
     * This method takes a {@code MultipartFile} representing an image file and a unique identifier {@code wineArticle},
     * saves the image to a server directory, and returns the path where the image is saved.
     *
     * @param imageFile    The {@code MultipartFile} representing the image file to be saved.
     * @param wineArticle  A unique identifier associated with the wine article.
     * @return The path where the image is saved.
     * @throws IOException If an I/O exception occurs during the file copying process.
     */
    public static String saveImageIntoServerAndReturnPath(MultipartFile imageFile, String wineArticle) throws IOException {
        // Define the destination path for saving the image
        Path destination = Paths.get("/home/developer/my folder/java_projects/craftWineShop/CraftWineShop/src/main/wine_images/"
                + "wine-" + wineArticle + ".jpg");

        // Copy the image file to the specified destination path, replacing existing files
        Files.copy(imageFile.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        // Return the path where the image is saved
        return destination.toString();
    }
}
