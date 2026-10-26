package ma.ensa.pharmaciestock.controllers.rest;

import ma.ensa.pharmaciestock.entities.Produit;
import ma.ensa.pharmaciestock.services.ProduitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
public class ProduitRestController {

    private final ProduitService produitService;

    public ProduitRestController(ProduitService produitService) {
        this.produitService = produitService;
    }

    /**
     * GET /api/produits -> lister tous les produits ou filtrer par categorie
     * Status HTTP: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits(@RequestParam(required = false) Long categorieId) {
        if (categorieId != null) {
            return ResponseEntity.ok(produitService.getProduitsByCategorie(categorieId));
        }
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    /**
     * GET /api/produits/{id} -> afficher un produit par son ID
     * Status HTTP: 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        return ResponseEntity.ok(produit);
    }

    /**
     * POST /api/produits -> ajouter un nouveau produit
     * Status HTTP: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit saved = produitService.saveProduit(produit);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * PUT /api/produits/{id} -> modifier un produit existant
     * Status HTTP: 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit updated = produitService.updateProduit(id, produit);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/produits/{id} -> supprimer un produit
     * Status HTTP: 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
