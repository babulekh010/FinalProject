package kg.megacom.NatvProject.mappers;
import kg.megacom.NatvProject.models.dtos.AdvertisementDto;
import kg.megacom.NatvProject.models.entities.Advertisement;
import org.mapstruct.Mapper;

@Mapper
public interface AdvertisementMapper extends BaseMapper<Advertisement, AdvertisementDto>{
}
