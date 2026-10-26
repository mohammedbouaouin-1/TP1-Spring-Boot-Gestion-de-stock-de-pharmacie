package ma.ensa.pharmaciestock.services;

import ma.ensa.pharmaciestock.entities.*;
import ma.ensa.pharmaciestock.exception.ResourceNotFoundException;
import ma.ensa.pharmaciestock.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final FournisseurRepository fournisseurRepository;
    private final DetailProduitRepository detailProduitRepository;
    private final TagRepository tagRepository;

    public ProduitService(ProduitRepository produitRepository,
                          CategorieRepository categorieRepository,
                          FournisseurRepository fournisseurRepository,
                          DetailProduitRepository detailProduitRepository,
                          TagRepository tagRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.detailProduitRepository = detailProduitRepository;
        this.tagRepository = tagRepository;
    }

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));
    }

    public List<Produit> getProduitsByCategorie(Long categorieId) {
        return produitRepository.findByCategorieId(categorieId);
    }

    public List<Produit> searchProduits(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProduits();
        }
        return produitRepository.findByNomContainingIgnoreCase(keyword);
    }

    public Produit saveProduit(Produit produit) {
        resolveRelations(produit);
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit existing = getProduitById(id);

        existing.setNom(produitDetails.getNom());
        existing.setDescription(produitDetails.getDescription());
        existing.setPrix(produitDetails.getPrix());
        existing.setCodeBarre(produitDetails.getCodeBarre());
        existing.setDateExpiration(produitDetails.getDateExpiration());

        // Category
        if (produitDetails.getCategorie() != null && produitDetails.getCategorie().getId() != null) {
            Categorie cat = categorieRepository.findById(produitDetails.getCategorie().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'id: " + produitDetails.getCategorie().getId()));
            existing.setCategorie(cat);
        } else if (produitDetails.getCategorie() == null) {
            existing.setCategorie(null);
        }

        // Fournisseur
        if (produitDetails.getFournisseur() != null && produitDetails.getFournisseur().getId() != null) {
            Fournisseur four = fournisseurRepository.findById(produitDetails.getFournisseur().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'id: " + produitDetails.getFournisseur().getId()));
            existing.setFournisseur(four);
        } else if (produitDetails.getFournisseur() == null) {
            existing.setFournisseur(null);
        }

        // DetailProduit
        if (produitDetails.getDetailProduit() != null) {
            DetailProduit newDetail = produitDetails.getDetailProduit();
            if (existing.getDetailProduit() != null) {
                DetailProduit cur = existing.getDetailProduit();
                cur.setFabricant(newDetail.getFabricant());
                cur.setPaysOrigine(newDetail.getPaysOrigine());
                cur.setComposition(newDetail.getComposition());
                cur.setDosage(newDetail.getDosage());
                cur.setIndications(newDetail.getIndications());
            } else {
                existing.setDetailProduit(newDetail);
            }
        }

        // Tags
        if (produitDetails.getTags() != null) {
            List<Tag> resolvedTags = new ArrayList<>();
            for (Tag t : produitDetails.getTags()) {
                if (t.getId() != null) {
                    Tag found = tagRepository.findById(t.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Tag non trouvé avec l'id: " + t.getId()));
                    resolvedTags.add(found);
                } else if (t.getLibelle() != null) {
                    Tag byLib = tagRepository.findByLibelle(t.getLibelle())
                            .orElseGet(() -> tagRepository.save(new Tag(t.getLibelle())));
                    resolvedTags.add(byLib);
                }
            }
            existing.setTags(resolvedTags);
        }

        return produitRepository.save(existing);
    }

    public void deleteProduit(Long id) {
        Produit produit = getProduitById(id);
        produitRepository.delete(produit);
    }

    private void resolveRelations(Produit produit) {
        // Categorie
        if (produit.getCategorie() != null && produit.getCategorie().getId() != null) {
            Categorie cat = categorieRepository.findById(produit.getCategorie().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable avec l'id: " + produit.getCategorie().getId()));
            produit.setCategorie(cat);
        }

        // Fournisseur
        if (produit.getFournisseur() != null && produit.getFournisseur().getId() != null) {
            Fournisseur four = fournisseurRepository.findById(produit.getFournisseur().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Fournisseur introuvable avec l'id: " + produit.getFournisseur().getId()));
            produit.setFournisseur(four);
        }

        // Tags
        if (produit.getTags() != null && !produit.getTags().isEmpty()) {
            List<Tag> resolvedTags = new ArrayList<>();
            for (Tag t : produit.getTags()) {
                if (t.getId() != null) {
                    Tag found = tagRepository.findById(t.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Tag introuvable avec l'id: " + t.getId()));
                    resolvedTags.add(found);
                } else if (t.getLibelle() != null) {
                    Tag byLib = tagRepository.findByLibelle(t.getLibelle())
                            .orElseGet(() -> tagRepository.save(new Tag(t.getLibelle())));
                    resolvedTags.add(byLib);
                }
            }
            produit.setTags(resolvedTags);
        }
    }
}
