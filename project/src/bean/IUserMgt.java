package bean;

import bean.Calendar;
import bean.ILoginUser;
import javax.ejb.Remove;

public interface IUserMgt extends ILoginUser
{
    public void login(String name, String email);
    public Calendar getUserCalendar(String email);
    @Remove void remove();
}
