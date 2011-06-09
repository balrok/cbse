package client;


import bean.LineItem;
import bean.Order;
import bean.ShoppingCart;
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


      ShoppingCart cart = (ShoppingCart) ctx.lookup("ShoppingCartBean/remote");

      System.out.println("Buying 2 memory sticks");
      cart.buy("Memory stick", 2, 500.00);
      System.out.println("Buying a laptop");
      cart.buy("Laptop", 1, 2000.00);

      System.out.println("Print cart:");
      Order order = cart.getOrder();
      System.out.println("Total: $" + order.getTotal());
      for (LineItem item : order.getLineItems())
      {
         System.out.println(item.getQuantity() + "     " + item.getProduct() + "     " + item.getSubtotal());
      }

      System.out.println("Checkout");
      cart.checkout();

   }
}
