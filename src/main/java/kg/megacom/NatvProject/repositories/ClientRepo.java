package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}
