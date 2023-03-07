package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.ChannelDto;
import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.entities.Channel;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> saveAll(List<DiscountDto> discounts, ChannelDto channel);
    List<DiscountDto> findActiveDiscountsByChannel(ChannelDto c);
    void update(List<DiscountDto> discounts, ChannelDto channel);
}
