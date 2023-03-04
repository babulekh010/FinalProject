package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepo extends JpaRepository<Balance, Long> {
    Balance findByClient_Id(Long clientId);

}
