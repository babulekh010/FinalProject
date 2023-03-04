package kg.megacom.NatvProject.models.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "tb_prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double bannerPrice;
    Double pricePerLetter;
    LocalDateTime startDate;
    LocalDateTime endDate;
    @ManyToOne
    Channel channel;

}
