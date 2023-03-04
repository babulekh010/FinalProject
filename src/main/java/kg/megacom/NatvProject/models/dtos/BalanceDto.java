package kg.megacom.NatvProject.models.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BalanceDto {
    Long id;
    Double balance;
    ClientDto client;
}
