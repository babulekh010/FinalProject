package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Channel;
import kg.megacom.NatvProject.models.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepo extends JpaRepository<Discount, Long> {
    List<Discount> findAllByChannelAndEndDateAfter(Channel channel, LocalDateTime time);
}
