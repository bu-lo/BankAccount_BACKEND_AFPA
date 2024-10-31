package fr.afpa.orm.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.afpa.orm.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE YEAR(u.createdAt) > 1990")
    List<User> findAllUserCreatedAfterTheNineties();
    
}
