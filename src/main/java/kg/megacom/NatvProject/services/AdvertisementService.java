package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.AdvertisementDto;
import kg.megacom.NatvProject.models.dtos.ClientDto;

public interface AdvertisementService {
    AdvertisementDto saveAdvertisement(String text, ClientDto clientDto);
    void saveTotalPrice(Long id, double totalPrice);
    AdvertisementDto findById(Long id);
    AdvertisementDto save(AdvertisementDto advertisementDto);
}
