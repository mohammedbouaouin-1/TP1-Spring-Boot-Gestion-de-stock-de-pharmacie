package ma.ensa.pharmaciestock.controllers.web;

import ma.ensa.pharmaciestock.entities.DetailProduit;
import ma.ensa.pharmaciestock.entities.Produit;
import ma.ensa.pharmaciestock.services.CategorieService;
import ma.ensa.pharmaciestock.services.FournisseurService;
import ma.ensa.pharmaciestock.services.ProduitService;
import ma.ensa.pharmaciestock.services.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping({"/", "/produits"})
public class ProduitWebController {

    private final ProduitService produitService;
    private final CategorieService categorieService;
    private final FournisseurService fournisseurService;
    private final TagService tagService;

    public ProduitWebController(ProduitService produitService,
                                CategorieService categorieService,
                                FournisseurService fournisseurService,
                                TagService tagService) {
        this.produitService = produitService;
        this.categorieService = categorieService;
        this.fournisseurService = fournisseurService;
        this.tagService = tagService;
    }

    /**
     * Page d'accueil / Liste des produits Thymeleaf
     */
    @GetMapping({"", "produits"})
    public String listeProduits(@RequestParam(required = false) Long categorieId,
                                @RequestParam(required = false) String search,
                                Model model) {
        List<Produit> produits;
        if (search != null && !search.trim().isEmpty()) {
            produits = produitService.searchProduits(search);
        } else if (categorieId != null) {
            produits = produitService.getProduitsByCategorie(categorieId);
        } else {
            produits = produitService.getAllProduits();
        }

        model.addAttribute("produits", produits);
        model.addAttribute("categories", categorieService.getAllCategories());
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("selectedCategorie", categorieId);
        model.addAttribute("searchTerm", search);
        return "produits";
    }

    /**
     * Formulaire d'ajout d'un produit
     */
    @GetMapping("produits/nouveau")
    public String formNouveauProduit(Model model) {
        Produit produit = new Produit();
        produit.setDetailProduit(new DetailProduit());

        model.addAttribute("produit", produit);
        model.addAttribute("categories", categorieService.getAllCategories());
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("isEdit", false);
        return "produit-form";
    }

    /**
     * Formulaire d'édition d'un produit
     */
    @GetMapping("produits/modifier/{id}")
    public String formModifierProduit(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        if (produit.getDetailProduit() == null) {
            produit.setDetailProduit(new DetailProduit());
        }

        model.addAttribute("produit", produit);
        model.addAttribute("categories", categorieService.getAllCategories());
        model.addAttribute("fournisseurs", fournisseurService.getAllFournisseurs());
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("isEdit", true);
        return "produit-form";
    }

    /**
     * Traitement de l'enregistrement (Ajout / Modification)
     */
    @PostMapping("produits/save")
    public String saveProduit(@ModelAttribute("produit") Produit produit,
                              @RequestParam(required = false) List<Long> tagIds,
                              RedirectAttributes redirectAttributes) {
        if (tagIds != null && !tagIds.isEmpty()) {
            produit.setTags(tagIds.stream().map(tagService::getTagById).toList());
        }

        if (produit.getId() != null) {
            produitService.updateProduit(produit.getId(), produit);
            redirectAttributes.addFlashAttribute("successMessage", "Produit mis à jour avec succès !");
        } else {
            produitService.saveProduit(produit);
            redirectAttributes.addFlashAttribute("successMessage", "Nouveau produit ajouté avec succès !");
        }

        return "redirect:/produits";
    }

    /**
     * Affichage du détail complet d'un produit
     */
    @GetMapping("produits/{id}")
    public String detailsProduit(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        model.addAttribute("produit", produit);
        return "produit-detail";
    }

    /**
     * Suppression d'un produit
     */
    @GetMapping("produits/supprimer/{id}")
    public String supprimerProduit(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produitService.deleteProduit(id);
        redirectAttributes.addFlashAttribute("successMessage", "Produit supprimé avec succès !");
        return "redirect:/produits";
    }
}
