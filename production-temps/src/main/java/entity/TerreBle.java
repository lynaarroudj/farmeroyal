package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class TerreBle {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String typeProduction; // Type de production (exemple : "blé", "salade", etc.)
    private int tempsProduction; // Temps calculé automatiquement
    private LocalDateTime productionStartTime;

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

    public String getTypeProduction() {
        return typeProduction;
    }

    public void setTypeProduction(String typeProduction) {
        this.typeProduction = typeProduction;
    }

    public int getTempsProduction() {
        return tempsProduction;
    }

    public void setTempsProduction(int tempsProduction) {
        this.tempsProduction = tempsProduction;
    }

    public LocalDateTime getProductionStartTime() {
        return productionStartTime;
    }

    public void setProductionStartTime(LocalDateTime productionStartTime) {
        this.productionStartTime = productionStartTime;
    }

    public int getTempsRestant() {
        if (productionStartTime == null) {
            return tempsProduction; // Temps total si jamais lancé
        }
        int minutesElapsed = (int) java.time.Duration.between(productionStartTime, LocalDateTime.now()).toMinutes();
        return Math.max(0, tempsProduction - minutesElapsed);
    }
}
