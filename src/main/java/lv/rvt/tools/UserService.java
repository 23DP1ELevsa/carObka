package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {
    public static ArrayList<Person> users = new ArrayList<>();
    public static final String CSV_FILE = "/workspaces/carObka/data/users.csv";
    
    // Lietotāja dzēšanas metode
    public static void deleteUser(Scanner scanner, Person user) {
        while (true) {
            // Izvadīt lietotāju sarakstu tabulas veidā ar rāmjiem
            System.out.println(user.getColor() + "\nLietotāju saraksts:");
            System.out.println("+-----+----------------------+----------+-------------------+");
            System.out.printf("| %-3s | %-20s | %-8s | %-17s |\n", "Nr.", "Lietotājvārds", "Admins", "Iemīļotās mašīnas");
            System.out.println("+-----+----------------------+----------+-------------------+");
            int index = 1;
            for (Person u : users) {
                System.out.printf("| %-3d | %-20s | %-8s | %-17d |\n", index++, u.getUsername(), u.isAdmin() ? "Jā" : "Nē", u.getFavorites().size());
            }
            System.out.println("+-----+----------------------+----------+-------------------+");
    
            System.out.print("\nIevadiet lietotāja kārtas numuru, kuru vēlaties dzēst (vai '0', lai atgrieztos): ");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                return;
            }
    
            try {
                int userIndex = Integer.parseInt(input) - 1;
                if (userIndex >= 0 && userIndex < users.size()) {
                    Person userToDelete = users.get(userIndex);
                    if (userToDelete.isAdmin()) {
                        ClearConsole.clearConsole();
                        System.out.println("Administrators nevar tikt dzēsts.");
                        continue;
                    }
                    users.remove(userToDelete);
                    saveUsers();
                    System.out.println("Profils " + userToDelete.getUsername() + " veiksmīgi dzēsts.");
                    return;
                } else {
                    ClearConsole.clearConsole();
                    System.out.println("Nepareizs kārtas numurs. Mēģiniet vēlreiz.");
                }
            } catch (NumberFormatException e) {
                ClearConsole.clearConsole();
                System.out.println("Lūdzu ievadiet derīgu skaitli.");
            }
        }
    }

    // Lietotāju saraksta metode
    public static void listUsers(Scanner scanner) {
        System.out.println(ConsoleColors.RED + "\nLietotāju saraksts:");
        System.out.println("+-----+----------------------+----------+-------------------+");
        System.out.printf("| %-3s | %-20s | %-8s | %-17s |\n", "Nr.", "Lietotājvārds", "Admins", "Iemīļotās mašīnas");
        System.out.println("+-----+----------------------+----------+-------------------+");
        int index = 1;
        for (Person user : users) {
            System.out.printf("| %-3d | %-20s | %-8s | %-17d |\n", index++, user.getUsername(), user.isAdmin() ? "Jā" : "Nē", user.getFavorites().size());
        }
        System.out.println("+-----+----------------------+----------+-------------------+");
    
        System.out.print("\nIevadiet '0', lai atgrieztos: ");
        String input = scanner.nextLine();
        if (input.equals("0")) {
            return;
        } else {
            ClearConsole.clearConsole();
            System.out.println("Nepareiza ievade. Mēģiniet vēlreiz.");
            listUsers(scanner);
        }
    }

    // Meklē lietotāju pēc lietotājvārda
    public static Person findUser(String username) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Lietotāju ielāde no CSV faila
    public static void loadUsers() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4); // Sadalīt līdz 4 daļām: username, password, isAdmin, favorites
                if (parts.length >= 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    boolean isAdmin = Boolean.parseBoolean(parts[2].trim());
                    Person user = new Person(username, password, isAdmin);
    
                    // Ielādē iemīļotās mašīnas, ja tās ir norādītas
                    if (parts.length == 4 && !parts[3].isEmpty()) {
                        String[] favoriteCars = parts[3].split(",");
                        for (String carData : favoriteCars) {
                            String[] carParts = carData.split("\\|");
                            if (carParts.length == 10) {
                                String brand = carParts[0].trim();
                                String model = carParts[1].trim();
                                int year = Integer.parseInt(carParts[2].trim());
                                int horsepower = Integer.parseInt(carParts[3].trim());
                                String fuelType = carParts[4].trim();
                                String drive = carParts[5].trim();
                                String generation = carParts[6].trim();
                                double fuelConsumption = Double.parseDouble(carParts[7].trim());
                                int price = Integer.parseInt(carParts[8].trim());
                                String description = carParts[9].trim();
                                Car car = new Car(brand, model, year, horsepower, fuelType, drive, generation, fuelConsumption, price, description);
                                user.addFavorite(car);
                            }
                        }
                    }
    
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Lietotāju ievades kļūda: " + e.getMessage());
        }
    }

    // Saglabā lietotāju datus CSV failā
    public static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Person user : users) {
                // Sagatavo iemīļoto mašīnu sarakstu kā komatu atdalītu vērtību
                StringBuilder favorites = new StringBuilder();
                for (Car car : user.getFavorites()) {
                    favorites.append(car.getBrand()).append("|")
                            .append(car.getModel()).append("|")
                            .append(car.getYear()).append("|")
                            .append(car.getHorsepower()).append("|")
                            .append(car.getFuelType()).append("|")
                            .append(car.getDrive()).append("|")
                            .append(car.getGeneration()).append("|")
                            .append(car.getFuelConsumption()).append("|")
                            .append(car.getPrice()).append("|")
                            .append(car.getDescription()).append(",");
                }
                // Noņem pēdējo komatu, ja ir iemīļotās mašīnas
                if (favorites.length() > 0) {
                    favorites.setLength(favorites.length() - 1);
                }

                // Saglabā lietotāju ar iemīļotajām mašīnām
                pw.println(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin() + "," + favorites);
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot lietotājus: " + e.getMessage());
        }
    }

    // Profila ieejas vai reģistrācijas metode
    public static void loginOrRegister(Scanner scanner, Person user) {
        int choice = -1;
        do {
            System.out.println(ConsoleColors.GREEN + "\nIeiet profilā:");
            System.out.println("1 - Ieiet kā lietotājs");
            System.out.println("2 - Ieiet kā administrators");
            System.out.println("3 - Pievienot jaunu profilu");
            System.out.println("4 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Kļūda: Lūdzu ievadiet skaitli!");
                choice = -1; // Nepareiza izvēle
            }

            ClearConsole.clearConsole();
            scanner.nextLine(); // Atstarpe ievades lasīšanai

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
                    System.out.println(ConsoleColors.CYAN + "Laipni lūgti, " + username + "!");
                    Menu.userMenu(scanner, foundUser);
                } else {
                    System.out.println(user.getColor() + "Nederīgs lietotāja vārds vai parole, vai arī profils nav lietotāja profils.");
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
                    System.out.println(user.getColor() + "Nederīgs lietotāja vārds vai parole, vai arī profils nav administratora profils.");
                }
            } else if (choice == 3) {
                System.out.println("\nPievienot jaunu profilu:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String newUsername = scanner.nextLine();
                if (newUsername.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String newPassword = scanner.nextLine();
                if (newPassword.equals("0")) {
                    ClearConsole.clearConsole();
                    continue;
                }
                Loading.LoadingScreen();
                if (UserService.findUser(newUsername) == null) {
                    boolean isAdmin = UserService.users.isEmpty();
                    UserService.users.add(new Person(newUsername, newPassword, isAdmin));
                    UserService.saveUsers();
                    if (isAdmin) {
                        System.out.println(user.getColor() + "Pirmais profils izveidots kā administrators!");
                    } else {
                        System.out.println(user.getColor() + "Profils " + newUsername + " veiksmīgi izveidots.");
                    }
                } else {
                    System.out.println(user.getColor() + "Lietotājs ar šādu lietotājvārdu jau pastāv.");
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
