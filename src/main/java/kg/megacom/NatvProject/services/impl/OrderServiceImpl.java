package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.AdvertisementMapper;
import kg.megacom.NatvProject.mappers.BannerMapper;
import kg.megacom.NatvProject.mappers.ChannelMapper;
import kg.megacom.NatvProject.mappers.OrderMapper;
import kg.megacom.NatvProject.models.dtos.*;
import kg.megacom.NatvProject.models.entities.Channel;
import kg.megacom.NatvProject.models.entities.Order;
import kg.megacom.NatvProject.repositories.OrderRepo;
import kg.megacom.NatvProject.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ClientService clientService;
    private final AdvertisementService advertisementService;
    private final OrderDateService orderDateService;
    private final BalanceService balanceService;
    private final PriceService priceService;
    private final DiscountService discountService;
    private final BannerService bannerService;
    private final NotificationServiceFeignClient notificationService;
    private final ChannelService channelService;
    private final OrderMapper orderMapper;
    private final ChannelMapper channelMapper;
    private final AdvertisementMapper advertisementMapper;
    private final BannerMapper bannerMapper;

    @Override
    public ResponseEntity<?> saveAdvertisement(AdvertisementOrderDto order) {

        int symbolAmount = order.getText().replace(" ", "").length();

        if (symbolAmount < 20) {
            return ResponseEntity.status(403)
                    .body("Текст объявления должен содержать не менее 20 символов!");
        } else {
            // Создание клиента
            ClientDto clientDto = clientService.saveClient(
                    order.getClientEmail(), order.getClientFIO(), order.getClientPhone());
            balanceService.saveBalance(clientDto);

            // Создание объявления
            AdvertisementDto advertisementDto = advertisementService.saveAdvertisement(order.getText(), clientDto);

            // Email о статусе заявки
            sendStatusNotification(clientDto);

            double totalPrice = 0;

            for (OrderChannelDto channelDto : order.getChannels()) {

                Channel channel = channelMapper.toEntity(channelService.findById(channelDto.getChannelId()));

                // Подсчет цены
                List<DiscountDto> activeDiscounts = discountService.findActiveDiscountsByChannel(channel);
                double pricePerLetter = priceService.findActivePriceByChannel(channel).getPricePerLetter();
                int days = channelDto.getDays().size();

                double priceWithDiscount = priceService.
                        getFinalAdvertisementPrice(activeDiscounts, pricePerLetter, symbolAmount, days);

                channelDto.setPrice(priceService
                        .advertisementPriceWithoutDiscount(symbolAmount, pricePerLetter, days));
                channelDto.setPriceWithDiscount(priceWithDiscount);

                totalPrice += priceWithDiscount;

                // Сохранение заказов
                OrderDto orderDto = saveAdvertisementOrder(advertisementDto, channel);

                // Сохранение order dates
                orderDateService.saveAll(channelDto.getDays(), orderDto);
            }
            order.setTotalPrice(totalPrice);
            order.setStatus(advertisementDto.getStatus());

            advertisementService.saveTotalPrice(advertisementDto.getId(), totalPrice);

            log.info("Объявление с ID «" + advertisementDto.getId() + "» сохранено!");
            return ResponseEntity.status(200).body(order);
        }
    }

    private OrderDto saveAdvertisementOrder(AdvertisementDto advertisement, Channel channel) {
        Order order = Order.builder()
                .advertisement(advertisementMapper.toEntity(advertisement))
                .channel(channel)
                .build();
        return orderMapper.toDto(orderRepo.save(order));
    }

    @Override
    public ResponseEntity<?> saveBanner(BannerOrderDto order) {

        // Создание клиента
        ClientDto clientDto = clientService.saveClient(
                order.getClientEmail(), order.getClientFIO(), order.getClientPhone());
        balanceService.saveBalance(clientDto);

        // Создание баннерной рекламы
        BannerDto bannerDto = bannerService.saveBanner(clientDto);

        // Email о статусе заявки
        sendStatusNotification(clientDto);

        double totalPrice = 0;

        for (OrderChannelDto channelDto : order.getChannels()) {

            Channel channel = channelMapper.toEntity(channelService.findById(channelDto.getChannelId()));

            // Подсчет цены

            PriceDto price = priceService.findActivePriceByChannel(channel);
            int days = channelDto.getDays().size();
            double finalBannerPrice = priceService.getBannerPrice(price.getBannerPrice(), days);

            channelDto.setPrice(finalBannerPrice);
            channelDto.setPriceWithDiscount(finalBannerPrice);

            totalPrice += finalBannerPrice;

            // Сохранение заказов
            OrderDto orderDto = saveBannerOrder(bannerDto, channel);

            // Сохранение order dates
            orderDateService.saveAll(channelDto.getDays(), orderDto);
        }
        order.setTotalPrice(totalPrice);
        order.setStatus(bannerDto.getStatus());

        bannerService.saveTotalPrice(bannerDto.getId(), totalPrice);

        log.info("Баннер с ID «" + bannerDto.getId() + "» сохранен!");
        return ResponseEntity.status(200).body(order);

    }

    private OrderDto saveBannerOrder(BannerDto bannerDto, Channel channel) {
        Order order = Order.builder()
                .banner(bannerMapper.toEntity(bannerDto))
                .channel(channel)
                .build();
        return orderMapper.toDto(orderRepo.save(order));

    }

    private void sendStatusNotification(ClientDto clientDto) {
        notificationService.send(new EmailDataDto(clientDto.getEmail(),
                "NATV",
                "Ваша заявка на рассмотрении!"));

        log.info("Сообщение о статусе заявки отправлено клиенту на email: " + clientDto.getEmail());
    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepo.findById(id).get());
    }
}
