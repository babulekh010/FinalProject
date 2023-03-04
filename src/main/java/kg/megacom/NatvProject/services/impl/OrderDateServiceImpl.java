package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.OrderDateMapper;
import kg.megacom.NatvProject.mappers.OrderMapper;
import kg.megacom.NatvProject.models.dtos.OrderDateDto;
import kg.megacom.NatvProject.models.dtos.OrderDto;
import kg.megacom.NatvProject.models.entities.OrderDate;
import kg.megacom.NatvProject.repositories.OrderDateRepo;
import kg.megacom.NatvProject.services.OrderDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDateServiceImpl implements OrderDateService {

    private final OrderDateRepo orderDateRepo;
    private final OrderDateMapper orderDateMapper;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDateDto> saveAll(List<LocalDate> date, OrderDto order) {
        return orderDateMapper.toDTOList(
                orderDateRepo.saveAll(
                        toOrderDateList(date, order)));
    }

    private List<OrderDate> toOrderDateList(List<LocalDate> date, OrderDto order) {
        return date.stream()
                .map(x -> toOrderDate(x, order))
                .collect(Collectors.toList());
    }

    private OrderDate toOrderDate(LocalDate date, OrderDto order) {
        return OrderDate.builder()
                .date(date)
                .order(orderMapper.toEntity(order))
                .build();
    }
}
