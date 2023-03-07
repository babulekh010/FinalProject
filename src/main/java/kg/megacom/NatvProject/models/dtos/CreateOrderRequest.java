package kg.megacom.NatvProject.models.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    Long channelId;
    @JsonFormat(pattern="dd.MM.yyyy")
    List<LocalDate> days;
    Double price;
    Double priceWithDiscount;
}
