package ma.ensa.pharmaciestock.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fournisseur")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String contact;

    private String adresse;

    @OneToMany(mappedBy = "fournisseur", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("fournisseur")
    private List<Produit> produits = new ArrayList<>();

    public Fournisseur() {
    }

    public Fournisseur(Long id) {
        this.id = id;
    }

    public Fournisseur(String nom, String contact, String adresse) {
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
    }

    public Fournisseur(Long id, String nom, String contact, String adresse) {
        this.id = id;
        this.nom = nom;
        this.contact = contact;
        this.adresse = adresse;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
