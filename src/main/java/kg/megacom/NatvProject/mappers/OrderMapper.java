package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.OrderDto;
import kg.megacom.NatvProject.models.entities.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order, OrderDto> {
}
