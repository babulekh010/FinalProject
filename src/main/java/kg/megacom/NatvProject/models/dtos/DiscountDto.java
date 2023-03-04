package kg.megacom.NatvProject.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountDto {
    Long id;
    @JsonIgnore
    LocalDateTime startDate;
    @JsonIgnore
    LocalDateTime endDate;
    Integer discount;
    Integer fromDaysCount;
    @JsonIgnore
    ChannelDto channel;
}
