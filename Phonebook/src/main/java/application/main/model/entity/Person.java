package application.main.model.entity;

import java.util.Objects;

public class Person {
    private String name;
    private int age;
    private int id;
    private Address permanent;
    private Address temporary;


  public Person setName(String name)
  {
    this.name = name;
    return this;
  }

  public Person setAge(int age)
  {
    this.age = age;
    return this;
  }

  public Person setId(int id)
  {
    this.id = id;
    return this;
  }

  public Person setPermanentAddress(Address permanent)
  {
    //Everyone needs at least one address
    if(permanent == null)
    {
      throw new NullPointerException("Permanent address cannot be null");
    }
    this.permanent = permanent;
    return this;
  }

  public Person setTemporaryAddress(Address temporary)
  {
    this.temporary = temporary;
    return this;
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
