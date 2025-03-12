package lv.rvt.tools;

import java.util.Scanner;

public class Register extends LoginRegister {
    private String username;
    private String password;
    private Scanner scanner = new Scanner(System.in);

    public Register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        System.out.print("Ievadiet username: ");
        this.username = scanner.nextLine();
        while (this.username.equals("hacha")) {
            System.out.println("User already exists - change username");
            this.username = scanner.nextLine();
        } 
        return this.username;
    }

    @Override
    public String getPassword() {
        System.out.print("Ievadiet password: ");
        this.password = scanner.nextLine();
        return this.password;
    }

    // saves in csv
}
