package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.dtos.PriceDto;
import kg.megacom.NatvProject.models.entities.Channel;

import java.util.List;

public interface PriceService {
    PriceDto save(PriceDto priceDto, Channel channel);
    PriceDto findActivePriceByChannel(Channel c);
    double getFinalAdvertisementPrice(List<DiscountDto> activeDiscounts, double pricePerLetter, int symbolsAmount, int days);
    double advertisementPriceWithoutDiscount(int symbolsAmount, double pricePerLetter, int adDays);
    double getBannerPrice(Double bannerPrice, int days);
    PriceDto update(PriceDto price, Channel channel);
}
