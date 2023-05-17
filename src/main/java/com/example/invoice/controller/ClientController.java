package com.example.invoice.controller;

import com.example.invoice.entity.Client;
import com.example.invoice.entity.Produit;
import com.example.invoice.entity.User;
import com.example.invoice.repository.ClientRepository;
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
@RequestMapping("/client")
public class ClientController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @GetMapping
    public String displayClientDetails(Model model){
        Optional<User> user = Optional.ofNullable(userDetailsServiceImpl.getCurrentUser());

        if (user.isPresent()) {
            User currentUser = user.get();
            model.addAttribute("user", currentUser);

            // Récupérer les clients correspondants à l'ID utilisateur
            List<Client> clients = clientRepository.findByUserId(currentUser.getId());
            model.addAttribute("clients", clients);

            return "clients";
        } else {
            // Gérer l'absence de l'utilisateur
            return "error";
        }
    }
    @PostMapping
    public String createClient(@ModelAttribute Client client, Model model) {
        clientRepository.save(client);

        return "redirect:/client"; // Redirection
    }

}
