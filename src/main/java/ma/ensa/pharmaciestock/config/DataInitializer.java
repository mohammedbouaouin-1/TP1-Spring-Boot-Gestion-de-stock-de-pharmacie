package ma.ensa.pharmaciestock.config;

import ma.ensa.pharmaciestock.entities.*;
import ma.ensa.pharmaciestock.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategorieRepository categorieRepository;
    private final FournisseurRepository fournisseurRepository;
    private final TagRepository tagRepository;
    private final ProduitRepository produitRepository;

    public DataInitializer(CategorieRepository categorieRepository,
                           FournisseurRepository fournisseurRepository,
                           TagRepository tagRepository,
                           ProduitRepository produitRepository) {
        this.categorieRepository = categorieRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.tagRepository = tagRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public void run(String... args) {
        if (produitRepository.count() > 0) {
            return;
        }

        System.out.println("Initialisation du jeu de donnees de la pharmacie...");

        // 1. Categories
        Categorie c1 = categorieRepository.save(new Categorie("Antibiotiques", "Médicaments utilisés pour traiter les infections bactériennes."));
        Categorie c2 = categorieRepository.save(new Categorie("Antalgiques", "Médicaments pour soulager la douleur."));
        Categorie c3 = categorieRepository.save(new Categorie("Vitamines", "Compléments nutritionnels et fortifiants."));
        Categorie c4 = categorieRepository.save(new Categorie("Antiseptiques", "Produits désinfectants pour usage externe."));

        // 2. Fournisseurs
        Fournisseur f1 = fournisseurRepository.save(new Fournisseur("PharmaMaroc", "contact@pharmamaroc.ma", "Casablanca, Maroc"));
        Fournisseur f2 = fournisseurRepository.save(new Fournisseur("Medisup France", "info@medisup.fr", "Paris, France"));
        Fournisseur f3 = fournisseurRepository.save(new Fournisseur("Sandoz", "export@sandoz.com", "Bâle, Suisse"));
        Fournisseur f4 = fournisseurRepository.save(new Fournisseur("Cooper Pharma", "support@cooperpharma.ma", "Rabat, Maroc"));

        // 3. Tags
        Tag t1 = tagRepository.save(new Tag("Sans ordonnance"));
        Tag t2 = tagRepository.save(new Tag("Prescription obligatoire"));
        Tag t3 = tagRepository.save(new Tag("En promotion"));
        Tag t4 = tagRepository.save(new Tag("Stock limité"));
        Tag t5 = tagRepository.save(new Tag("Importé"));

        // 4. Details & Produits
        // Produit 1 : Amoxicilline 500mg
        DetailProduit d1 = new DetailProduit("Sanofi", "France", "Amoxicilline trihydratée 500 mg", "500 mg", "Infections ORL et respiratoires");
        Produit p1 = new Produit(null, "Amoxicilline 500mg", "Antibiotique à large spectre.",
                new BigDecimal("45.00"), "AMX500-2025", LocalDate.of(2026, 3, 10),
                c1, f1, d1, List.of(t2));
        produitRepository.save(p1);

        // Produit 2 : Doliprane 500mg
        DetailProduit d2 = new DetailProduit("Bayer", "Allemagne", "Paracétamol 500 mg", "500 mg", "Douleurs légères à modérées, fièvre");
        Produit p2 = new Produit(null, "Doliprane 500mg", "Antalgique et antipyrétique.",
                new BigDecimal("25.50"), "DLP500-2025", LocalDate.of(2025, 11, 1),
                c2, f2, d2, List.of(t1, t3));
        produitRepository.save(p2);

        // Produit 3 : Vitamine C 1000mg
        DetailProduit d3 = new DetailProduit("Pfizer", "États-Unis", "Vitamine C 1000 mg", "1000 mg", "Renforcement immunitaire");
        Produit p3 = new Produit(null, "Vitamine C 1000mg", "Complément pour renforcer les défenses.",
                new BigDecimal("30.00"), "VITC1000-2025", LocalDate.of(2026, 6, 15),
                c3, f3, d3, List.of(t1, t5));
        produitRepository.save(p3);

        // Produit 4 : Bétadine Solution 10%
        DetailProduit d4 = new DetailProduit("Johnson & Johnson", "Maroc", "Povidone iodée 10%", "10%", "Désinfection des plaies");
        Produit p4 = new Produit(null, "Bétadine Solution 10%", "Désinfectant pour usage externe.",
                new BigDecimal("18.00"), "BETADINE10-2025", LocalDate.of(2027, 1, 30),
                c4, f4, d4, List.of(t1, t4));
        produitRepository.save(p4);

        // Produit 5 : Nurofen 400mg
        DetailProduit d5 = new DetailProduit("GSK", "Royaume-Uni", "Ibuprofène 400 mg", "400 mg", "Inflammations et douleurs musculaires");
        Produit p5 = new Produit(null, "Nurofen 400mg", "Soulage la douleur et la fièvre.",
                new BigDecimal("42.00"), "NURO400-2025", LocalDate.of(2026, 2, 20),
                c2, f3, d5, List.of(t1, t5));
        produitRepository.save(p5);

        System.out.println("Donnees initialisees avec succes.");
    }
}
