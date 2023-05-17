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
@RequestMapping("/factures")
public class FactureController {

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

    @GetMapping
    public String displayFactures(Model model) {
        User currentUser = userDetailsServiceImpl.getCurrentUser();
        List<Produit> produits = produitRepository.findAll();
        model.addAttribute("produits", produits);
        model.addAttribute("user", currentUser);

        if (currentUser != null) {
            Long userId = currentUser.getId();
            List<Client> clients = clientRepository.findByUserId(userId);
            List<Facture> facturesList = new ArrayList<>();
            model.addAttribute("clients", clients);

            for (Client client : clients) {
                List<Facture> factures = factureRepository.getFacturesByClientId(client.getId());

                for (Facture facture : factures) {
                    List<FactureProduit> factureProduits = factureProduitRepository.getFactureProduitsByFactureId(facture.getId());
                    facture.setFactureProduits(factureProduits);
                }

                facturesList.addAll(factures);
            }

            model.addAttribute("facturesList", facturesList);
            return "factures";
        } else {
            return "errorPage";
        }
    }


    @PostMapping
    public String createFacture(@RequestParam("dateCreation") LocalDate dateCreation,
                                @RequestParam("moyenPaiement") String moyenPaiement,
                                @RequestParam("clientId") Long clientId,
                                @RequestParam("produitId") Long produitId,
                                @RequestParam("quantite") int quantite,
                                @RequestParam(value = "factureId", required = false) Long factureId,
                                Model model) {
        User currentUser = userDetailsServiceImpl.getCurrentUser();
        model.addAttribute("user", currentUser);
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isEmpty()) {
            // Gérer l'absence du client
            return "error";
        }
        Client client = clientOptional.get();

        Produit produit = produitRepository.findById(produitId).orElse(null);
        if (produit == null) {
            // Gérer l'absence du produit
            return "error";
        }

        Facture facture;
        if (factureId != null) {
            Optional<Facture> existingFactureOptional = factureRepository.findById(factureId);
            if (existingFactureOptional.isPresent()) {
                facture = existingFactureOptional.get();
            } else {

                return "error";
            }
        } else {
            facture = new Facture();
        }

        facture.setDateCreation(dateCreation);
        facture.setMoyenPaiement(moyenPaiement);
        facture.setClient(client);
        factureRepository.save(facture); // Sauvegarde de la facture

        FactureProduit factureProduit = new FactureProduit();
        factureProduit.setProduit(produit);
        factureProduit.setQuantite(quantite);
        factureProduit.setFacture(facture);

        factureProduitRepository.save(factureProduit);

        return "redirect:/factures";
        // Redirection vers la page des factures après la création
    }
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

//    @PostMapping
//    public String createFacture(
//                              @RequestParam(value = "factureId", required = false) Long factureId,
//                                @RequestParam("produitId") Long produitId,
//                                @RequestParam("quantite") int quantite,
//                                Model model) {
//        User currentUser = userDetailsServiceImpl.getCurrentUser();
//        model.addAttribute("user", currentUser);
//
//        Facture facture;
//        if (factureId != null) {
//            Optional<Facture> existingFactureOptional = factureRepository.findById(factureId);
//            if (existingFactureOptional.isPresent()) {
//                facture = existingFactureOptional.get();
//            } else {
//                // Gérer l'absence de la facture existante
//                return "error";
//            }
//        } else {
//            facture = new Facture();
//            factureRepository.save(facture); // Sauvegarder la nouvelle facture
//        }
//
//        Produit produit = produitRepository.findById(produitId).orElse(null);
//        if (produit == null) {
//            // Gérer l'absence du produit
//            return "error";
//        }
//
//        FactureProduit factureProduit = new FactureProduit();
//        factureProduit.setProduit(produit);
//        factureProduit.setQuantite(quantite);
//        factureProduit.setFacture(facture); // Assigner la facture à FactureProduit
//
//        factureProduitRepository.save(factureProduit); // Sauvegarder FactureProduit
//
//        return "redirect:/factures";
//        // Redirection vers la page des factures après la création
//    }
//

}