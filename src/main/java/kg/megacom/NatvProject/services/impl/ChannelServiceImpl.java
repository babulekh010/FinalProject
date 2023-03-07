package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.ChannelMapper;
import kg.megacom.NatvProject.mappers.DiscountMapper;
import kg.megacom.NatvProject.models.dtos.*;
import kg.megacom.NatvProject.models.entities.Channel;
import kg.megacom.NatvProject.repositories.ChannelRepo;
import kg.megacom.NatvProject.services.ChannelService;
import kg.megacom.NatvProject.services.DiscountService;
import kg.megacom.NatvProject.services.FileService;
import kg.megacom.NatvProject.services.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepo channelRepo;
    private final PriceService priceService;
    private final DiscountService discountService;
    private final DiscountMapper discountMapper;
    private final ChannelMapper channelMapper;
    private final FileService fileService;
    @Value("${channel-logo.storage.directory}")
    private String myPath;

    @Override
    public ChannelFullDataDto save(ChannelFullDataDto channelDto) {

        // Создание канала
        Channel channel = Channel
                .builder()
                .name(channelDto.getChannelName())
                .active(channelDto.getActive())
                .logoPath(channelDto.getLogoPath())
                .build();
        channelRepo.save(channel);
        channelDto.setChannelId(channel.getId());

        // Создание price
        channelDto.setPrice(priceService.save(channelDto.getPrice(), channelMapper.toDto(channel)));

        // Создание discounts
        discountService.saveAll(channelDto.getDiscounts(), channelMapper.toDto(channel));

        log.info("Канал с ID «" + channelDto.getChannelId() + "» сохранен");
        return channelDto;
    }

    @Override
    public List<ChannelDataDto> findAll() {

        List<Channel> channels = channelRepo.findAllByActiveTrue(
                Sort.by(Sort.Direction.ASC, "name"));

        return channels.stream()
                .map(this::toChannelDataDto)
                .collect(Collectors.toList());

    }

    @Override
    public ChannelDto findById(Long id) {
        return channelMapper.toDto(channelRepo.findById(id).get());
    }

    private ChannelDataDto toChannelDataDto(Channel x) {
        return ChannelDataDto.builder()
                .channelName(x.getName())
                .logo(x.getLogoPath())
                .discounts(discountMapper.toDiscountInfoList(discountService.findActiveDiscountsByChannel(channelMapper.toDto(x))))
                .pricePerLetter(priceService.findActivePriceByChannel(channelMapper.toDto(x)).getPricePerLetter())
                .build();
    }

    @Override
    public ChannelFullDataDto getChannelInfo(Long channelId) {

        ChannelDto channel = findById(channelId);

        // Актуальный прайс канала
        PriceDto price = priceService.findActivePriceByChannel(channel);

        // Актуальные скидки
        List<DiscountDto> discounts = discountService.findActiveDiscountsByChannel(channel);

        return ChannelFullDataDto.builder()
                .channelId(channel.getId())
                .channelName(channel.getName())
                .active(channel.getActive())
                .price(price)
                .channelName(channel.getName())
                .logoPath(channel.getLogoPath())
                .discounts(discounts)
                .build();
    }

    @Override
    public void uploadLogo(String channelId, MultipartFile file) {

        // Сохранение абсолютного пути в БД
        Channel channel = channelRepo.findById(Long.valueOf(channelId)).get();
        channel.setLogoPath(fileService.uploadImage(myPath, file));
        channelRepo.save(channel);

        log.info("Логотип для канала с ID «" + channel.getId() + "» сохранен");
    }

    @Override
    public ResponseEntity<?> calculate(ChannelCalculationDto channelDto) {

        int symbolAmount = channelDto.getText().replace(" ", "").length();
        ChannelDto channel = findById(channelDto.getChannelId());

        if (symbolAmount < 20) {
            return ResponseEntity.status(403).body
                    ("Текст объявления должен содержать не менее 20 символов!");
        } else if (!channel.getActive()){
            return ResponseEntity.status(403).body
                    ("Канал в данное время неактивен!");
        }else{
            List<DiscountDto> activeDiscounts = discountService.findActiveDiscountsByChannel(channel);

            double pricePerLetter = priceService.findActivePriceByChannel(channel).getPricePerLetter();

            channelDto.setPrice(priceService
                    .calculateAdPriceWithoutDiscount(symbolAmount, pricePerLetter, channelDto.getDaysCount()));
            channelDto.setPriceWithDiscount(priceService.
                    calculateAdPriceWithDiscount(activeDiscounts, pricePerLetter, symbolAmount, channelDto.getDaysCount()));
            return ResponseEntity.ok(channelDto);
        }
    }

    @Override
    public ChannelFullDataDto update(ChannelFullDataDto channelDto) {

        // Обновление канала
        Channel channel = channelRepo.findById(channelDto.getChannelId()).get();
        channel = channelMapper.fullDataDtoToChannel(channelDto);
        channel = channelRepo.save(channel);

        // Обновление цены
        priceService.update(channelDto.getPrice(), channelMapper.toDto(channel));

        // Обновление скидок
        discountService.update(channelDto.getDiscounts(), channelMapper.toDto(channel));
        channelDto.setDiscounts(discountService.findActiveDiscountsByChannel(channelMapper.toDto(channel)));

        log.info("Канал с ID «" + channelDto.getChannelId() + "» обновлен");
        return channelDto;
    }
}