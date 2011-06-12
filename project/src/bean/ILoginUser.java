package bean;

import javax.ejb.Remove;

public interface ILoginUser
{
    public void login(String name, String email);
    @Remove void remove();
}
