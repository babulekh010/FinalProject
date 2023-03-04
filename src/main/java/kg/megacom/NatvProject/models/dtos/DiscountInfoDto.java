package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountInfoDto {
    Integer discount;
    Integer fromDaysCount;
}
