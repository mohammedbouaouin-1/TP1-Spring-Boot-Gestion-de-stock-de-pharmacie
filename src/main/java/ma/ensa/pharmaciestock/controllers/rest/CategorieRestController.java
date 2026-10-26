package ma.ensa.pharmaciestock.controllers.rest;

import ma.ensa.pharmaciestock.entities.Categorie;
import ma.ensa.pharmaciestock.services.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/categories", "/categories"})
@CrossOrigin(origins = "*")
public class CategorieRestController {

    private final CategorieService categorieService;

    public CategorieRestController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * GET /api/categories -> lister toutes les catégories
     * Status HTTP: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        return ResponseEntity.ok(categorieService.getAllCategories());
    }

    /**
     * GET /api/categories/{id} -> afficher une catégorie par son ID
     * Status HTTP: 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.getCategorieById(id));
    }

    /**
     * POST /api/categories -> ajouter une catégorie
     * Status HTTP: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        Categorie saved = categorieService.saveCategorie(categorie);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * PUT /api/categories/{id} -> modifier une catégorie
     * Status HTTP: 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        Categorie updated = categorieService.updateCategorie(id, categorie);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/categories/{id} -> supprimer une catégorie
     * Status HTTP: 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}
