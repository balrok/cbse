package client;


import bean.IUserMgt;
import bean.Calendar;

import javax.naming.InitialContext;

public class Client
{
    public static void main(String[] args) throws Exception
    {
        InitialContext ctx = new InitialContext();
        IUserMgt uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");
        System.out.println("starting login");
        uMgt.login("name", "email");
        Calendar cal = uMgt.getUserCalendar("email");
        if (cal == null)
            System.out.println("this user doesn't have a calendar --> error");
    }
}
