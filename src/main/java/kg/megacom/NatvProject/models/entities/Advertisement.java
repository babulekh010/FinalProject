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
@Table(name = "tb_advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String advertisementText;
    @ManyToOne
    Client client;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
    Double totalPrice;

}
