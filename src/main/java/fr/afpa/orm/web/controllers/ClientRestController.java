package fr.afpa.orm.web.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

import fr.afpa.orm.dto.ClientDto;
import fr.afpa.orm.entities.Account;
// import fr.afpa.orm.dto.ClientDto;
import fr.afpa.orm.entities.Client;
import fr.afpa.orm.entities.Insurance;
import fr.afpa.orm.repositories.ClientRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * TODO ajouter la/les annotations nécessaires pour faire de "ClientRestController" un contrôleur de REST API
 */
@RestController // DECLARATION DE LA CLASSE RESTCONTROLLER
@RequestMapping("/api/clients") //NOM ENDPOINTS
public class ClientRestController {

    private final ClientRepository clientRepository;
    /** 
     * TODO implémenter un constructeur
     *  
     * TODO injecter {@link ClientRepository} en dépendance par injection via le constructeur
     * Plus d'informations -> https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */

    @Autowired //JUST 1 CONTRUCTEUR
    public ClientRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    };

    /**
     * TODO implémenter une méthode qui traite les requêtes GET et qui renvoie une liste de comptes
     *
     * Attention, il manque peut être une annotation :)
     */
    @GetMapping //APPEL CHEMIN
    public Iterable<ClientDto> getAll() {
        // TODO récupération des compte provenant d'un repository
        // Iterable<Client> clients = clientRepository.findAll();
        // TODO renvoyer les objets de la classe "Client"
        // return clientRepository.findAll();
    
        Iterable<Client>clients = clientRepository.findAll();
        List<ClientDto>clientDtos = new ArrayList<>();

        for (Client client : clients) {
            List<Account>accounts = client.getAccounts();
            List<Insurance>insurances= client.getInsurances();
            List<Long>account_ids= accounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());
            String insuranceType = insurances.stream()
                .map(insurance -> insurance.getName().name())
                .collect(Collectors.joining(", "));

            ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getBirthdate(), account_ids, insuranceType);
            clientDtos.add(clientDto);
        }

        return clientDtos;
    }

    // TRANSFORMER UNE LISTE EN ITERABLE - LIST // PASSER D'UN TYPE A L'AUTRE
    // TRANSFORMER DES COLLECTIONS
    // List<Client> clients = new ArrayList<Client>();
    // Iterable<Client> clientIterable = repo.findAll();   
    // clientIterable.forEach(clients::add);

    // import java.util.ArrayList; 
 

    /**
     * TODO implémenter une méthode qui traite les requêtes GET avec un identifiant "variable de chemin" et qui retourne les informations du compte associé
     * Plus d'informations sur les variables de chemin -> https://www.baeldung.com/spring-pathvariable
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getOne(@PathVariable UUID id) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        if (clientOptional.isPresent()){
            Client client = clientOptional.get();
            List<Account>accounts = client.getAccounts();
            List<Insurance>insurances= client.getInsurances();
            List<Long>account_ids= accounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());
            String insuranceType = insurances.stream()
                .map(insurance -> insurance.getName().name())
                .collect(Collectors.joining(", "));

            ClientDto clientDto = new ClientDto(client.getId(), client.getFirstName(), client.getLastName(), client.getEmail(), client.getBirthdate(), account_ids, insuranceType);
            return new ResponseEntity<>(clientDto,HttpStatus.OK);
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
    public Client create(@RequestBody Client client) {
        Client savedClient = clientRepository.save(client);
        return savedClient;
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes PUT
     * 
     * Attention de bien ajouter les annotations qui conviennent
     */
    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable UUID id, @RequestBody Client updatedClient) {
        Client clientToPut = clientRepository.findById(id).get();
        clientToPut.setFirstName(updatedClient.getFirstName());
        clientToPut.setLastName(updatedClient.getLastName());
        clientToPut.setEmail(updatedClient.getEmail());
        clientToPut.setBirthdate(updatedClient.getBirthdate());
    
        clientRepository.save(clientToPut);
        return new ResponseEntity<>(clientToPut, HttpStatus.OK);
    }

    /**
     * TODO implémenter une méthode qui traite les requêtes  DELETE 
     * L'identifiant du compte devra être passé en "variable de chemin" (ou "path variable")
     * Dans le cas d'une suppression effectuée avec succès, le serveur doit retourner un status http 204 (No content)
     * 
     * Il est possible de modifier la réponse du serveur en utilisant la méthode "setStatus" de la classe HttpServletResponse pour configurer le message de réponse du serveur
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id, HttpServletResponse response) {
        Optional<Client> clientToDelete = clientRepository.findById(id);
        clientRepository.delete(clientToDelete.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
