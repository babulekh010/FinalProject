package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepo extends JpaRepository<Advertisement, Long> {
}
