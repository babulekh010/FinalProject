package kg.megacom.NatvProject.mappers;

import kg.megacom.NatvProject.models.dtos.ChannelDto;
import kg.megacom.NatvProject.models.dtos.ChannelFullDataDto;
import kg.megacom.NatvProject.models.entities.Channel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ChannelMapper extends BaseMapper<Channel, ChannelDto>{

    @Mapping(target = "name", source = "channel.channelName")
    @Mapping(target = "id", source = "channel.channelId")
    Channel fullDataDtoToChannel(ChannelFullDataDto channel);

}
