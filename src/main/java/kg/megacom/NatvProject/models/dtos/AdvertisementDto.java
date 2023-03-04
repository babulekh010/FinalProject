package kg.megacom.NatvProject.models.dtos;

import kg.megacom.NatvProject.models.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdvertisementDto {
    Long id;
    String advertisementText;
    ClientDto client;
    OrderStatus status;
    Double totalPrice;

}
