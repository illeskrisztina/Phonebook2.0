package application.main.model.entity.dto;

public class SimplePersonDTO
{
  private String name;
  private int age;
  private int Id;

  public SimplePersonDTO()
  {
  }

  public SimplePersonDTO(String name, int age, int Id)
  {
    this.name = name;
    this.age = age;
    this.Id = Id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public void setId(int Id)
  {
    this.Id = Id;
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
}
