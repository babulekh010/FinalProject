package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.entities.Channel;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> saveAll(List<DiscountDto> discounts, Channel channel);
    List<DiscountDto> findActiveDiscountsByChannel(Channel c);
    void update(List<DiscountDto> discounts, Channel channel);
}
