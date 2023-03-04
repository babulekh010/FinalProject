package kg.megacom.NatvProject.services;

import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> payForAdvertisement(Long id);
    ResponseEntity<?> payForBanner(Long id);

}
