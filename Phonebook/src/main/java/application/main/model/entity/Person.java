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
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(mappedBy = "person")
    @JoinColumn(name = "permanent_id")
    private Address permanentAddress;

    @OneToOne(mappedBy = "person")
    @JoinColumn(name = "temporary_id")
    private Address temporaryAddress;
}
