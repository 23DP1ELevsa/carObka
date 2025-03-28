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
        Loading loading = new Loading();
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
                    System.out.println("Brīdinājums: Sazināties ar mums var tikai pēc profila ienākšanas. Lūdzu, ieejiet savā profilā.");
                    loginOrRegister(scanner);
                    break;
                case 0:
                    System.out.println("Paldies, ka izmantojāt CarObka!");
                    break;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 0);
    }

    // Jaunā lietotāja izvēlne, kas tiek attēlota pēc veiksmīgas pieslēgšanās (neadministratoram)
    private static void userMenu(Scanner scanner, Person user) {
        int choice;
        do {
            System.out.println("\nCarObka - automašīnu kolekcija");
            System.out.println("1 - Kolekcija");
            System.out.println("2 - Iemīļotās mašīnas");
            System.out.println("3 - Sazināties ar mums");
            System.out.println("4 - Iziet no profila");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            Loading loading = new Loading();
    
            switch (choice) {
                case 1:
                    loading.LoadingScreen();
                    displayCarCollection(scanner);
                    break;
                case 2:
                    loading.LoadingScreen();
                    manageFavorites(scanner, user);
                    break;
                case 3:
                    loading.LoadingScreen();
                    contactUs(scanner);
                    break;
                case 4:
                    System.out.println("Iziet no profila...");
                    break;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    // Kolekcijas apskate – pēc markas izvēles parādās tikai pamatdati un pēc tam iespēja redzēt papildinformāciju
    private static void displayCarCollection(Scanner scanner) {
        int brandChoice;
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
            Loading loading = new Loading();
            loading.LoadingScreen();
            
            String selectedBrand = null;
            switch (brandChoice) {
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
                    // Clear the console before starting the loop
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    return;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                    continue;
            }
            
            if (selectedBrand != null) {
                displayCarsByBrand(selectedBrand, scanner);
            }
        } while(brandChoice != 6);
    }

    // Parāda tabulu ar mašīnu pamatdatiem pēc izvēlētās markas un ļauj apskatīt detalizētu informāciju
    private static void displayCarsByBrand(String brand, Scanner scanner) {
        List<Car> brandCars = new ArrayList<>();
        int index = 1;
        System.out.println("\n" + brand + " modeļi:");
        System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
            "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
        System.out.println("-".repeat(124));
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n", 
                    index, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
                brandCars.add(car);
                index++;
            }
        }
        if (brandCars.isEmpty()) {
            System.out.println("Nav atrasti modeļi šai markai.");
            return;
        }
    
        int selection;
        do {
            System.out.print("\nIevadiet modeļa numuru, lai redzētu detalizētu informāciju (vai 0, lai atgrieztos): ");
            selection = scanner.nextInt();
            Loading loading = new Loading();
            loading.LoadingScreen();
            scanner.nextLine(); // Atstarpe ievades lasīšanai
    
            if (selection > 0 && selection <= brandCars.size()) {
                Car selectedCar = brandCars.get(selection - 1);
                do {
                    System.out.println("\nDetalizēta informācija par " + selectedCar.getBrand() + " " + selectedCar.getModel() + ":");
                    System.out.println("Izlaides gads: " + selectedCar.getYear());
                    System.out.println("Zirgspēki: " + selectedCar.getHorsepower());
                    System.out.println("Degviela: " + selectedCar.getFuelType());
                    System.out.println("Piedziņa: " + selectedCar.getDrive());
                    System.out.println("Paaudze: " + selectedCar.getGeneration());
                    System.out.println("Vidējais degvielas patēriņš: " + selectedCar.getFuelConsumption() + " l/100km");
                    System.out.println("Cena: " + selectedCar.getPrice() + " EUR");
                    System.out.println("Apraksts: " + selectedCar.getDescription());
    
                    // Papildu izvēlne pēc detalizētas informācijas izvades
                    System.out.println("\nKo vēlaties darīt tālāk?");
                    System.out.println("1 - Atgriezties pie kolekcijas");
                    System.out.println("2 - Parādīt izvēlētās markas modeļus");
                    System.out.print("Ievadiet izvēli: ");
                    int nextChoice = scanner.nextInt();
                    loading.LoadingScreen();
                    scanner.nextLine(); // Atstarpe ievades lasīšanai
    
                    if (nextChoice == 1) {
                        return; // Atgriežas pie kolekcijas
                    } else if (nextChoice == 2) {
                        // Parāda izvēlētās markas modeļus tabulas veidā
                        System.out.println("\n" + brand + " modeļi:");
                        System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
                            "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
                        System.out.println("-".repeat(124));
                        int index2 = 1;
                        for (Car car : brandCars) {
                            System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n", 
                                index2++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
                        }
                    } else {
                        System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                    }
                } while (true);
            } else if (selection != 0) {
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                // Parāda izvēlētās markas modeļus tabulas veidā
                System.out.println("\n" + brand + " modeļi:");
                System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
                    "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
                System.out.println("-".repeat(124));
                int index2 = 1;
                for (Car car : brandCars) {
                    System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n", 
                        index2++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
                }
            }
        } while (selection != 0);
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
                // CSV formāts: marka,modelis,izlaides gads,zirgspēki,degviela,piedziņa,paaudze,vidējais patēriņš,cena,apraksts
                String[] parts = line.split(",", 10);
                if (parts.length == 10) {
                    String brand = parts[0].trim();
                    String model = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    int horsepower = Integer.parseInt(parts[3].trim());
                    String fuelType = parts[4].trim();
                    String drive = parts[5].trim();
                    String generation = parts[6].trim();
                    double fuelConsumption = Double.parseDouble(parts[7].trim());
                    int price = Integer.parseInt(parts[8].trim());
                    String description = parts[9].trim();
                    cars.add(new Car(brand, model, year, horsepower, fuelType, drive, generation, fuelConsumption, price, description));
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
                        car.getGeneration() + "," + car.getFuelConsumption() + "," + car.getPrice() + "," +
                        car.getDescription());
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot cars.csv: " + e.getMessage());
        }
    }

    private static void displayFavorites(Person user, Scanner scanner) {
        List<Car> favorites = user.getFavorites();
        if (favorites.isEmpty()) {
            System.out.println("Nav pievienotu iemīļoto mašīnu.");
            return;
        }
    
        int choice;
        do {
            System.out.println("\nIemīļotās mašīnas:");
            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n", 
                "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
            int index = 1;
            for (Car car : favorites) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n", 
                    index++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
            }
    
            // Izvēlne, lai atgrieztos
            System.out.println("\n1 - Atgriezties uz iepriekšējo izvēlni");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            Loading loading = new Loading();
            loading.LoadingScreen();
    
            if (choice != 1) {
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (choice != 1);
    }

    private static void addFavoriteCar(Scanner scanner, Person user) {
        int brandChoice;
        do {
            System.out.println("\nKolekcija:");
            System.out.println("1 - Audi");
            System.out.println("2 - BMW");
            System.out.println("3 - Mercedes-Benz");
            System.out.println("4 - Porsche");
            System.out.println("5 - Volkswagen");
            System.out.println("6 - Atgriezties uz lietotāja izvēlni");
            System.out.print("Ievadiet izvēli: ");
            brandChoice = scanner.nextInt();
            scanner.nextLine();
            Loading loading = new Loading();
            loading.LoadingScreen();
    
            String selectedBrand = null;
            switch (brandChoice) {
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
                    System.out.println("Atgriežamies uz lietotāja izvēlni...");
                    return;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
    
            if (selectedBrand != null) {
                List<Car> brandCars = new ArrayList<>();
                int index = 1;
                System.out.println("\n" + selectedBrand + " modeļi:");
                System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n",
                        "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
                for (Car car : cars) {
                    if (car.getBrand().equalsIgnoreCase(selectedBrand)) {
                        System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n",
                                index, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
                        brandCars.add(car);
                        index++;
                    }
                }
    
                if (brandCars.isEmpty()) {
                    System.out.println("Nav atrasti modeļi šai markai.");
                    continue;
                }
    
                int carChoice;
                do {
                    System.out.print("\nIevadiet modeļa numuru, lai pievienotu iemīļotajām (vai 0, lai atgrieztos): ");
                    carChoice = scanner.nextInt();
                    scanner.nextLine();
                    loading.LoadingScreen();
    
                    if (carChoice > 0 && carChoice <= brandCars.size()) {
                        Car selectedCar = brandCars.get(carChoice - 1);
    
                        // Pārbaude, vai mašīna jau ir iemīļoto sarakstā
                        if (user.getFavorites().stream().anyMatch(car -> car.getBrand().equals(selectedCar.getBrand())
                                && car.getModel().equals(selectedCar.getModel())
                                && car.getYear() == selectedCar.getYear())) {
                            System.out.println("Mašīna " + selectedCar.getBrand() + " " + selectedCar.getModel() + " jau ir iemīļoto sarakstā.");
    
                            // Parāda tabulu ar izvēlētās markas modeļiem
                            System.out.println("\n" + selectedBrand + " modeļi:");
                            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n",
                                    "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
                            int index2 = 1;
                            for (Car car : brandCars) {
                                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n",
                                        index2++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
                            }
                        } else {
                            user.addFavorite(selectedCar);
                            saveUsers(); // Saglabā izmaiņas failā
                            System.out.println("Mašīna " + selectedCar.getBrand() + " " + selectedCar.getModel() + " pievienota iemīļotajām.");
                            // Parāda tabulu ar izvēlētās markas modeļiem
                            System.out.println("\n" + selectedBrand + " modeļi:");
                            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n",
                                    "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
                            int index2 = 1;
                            for (Car car : brandCars) {
                                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n",
                                        index2++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
                            }
                        }
                    } else if (carChoice != 0) {
                        System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                        // Parāda tabulu ar izvēlētās markas modeļiem
                            System.out.println("\n" + selectedBrand + " modeļi:");
                            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n",
                                    "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
                            int index2 = 1;
                            for (Car car : brandCars) {
                                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n", 
                                        index2++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
                            }
                    }
                } while (carChoice != 0);
            }
        } while (brandChoice != 6);
    }

    private static void removeFavoriteCar(Scanner scanner, Person user) {
        List<Car> favorites = user.getFavorites();
        if (favorites.isEmpty()) {
            System.out.println("Nav pievienotu iemīļoto mašīnu.");
            return;
        }
        int carIndex;
        Loading loading = new Loading();
        do {
            // Parāda iemīļoto mašīnu sarakstu
            System.out.println("\nIemīļotās mašīnas:");
            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s\n", 
                "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Cena");
            int index = 1;
            for (Car car : favorites) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15d\n", 
                    index++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getPrice());
            }
    
            // Lietotājs izvēlas, kuru mašīnu dzēst
            System.out.print("\nIevadiet mašīnas numuru, lai dzēstu no iemīļotajām (vai 0, lai atgrieztos): ");
            carIndex = scanner.nextInt() - 1;
            scanner.nextLine();
    
            if (carIndex >= 0 && carIndex < favorites.size()) {
                Car removedCar = favorites.get(carIndex);
                user.removeFavorite(removedCar);
                saveUsers(); // Saglabā izmaiņas failā
                loading.LoadingScreen();
                System.out.println("Mašīna " + removedCar.getBrand() + " " + removedCar.getModel() + " dzēsta no iemīļotajām.");
            } else if (carIndex == -1) {
                loading.LoadingScreen();
                return;
            } else {
                loading.LoadingScreen();
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (carIndex != -1);
    }

    private static void manageFavorites(Scanner scanner, Person user) {
        int choice;
        Loading loading = new Loading();
        do {
            System.out.println("\nIemīļotās mašīnas:");
            System.out.println("1 - Apskatīt iemīļotās mašīnas");
            System.out.println("2 - Pievienot mašīnu iemīļotajām");
            System.out.println("3 - Dzēst mašīnu no iemīļotajām");
            System.out.println("4 - Atgriezties uz lietotāja izvēlni");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            loading.LoadingScreen();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    displayFavorites(user, scanner);
                    break;
                case 2:
                    addFavoriteCar(scanner, user);
                    break;
                case 3:
                    removeFavoriteCar(scanner, user);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    // Profila ieejas vai reģistrācijas metode
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
            System.out.print("\033[H\033[2J");
            System.out.flush();
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
                    userMenu(scanner, foundUser);
                } else {
                    System.out.println("Nederīgs lietotāja vārds vai parole, vai arī profils nav lietotāja profils.");
                }
            } else if (choice == 2) {
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
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    // Administratora izvēlne ar iespēju pārvaldīt lietotājus, pievienot jaunas mašīnas un izmantot paplašinātās datu operācijas
    private static void adminMenu(Scanner scanner) {
        int adminChoice;
        do {
            System.out.println("\nAdministrācijas izvēlne:");
            System.out.println("1 - Dzēst profilu");
            System.out.println("2 - Parādīt profilu sarakstu");
            System.out.println("3 - Pievienot jaunu mašīnu (marku/modeli)");
            System.out.println("4 - Paplašinātās datu operācijas");
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
            } else if (adminChoice == 4) {
                advancedDataOperationsMenu(scanner);
            } else if (adminChoice == 0) {
                System.out.println("Izeja no administratora izvēlnes...");
            } else {
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (adminChoice != 0);
    }

    // Paplašinātās datu operācijas izvēlne – ietver datu kārtošanu, filtrēšanu un apstrādi
    private static void advancedDataOperationsMenu(Scanner scanner) {
        int opChoice;
        do {
            System.out.println("\nPaplašinātās datu operācijas:");
            System.out.println("1 - Kārtot automobiļus: pēc izlaides gada (augšup) un zirgspēkiem (dilstoši)");
            System.out.println("2 - Kārtot automobiļus: pēc markas un modeļa (alfabētiski)");
            System.out.println("3 - Atrast automobiļus pēc gada diapazona");
            System.out.println("4 - Atrast automobiļus ar zirgspēkiem virs sliekšņa");
            System.out.println("5 - Atrast automobiļus pēc degvielas tipa");
            System.out.println("6 - Saskaitīt kopējos zirgspēkus izvēlētā gada diapazonā");
            System.out.println("7 - Saskaitīt automobiļu skaitu izvēlētā gada diapazonā");
            System.out.println("8 - Aprēķināt vidējos zirgspēkus visiem automobiļiem");
            System.out.println("9 - Atrast maksimālos zirgspēkus izvēlētai markai");
            System.out.println("0 - Atgriezties administratora izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            opChoice = scanner.nextInt();
            scanner.nextLine();
            Loading loading = new Loading();
            loading.LoadingScreen();

            switch(opChoice) {
                case 1:
                    sortCarsByYearThenHorsepower();
                    break;
                case 2:
                    sortCarsByBrandThenModel();
                    break;
                case 3:
                    filterCarsByYearRange(scanner);
                    break;
                case 4:
                    filterCarsByHorsepowerThreshold(scanner);
                    break;
                case 5:
                    filterCarsByFuelType(scanner);
                    break;
                case 6:
                    sumHorsepowerInYearRange(scanner);
                    break;
                case 7:
                    countCarsInYearRange(scanner);
                    break;
                case 8:
                    averageHorsepower(scanner);
                    break;
                case 9:
                    maxHorsepowerForBrand(scanner);
                    break;
                case 0:
                    System.out.println("Atgriežamies administratora izvēlnē...");
                    break;
                default:
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
            System.out.println(); // tukša rinda starp operācijām
        } while(opChoice != 0);
    }

    // Metode – kārto automobiļus pēc izlaides gada augošā secībā un, ja gadi sakrīt, pēc zirgspēkiem dilstošā secībā
    private static void sortCarsByYearThenHorsepower() {
        List<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, Comparator.comparingInt(Car::getYear)
                .thenComparing(Comparator.comparingInt(Car::getHorsepower).reversed()));
        System.out.println("Automobiļi sakārtoti pēc izlaides gada (augšup) un zirgspēkiem (dilstoši):");
        System.out.format("%-15s %-15s %-10s %-15s\n", "Marka", "Modelis", "Izlaides gads", "Zirgspēki");
        for (Car car : sortedCars) {
            System.out.format("%-15s %-15s %-10d %-15d\n", car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower());
        }
    }

    // Metode – kārto automobiļus pēc markas un modeļa alfabētiskā secībā
    private static void sortCarsByBrandThenModel() {
        List<Car> sortedCars = new ArrayList<>(cars);
        Collections.sort(sortedCars, Comparator.comparing(Car::getBrand)
                .thenComparing(Car::getModel));
        System.out.println("Automobiļi sakārtoti pēc markas un modeļa (alfabētiski):");
        System.out.format("%-15s %-15s %-10s %-15s\n", "Marka", "Modelis", "Izlaides gads", "Zirgspēki");
        for (Car car : sortedCars) {
            System.out.format("%-15s %-15s %-10d %-15d\n", car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower());
        }
    }

    // Metode – filtrē automobiļus pēc gada diapazona
    private static void filterCarsByYearRange(Scanner scanner) {
        System.out.print("Ievadiet sākuma gadu: ");
        int startYear = scanner.nextInt();
        System.out.print("Ievadiet beigu gadu: ");
        int endYear = scanner.nextInt();
        scanner.nextLine();
        List<Car> filtered = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() >= startYear && car.getYear() <= endYear) {
                filtered.add(car);
            }
        }
        if (filtered.isEmpty()) {
            System.out.println("Nav atrasti automobiļi gada diapazonā " + startYear + " - " + endYear + ".");
        } else {
            System.out.println("Atrasti automobiļi gada diapazonā " + startYear + " - " + endYear + ":");
            System.out.format("%-15s %-15s %-10s\n", "Marka", "Modelis", "Izlaides gads");
            for (Car car : filtered) {
                System.out.format("%-15s %-15s %-10d\n", car.getBrand(), car.getModel(), car.getYear());
            }
        }
    }

    // Metode – filtrē automobiļus, kuriem zirgspēki ir virs norādītā sliekšņa
    private static void filterCarsByHorsepowerThreshold(Scanner scanner) {
        System.out.print("Ievadiet zirgspēku slieksni: ");
        int threshold = scanner.nextInt();
        scanner.nextLine();
        List<Car> filtered = new ArrayList<>();
        for (Car car : cars) {
            if (car.getHorsepower() >= threshold) {
                filtered.add(car);
            }
        }
        if (filtered.isEmpty()) {
            System.out.println("Nav atrasti automobiļi ar zirgspēkiem virs " + threshold + ".");
        } else {
            System.out.println("Atrasti automobiļi ar zirgspēkiem virs " + threshold + ":");
            System.out.format("%-15s %-15s %-10s %-15s\n", "Marka", "Modelis", "Izlaides gads", "Zirgspēki");
            for (Car car : filtered) {
                System.out.format("%-15s %-15s %-10d %-15d\n", car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower());
            }
        }
    }

    // Metode – filtrē automobiļus pēc degvielas tipa
    private static void filterCarsByFuelType(Scanner scanner) {
        System.out.print("Ievadiet degvielas tipu: ");
        String fuel = scanner.nextLine().trim();
        List<Car> filtered = new ArrayList<>();
        for (Car car : cars) {
            if (car.getFuelType().equalsIgnoreCase(fuel)) {
                filtered.add(car);
            }
        }
        if (filtered.isEmpty()) {
            System.out.println("Nav atrasti automobiļi ar degvielas tipu " + fuel + ".");
        } else {
            System.out.println("Atrasti automobiļi ar degvielas tipu " + fuel + ":");
            System.out.format("%-15s %-15s %-10s %-15s\n", "Marka", "Modelis", "Izlaides gads", "Degviela");
            for (Car car : filtered) {
                System.out.format("%-15s %-15s %-10d %-15s\n", car.getBrand(), car.getModel(), car.getYear(), car.getFuelType());
            }
        }
    }

    // Aprēķins 1 – saskaita kopējos zirgspēkus automobiļiem izvēlētā gada diapazonā
    private static void sumHorsepowerInYearRange(Scanner scanner) {
        System.out.print("Ievadiet sākuma gadu: ");
        int startYear = scanner.nextInt();
        System.out.print("Ievadiet beigu gadu: ");
        int endYear = scanner.nextInt();
        scanner.nextLine();
        int sum = 0;
        for (Car car : cars) {
            if (car.getYear() >= startYear && car.getYear() <= endYear) {
                sum += car.getHorsepower();
            }
        }
        System.out.println("Kopējie zirgspēki no " + startYear + " līdz " + endYear + ": " + sum);
    }

    // Aprēķins 2 – saskaita automobiļu skaitu izvēlētā gada diapazonā
    private static void countCarsInYearRange(Scanner scanner) {
        System.out.print("Ievadiet sākuma gadu: ");
        int startYear = scanner.nextInt();
        System.out.print("Ievadiet beigu gadu: ");
        int endYear = scanner.nextInt();
        scanner.nextLine();
        int count = 0;
        for (Car car : cars) {
            if (car.getYear() >= startYear && car.getYear() <= endYear) {
                count++;
            }
        }
        System.out.println("Automobiļu skaits no " + startYear + " līdz " + endYear + ": " + count);
    }

    // Aprēķins 3 – aprēķina vidējos zirgspēkus visiem automobiļiem
    private static void averageHorsepower(Scanner scanner) {
        if (cars.isEmpty()) {
            System.out.println("Nav pieejamu datu aprēķiniem.");
            return;
        }
        int total = 0;
        for (Car car : cars) {
            total += car.getHorsepower();
        }
        double average = (double) total / cars.size();
        System.out.println("Vidējie zirgspēki visiem automobiļiem: " + average);
    }

    // Aprēķins 4 – nosaka maksimālos zirgspēkus izvēlētai markai
    private static void maxHorsepowerForBrand(Scanner scanner) {
        System.out.print("Ievadiet marku: ");
        String brand = scanner.nextLine().trim();
        int maxHP = -1;
        for (Car car : cars) {
            if (car.getBrand().equalsIgnoreCase(brand) && car.getHorsepower() > maxHP) {
                maxHP = car.getHorsepower();
            }
        }
        if (maxHP == -1) {
            System.out.println("Nav atrasts automobiļu ar marku: " + brand);
        } else {
            System.out.println("Maksimālie zirgspēki pie " + brand + ": " + maxHP);
        }
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
        System.out.print("Ievadiet vidējo degvielas patēriņu (litri uz 100 km): ");
        double fuelConsumption = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ievadiet cenu (eiro): ");
        int price = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ievadiet detalizētu aprakstu (2-3 teikumi): ");
        String description = scanner.nextLine().trim();
    
        // Izveido jaunu Car objektu ar visiem parametriem
        Car newCar = new Car(brand, model, year, horsepower, fuelType, drive, generation, fuelConsumption, price, description);
    
        // Pievieno jauno automašīnu sarakstam
        cars.add(newCar);
        saveCars(); // Saglabā izmaiņas CSV failā
        System.out.println("Jauna mašīna veiksmīgi pievienota!");
    }

    // Lietotāja dzēšanas metode
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
    private static void saveUsers() {
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

    // Metode, lai sazinātos ar mums
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
        private List<Car> favorites; // Saraksts iemīļotajām mašīnām
    
        public Person(String username, String password, boolean admin) {
            this.username = username;
            this.password = password;
            this.admin = admin;
            this.favorites = new ArrayList<>();
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
    
        public List<Car> getFavorites() {
            return favorites;
        }
    
        public void addFavorite(Car car) {
            favorites.add(car);
        }
    
        public void removeFavorite(Car car) {
            favorites.remove(car);
        }
    
        public int getFavoriteCount() {
            return favorites.size();
        }
    }

    // Car klase ar papildu lauku degvielas tipam
    static class Car {
        private String brand;
        private String model;
        private int year;
        private int horsepower;
        private String fuelType;
        private String drive;
        private String generation;
        private double fuelConsumption; // Jauns lauks: vidējais degvielas patēriņš
        private int price; // Jauns lauks: cena
        private String description;
    
        public Car(String brand, String model, int year, int horsepower, String fuelType, String drive, String generation, double fuelConsumption, int price, String description) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.horsepower = horsepower;
            this.fuelType = fuelType;
            this.drive = drive;
            this.generation = generation;
            this.fuelConsumption = fuelConsumption;
            this.price = price;
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
    
        public String getFuelType() {
            return fuelType;
        }
    
        public String getDrive() {
            return drive;
        }
    
        public String getGeneration() {
            return generation;
        }
    
        public double getFuelConsumption() {
            return fuelConsumption;
        }
    
        public int getPrice() {
            return price;
        }
    
        public String getDescription() {
            return description;
        }
    }
}