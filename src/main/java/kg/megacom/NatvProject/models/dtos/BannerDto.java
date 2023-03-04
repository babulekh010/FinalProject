package kg.megacom.NatvProject.models.dtos;

import kg.megacom.NatvProject.models.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerDto {
    Long id;
    ClientDto client;
    String path;
    OrderStatus status;
    Double totalPrice;
}
