package application.main.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain=true)
@Entity
@Table(name = "person", schema = "phonebook")
public class Person {
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "permanent_id")
    @OneToOne
    private Integer permanentAddressId;
    @Column(name = "temporary_id")
    @OneToOne
    private Integer temporaryAddressId;
}
