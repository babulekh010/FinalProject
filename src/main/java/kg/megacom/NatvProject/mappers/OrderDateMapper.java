package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.OrderDateDto;
import kg.megacom.NatvProject.models.entities.OrderDate;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDateMapper extends BaseMapper<OrderDate, OrderDateDto> {
}
