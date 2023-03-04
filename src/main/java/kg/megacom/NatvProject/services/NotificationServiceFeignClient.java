package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.EmailDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "notification", url = "http://localhost:8085")
public interface NotificationServiceFeignClient {

    @PostMapping("/sendEmail")
    void send(@RequestBody EmailDataDto dataDTO);

}