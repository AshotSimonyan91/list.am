package com.example.listam.controller;


import com.example.listam.entity.Comment;
import com.example.listam.entity.Item;
import com.example.listam.repository.CommentRepository;
import com.example.listam.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/comments/items/{id}")
    public String singleItemsComment(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            modelMap.addAttribute("item", item);
            List<Comment> allByItemId = commentRepository.findAllByItemId(id);
            modelMap.addAttribute("comments", allByItemId);
            return "singleItem";
        } else {
            return "redirect:/items/" + id;
        }
    }

    @PostMapping("/comments/items/{id}")
    public String singleItemsAddComment(@PathVariable("id") int id, @RequestParam("comm") String comm) {
        Comment comment = new Comment();
        comment.setComment(comm);
        Optional<Item> byId = itemRepository.findById(id);
        if (byId.isPresent()) {
            Item item = byId.get();
            comment.setItem(item);
            commentRepository.save(comment);
            return "redirect:/comments/items/" + id;
        } else{
            return "singleItem";
        }

    }

    @GetMapping("/comments/remove")
    public String removeComment(@RequestParam("comm_id") int comm_id, @RequestParam("item_id")  int item_id) {
        commentRepository.deleteById(comm_id);
        return "redirect:/comments/items/" + item_id;
    }
}
