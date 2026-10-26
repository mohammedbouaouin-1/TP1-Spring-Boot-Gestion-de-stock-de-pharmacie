package ma.ensa.pharmaciestock.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    private List<Produit> produits = new ArrayList<>();

    public Tag() {
    }

    public Tag(Long id) {
        this.id = id;
    }

    public Tag(String libelle) {
        this.libelle = libelle;
    }

    public Tag(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
