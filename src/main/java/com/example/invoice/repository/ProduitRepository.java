package com.example.invoice.repository;

import com.example.invoice.entity.Produit;
import com.example.invoice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
