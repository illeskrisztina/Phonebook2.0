package application.main.model.entity.dto;

public class SimplePersonDTO
{
  private String name;
  private int age;
  private int id;

  public SimplePersonDTO()
  {
  }

  public SimplePersonDTO(String name, int age, int id)
  {
    this.name = name;
    this.age = age;
    this.id = id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public void setId(int id)
  {
    this.id = id;
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
    return id;
  }
}
