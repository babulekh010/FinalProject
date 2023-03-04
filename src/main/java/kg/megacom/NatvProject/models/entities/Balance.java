package kg.megacom.NatvProject.models.entities;

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
@Table(name = "tb_balances")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double balance;
    @OneToOne(fetch = FetchType.LAZY)
    Client client;

    Balance(Double balance, Client client){
        this.balance = balance;
        this.client = client;
    }
}
