package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.OrderDateDto;
import kg.megacom.NatvProject.models.dtos.OrderDto;

import java.time.LocalDate;
import java.util.List;

public interface OrderDateService {
    List<OrderDateDto> saveAll(List<LocalDate> days, OrderDto order);
}
