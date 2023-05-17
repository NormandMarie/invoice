package com.example.invoice.repository;

import com.example.invoice.entity.Client;
import com.example.invoice.entity.Facture;
import com.example.invoice.entity.FactureProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureProduitRepository extends JpaRepository <FactureProduit,Long> {

    List<FactureProduit> getFactureProduitsByFactureId(Long factureId);
}
