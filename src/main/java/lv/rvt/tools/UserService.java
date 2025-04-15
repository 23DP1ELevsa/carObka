package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    public static ArrayList<Person> users = new ArrayList<>();
    public static final String CSV_FILE = "/workspaces/carObka/data/users.csv";
    
    // Lietotāja dzēšanas metode
    public static void deleteUser(Scanner scanner, Person user) {
        // Izvadīt lietotāju sarakstu
        listUsers();
        user.getColor();
        System.out.print("Ievadiet lietotāja vārdu, kuru vēlaties dzēst (vai '0', lai atgrieztos): ");
        String usernameToDelete = scanner.nextLine();
        if (usernameToDelete.equals("0")) {
            System.out.println("Atcelts.");
            return;
        }
        Person userToDelete = findUser(usernameToDelete);
        if (userToDelete != null) {
            if (userToDelete.isAdmin()) {
                System.out.println("Administrators nevar tikt dzēsts.");
                return;
            }
            users.remove(userToDelete);
            saveUsers();
            System.out.println("Profils " + usernameToDelete + " veiksmīgi dzēsts.");
        } else {
            System.out.println("Lietotājs ar šādu lietotājvārdu nav atrasts.");
        }
    }

    // Lietotāju saraksta metode
    public static void listUsers() {
        System.out.println("\nLietotāju saraksts:");
        for (Person user : users) {
            System.out.println("Lietotājs: " + user.getUsername() + " (Administrators: " + user.isAdmin() + ")");
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
}
