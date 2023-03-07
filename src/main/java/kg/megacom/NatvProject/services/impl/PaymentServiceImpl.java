package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.models.dtos.*;
import kg.megacom.NatvProject.models.enums.OrderStatus;
import kg.megacom.NatvProject.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final NotificationServiceFeignClient notificationServiceFeignClient;
    private final BalanceService balanceService;
    private final AdvertisementService advertisementService;
    private final BannerService bannerService;

    @Override
    public ResponseEntity<?> payForAdvertisement(Long id) {

        AdvertisementDto advertisementDto = advertisementService.findById(id);

        if (advertisementDto.getStatus() == OrderStatus.PAID ||
                advertisementDto.getStatus() == OrderStatus.CANCELLED) {
            return ResponseEntity.status(407).body("Не удалось произвести оплату");
        }
        BalanceDto balance = balanceService.findByClientId(advertisementDto.getClient().getId());

        if (balance.getBalance() < advertisementDto.getTotalPrice()) {
            return ResponseEntity.status(407).body("У Вас недостаточно средств на счету!");
        }

        advertisementDto.setStatus(OrderStatus.PAID);
        advertisementService.save(advertisementDto);

        log.info("Объявление с ID «" + advertisementDto.getId() + "» оплачено");
        return payment(advertisementDto.getClient(), balance, advertisementDto.getTotalPrice());
    }


    @Override
    public ResponseEntity<?> payForBanner(Long id) {

        BannerDto bannerDto = bannerService.findById(id);

        if (bannerDto.getStatus() == OrderStatus.PAID ||
                bannerDto.getStatus() == OrderStatus.CANCELLED) {
            return ResponseEntity.status(407).body("Не удалось произвести оплату.");
        }
        BalanceDto balance = balanceService.findByClientId(bannerDto.getClient().getId());

        if (balance.getBalance() < bannerDto.getTotalPrice()) {
            return ResponseEntity.status(407).body("У Вас недостаточно средств на счету");
        }

        bannerDto.setStatus(OrderStatus.PAID);
        bannerService.save(bannerDto);

        log.info("Баннер с ID «" + bannerDto.getId() + "» оплачен");
        return payment(bannerDto.getClient(), balance, bannerDto.getTotalPrice());
    }

    private ResponseEntity<?> payment(ClientDto clientDto, BalanceDto balance, double totalPrice) {

        balance.setBalance(balance.getBalance() - totalPrice);
        balanceService.update(balance);

//        notificationServiceFeignClient.send(new EmailDataDto(clientDto.getEmail(),
//                "Payment notification",
//                "С Вашего счета списано " + totalPrice + " сом!"));

        log.info("У клиента с ID «" + clientDto.getId() + "» списано со счета " + totalPrice);
        return ResponseEntity.status(200).body("Операция проведена успешно");
    }
}
