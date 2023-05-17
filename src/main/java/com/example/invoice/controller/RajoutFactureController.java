package com.example.invoice.controller;

import com.example.invoice.entity.*;
import com.example.invoice.repository.*;
import com.example.invoice.service.FactureService;
import com.example.invoice.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping
public class RajoutFactureController {



        @Autowired
        UserRepository userRepository;
        @Autowired
        FactureService factureService;
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        ProduitRepository produitRepository;

        @Autowired
        FactureRepository factureRepository;
        @Autowired
        FactureProduitRepository factureProduitRepository;
        @Autowired
        UserDetailsServiceImpl userDetailsServiceImpl;




//@PostMapping
//public String createFacture( @RequestParam(value = "factureId", required = false) Long factureId,
//                            @RequestParam("produitId") Long produitId,
//                            @RequestParam("quantite") int quantite,
//                            Model model) {
//    User currentUser = userDetailsServiceImpl.getCurrentUser();
//    model.addAttribute("user", currentUser);
//
//    Optional<Facture> existingFactureOptional = factureRepository.findById(factureId);
//    if (existingFactureOptional.isEmpty()) {
//        // Gérer l'absence de la facture existante
//        return "error";
//    }
//    Facture facture = existingFactureOptional.get();
//
//    Produit produit = produitRepository.findById(produitId).orElse(null);
//    if (produit == null) {
//        // Gérer l'absence du produit
//        return "error";
//    }
//
//
//    FactureProduit factureProduit = new FactureProduit();
//    factureProduit.setProduit(produit);
//    factureProduit.setQuantite(quantite);
//    factureProduit.setFacture(facture);
//
//    factureProduitRepository.save(factureProduit);
//
//    return "redirect:/factures";
//    // Redirection vers la page des factures après la création
//}

    @PostMapping("/factures-Rajout")
    public String createFacture(
                              @RequestParam(value = "factureId", required = false) Long factureId,
                                @RequestParam("produitId") Long produitId,
                                @RequestParam("quantite") int quantite,
                                Model model) {
        User currentUser = userDetailsServiceImpl.getCurrentUser();
        model.addAttribute("user", currentUser);

        Facture facture;
        if (factureId != null) {
            Optional<Facture> existingFactureOptional = factureRepository.findById(factureId);
            if (existingFactureOptional.isPresent()) {
                facture = existingFactureOptional.get();
            } else {
                // Gérer l'absence de la facture existante
                return "error";
            }
        } else {
            facture = new Facture();
            factureRepository.save(facture); // Sauvegarder la nouvelle facture
        }

        Produit produit = produitRepository.findById(produitId).orElse(null);
        if (produit == null) {
            // Gérer l'absence du produit
            return "error";
        }

        FactureProduit factureProduit = new FactureProduit();
        factureProduit.setProduit(produit);
        factureProduit.setQuantite(quantite);
        factureProduit.setFacture(facture); // Assigner la facture à FactureProduit

        factureProduitRepository.save(factureProduit); // Sauvegarder FactureProduit

        return "redirect:/factures";
        // Redirection vers la page des factures après la création
    }

}
