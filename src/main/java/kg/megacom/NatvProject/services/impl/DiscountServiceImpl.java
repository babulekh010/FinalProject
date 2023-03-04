package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.DiscountMapper;
import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.entities.Channel;
import kg.megacom.NatvProject.models.entities.Discount;
import kg.megacom.NatvProject.repositories.DiscountRepo;
import kg.megacom.NatvProject.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepo discountRepo;
    private final DiscountMapper discountMapper;

    @Override
    public List<DiscountDto> saveAll(List<DiscountDto> discounts,
                                     Channel channel) {
        return discountMapper.toDTOList(
                discountRepo.saveAll(toDiscountList(discounts, channel)));
    }

    @Override
    public List<DiscountDto> findActiveDiscountsByChannel(Channel channel) {
        return discountMapper.toDTOList(
                discountRepo.findAllByChannelAndEndDateAfter
                        (channel, LocalDateTime.now()));
    }
    private List<Discount> toDiscountList(List<DiscountDto> discounts,
                                          Channel channel) {
        return discounts.stream()
                .map(x -> toDiscount(x, channel))
                .collect(Collectors.toList());
    }

    private Discount toDiscount(DiscountDto x, Channel channel) {
        return Discount.builder()
                .id(x.getId())
                .channel(channel)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusYears(1000))
                .fromDaysCount(x.getFromDaysCount())
                .discount(x.getDiscount())
                .build();
    }

    @Override
    public void update(List<DiscountDto> discounts, Channel channel) {

        // Актуальные скидки
        List<Discount> currentDiscounts = discountRepo.findAllByChannelAndEndDateAfter(channel, LocalDateTime.now());

        // Скидки на удаление
        List<Discount> discountsForDelete = currentDiscounts.stream()
                .filter(currentDiscount -> discounts.stream()
                        .filter(discount -> discount.getId()!=null)
                        .noneMatch(discount -> discount.getId().equals(currentDiscount.getId())))
                .peek(discount -> discount.setEndDate(LocalDateTime.now()))
                .collect(Collectors.toList());
        discountRepo.saveAll(discountsForDelete);

        // Скидки на сохранение
        List<DiscountDto> newDiscounts = discounts.stream()
                .filter(x -> x.getId()==null)
                .collect(Collectors.toList());
        saveAll(newDiscounts, channel);
    }

}
