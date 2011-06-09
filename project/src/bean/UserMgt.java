package bean;

import bean.Calendar;
import bean.User;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
@Remote(IUserMgt.class)
public class UserMgt implements IUserMgt, java.io.Serializable
{
    @PersistenceContext
    private EntityManager manager;

    public void login(String name, String email)
    {
        System.out.println("login with "+name+":"+email);
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
        System.out.println("addUser with "+name+":"+email);
        User u = new User();
        u.email = email;
        u.name = name;
        u.calendar = new Calendar();
        manager.persist(u);
    }

    protected User getUser(String email)
    {
        System.out.println("getUser with "+email);
        User u = manager.find(User.class, email);
        return u;
    }

    public Calendar getUserCalendar(String email)
    {
        System.out.println("getUserCalendar with "+email);
        User u = getUser(email);
        if (u == null)
            return null;
        return u.calendar;
    }

    @Remove
    public void remove()
    {
    }
}
