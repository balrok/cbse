package bean;

import bean.Calendar;
import javax.ejb.Remove;

public interface IUserMgt
{
    public void login(String name, String email);
    public Calendar getUserCalendar(String email);
    @Remove void remove();
}
