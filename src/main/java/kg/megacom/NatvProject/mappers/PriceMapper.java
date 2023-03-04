package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.PriceDto;
import kg.megacom.NatvProject.models.entities.Price;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper extends BaseMapper<Price, PriceDto>{
}
