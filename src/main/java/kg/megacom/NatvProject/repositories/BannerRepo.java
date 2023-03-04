package kg.megacom.NatvProject.repositories;

import kg.megacom.NatvProject.models.entities.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepo extends JpaRepository<Banner, Long> {
}
