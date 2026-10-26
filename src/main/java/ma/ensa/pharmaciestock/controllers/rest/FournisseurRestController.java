package ma.ensa.pharmaciestock.controllers.rest;

import ma.ensa.pharmaciestock.entities.Fournisseur;
import ma.ensa.pharmaciestock.services.FournisseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/fournisseurs", "/fournisseurs"})
@CrossOrigin(origins = "*")
public class FournisseurRestController {

    private final FournisseurService fournisseurService;

    public FournisseurRestController(FournisseurService fournisseurService) {
        this.fournisseurService = fournisseurService;
    }

    /**
     * GET /api/fournisseurs -> lister tous les fournisseurs
     * Status HTTP: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs() {
        return ResponseEntity.ok(fournisseurService.getAllFournisseurs());
    }

    /**
     * GET /api/fournisseurs/{id} -> afficher un fournisseur par ID
     * Status HTTP: 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> getFournisseurById(@PathVariable Long id) {
        return ResponseEntity.ok(fournisseurService.getFournisseurById(id));
    }

    /**
     * POST /api/fournisseurs -> ajouter un fournisseur
     * Status HTTP: 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Fournisseur> createFournisseur(@RequestBody Fournisseur fournisseur) {
        Fournisseur saved = fournisseurService.saveFournisseur(fournisseur);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * PUT /api/fournisseurs/{id} -> modifier un fournisseur
     * Status HTTP: 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        Fournisseur updated = fournisseurService.updateFournisseur(id, fournisseur);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/fournisseurs/{id} -> supprimer un fournisseur
     * Status HTTP: 204 NO CONTENT
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.noContent().build();
    }
}
