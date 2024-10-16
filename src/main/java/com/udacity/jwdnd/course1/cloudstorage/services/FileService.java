package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileMapper fileMapper;

    public List<File> getAllFilesByUserId(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public int save(MultipartFile file, int userId) throws IOException {
        byte[] bytes = file.getInputStream().readAllBytes();
        File newFile = new File(null, file.getOriginalFilename(),
                file.getContentType(),
                String.valueOf(file.getSize()),
                userId,
        Base64.encodeBase64String(bytes));

        return fileMapper.insert(newFile);
    }

    public void delete(Integer fileId) {
        fileMapper.delete(fileId);
    }
}
