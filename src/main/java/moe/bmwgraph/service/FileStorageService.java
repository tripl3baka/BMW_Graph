package moe.bmwgraph.service;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private final Path imgStorageLocation;

    @Autowired
    public FileStorageService(Environment env) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/csv"))
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }

        this.imgStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/img"))
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.imgStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFile(MultipartFile file) {

        String fileName =
                new Date().getTime() + "-csv." + getFileExtension(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String getFileURL(String filename) {
        return "/uploads/files/" + filename;
    }

    public Path getFilePath(String filename) {
        return fileStorageLocation.resolve(filename);
    }

    public Resource openFile(String filename) {
        try {
            Path file = getFilePath(filename);
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String storeImgFile(String base64String) {
        String outputFileName =
                new Date().getTime() + "-image.png";

        String[] strings = base64String.split(",");

        byte[] data = Base64.getDecoder().decode(strings[1]);
        File file = new File(imgStorageLocation.toUri());
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputFileName;
    }

}