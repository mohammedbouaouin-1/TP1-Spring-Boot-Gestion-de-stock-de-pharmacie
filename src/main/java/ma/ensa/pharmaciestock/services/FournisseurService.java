package ma.ensa.pharmaciestock.services;

import ma.ensa.pharmaciestock.entities.Fournisseur;
import ma.ensa.pharmaciestock.exception.ResourceNotFoundException;
import ma.ensa.pharmaciestock.repositories.FournisseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public FournisseurService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'id: " + id));
    }

    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    public Fournisseur updateFournisseur(Long id, Fournisseur details) {
        Fournisseur existing = getFournisseurById(id);
        existing.setNom(details.getNom());
        existing.setContact(details.getContact());
        existing.setAdresse(details.getAdresse());
        return fournisseurRepository.save(existing);
    }

    public void deleteFournisseur(Long id) {
        Fournisseur existing = getFournisseurById(id);
        fournisseurRepository.delete(existing);
    }
}
