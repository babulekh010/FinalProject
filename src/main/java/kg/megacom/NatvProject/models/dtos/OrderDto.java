package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    AdvertisementDto advertisement;
    BannerDto banner;
    ChannelDto channel;
    Double orderPrice;

}
