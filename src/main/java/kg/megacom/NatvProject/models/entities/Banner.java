package kg.megacom.NatvProject.models.entities;

import kg.megacom.NatvProject.models.enums.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "tb_banners")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    Client client;
    String path;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
    Double totalPrice;
}
