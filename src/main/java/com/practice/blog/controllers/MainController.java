package com.practice.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        model.addAttribute("text", "This is a text for the link that was passed from controller to the template.");
        model.addAttribute("linkText", "Back to the main page");
        model.addAttribute("mainPage", "/");
        return "about";
    }

}
