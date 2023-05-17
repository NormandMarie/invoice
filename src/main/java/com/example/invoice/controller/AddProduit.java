package com.example.invoice.controller;

import com.example.invoice.entity.Produit;
import com.example.invoice.entity.User;
import com.example.invoice.repository.ProduitRepository;
import com.example.invoice.repository.UserRepository;
import com.example.invoice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/add-product")
public class AddProduit {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping
    public String displayUserDetails(Model model) {
        Optional<User> user = Optional.ofNullable(userDetailsServiceImpl.getCurrentUser());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "add-product";
        } else {
            // Gérer l'absence de l'utilisateur
            return "error";
        }
    }

    @PostMapping
    public String AjoutProduit(@ModelAttribute Produit produit, Model model) {

        produitRepository.save(produit);

        Optional<User> user = Optional.ofNullable(userDetailsServiceImpl.getCurrentUser());

        if (user.isPresent()) {
            model.addAttribute("user", user.get());

            return "redirect:/home";
        } else {
            // Gérer l'absence de l'utilisateur
            return "error";
        }
    }
}