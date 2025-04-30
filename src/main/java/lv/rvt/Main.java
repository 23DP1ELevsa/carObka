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

        if (UserService.users.isEmpty()) {
            System.out.println("Lietotāju nav. Izveidojiet pirmo lietotāju (administratoru).");
        }

        int choice = -1;
        do {
            Menu.StartMenu();
            System.out.print("Ievadiet izvēli: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Kļūda: Lūdzu ievadiet skaitli!");
                choice = -1; // Nepareiza izvēle
            }

            scanner.nextLine(); // Atstarpe ievades lasīšanai
            Person user = new Person();

            switch (choice) {
                case 1:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    CarService.displayCarCollection(scanner, user);
                    break;
                case 2:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    UserService.loginOrRegister(scanner, user);
                    break;
                case 3:
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    System.out.println(user.getColor() + "Brīdinājums: Sazināties ar mums var tikai pēc profila ienākšanas. Lūdzu, ieejiet savā profilā.");
                    UserService.loginOrRegister(scanner, user);
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
}