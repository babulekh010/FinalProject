package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.ChannelMapper;
import kg.megacom.NatvProject.mappers.PriceMapper;
import kg.megacom.NatvProject.models.dtos.ChannelDto;
import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.dtos.PriceDto;
import kg.megacom.NatvProject.models.entities.Price;
import kg.megacom.NatvProject.repositories.PriceRepo;
import kg.megacom.NatvProject.services.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final PriceRepo priceRepo;
    private final PriceMapper priceMapper;
    private final ChannelMapper channelMapper;

    @Override
    public PriceDto save(PriceDto priceDto, ChannelDto channel) {

        Price currentPrice = priceRepo.findByChannelAndEndDateAfter(
                channelMapper.toEntity(channel), LocalDateTime.now());

        if (Objects.nonNull(currentPrice)) {
            currentPrice.setEndDate(LocalDateTime.now());
            priceRepo.save(currentPrice);
        }
        return priceMapper.toDto(priceRepo.save(toNewPrice(priceDto, channel)));
    }

    private Price toNewPrice(PriceDto p, ChannelDto c) {

        return Price.builder()
                .id(p.getId())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusYears(1000))
                .pricePerLetter(p.getPricePerLetter())
                .bannerPrice(p.getBannerPrice())
                .channel(channelMapper.toEntity(c))
                .build();
    }

    @Override
    public PriceDto findActivePriceByChannel(ChannelDto channel) {
        return priceMapper.toDto(
                priceRepo.findByChannelAndEndDateAfter(
                        channelMapper.toEntity(channel), LocalDateTime.now()));
    }

    public double calculateAdPriceWithDiscount(List<DiscountDto> activeDiscounts, double pricePerLetter, int symbolsAmount, int days) {

        double totalPrice = 0;

        if (activeDiscounts.size() == 0) {
            totalPrice = calculateAdPriceWithoutDiscount(symbolsAmount, pricePerLetter, days);

        } else if (activeDiscounts.size() == 1) {
            int discount = activeDiscounts.get(0).getDiscount();
            totalPrice = advertisementPriceWithDiscount(symbolsAmount, pricePerLetter, days, discount);

        } else {
            activeDiscounts.sort(Comparator.comparingInt(DiscountDto::getFromDaysCount));

            DiscountDto discount = activeDiscounts.stream()
                    .filter(x -> x.getFromDaysCount() <= days)
                    .max(Comparator.comparing(DiscountDto::getFromDaysCount))
                    .orElse(new DiscountDto());

            if (Objects.isNull(discount.getId())) {
                totalPrice = calculateAdPriceWithoutDiscount(symbolsAmount, pricePerLetter, days);
            } else {
                totalPrice = advertisementPriceWithDiscount(symbolsAmount, pricePerLetter, days, discount.getDiscount());
            }
        }

        return totalPrice;
    }

    public double calculateAdPriceWithoutDiscount(int symbolsAmount, double pricePerLetter, int adDays) {
        return symbolsAmount * pricePerLetter * adDays;
    }

    private double advertisementPriceWithDiscount(int symbolsAmount, double pricePerLetter, int days, int discount) {
        double discountSum = symbolsAmount * pricePerLetter * days / 100 * discount;
        return symbolsAmount * pricePerLetter * days - discountSum;
    }

    @Override
    public double calculateBannerPrice(Double bannerPrice, int days) {
        return bannerPrice * days;
    }

    @Override
    public PriceDto update(PriceDto price, ChannelDto channel) {

        Price currentPrice = priceRepo.findByChannelAndEndDateAfter(
                channelMapper.toEntity(channel), LocalDateTime.now());

        if (!currentPrice.getId().equals(price.getId())) {
            return save(price, channel);
        }
        return priceMapper.toDto(currentPrice);
    }
}
