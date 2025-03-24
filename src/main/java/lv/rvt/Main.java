package lv.rvt;

import lv.rvt.tools.*;
import java.util.*;
import java.io.*;

public class Main {
    private static ArrayList<Person> users = new ArrayList<>();
    private static final String CSV_FILE = "data/users.csv";

    // Automobiļu kolekcijas mainīgās
    private static ArrayList<Car> cars = new ArrayList<>();
    private static final String CARS_FILE = "data/cars.csv";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Lietotāju ielāde no CSV
        loadUsers();
        // Ielādē automašīnu datus no CSV
        loadCars();
        
        if(users.isEmpty()){
            System.out.println("Lietotāju nav. Izveidojiet pirmo lietotāju (administratoru).");
        }

        int choice;
        do {
            System.out.println("\nCarObka - automobiļu kolekcija");
            System.out.println("1 - Kolekcijas apskate");
            System.out.println("2 - Ieiet profilā vai reģistrēties");
            System.out.println("3 - Sazināties ar mums");
            System.out.println("0 - Iziet no programmas");
            System.out.print("Ievadiet izvēli: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Atstarpe ievades lasīšanai
            Loading loading = new Loading();

            switch (choice) {
                case 1:
                    loading.LoadingScreen(); // Ielādēšanas ekrāns
                    displayCarCollection(scanner);
                    break;
                case 2:
                loading.LoadingScreen(); // Ielādēšanas ekrāns
                    loginOrRegister(scanner);
                    break;
                case 3:
                    loading.LoadingScreen(); // Ielādēšanas ekrāns
                    contactUs(scanner);
                    break;
                case 0:
                    System.out.println("Paldies, ka izmantojāt CarObka!");
                    break;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 0);
    }

    // Kolekcijas apskate – pēc markas izvēles parādās tikai pamatdati un pēc tam iespēja redzēt papildinformāciju
    private static void displayCarCollection(Scanner scanner) {
        int brandChoice;
        Loading loading = new Loading();
        do {
            System.out.println("\nKolekcija:");
            System.out.println("1 - Audi");
            System.out.println("2 - BMW");
            System.out.println("3 - Mercedes-Benz");
            System.out.println("4 - Porsche");
            System.out.println("5 - Volkswagen");
            System.out.println("6 - Atgriezties uz sākumu");
            System.out.print("Ievadiet izvēli: ");
            brandChoice = scanner.nextInt();
            scanner.nextLine();
            
            String selectedBrand = null;
            switch(brandChoice) {
                case 1: 
                    selectedBrand = "Audi"; 
                    break;
                case 2: 
                    selectedBrand = "BMW"; 
                    break;
                case 3: 
                    selectedBrand = "Mercedes-Benz"; 
                    break;
                case 4: 
                    selectedBrand = "Porsche"; 
                    break;
                case 5:   
                    selectedBrand = "Volkswagen"; 
                    break;
                case 6:
                    System.out.println("Atgriežamies uz sākumu...");
                    return;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
            
            if (selectedBrand != null) {
                displayCarsByBrand(selectedBrand, scanner);
            }
        } while(brandChoice != 6);
    }

    // Parāda tabulu ar mašīnu pamatdatiem pēc izvēlētās markas un ļauj apskatīt detalizētu informāciju
    // Parāda tabulu ar mašīnu pamatdatiem pēc izvēlētās markas un ļauj apskatīt detalizētu informāciju
    private static void displayCarsByBrand(String brand, Scanner scanner) {
        Loading loading = new Loading();
        List<Car> brandCars = new ArrayList<>();
        int index = 1;
        loading.LoadingScreen();
        System.out.println("\n" + brand + " modeļi:");
        System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s\n", "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s\n", index, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive());
                brandCars.add(car);
                index++;
            }
        }
        if (brandCars.isEmpty()) {
            System.out.println("Nav atrasti modeļi šai markai.");
            return;
        }
        System.out.print("\nIevadiet modeļa numuru, lai redzētu detalizētu informāciju (vai 0, lai atgrieztos): ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        loading.LoadingScreen();
        if (selection > 0 && selection <= brandCars.size()) {
            Car selectedCar = brandCars.get(selection - 1);
            int backChoice = 0;
            // Cikls, kas atkārtoti parāda detalizēto informāciju, kamēr nav ievadīts "1"
            do {
                System.out.println("\nDetalizēta informācija par " + selectedCar.getBrand() + " " + selectedCar.getModel() + ":");
                System.out.println("Izlaides gads: " + selectedCar.getYear());
                System.out.println("Zirgspēki: " + selectedCar.getHorsepower());
                System.out.println("Degviela: " + selectedCar.getFuelType());
                System.out.println("Piedziņa: " + selectedCar.getDrive());
                System.out.println("Paaudze: " + selectedCar.getGeneration());
                System.out.println("Apraksts: " + selectedCar.getDescription());
                System.out.println("\nIevadiet 1, lai atgrieztos:");
                backChoice = scanner.nextInt();
                scanner.nextLine();
            } while (backChoice != 1);
        } else if (selection != 0) {
            System.out.println("Nepareiza izvēle.");
        }
    }

    // Ielādē automašīnu datus no CSV faila
private static void loadCars() {
    File file = new File(CARS_FILE);
    if (!file.exists()) {
        System.out.println("Cars data file (" + CARS_FILE + ") nav atrasts.");
        return;
    }
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            // CSV formāts: marka,modelis,izlaides gads,zirgspēki,degviela,piedziņa,paaudze,apraksts
            String[] parts = line.split(",", 8);
            if (parts.length == 8) {
                String brand = parts[0].trim();
                String model = parts[1].trim();
                int year = Integer.parseInt(parts[2].trim());
                int horsepower = Integer.parseInt(parts[3].trim());
                String fuelType = parts[4].trim();
                String drive = parts[5].trim();
                String generation = parts[6].trim();
                String description = parts[7].trim();
                cars.add(new Car(brand, model, year, horsepower, fuelType, drive, generation, description));
            }
        }
    } catch (IOException e) {
        System.out.println("Kļūda, ielādējot cars.csv: " + e.getMessage());
    }
}

    // Saglabā automašīnu datus CSV failā
    private static void saveCars() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CARS_FILE))) {
            for (Car car : cars) {
                pw.println(car.getBrand() + "," + car.getModel() + "," + car.getYear() + "," +
                        car.getHorsepower() + "," + car.getFuelType() + "," + car.getDrive() + "," +
                        car.getGeneration() + "," + car.getDescription());
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot cars.csv: " + e.getMessage());
        }
    }


    // Profila ieejas vai reģistrācijas metode (nemainīta)
    private static void loginOrRegister(Scanner scanner) {
        int choice;
        Loading loading = new Loading();
        do {
            System.out.println("\nIeiet profilā:");
            System.out.println("1 - Ieiet kā lietotājs");
            System.out.println("2 - Ieiet kā administrators");
            System.out.println("3 - Pievienot jaunu profilu");
            System.out.println("4 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\nIeiet kā lietotājs:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                loading.LoadingScreen();
                if (foundUser != null && foundUser.validatePassword(password) && !foundUser.isAdmin()) {
                    System.out.println("Laipni lūgti, " + username + "!");
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav lietotāja profils.");
                }
            } else if (choice == 2) {
                loading.LoadingScreen();
                System.out.println("\nIeiet kā administrators:");
                System.out.print("Lietotāja vārds (vai '0' lai atgriezties): ");
                String username = scanner.nextLine();
                if (username.equals("0")) continue;
                System.out.print("Parole (vai '0' lai atgriezties): ");
                String password = scanner.nextLine();
                if (password.equals("0")) continue;
                Person foundUser = findUser(username);
                loading.LoadingScreen();
                if (foundUser != null && foundUser.validatePassword(password) && foundUser.isAdmin()) {
                    System.out.println("Laipni lūgti, administrators " + username + "!");
                    adminMenu(scanner);
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
                loading.LoadingScreen();
                if (findUser(newUsername) == null) {
                    boolean isAdmin = users.isEmpty();
                    users.add(new Person(newUsername, newPassword, isAdmin));
                    saveUsers();
                    if(isAdmin){
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
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    // Administratora izvēlne ar iespēju pārvaldīt lietotājus un pievienot jaunas mašīnas
    private static void adminMenu(Scanner scanner) {
        int adminChoice;
        Loading loading = new Loading();
        do {
            System.out.println("\nAdministrācijas izvēlne:");
            System.out.println("1 - Dzēst profilu");
            System.out.println("2 - Parādīt profilu sarakstu");
            System.out.println("3 - Pievienot jaunu mašīnu (marku/modeli)");
            System.out.println("0 - Iziet no administratora izvēlnes");
            System.out.print("Ievadiet izvēli: ");
            adminChoice = scanner.nextInt();
            scanner.nextLine();

            if (adminChoice == 1) {
                deleteUser(scanner);
            } else if (adminChoice == 2) {
                listUsers();
            } else if (adminChoice == 3) {
                addNewCar(scanner);
            } else if (adminChoice == 0) {
                System.out.println("Izeja no administratora izvēlnes...");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (adminChoice != 0);
    }

    // Administratora metode jaunas mašīnas (markas/modela) pievienošanai
    private static void addNewCar(Scanner scanner) {
        System.out.println("\nPievienot jaunu mašīnu");
        System.out.print("Ievadiet marku: ");
        String brand = scanner.nextLine().trim();
        System.out.print("Ievadiet modeli: ");
        String model = scanner.nextLine().trim();
        System.out.print("Ievadiet izlaides gadu: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ievadiet zirgspēkus: ");
        int horsepower = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ievadiet degvielas tipu (piem., Benzīns, Dīzelis, Elektriska): ");
        String fuelType = scanner.nextLine().trim();
        System.out.print("Ievadiet piedziņu (piem., FWD, AWD, RWD): ");
        String drive = scanner.nextLine().trim();
        System.out.print("Ievadiet paaudzi (piem., I, II, III): ");
        String generation = scanner.nextLine().trim();
        System.out.println("Ievadiet detalizētu aprakstu (2-3 teikumi): ");
        String description = scanner.nextLine().trim();

        Car newCar = new Car(brand, model, year, horsepower, fuelType, drive, generation, description);

        // Meklējam, vai jau ir kādi ieraksti ar šādu marku
        int insertIndex = cars.size(); // noklusējuma gadījumā pievienot beigās
        for (int i = 0; i < cars.size(); i++) {
            // Ja atrodam ierakstu ar šādu marku, saglabājam pēdējo tā atrašanās vietu
            if (cars.get(i).getBrand().equalsIgnoreCase(brand)) {
                insertIndex = i + 1;
            }
        }
        // Ja jau ir vairāki ieraksti ar šo marku, jauns ieraksts tiks ievietots pēc pēdējā
        cars.add(insertIndex, newCar);
        saveCars();
        System.out.println("Jauna mašīna veiksmīgi pievienota!");
    }


    // Lietotāja dzēšanas metode (nemainīta)
    private static void deleteUser(Scanner scanner) {
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
    private static void listUsers() {
        System.out.println("\nLietotāju saraksts:");
        for (Person user : users) {
            System.out.println("Lietotājs: " + user.getUsername() + " (Administrators: " + user.isAdmin() + ")");
        }
    }

    // Meklē lietotāju pēc lietotājvārda
    private static Person findUser(String username) {
        for (Person user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Lietotāju ielāde no CSV faila
    private static void loadUsers() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    boolean isAdmin = Boolean.parseBoolean(parts[2].trim());
                    users.add(new Person(username, password, isAdmin));
                }
            }
        } catch (IOException e) {
            System.out.println("Lietotāju ievades kļūda " + e.getMessage());
        }
    }

    // Lietotāju saglabāšana CSV failā
    private static void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (Person user : users) {
                pw.println(user.getUsername() + "," + user.getPassword() + "," + user.isAdmin());
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot lietotājus: " + e.getMessage());
        }
    }

    // Metode, lai sazinātos ar mums (nemainīta)
    private static void contactUs(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nSazināties ar mums:");
            System.out.println("1 - Sazināties");
            System.out.println("2 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                int subChoice;
                do {
                    System.out.println("\nSaziņa ar mums:");
                    System.out.println("1 - Atstāt atsauksmi");
                    System.out.println("2 - Paziņot par kļūdu");
                    System.out.println("3 - Iedot savu ideju");
                    System.out.println("4 - Atgriezties galvenajā izvēlnē");
                    System.out.print("Ievadiet izvēli: ");
                    subChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (subChoice == 1) {
                        System.out.println("Lūdzu, atstājiet savu atsauksmi.");
                    } else if (subChoice == 2) {
                        System.out.println("Lūdzu, aprakstiet kļūdu.");
                    } else if (subChoice == 3) {
                        System.out.println("Lūdzu, aprakstiet savu ideju.");
                    } else if (subChoice == 4) {
                        System.out.println("Atgriežamies galvenajā izvēlnē...");
                    } else {
                        System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                    }
                } while (subChoice != 4);
            } else if (choice == 2) {
                System.out.println("Atgriežamies galvenajā izvēlnē...");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 2);
    }

    // Person klase
    static class Person {
        private String username;
        private String password;
        private boolean admin;

        public Person(String username, String password, boolean admin) {
            this.username = username;
            this.password = password;
            this.admin = admin;
        }

        public String getUsername() {
            return username;
        }
        
        public String getPassword() {
            return password;
        }

        public boolean isAdmin() {
            return admin;
        }

        public boolean validatePassword(String password) {
            return this.password.equals(password);
        }
    }

    // Car klase ar papildu lauku aprakstam
    // Car klase ar papildu lauku degvielas tipam
    static class Car {
        private String brand;
        private String model;
        private int year;
        private int horsepower;
        private String fuelType;   // Jaunais lauks
        private String drive;
        private String generation;
        private String description;

        public Car(String brand, String model, int year, int horsepower, String fuelType, String drive, String generation, String description) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.horsepower = horsepower;
            this.fuelType = fuelType;
            this.drive = drive;
            this.generation = generation;
            this.description = description;
        }

        public String getBrand() {
            return brand;
        }
        public String getModel() {
            return model;
        }
        public int getYear() {
            return year;
        }
        public int getHorsepower() {
            return horsepower;
        }
        public String getFuelType() { // Getter jaunajam laukam
            return fuelType;
        }
        public String getDrive() {
            return drive;
        }
        public String getGeneration() {
            return generation;
        }
        public String getDescription() {
            return description;
        }
    }
}