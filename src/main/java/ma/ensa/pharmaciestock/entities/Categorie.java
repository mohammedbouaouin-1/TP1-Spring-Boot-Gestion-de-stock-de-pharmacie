package ma.ensa.pharmaciestock.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "categorie", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("categorie")
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(Long id) {
        this.id = id;
    }

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Categorie(Long id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
