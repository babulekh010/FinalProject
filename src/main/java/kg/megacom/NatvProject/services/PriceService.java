package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.ChannelDto;
import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.dtos.PriceDto;

import java.util.List;

public interface PriceService {
    PriceDto save(PriceDto priceDto, ChannelDto channel);
    PriceDto findActivePriceByChannel(ChannelDto c);
    double calculateAdPriceWithDiscount(List<DiscountDto> activeDiscounts, double pricePerLetter, int symbolsAmount, int days);
    double calculateAdPriceWithoutDiscount(int symbolsAmount, double pricePerLetter, int adDays);
    double calculateBannerPrice(Double bannerPrice, int days);
    PriceDto update(PriceDto price, ChannelDto channel);
}
