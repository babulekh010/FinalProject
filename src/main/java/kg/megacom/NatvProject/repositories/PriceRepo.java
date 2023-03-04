package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Channel;
import kg.megacom.NatvProject.models.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PriceRepo extends JpaRepository<Price, Long> {
    Price findByChannelAndEndDateAfter(Channel channel, LocalDateTime time);
}
