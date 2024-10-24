package fr.afpa.orm.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Classe représentant le compte bancaire d'un utilisateur
 * 
 * TODO faire de cette classe une entité
 * Plus d'informations sur les entités ->
 * https://gayerie.dev/epsi-b3-orm/javaee_orm/jpa_entites.html
 * Attention de bien choisir les types en fonction de ceux du script SQL.
 */
@Entity // 1) ANNOTATION DE LA CLASSE: ENTITY
@Table(name = "account") // 2) SI TABLE DE MA BDD N'A PAS LE MEME NOM QUE MA CLASSE
public class Account {
    /**
     * Identifiant unique du compte
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    private Double balance; // FLOAT

    /**
     * TODO ajout d'une association de type @ManyToOne : plusieurs comptes
     * différents peuvent être associés à la même personne
     * 
     * Tutoriel présentant l'utilisation d'une telle association :
     * https://koor.fr/Java/TutorialJEE/jee_jpa_many_to_one.wp
     */
    @ManyToOne // CLEFS ETRANGERE AVEC CONDITIONS //***************************************************************************
    @JoinColumn(name = "id_client", nullable = false)
    private Client owner;

    /*
     * TODO implémenter un constructeur vide --> obligatoire pour l'utilisation d'un
     * ORM
     */
    public Account() {
        // CONSTRUCTEUR VIDE
    }

    /*
     * TODO implémenter les getters et les setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", creationTime=" + creationTime + ", balance=" + balance + ", owner=" + owner
                + "]";
    }

}
