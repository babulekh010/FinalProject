package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelCalculationDto {

    String text;
    Integer daysCount;
    Long channelId;
    Double price;
    Double priceWithDiscount;

}
