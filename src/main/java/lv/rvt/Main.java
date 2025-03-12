package lv.rvt;

import java.util.*;

import lv.rvt.tools.Login;

public class Main 
{
    public static void main( String[] args )
    {
        System.out.println("Hello World!");
        System.out.println("Eddy ready");
        Login login = new Login("alo", "hacha");
        login.getUsername();
    }
}
