package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.BalanceDto;
import kg.megacom.NatvProject.models.entities.Balance;
import org.mapstruct.Mapper;

@Mapper
public interface BalanceMapper extends BaseMapper<Balance, BalanceDto> {
}
