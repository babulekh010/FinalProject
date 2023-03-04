package kg.megacom.NatvProject.repositories;


import kg.megacom.NatvProject.models.entities.OrderDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDateRepo extends JpaRepository<OrderDate, Long> {
}
