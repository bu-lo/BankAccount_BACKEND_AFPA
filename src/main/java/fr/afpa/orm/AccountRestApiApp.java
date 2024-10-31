package fr.afpa.orm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe principale du projet.
 * TODO ajouter l'annotation @SpringBootApplication à la classe
 * 
 * Cette annotation est FONDAMENTALE pour activer la recherche automatique des beans dans les différents packages de l'
 * 
 * Documentation -> https://medium.com/@boris.alexandre.rose/springbootapplication-ab08032a7049
 * 
 */
@EntityScan
@SpringBootApplication
public class AccountRestApiApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(AccountRestApiApp.class, args);
    }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**") // Autorise toutes les routes
    //             .allowedOrigins("*") // Remplacez par l'URL de votre frontend
    //             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Méthodes autorisées
    //             .allowedHeaders("*"); // Autorise tous les headers
    // }
}
//LANCE L'APPLICATION //APP.JAVA
