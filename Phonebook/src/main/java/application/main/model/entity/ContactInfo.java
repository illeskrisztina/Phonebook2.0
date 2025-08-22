package application.main.model.entity;

public class ContactInfo
{
  private String type;
//  Something to format contacts so that they are uniform could be nice?
  private String contact;

  public ContactInfo(){}

  public ContactInfo setType(String type)
  {
    this.type = type;
    return this;
  }

  public ContactInfo setContact(String contact)
  {
    this.contact = contact;
    return this;
  }

  public String getType()
  {
    return type;
  }

  public String getContact()
  {
    return contact;
  }

  public boolean equals(Object obj)
  {
    if(obj == this)
    {
      return true;
    }
    if(obj == null || obj.getClass() != this.getClass())
    {
      return false;
    }

    ContactInfo other = (ContactInfo) obj;
    return other.type.equals(this.type) && other.contact.equals(this.contact);
  }

  public String toString()
  {
    return type + ": " + contact;
  }
}
