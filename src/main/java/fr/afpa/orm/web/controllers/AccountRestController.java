package fr.afpa.orm.web.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.AccountDto;
// import fr.afpa.orm.dto.AccountDto;
import fr.afpa.orm.entities.Account;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.repositories.AccountRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO ajouter la/les annotations nécessaires pour faire de "AccountRestController" un contrôleur de REST API
 */
@RestController // DECLARATION DE LA CLASSE RESTCONTROLLER
@RequestMapping("/api/accounts") //NOM ENDPOINTS
public class AccountRestController {

    private final AccountRepository accountRepository;
    /** 
     * TODO implémenter un constructeur
     *  
     * TODO injecter {@link AccountRepository} en dépendance par injection via le constructeur
     * Plus d'informations -> https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */

    @Autowired
    public AccountRestController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    };

    /**
     * TODO implémenter une méthode qui traite les requêtes GET et qui renvoie une liste de comptes
     *
     * Attention, il manque peut être une annotation :)
     */
    @GetMapping //APPEL CHEMIN
    public List<AccountDto> getAll() {
        // TODO récupération des compte provenant d'un repository
        // Iterable<Account> accounts = accountRepository.findAll();
        // TODO renvoyer les objets de la classe "Account"

        Iterable<Account> accounts = accountRepository.findAll();

        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            Client owner = account.getOwner();
            String fullName = owner.getFirstName() + " " + owner.getLastName();

            AccountDto accountDto = new AccountDto(account.getId(),
                                        account.getCreationTime(),
                                        account.getBalance(),
                                        fullName);

            accountDtos.add(accountDto);
        }
        return accountDtos;
    }

    // TRANSFORMER UNE LISTE EN ITERABLE - LIST // PASSER D'UN TYPE A L'AUTRE
    // TRANSFORMER DES COLLECTIONS
    // List<Account> accounts = new ArrayList<Account>();
    // Iterable<Account> accountIterable = repo.findAll();   
    // accountIterable.forEach(accounts::add);

    // import java.util.ArrayList; 
 

    /**
     * TODO implémenter une méthode qui traite les requêtes GET avec un identifiant "variable de chemin" et qui retourne les informations du compte associé
     * Plus d'informations sur les variables de chemin -> https://www.baeldung.com/spring-pathvariable
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getOne(@PathVariable long id) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()){
            Account account = accountOptional.get();

            Client owner = account.getOwner();
            String fullName = owner.getFirstName() + " " + owner.getLastName();

            AccountDto accountDto = new AccountDto(account.getId(),
                                                    account.getCreationTime(),
                                                    account.getBalance(),
                                                    fullName); 

            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes POST
     * Cette méthode doit recevoir les informations d'un compte en tant que "request body", elle doit sauvegarder le compte en mémoire et retourner ses informations (en json)
     * Tutoriel intéressant -> https://stackabuse.com/get-http-post-body-in-spring/
     * Le serveur devrai retourner un code http de succès (201 Created)
     **/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody Account account) {
        if (account.getCreationTime() == null) {
        account.setCreationTime(LocalDateTime.now());
    }
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes PUT
     * 
     * Attention de bien ajouter les annotations qui conviennent
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable long id, @RequestBody Account updatedAccount) {
        Account accountToPut = accountRepository.findById(id).get();
        // accountToPut.setFirstName(updatedAccount.getFirstName());
        // accountToPut.setLastName(updatedAccount.getLastName());
        // accountToPut.setEmail(updatedAccount.getEmail());
        // accountToPut.setBirthday(updatedAccount.getBirthday());
        accountToPut.setBalance(updatedAccount.getBalance());

        if (updatedAccount.getOwner() != null){    
            accountToPut.setOwner(updatedAccount.getOwner());
        }
    
        accountRepository.save(accountToPut);
        return new ResponseEntity<>(accountToPut, HttpStatus.OK);
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes  DELETE 
     * L'identifiant du compte devra être passé en "variable de chemin" (ou "path variable")
     * Dans le cas d'une suppression effectuée avec succès, le serveur doit retourner un status http 204 (No content)
     * 
     * Il est possible de modifier la réponse du serveur en utilisant la méthode "setStatus" de la classe HttpServletResponse pour configurer le message de réponse du serveur
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable long id, HttpServletResponse response) {
        Optional<Account> accountToDelete = accountRepository.findById(id);
        accountRepository.delete(accountToDelete.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
