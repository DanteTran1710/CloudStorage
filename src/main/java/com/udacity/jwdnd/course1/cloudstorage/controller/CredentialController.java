package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;
    private final Map<String, String> activeModelObject;
    @Autowired
    private Environment env;

    @Value("${ENCRYPTION_KEY}")
    private String encryptKey;

    public CredentialController(CredentialService credentialService, EncryptionService encryptionService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.activeModelObject = new HashMap<>();
        activeModelObject.put("credential", "active show");
        activeModelObject.put("credentialTabPanel", "show active");
    }

    @RequestMapping()
    public String getHomePage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (Objects.isNull(user)) {
            return "redirect:/login";
        } else {
            List<Credential> credentials = credentialService.getAllCredentialsByUserId(user.getUserId());
            model.addAttribute("credentialList", credentials);
            model.addAttribute("encryptionService", encryptionService);
            model.addAttribute("encryptionKey", env.getProperty("ENCRYPTION_KEY"));
            model.addAllAttributes(activeModelObject);
        }
        return "home";
    }

    @PostMapping("/save")
    public String saveCredential(Model model, @ModelAttribute Credential credential, @RequestParam Integer credentialId, @RequestParam String url, @RequestParam String username, @RequestParam String password, Principal principal) {
        Integer userId = userService.getUser(principal.getName()).getUserId();
//        Credential credential = new Credential(credentialId, url, username, key, password, userId);
        credential.setUserId(userId);
        credentialService.saveOrUpdate(credential);
        model.addAllAttributes(activeModelObject);
        return "redirect:/credentials";
    }

    @GetMapping("delete/{id}")
    public String deleteCredential(Model model, @PathVariable Integer id) {
        credentialService.delete(id);
        model.addAllAttributes(activeModelObject);
        return "redirect:/credentials";
    }
}
