package client;


import bean.LineItem;
import bean.Order;
import bean.ShoppingCart;

import javax.naming.InitialContext;

public class Client
{
   public static void main(String[] args) throws Exception
   {
      InitialContext ctx = new InitialContext();
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
