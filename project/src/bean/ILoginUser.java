package bean;

public interface ILoginUser
{
    // returns true when a new user was created
    public Boolean login(String name, String email);
}
