package bean;

import bean.Calendar;

public interface IUserMgt
{
    public void login(String name, String email);
    public Calendar getUserCalendar(String email);
}
