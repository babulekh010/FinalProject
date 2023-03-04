package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelFullDataDto {
    Long channelId;
    String channelName;
    String logoPath;
    Boolean active;
    PriceDto price;
    List<DiscountDto> discounts;

}
