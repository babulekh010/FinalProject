package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.ChannelCalculationDto;
import kg.megacom.NatvProject.models.dtos.ChannelDataDto;
import kg.megacom.NatvProject.models.dtos.ChannelDto;
import kg.megacom.NatvProject.models.dtos.ChannelFullDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ChannelService {
    ChannelFullDataDto save(ChannelFullDataDto request);
    List<ChannelDataDto> findAll();
    ChannelDto findById(Long id);
    ChannelFullDataDto getChannelInfo(Long channelId);
    ResponseEntity<?> calculate(ChannelCalculationDto channel);
    void uploadLogo(String channelId, MultipartFile file);
    ChannelFullDataDto update(ChannelFullDataDto channel);
}
