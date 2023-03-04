package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDateDto {
    Long id;
    OrderDto order;
    LocalDate date;
}
