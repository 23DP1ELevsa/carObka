package lv.rvt.tools;

import java.util.Scanner;

public class Login extends LoginRegister {
    private String username;
    private String password;
    private Scanner scanner = new Scanner(System.in);

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        System.out.print("Ievadiet username: ");
        this.username = scanner.nextLine();
        return this.username;
    }

    @Override
    public String getPassword() {
        System.out.print("Ievadiet password: ");
        this.password = scanner.nextLine();
        return this.password;
    }
}