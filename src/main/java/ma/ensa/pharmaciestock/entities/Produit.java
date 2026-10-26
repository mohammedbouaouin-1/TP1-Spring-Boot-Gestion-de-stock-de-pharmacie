package ma.ensa.pharmaciestock.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 1000)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal prix;

    @Column(name = "code_barre")
    private String codeBarre;

    @Column(name = "date_expiration")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateExpiration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id")
    @JsonIgnoreProperties("produits")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fournisseur_id")
    @JsonIgnoreProperties("produits")
    private Fournisseur fournisseur;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "detail_id")
    @JsonIgnoreProperties("produit")
    private DetailProduit detailProduit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "produit_tag",
        joinColumns = @JoinColumn(name = "produit_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonIgnoreProperties("produits")
    private List<Tag> tags = new ArrayList<>();

    public Produit() {
    }

    public Produit(Long id) {
        this.id = id;
    }

    public Produit(String nom, String description, BigDecimal prix, String codeBarre, LocalDate dateExpiration) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.codeBarre = codeBarre;
        this.dateExpiration = dateExpiration;
    }

    public Produit(Long id, String nom, String description, BigDecimal prix, String codeBarre, LocalDate dateExpiration,
                   Categorie categorie, Fournisseur fournisseur, DetailProduit detailProduit, List<Tag> tags) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.codeBarre = codeBarre;
        this.dateExpiration = dateExpiration;
        this.categorie = categorie;
        this.fournisseur = fournisseur;
        this.detailProduit = detailProduit;
        if (tags != null) {
            this.tags = tags;
        }
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

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public DetailProduit getDetailProduit() {
        return detailProduit;
    }

    public void setDetailProduit(DetailProduit detailProduit) {
        this.detailProduit = detailProduit;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        if (!this.tags.contains(tag)) {
            this.tags.add(tag);
        }
    }
}
