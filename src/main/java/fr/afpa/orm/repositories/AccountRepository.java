package fr.afpa.orm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.afpa.orm.entities.Account;

/**
 * TODO implémenter un "repository" (similaire à un DAO) permettant d'interagir avec les données de la BDD
 * Tutoriel -> https://www.geeksforgeeks.org/spring-boot-crudrepository-with-example/
 */

// Déclaration du repository, récupération des méthodes CRUD grâce à l'interface CrudRepository
@Repository
public interface AccountRepository extends CrudRepository<Account,Long> { //DAO


}

// TYPE DE LA CLEF PRIMAIRE, ICI LONG // ID