package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.BannerMapper;
import kg.megacom.NatvProject.mappers.ClientMapper;
import kg.megacom.NatvProject.models.dtos.BannerDto;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.entities.Banner;
import kg.megacom.NatvProject.models.enums.OrderStatus;
import kg.megacom.NatvProject.repositories.BannerRepo;
import kg.megacom.NatvProject.services.BannerService;
import kg.megacom.NatvProject.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepo bannerRepo;
    private final FileService fileService;
    private final BannerMapper bannerMapper;
    private final ClientMapper clientMapper;
    @Value("${banners.storage.directory}")
    private String myPath;

    @Override
    public void uploadBanner(String bannerId, MultipartFile file) {

        // Сохранение абсолютного пути в БД
        Banner banner = bannerRepo.findById(Long.valueOf(bannerId)).get();
        banner.setPath(fileService.uploadImage(myPath, file));
        bannerRepo.save(banner);
    }

    @Override
    public BannerDto saveBanner(ClientDto clientDto) {
        return bannerMapper.toDto(
                bannerRepo.save(toBanner(clientDto)));
    }

    private Banner toBanner(ClientDto clientDto) {
        return Banner.builder()
                .status(OrderStatus.CREATED)
                .client(clientMapper.toEntity(clientDto))
                .build();
    }

    @Override
    public BannerDto findById(Long id) {
        return bannerMapper.toDto(bannerRepo.findById(id).get());
    }

    @Override
    public List<BannerDto> findAll() {
        return bannerMapper.toDTOList(bannerRepo.findAll());
    }

    @Override
    public void saveTotalPrice(Long id, double totalPrice) {

        Banner banner = bannerRepo.findById(id).get();
        banner.setTotalPrice(totalPrice);
        bannerRepo.save(banner);
    }

    @Override
    public BannerDto save(BannerDto bannerDto) {
        return bannerMapper.toDto(
                bannerRepo.save(bannerMapper.toEntity(bannerDto)));
    }

}
