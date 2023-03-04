package kg.megacom.NatvProject.models.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelDto {
    Long id;
    String name;
    String logoPath;
    Boolean active;

}
