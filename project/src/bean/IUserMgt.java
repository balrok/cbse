package bean;

import bean.Calendar;
import bean.ILoginUser;

public interface IUserMgt extends ILoginUser
{
    public Boolean login(String name, String email);
    public Calendar getUserCalendar(String email);
}
