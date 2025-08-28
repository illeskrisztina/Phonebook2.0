package application.main.model.Entity;

public class Person
{
  private String name;
  private int age;
  private int Id;
  private Address permanent;
  private Address temporary;

  public Person()
  {
  }

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

  public Person setId(int Id)
  {
    this.Id = Id;
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

  public String getName()
  {
    return name;
  }

  public int getAge()
  {
    return age;
  }

  public int getId()
  {
    return Id;
  }

  public Address getPermanentAddress()
  {
    return permanent;
  }

  public Address getTemporaryAddress()
  {
    return temporary;
  }

  public boolean equals(Object obj)
  {
    if (obj == this)
    {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }

    Person other = (Person) obj;

    return other.name.equals(this.name) && other.age == this.age
        && other.Id == this.Id && other.permanent.equals(this.permanent)
        && other.temporary.equals(this.temporary);
  }

  public String toString()
  {
    if(temporary == null)
    {
      return "Name: " + name + "\nAge: " + age + "\nPermanent residence:\n" + permanent.toString();
    }
    return "Name: " + name + "\nAge: " + age + "\nPermanent residence:\n" + permanent.toString() + "\nTemporary residence:\n" + temporary.toString();
  }
}
