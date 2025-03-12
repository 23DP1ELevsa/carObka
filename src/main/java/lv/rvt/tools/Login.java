package lv.rvt.tools;

import java.util.Scanner;

public class Login extends LoginRegister {
    private String username;
    private String password;

    public Login(String username, String password){
        this.username = username;
        this.password = password;
    };
    Scanner scanner = new Scanner(System.in);
    @Override
    public String getUsername() {
        System.out.print("Username: ");
        this.username = String.valueOf(scanner.nextLine());
        return this.username;
    };
    
    @Override
    public String getPassword() {
        System.out.print("Password: ");
        this.password = String.valueOf(scanner.nextLine());
        return this.password;
    };
    
    public String returnUsername() {
        return this.username;
    }
    public String returnPassword() {
        return this.password;
    }
}
