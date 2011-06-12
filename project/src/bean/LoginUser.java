package bean;

import bean.UserMgt;
import bean.ILoginUser;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.naming.InitialContext;

@Stateful
@Remote(ILoginUser.class)
public class LoginUser implements ILoginUser
{
    public void login(String name, String email)
    {
        IUserMgt uMgt = null;
        try {
            InitialContext ctx = new InitialContext();
            uMgt = (IUserMgt) ctx.lookup("UserMgt/remote");
        } catch (Exception e)
        {
            return;
        }
        uMgt.login(name, email);
    }

    @Remove
    public void remove()
    {
    }
}
