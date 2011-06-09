package bean;

import bean.Calendar;
import bean.User;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// @Stateful
@Remote(ShoppingCart.class)
public class UserMgt implements IUserMgt, java.io.Serializable
{
    @PersistenceContext
    private EntityManager manager;

    public void login(String name, String email)
    {
        User u = getUser(email);
        if (u == null)
        {
            addUser(name, email);
            u = getUser(email);
        }
        return;
    }

    protected void addUser(String name, String email)
    {
        User u = new User();
        u.calendar = new Calendar();
        manager.persist(u);
    }

    protected User getUser(String email)
    {
        User u = manager.find(User.class, email);
        return u;
    }

    public Calendar getUserCalendar(String email)
    {
        User u = getUser(email);
        if (u == null)
            return null;
        return u.calendar;
    }
}
