package com.practice.blog.controllers;

import com.practice.blog.models.Post;
import com.practice.blog.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private final PostRepository postRepository;

    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping()
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/add")
    public String blogAdd() {
        return "blogAdd";
    }

    @PostMapping("/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String intro,
                              @RequestParam String full_text) {
        postRepository.save(new Post(title, intro, full_text));
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id))
            return "redirect:/blog";

        model.addAttribute("post",
                postRepository.findById(id).orElse(new Post()));
        return "blog-details";
    }

    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id))
            return "redirect:/blog";

        model.addAttribute("post",
                postRepository.findById(id).orElse(new Post()));
        return "blog-edit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostUpdate(@RequestParam String title,
                                 @RequestParam String intro,
                                 @RequestParam String full_text,
                                 @PathVariable(value = "id") long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setIntro(intro);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }
    @PostMapping("/{id}/delete")
    public String blogPostDelete(@PathVariable(value = "id") long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
