package kg.megacom.NatvProject.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.megacom.NatvProject.models.dtos.BannerDto;
import kg.megacom.NatvProject.services.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static kg.megacom.NatvProject.config.SpringFoxConfig.BANNER;

@Api(tags = BANNER)
@RestController
@RequestMapping("/api/v1/banners")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;

    @PostMapping("/uploadImage")
    @ApiOperation(value = "Сохранение баннера в БД")
    public void uploadBanner(@RequestParam String bannerId,
                             @RequestPart MultipartFile file) {
        bannerService.uploadBanner(bannerId, file);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Вывод баннера по ID")
    public BannerDto getById(@PathVariable Long id) {
        return bannerService.findById(id);
    }

    @GetMapping("/list")
    @ApiOperation(value = "Вывод списка баннеров")
    public List<BannerDto> findAll() {
        return bannerService.findAll();
    }
}
