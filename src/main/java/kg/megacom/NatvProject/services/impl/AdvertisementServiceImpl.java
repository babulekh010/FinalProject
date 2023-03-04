package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.AdvertisementMapper;
import kg.megacom.NatvProject.mappers.ClientMapper;
import kg.megacom.NatvProject.models.dtos.AdvertisementDto;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.entities.Advertisement;
import kg.megacom.NatvProject.models.enums.OrderStatus;
import kg.megacom.NatvProject.repositories.AdvertisementRepo;
import kg.megacom.NatvProject.services.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepo advertisementRepo;
    private final AdvertisementMapper advertisementMapper;
    private final ClientMapper clientMapper;


    @Override
    public AdvertisementDto saveAdvertisement(String text, ClientDto clientDto) {
        return advertisementMapper.toDto(
                advertisementRepo.save(toAdvertisement(text, clientDto)));
    }

    private Advertisement toAdvertisement(String text, ClientDto clientDto) {
        return Advertisement.builder()
                .advertisementText(text)
                .status(OrderStatus.CREATED)
                .client(clientMapper.toEntity(clientDto))
                .build();
    }

    @Override
    public void saveTotalPrice(Long id, double totalPrice) {

        Advertisement advertisement = advertisementRepo.findById(id).get();
        advertisement.setTotalPrice(totalPrice);
        advertisementRepo.save(advertisement);
    }

    @Override
    public AdvertisementDto findById(Long id) {
        return advertisementMapper.toDto(advertisementRepo.findById(id).get());
    }

    @Override
    public AdvertisementDto save(AdvertisementDto advertisementDto) {
        return advertisementMapper.toDto(
                advertisementRepo.save(advertisementMapper.toEntity(advertisementDto)));
    }
}
