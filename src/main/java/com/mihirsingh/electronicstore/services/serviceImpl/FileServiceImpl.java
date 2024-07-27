package com.mihirsingh.electronicstore.services.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mihirsingh.electronicstore.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(FileService.class);

    @Override
    public String uploadFile(MultipartFile file, String path) {
        // TODO Auto-generated method stub
        String originalFileName = file.getOriginalFilename();
        logger.info("originalFileName ::: " + originalFileName);
        String fileName = UUID.randomUUID().toString();
        String extenion = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtension = fileName + extenion;
        String fullPathwithFileName = path + fileNameWithExtension;
        if (extenion.equalsIgnoreCase(".jpg") || extenion.equalsIgnoreCase(".png")
                || extenion.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            try {
                Files.copy(file.getInputStream(), Paths.get(fullPathwithFileName));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return fileNameWithExtension;
        } else {
            throw new RuntimeException("File with this extension " + extenion + " is not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        // TODO Auto-generated method stub
        String fullpath = path + File.separator + name ;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }

}
