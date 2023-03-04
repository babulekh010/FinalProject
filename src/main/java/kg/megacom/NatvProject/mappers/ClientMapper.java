package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.entities.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper extends BaseMapper<Client, ClientDto> {
}
