package fr.afpa.orm.entities;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="insurance")
public class Insurance {
    
    //ATTRIBUTS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public enum Name{
        HOME_INS,
        HEALTH_INS,
        LIFE_INS,
        SCHOOL_INS,
        PERSONAL_CIVIL_LIABILITY,
        PROFESSIONAL_CIVIL_LIABILITY
    }

    @Enumerated(EnumType.STRING)  //ENUM ****************
    @Column(name = "insurance_type")
    private Name name;

    //ASSOCIATION MANY TO MANY
    @ManyToMany
    @JoinTable(name ="subscribes",
        joinColumns = @JoinColumn(name = "id_insurance"),
        inverseJoinColumns = @JoinColumn(name="id_client"))
    private Set<Client>clients;


    //CONSTRUCTEUR VIDE
    public Insurance(){
    }


    //GETTERS & SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }


    @Override
    public String toString() {
        return "Insurance [id=" + id + ", name=" + name + ", clients=" + clients + "]";
    }

}
