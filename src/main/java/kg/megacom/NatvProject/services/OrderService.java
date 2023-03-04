package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.AdvertisementOrderDto;
import kg.megacom.NatvProject.models.dtos.BannerOrderDto;
import kg.megacom.NatvProject.models.dtos.OrderDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> saveAdvertisement(AdvertisementOrderDto order);

    ResponseEntity<?> saveBanner(BannerOrderDto order);

    OrderDto findById(Long id);
}
