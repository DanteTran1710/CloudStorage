package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/notes")
public class NoteController {
    private UserService userService;
    private NoteService noteService;
    private final Map<String, String> commonModelObject;

    public NoteController(NoteService noteService,UserService userService) {
        this.userService = userService;
        this.noteService = noteService;
        this.commonModelObject = new HashMap<>();
        commonModelObject.put("note", "active show");
        commonModelObject.put("noteTabPanel", "show active");
    }

    @RequestMapping()
    public String getHomePage(Model model, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        if (Objects.isNull(user)) {
            return "redirect:/login";
        } else {
            List<Note> notes = noteService.getAllNotesByUserId(user.getUserId());
            model.addAttribute("notes", notes);
            model.addAllAttributes(commonModelObject);
        }
        return "home";
    }

    @PostMapping("/save")
    public String saveNote(Model model, @RequestParam Integer noteId, @RequestParam String noteTitle, @RequestParam String noteDescription, Principal principal) {
        Integer userId = userService.getUser(principal.getName()).getUserId();
        Note note = new Note(noteId, noteTitle, noteDescription, userId);
        noteService.saveOrUpdate(note);
        model.addAllAttributes(commonModelObject);
        return "redirect:/notes";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(Model model, @PathVariable Integer id) {
        noteService.delete(id);
        model.addAllAttributes(commonModelObject);
        return "redirect:/notes";
    }
}
