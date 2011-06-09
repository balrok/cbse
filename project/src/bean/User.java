package bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User implements java.io.Serializable
{
   @Id
   public String email;

   public String name;
   // @OneToOne(fetch = FetchType.EAGER)
   // private Calendar calendar;

    public User()
    {
        //this.calendar = new Calendar();
    }
}
