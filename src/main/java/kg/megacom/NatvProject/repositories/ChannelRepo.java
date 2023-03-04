package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Channel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepo extends JpaRepository<Channel, Long> {
    List<Channel> findAllByActiveTrue(Sort name);
}
