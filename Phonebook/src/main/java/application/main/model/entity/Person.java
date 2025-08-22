package application.main.model.entity;

import application.main.model.entity.dto.SimplePersonDTO;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private int id;
    private Address permanent;
    private Address temporary;

    public Person() {
    }

    public Person(SimplePersonDTO personDTO) {
        this.name = personDTO.getName();
        this.age = personDTO.getAge();
        this.id = personDTO.getId();
    }

    public Person(String name, int age, int id, Address permanent) {
        this.name = name;
        this.age = age;
        this.id = id;

        //Everyone needs at least one address, temporary is not necessary
        if (permanent == null) {
            throw new NullPointerException("Permanent address cannot be null");
        }
        this.permanent = permanent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPermanentAddress(Address permanent) {
        //Everyone needs at least one address
        if (permanent == null) {
            throw new NullPointerException("Permanent address cannot be null");
        }
        this.permanent = permanent;
    }

    public void setTemporaryAddress(Address temporary) {
        this.temporary = temporary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public Address getPermanentAddress() {
        return permanent;
    }

    public Address getTemporaryAddress() {
        return temporary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, id, permanent, temporary);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Person other = (Person) obj;

        return other.name.equals(this.name) && other.age == this.age
                && other.id == this.id && other.permanent.equals(this.permanent)
                && other.temporary.equals(this.temporary);
    }

    public String toString() {
        if (temporary == null) {
            return "Name: " + name + "\nAge: " + age + "\nPermanent residence:\n" + permanent.toString();
        }
        return "Name: " + name + "\nAge: " + age + "\nPermanent residence:\n" + permanent.toString() + "\nTemporary residence:\n" + temporary.toString();
    }
}
