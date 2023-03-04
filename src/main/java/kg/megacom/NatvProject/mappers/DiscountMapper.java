package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.DiscountDto;
import kg.megacom.NatvProject.models.dtos.DiscountInfoDto;
import kg.megacom.NatvProject.models.entities.Discount;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface DiscountMapper extends BaseMapper<Discount, DiscountDto>{
    List<DiscountInfoDto> toDiscountInfoList(List<DiscountDto> discounts);
}
