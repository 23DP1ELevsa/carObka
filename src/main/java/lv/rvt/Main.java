package lv.rvt;

import lv.rvt.tools.Login;
import lv.rvt.tools.Register;

public class Main {
    public static void main(String[] args) {
        Register register = new Register("hacha", "lupa");
        
        String enteredUsername = register.getUsername();
        String enteredPassword = register.getPassword();
        
        System.out.println("\nSveiki, " + enteredUsername + "!");
        System.out.println("Tava parole: " + enteredPassword);
    }
}