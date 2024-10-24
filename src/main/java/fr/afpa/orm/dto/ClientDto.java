package fr.afpa.orm.dto;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

public record ClientDto (UUID id, String firstName, String lastName, String email, LocalDate birthDate, List<Long> account_id, String insName){}