package com.projet.housing.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static String saveFile(String fileName, String fileCode, MultipartFile multipartFile) throws IOException{
        Path uploadPath = Paths.get("Files-Upload");
        
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode+"-"+fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Fichier non sauvegarder: " + fileName, ioe);
        }
        return fileCode;
    }
}
