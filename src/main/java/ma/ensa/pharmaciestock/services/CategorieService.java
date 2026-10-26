package ma.ensa.pharmaciestock.services;

import ma.ensa.pharmaciestock.entities.Categorie;
import ma.ensa.pharmaciestock.exception.ResourceNotFoundException;
import ma.ensa.pharmaciestock.repositories.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'id: " + id));
    }

    public Categorie saveCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie updateCategorie(Long id, Categorie details) {
        Categorie existing = getCategorieById(id);
        existing.setNom(details.getNom());
        existing.setDescription(details.getDescription());
        return categorieRepository.save(existing);
    }

    public void deleteCategorie(Long id) {
        Categorie existing = getCategorieById(id);
        categorieRepository.delete(existing);
    }
}
