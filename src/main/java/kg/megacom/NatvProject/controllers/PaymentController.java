package kg.megacom.NatvProject.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.megacom.NatvProject.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static kg.megacom.NatvProject.config.SpringFoxConfig.PAYMENT;


@Api(tags = PAYMENT)
@RestController
@RequestMapping("/api/v1/pay")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/for-advertisement/{id}")
    @ApiOperation(value = "Оплата за текстовое объявление")
    public ResponseEntity<?> payForAdvertisement(@PathVariable Long id){
        return paymentService.payForAdvertisement(id);
    }

    @PutMapping("/for-banner/{id}")
    @ApiOperation(value = "Оплата за баннерное объявление")
    public ResponseEntity<?> payForBanner(@PathVariable Long id){
        return paymentService.payForBanner(id);
    }

}