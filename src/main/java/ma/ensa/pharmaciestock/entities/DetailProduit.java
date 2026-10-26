package ma.ensa.pharmaciestock.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "detail_produit")
public class DetailProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fabricant;

    @Column(name = "pays_origine")
    private String paysOrigine;

    @Column(length = 500)
    private String composition;

    private String dosage;

    @Column(length = 500)
    private String indications;

    @OneToOne(mappedBy = "detailProduit")
    @JsonIgnoreProperties("detailProduit")
    private Produit produit;

    public DetailProduit() {
    }

    public DetailProduit(Long id) {
        this.id = id;
    }

    public DetailProduit(String fabricant, String paysOrigine, String composition, String dosage, String indications) {
        this.fabricant = fabricant;
        this.paysOrigine = paysOrigine;
        this.composition = composition;
        this.dosage = dosage;
        this.indications = indications;
    }

    public DetailProduit(Long id, String fabricant, String paysOrigine, String composition, String dosage, String indications) {
        this.id = id;
        this.fabricant = fabricant;
        this.paysOrigine = paysOrigine;
        this.composition = composition;
        this.dosage = dosage;
        this.indications = indications;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFabricant() {
        return fabricant;
    }

    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }

    public String getPaysOrigine() {
        return paysOrigine;
    }

    public void setPaysOrigine(String paysOrigine) {
        this.paysOrigine = paysOrigine;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
}
