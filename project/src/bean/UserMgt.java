package bean;

import bean.Calendar;
import javax.ejb.Remote;

// @Stateful
@Remote(ShoppingCart.class)
public class UserMgt implements IUserMgt, java.io.Serializable
{
   public void login(String name, String email)
   {
       // TODO find user or create one
       return;
   }

   public Calendar getUserCalendar(String email)
   {
       // TODO find user and get his calendar
       return null;
   }
}
