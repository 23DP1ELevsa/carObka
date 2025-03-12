package lv.rvt;

import lv.rvt.tools.Login;

public class Main {
    public static void main(String[] args) {
        Login login = new Login("", "");
        
        String enteredUsername = login.getUsername();
        String enteredPassword = login.getPassword();
        
        System.out.println("\nSveiki, " + enteredUsername + "!");
        System.out.println("Tava parole: " + enteredPassword);
    }
}