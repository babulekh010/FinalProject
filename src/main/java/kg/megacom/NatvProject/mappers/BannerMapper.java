package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.BannerDto;
import kg.megacom.NatvProject.models.entities.Banner;
import org.mapstruct.Mapper;

@Mapper
public interface BannerMapper extends BaseMapper<Banner, BannerDto>{
}
