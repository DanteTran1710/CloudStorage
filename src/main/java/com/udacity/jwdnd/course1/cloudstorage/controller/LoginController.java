package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {
    private UserService userService;
    private final Map<String, String> commonModelObject;
    private AuthenticationService authenticationService;

    public LoginController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.commonModelObject = new HashMap<>();
        this.authenticationService = authenticationService;
        commonModelObject.put("file", "active show");
        commonModelObject.put("fileTabPanel", "show active");
        commonModelObject.put("signupSuccess", "false");
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAllAttributes(commonModelObject);
        return "login";
    }

    @PostMapping("/login-action")
    public String loginAction(Model model, @ModelAttribute User user, Authentication authentication) {
         User userSavedInDataBase = userService.getUser(user.getUsername());
        if (Objects.isNull(userSavedInDataBase)) {
            return "redirect:/login";
        }
        model.addAllAttributes(commonModelObject);
        SecurityContextHolder.getContext().setAuthentication(authenticationService.authenticate(userSavedInDataBase));
        return "redirect:/files";
    }
}
