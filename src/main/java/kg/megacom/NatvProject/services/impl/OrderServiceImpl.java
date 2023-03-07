package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.OrderMapper;
import kg.megacom.NatvProject.models.dtos.*;
import kg.megacom.NatvProject.models.enums.OrderStatus;
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
    private final AuthenticationService authService;
    private final AdvertisementService advertisementService;
    private final OrderDateService orderDateService;
    private final BalanceService balanceService;
    private final PriceService priceService;
    private final DiscountService discountService;
    private final BannerService bannerService;
    private final ChannelService channelService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<?> saveAdvertisement(CreateAdvertisementRequest request) {

        int symbolAmount = request.getText().replace(" ", "").length();

        if (symbolAmount < 20) {
            return ResponseEntity.status(403)
                    .body("Текст объявления должен содержать не менее 20 символов!");
        } else {
            // Регистрация клиента
            ClientDto clientDto = authService.register(
                    request.getClientEmail(), request.getClientFIO(), request.getClientPhone());

            // Создание баланса клиента
            balanceService.saveBalance(clientDto);

            // Создание объявления
            AdvertisementDto advertisementDto = advertisementService.saveAdvertisement(request.getText(), clientDto);

            double totalPrice = 0;

            // Сохранение списка заказов
            for (CreateOrderRequest order : request.getChannels()) {

                ChannelDto channel = channelService.findById(order.getChannelId());

                List<DiscountDto> activeDiscounts = discountService.findActiveDiscountsByChannel(channel);
                double pricePerLetter = priceService.findActivePriceByChannel(channel).getPricePerLetter();
                int days = order.getDays().size();

                // Цена со скидкой
                double priceWithDiscount = priceService.
                        calculateAdPriceWithDiscount(activeDiscounts, pricePerLetter, symbolAmount, days);
                order.setPriceWithDiscount(priceWithDiscount);

                // Цена без скидки
                order.setPrice(priceService
                        .calculateAdPriceWithoutDiscount(symbolAmount, pricePerLetter, days));

                totalPrice += priceWithDiscount;

                // Сохранение заказа
                OrderDto orderDto = saveAdvertisementOrder(advertisementDto, priceWithDiscount, channel);

                // Сохранение order dates
                orderDateService.saveAll(order.getDays(), orderDto);
            }

            request.setTotalPrice(totalPrice);
            request.setStatus(OrderStatus.CREATED);

            advertisementService.saveTotalPrice(advertisementDto.getId(), totalPrice);

            log.info("Объявление с ID «" + advertisementDto.getId() + "» сохранено");
            return ResponseEntity.status(200).body(request);
        }
    }

    private OrderDto saveAdvertisementOrder(AdvertisementDto advertisement, Double price, ChannelDto channel) {
        OrderDto order = OrderDto.builder()
                .advertisement(advertisement)
                .orderPrice(price)
                .channel(channel)
                .build();
        return orderMapper.toDto(orderRepo.save(orderMapper.toEntity(order)));
    }

    @Override
    public ResponseEntity<?> saveBanner(CreateBannerRequest request) {

        // Регистрация клиента
        ClientDto clientDto = authService.register(
                request.getClientEmail(), request.getClientFIO(), request.getClientPhone());

        // Создание баланса клиента
        balanceService.saveBalance(clientDto);

        // Создание баннерной рекламы
        BannerDto bannerDto = bannerService.saveBanner(clientDto);

        double totalPrice = 0;

        // Сохранение списка заказов
        for (CreateOrderRequest order : request.getChannels()) {

            ChannelDto channel = channelService.findById(order.getChannelId());

            PriceDto price = priceService.findActivePriceByChannel(channel);
            int days = order.getDays().size();

            // Подсчет цены
            double orderPrice = priceService.calculateBannerPrice(price.getBannerPrice(), days);
            order.setPrice(orderPrice);
            order.setPriceWithDiscount(orderPrice);

            totalPrice += orderPrice;

            // Сохранение заказа
            OrderDto orderDto = saveBannerOrder(bannerDto, orderPrice, channel);

            // Сохранение order dates
            orderDateService.saveAll(order.getDays(), orderDto);
        }
        request.setTotalPrice(totalPrice);
        request.setStatus(OrderStatus.CREATED);

        bannerService.saveTotalPrice(bannerDto.getId(), totalPrice);

        log.info("Баннер с ID «" + bannerDto.getId() + "» сохранен");
        return ResponseEntity.status(200).body(request);

    }

    private OrderDto saveBannerOrder(BannerDto bannerDto, Double price, ChannelDto channel) {
        OrderDto order = OrderDto.builder()
                .banner(bannerDto)
                .channel(channel)
                .orderPrice(price)
                .build();
        return orderMapper.toDto(orderRepo.save(orderMapper.toEntity(order)));

    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.toDto(orderRepo.findById(id).get());
    }
}
