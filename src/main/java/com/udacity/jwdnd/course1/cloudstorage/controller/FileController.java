package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/files")
public class FileController {
    private FileService fileService;

    private UserService userService;
    private final Map<String, String> activeModelObject;

    public FileController(FileService fileService, UserService userService) {
        this.userService = userService;
        this.fileService = fileService;
        this.activeModelObject = new HashMap<>();
        activeModelObject.put("file", "active show");
        activeModelObject.put("fileTabPanel", "show active");
    }

    @RequestMapping()
    public String getHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUser(authentication.getName());
        if (Objects.isNull(user)) {
            return "redirect:/login";
        } else {
            List<File> files = fileService.getAllFilesByUserId(user.getUserId());
            model.addAttribute("files", files);
            model.addAllAttributes(activeModelObject);
        }
        return "home";
    }


    @PostMapping("/uploadFile")
    public String uploadFile(Model model, @RequestParam("fileUpload") MultipartFile file, Principal principal) throws IOException {
        Integer userId = userService.getUser(principal.getName()).getUserId();
//        File newFile = new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), "abc".getBytes(StandardCharsets.UTF_8),userId) ;
        fileService.save(file, userId);
        model.addAllAttributes(activeModelObject);
        return "redirect:/files";
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<String> viewFile(@PathVariable Integer id) {
        File file = fileService.getFileById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData("attachment", file.getFileName());
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    @GetMapping("delete/{id}")
    public String deleteFile(@PathVariable Integer id) {
        fileService.delete(id);
        return "redirect:/files";
    }
}
