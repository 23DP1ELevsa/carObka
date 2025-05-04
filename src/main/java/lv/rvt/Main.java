package lv.rvt;

import java.util.InputMismatchException;
import java.util.Scanner;

import lv.rvt.tools.CarService;
import lv.rvt.tools.ClearConsole;
import lv.rvt.tools.Loading;
import lv.rvt.tools.Menu;
import lv.rvt.tools.Person;
import lv.rvt.tools.UserService;

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

        int choice;
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
                case 1 -> {
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    CarService.displayCarCollection(scanner, user);
                }
                case 2 -> {
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    UserService.loginOrRegister(scanner, user);
                }
                case 3 -> {
                    Loading.LoadingScreen(); // Ielādēšanas ekrāns
                    System.out.println(user.getColor() + "Brīdinājums: Sazināties ar mums var tikai pēc profila ienākšanas. Lūdzu, ieejiet savā profilā.");
                    UserService.loginOrRegister(scanner, user);
                }
                case 0 -> System.out.println("Paldies, ka izmantojāt CarObka!");
                default -> {
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                }
            }
        } while (choice != 0);
    }
}