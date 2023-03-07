package kg.megacom.NatvProject.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.megacom.NatvProject.models.dtos.AuthRequestDTO;
import kg.megacom.NatvProject.models.dtos.AuthResponseDTO;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.services.AuthenticationService;
import kg.megacom.NatvProject.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.megacom.NatvProject.config.SpringFoxConfig.CLIENT;

@Api(tags = CLIENT)
@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final AuthenticationService service;

    @PostMapping("/auth")
    @ApiOperation(value = "Аутентификация клиента")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Вывод клиента по ID")
    public ClientDto findById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @GetMapping("/list")
    @ApiOperation(value = "Вывод списка клиентов")
    public List<ClientDto> findAll(){
        return clientService.findAll();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Изменение информации об аккаунте")
    public ClientDto update(@RequestBody ClientDto clientDto){
        return clientService.update(clientDto);
    }

    @PutMapping("/deactivate/{id}")
    @ApiOperation(value = "Деактивация клиента по ID")
    public void deactivate(@PathVariable Long id){
        clientService.deactivate(id);
    }

}
