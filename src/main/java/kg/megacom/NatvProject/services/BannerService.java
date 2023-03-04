package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.BannerDto;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    void uploadBanner(String bannerId, MultipartFile file);
    BannerDto findById(Long id);
    List<BannerDto> findAll();
    BannerDto saveBanner(ClientDto clientDto);
    void saveTotalPrice(Long id, double totalPrice);
    BannerDto save(BannerDto bannerDto);
}
