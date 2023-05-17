package com.example.invoice.repository;

import com.example.invoice.entity.Client;
import com.example.invoice.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture,Long> {
    List<Facture> getFacturesByClientId(Long clientId);

    @Query(value = "SELECT id.nextval FROM dual", nativeQuery = true)
    Long getCurrentSequenceValue();

    @Modifying
    @Query(value = "ALTER SEQUENCE id RESTART WITH :newSequenceValue", nativeQuery = true)
    void updateSequenceValue(@Param("newSequenceValue") Long newSequenceValue);

    Optional<Facture> findById(Optional<Long> factureId);
}
