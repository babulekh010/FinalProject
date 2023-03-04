package kg.megacom.NatvProject.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PriceDto {
    Long id;
    Double bannerPrice;
    Double pricePerLetter;
    @JsonIgnore
    LocalDateTime startDate;
    @JsonIgnore
    LocalDateTime endDate;
    @JsonIgnore
    ChannelDto channel;

}
