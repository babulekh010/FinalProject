package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.BalanceMapper;
import kg.megacom.NatvProject.mappers.ClientMapper;
import kg.megacom.NatvProject.models.dtos.BalanceDto;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.entities.Balance;
import kg.megacom.NatvProject.repositories.BalanceRepo;
import kg.megacom.NatvProject.services.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepo balanceRepo;
    private final BalanceMapper balanceMapper;
    private final ClientMapper clientMapper;


    @Override
    public void saveBalance(ClientDto clientDto) {
        balanceMapper.toDto(balanceRepo.save(toNewBalance(clientDto)));
    }

    private Balance toNewBalance(ClientDto clientDto) {
        return Balance.builder()
                .balance(0D)
                .client(clientMapper.toEntity(clientDto))
                .build();
    }

    @Override
    public BalanceDto update(BalanceDto balance) {
        return balanceMapper.toDto(
                balanceRepo.save(balanceMapper.toEntity(balance)));
    }

    @Override
    public BalanceDto findByClientId(Long id) {
        return balanceMapper.toDto(balanceRepo.findByClient_Id(id));
    }

    @Override
    public BalanceDto addMoney(double sum, Long clientId) {
        Balance balance = balanceRepo.findById(clientId).get();
        balance.setBalance(sum);

        log.info("Баланс клиента с ID «"+ clientId + "» был пополнен на сумму " + sum);
        return balanceMapper.toDto(balanceRepo.save(balance));
    }
}
