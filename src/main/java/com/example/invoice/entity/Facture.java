package com.example.invoice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateCreation;

    @Column(nullable = false)
    private LocalDate dateEcheance;
    private String moyenPaiement;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;


    @OneToMany(mappedBy = "facture")
    private List<FactureProduit> factureProduits = new ArrayList<>();

    @PrePersist
    private void onCreate() {
        // Calculer la date d'échéance comme un mois après la date de création
            dateEcheance = dateCreation.plusMonths(1);

    }

    public List<FactureProduit> getFactureProduits() {
        return factureProduits;
    }

    public void setFactureProduits(List<FactureProduit> factureProduits) {
        this.factureProduits = factureProduits;
    }

}
