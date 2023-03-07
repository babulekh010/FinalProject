package kg.megacom.NatvProject.models.dtos;

import kg.megacom.NatvProject.models.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBannerRequest {
    String clientEmail;
    String clientFIO;
    String clientPhone;
    String path;
    Double totalPrice;
    OrderStatus status;
    List<CreateOrderRequest> channels;
}
