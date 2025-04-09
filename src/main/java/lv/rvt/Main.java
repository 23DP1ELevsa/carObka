package lv.rvt;

import lv.rvt.tools.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        ClearConsole.clearConsole();
        Scanner scanner = new Scanner(System.in);
        // Lietotāju ielāde no CSV
        UserService.loadUsers();
        // Ielādē automašīnu datus no CSV
        CarService.loadCars();
        
        if(UserService.users.isEmpty()){
            System.out.println("Lietotāju nav. Izveidojiet pirmo lietotāju (administratoru).");
        }

        int choice;
        do {
            Menu.StartMenu();
            System.out.print("Ievadiet izvēli: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Atstarpe ievades lasīšanai
            

            switch (choice) {
                case 1:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    CarService.displayCarCollection(scanner);
                    break;
                case 2:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    loginOrRegister(scanner);
                    break;
                case 3:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    System.out.println("Brīdinājums: Sazināties ar mums var tikai pēc profila ienākšanas. Lūdzu, ieejiet savā profilā.");
                    loginOrRegister(scanner);
                    break;
                case 0:
                    System.out.println("Paldies, ka izmantojāt CarObka!");
                    break;
                default:
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 0);
    }
    // Profila ieejas vai reģistrācijas metode
    public static void loginOrRegister(Scanner scanner) {
        int choice;
        do {
            System.out.println(ConsoleColors.GREEN+"\nIeiet profilā:");
            System.out.println("1 - Ieiet kā lietotājs");
            System.out.println("2 - Ieiet kā administrators");
            System.out.println("3 - Pievienot jaunu profilu");
            System.out.println("4 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            ClearConsole.clearConsole();
            scanner.nextLine();
    
            if (choice == 1) {
                System.out.println("\nIeiet kā lietotājs:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
                Person foundUser = UserService.findUser(username);
                Loading.LoadingScreen();
                if (foundUser != null && foundUser.validatePassword(password) && !foundUser.isAdmin()) {
                    System.out.println(ConsoleColors.CYAN+"Laipni lūgti, " + username + "!");
                    Menu.userMenu(scanner, foundUser);
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav lietotāja profils.");
                }
            } else if (choice == 2) {
                System.out.println("\nIeiet kā administrators:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
    
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
                Person foundUser = UserService.findUser(username);
                Loading.LoadingScreen();
                if (foundUser != null && foundUser.validatePassword(password) && foundUser.isAdmin()) {
                    System.out.println("Laipni lūgti, administrators " + username + "!");
                    Menu.adminMenu(scanner, foundUser);
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav administratora profils.");
                }
            } else if (choice == 3) {
                System.out.println("\nPievienot jaunu profilu:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String newUsername = scanner.nextLine();
                if (newUsername.equals("0")) continue;
    
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String newPassword = scanner.nextLine();
                if (newPassword.equals("0")) continue;
    
                Loading.LoadingScreen();
                if (UserService.findUser(newUsername) == null) {
                    boolean isAdmin = UserService.users.isEmpty();
                    UserService.users.add(new Person(newUsername, newPassword, isAdmin));
                    UserService.saveUsers();
                    if (isAdmin) {
                        System.out.println("Pirmais profils izveidots kā administrators!");
                    } else {
                        System.out.println("Profils " + newUsername + " veiksmīgi izveidots.");
                    }
                } else {
                    System.out.println("Lietotājs ar šādu lietotājvārdu jau pastāv.");
                }
            } else if (choice == 4) {
                System.out.println("Atgriežamies galvenajā izvēlnē...");
            } else {
                ClearConsole.clearConsole();
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }
}