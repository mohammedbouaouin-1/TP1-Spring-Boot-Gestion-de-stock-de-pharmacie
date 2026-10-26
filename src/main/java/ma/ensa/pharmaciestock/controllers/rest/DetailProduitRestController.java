package ma.ensa.pharmaciestock.controllers.rest;

import ma.ensa.pharmaciestock.entities.DetailProduit;
import ma.ensa.pharmaciestock.exception.ResourceNotFoundException;
import ma.ensa.pharmaciestock.repositories.DetailProduitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/details", "/details"})
@CrossOrigin(origins = "*")
public class DetailProduitRestController {

    private final DetailProduitRepository detailProduitRepository;

    public DetailProduitRestController(DetailProduitRepository detailProduitRepository) {
        this.detailProduitRepository = detailProduitRepository;
    }

    @GetMapping
    public ResponseEntity<List<DetailProduit>> getAllDetails() {
        return ResponseEntity.ok(detailProduitRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailProduit> getDetailById(@PathVariable Long id) {
        DetailProduit detail = detailProduitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DetailProduit non trouvé avec l'id: " + id));
        return ResponseEntity.ok(detail);
    }
}
