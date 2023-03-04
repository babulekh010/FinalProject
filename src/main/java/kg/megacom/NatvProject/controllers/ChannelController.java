package kg.megacom.NatvProject.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.megacom.NatvProject.models.dtos.ChannelCalculationDto;
import kg.megacom.NatvProject.models.dtos.ChannelDataDto;
import kg.megacom.NatvProject.models.dtos.ChannelFullDataDto;
import kg.megacom.NatvProject.services.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kg.megacom.NatvProject.config.SpringFoxConfig.CHANNEL;

@Api(tags = CHANNEL)
@RestController
@RequestMapping("/api/v1/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/save")
    @ApiOperation(value = "Сохранение канала в БД")
    public ChannelFullDataDto save(@RequestBody ChannelFullDataDto request) {
        return channelService.save(request);
    }

    @GetMapping("/list")
    @ApiOperation(value = "Вывод списка каналов")
    public List<ChannelDataDto> findAll() {
        return channelService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Вывод канала по ID")
    public ChannelFullDataDto getChannelInfoById(@PathVariable Long id) {
        return channelService.getChannelInfo(id);
    }

    @GetMapping("/calculate")
    @ApiOperation(value = "Получение стоимости рекламы на канале")
    public ResponseEntity<?> calculate(@RequestBody ChannelCalculationDto channel){
        return channelService.calculate(channel);
    }

    @PostMapping("/uploadImage")
    @ApiOperation(value = "Сохранение логотипа в БД")
    public void uploadLogo(@RequestParam String channelId,
                           @RequestPart MultipartFile file) {
        channelService.uploadLogo(channelId, file);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Изменение информации о канале")
    public ChannelFullDataDto update(@RequestBody ChannelFullDataDto channel) {
        return channelService.update(channel);
    }


}
