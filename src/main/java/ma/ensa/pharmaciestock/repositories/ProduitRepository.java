package ma.ensa.pharmaciestock.repositories;

import ma.ensa.pharmaciestock.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long categorieId);
    List<Produit> findByFournisseurId(Long fournisseurId);
    List<Produit> findByNomContainingIgnoreCase(String nom);
}
