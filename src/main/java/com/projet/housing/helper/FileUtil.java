package com.projet.housing.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    private static Path foundFile;

    public static String saveFile(String fileName, MultipartFile multipartFile) throws IOException{
        Path uploadPath = Paths.get("Files-Upload");
        
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Fichier non sauvegarder: " + fileName, ioe);
        }
        return fileName;
    }

    public static UrlResource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("./upload-file/").toAbsolutePath().normalize();
         
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().equals(fileCode)) {
                foundFile = file;
                return;
            }
        });
 
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
         
        return null;
    }
}
