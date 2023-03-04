package kg.megacom.NatvProject.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.megacom.NatvProject.models.dtos.AdvertisementOrderDto;
import kg.megacom.NatvProject.models.dtos.BannerOrderDto;
import kg.megacom.NatvProject.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kg.megacom.NatvProject.config.SpringFoxConfig.ORDER;

@Api(tags = ORDER)
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    @ApiOperation(value = "Создание текстовой рекламы")
    public ResponseEntity<?> saveAdvertisement(@RequestBody AdvertisementOrderDto order) {
        return orderService.saveAdvertisement(order);
    }

    @PostMapping("/save-banner")
    @ApiOperation(value = "Создание баннерной рекламы")
    public ResponseEntity<?> saveBanner(@RequestBody BannerOrderDto order) {
        return orderService.saveBanner(order);
    }
}
