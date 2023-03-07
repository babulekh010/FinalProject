package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.CreateAdvertisementRequest;
import kg.megacom.NatvProject.models.dtos.CreateBannerRequest;
import kg.megacom.NatvProject.models.dtos.OrderDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> saveAdvertisement(CreateAdvertisementRequest order);

    ResponseEntity<?> saveBanner(CreateBannerRequest order);

    OrderDto findById(Long id);
}
