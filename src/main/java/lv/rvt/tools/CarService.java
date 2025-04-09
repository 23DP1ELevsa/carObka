package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;


public class CarService {
    
    // Kārto automobiļus pēc izvēlētās īpašības un secības
    public static void sortCars(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nKārtošanas izvēlne:");
            System.out.println("1 - Kārtot pēc markas");
            System.out.println("2 - Kārtot pēc modeļa");
            System.out.println("3 - Kārtot pēc izlaides gada");
            System.out.println("4 - Kārtot pēc zirgspēkiem");
            System.out.println("5 - Kārtot pēc paaudzes");
            System.out.println("6 - Kārtot pēc vidējā degvielas patēriņa");
            System.out.println("7 - Kārtot pēc cenas");
            System.out.println("0 - Atgriezties uz kolekciju");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();
    
            if (choice == 0) {
                return; // Atgriežas uz kolekciju
            }

            System.out.println("\n1 - Augošā secībā");
            System.out.println("2 - Dilstošā secībā");
            System.out.print("Izvēlieties kārtošanas secību: ");
            int order = scanner.nextInt();
            scanner.nextLine();
            Loading.LoadingScreen();
    
            List<Car> sortedCars = new ArrayList<>(Car.cars);
    
            switch (choice) {
                case 1:
                    sortedCars.sort(order == 1 ? Comparator.comparing(Car::getBrand) : Comparator.comparing(Car::getBrand).reversed());
                    break;
                case 2:
                    sortedCars.sort(order == 1 ? Comparator.comparing(Car::getModel) : Comparator.comparing(Car::getModel).reversed());
                    break;
                case 3:
                    sortedCars.sort(order == 1 ? Comparator.comparingInt(Car::getYear) : Comparator.comparingInt(Car::getYear).reversed());
                    break;
                case 4:
                    sortedCars.sort(order == 1 ? Comparator.comparingInt(Car::getHorsepower) : Comparator.comparingInt(Car::getHorsepower).reversed());
                    break;
                case 5:
                    sortedCars.sort(order == 1 ? Comparator.comparing(Car::getGeneration) : Comparator.comparing(Car::getGeneration).reversed());
                    break;
                case 6:
                    sortedCars.sort(order == 1 ? Comparator.comparingDouble(Car::getFuelConsumption) : Comparator.comparingDouble(Car::getFuelConsumption).reversed());
                    break;
                case 7:
                    sortedCars.sort(order == 1 ? Comparator.comparingInt(Car::getPrice) : Comparator.comparingInt(Car::getPrice).reversed());
                    break;
                default:
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                    continue;
            }
    
            System.out.println("\nAutomobiļi sakārtoti:\n");
            displayCarList(sortedCars);
        } while (true);
    }

    // Filtrē automobiļus pēc izvēlētās īpašības un secības
    public static void filterCars(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nFiltrēšanas izvēlne:");
            System.out.println("1 - Filtrēt pēc markas");
            System.out.println("2 - Filtrēt pēc modeļa");
            System.out.println("3 - Filtrēt pēc izlaides gada diapazona");
            System.out.println("4 - Filtrēt pēc zirgspēku diapazona");
            System.out.println("5 - Filtrēt pēc degvielas tipa");
            System.out.println("6 - Filtrēt pēc piedziņas");
            System.out.println("7 - Filtrēt pēc paaudzes");
            System.out.println("8 - Filtrēt pēc vidējā degvielas patēriņa diapazona");
            System.out.println("9 - Filtrēt pēc cenas diapazona");
            System.out.println("0 - Atgriezties uz kolekciju");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                return; // Atgriežas uz kolekciju
            }

            List<Car> filteredCars = new ArrayList<>();

            switch (choice) {
                case 1:
                    System.out.print("Ievadiet marku: ");
                    String brand = scanner.nextLine().trim();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                case 2:
                    System.out.print("Ievadiet modeli: ");
                    String model = scanner.nextLine().trim();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getModel().equalsIgnoreCase(model))
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                case 3:
                    System.out.print("Ievadiet sākuma gadu: ");
                    int startYear = scanner.nextInt();
                    System.out.print("Ievadiet beigu gadu: ");
                    int endYear = scanner.nextInt();
                    scanner.nextLine();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getYear() >= startYear && car.getYear() <= endYear)
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                case 4:
                    System.out.print("Ievadiet minimālo zirgspēku skaitu: ");
                    int minHorsepower = scanner.nextInt();
                    System.out.print("Ievadiet maksimālo zirgspēku skaitu: ");
                    int maxHorsepower = scanner.nextInt();
                    scanner.nextLine();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getHorsepower() >= minHorsepower && car.getHorsepower() <= maxHorsepower)
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                case 5:
                    filteredCars = filterByDynamicOption(scanner, "degvielas tipu", Car::getFuelType);
                    break;
                case 6:
                    filteredCars = filterByDynamicOption(scanner, "piedziņu", Car::getDrive);
                    break;
                case 7:
                    filteredCars = filterByDynamicOption(scanner, "paaudzi", Car::getGeneration);
                    break;
                case 8:
                    System.out.print("Ievadiet minimālo patēriņu: ");
                    double minConsumption = scanner.nextDouble();
                    System.out.print("Ievadiet maksimālo patēriņu: ");
                    double maxConsumption = scanner.nextDouble();
                    scanner.nextLine();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getFuelConsumption() >= minConsumption && car.getFuelConsumption() <= maxConsumption)
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                case 9:
                    System.out.print("Ievadiet minimālo cenu: ");
                    int minPrice = scanner.nextInt();
                    System.out.print("Ievadiet maksimālo cenu: ");
                    int maxPrice = scanner.nextInt();
                    scanner.nextLine();
                    filteredCars = Car.cars.stream()
                        .filter(car -> car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                        .collect(Collectors.toList());
                        Loading.LoadingScreen();
                    break;
                default:
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                    continue;
            }

            ClearConsole.clearConsole();
            if (filteredCars.isEmpty()) {
                Empty.EmptyScreen();
                System.out.println("Nav atrasti automobiļi ar norādītajiem kritērijiem.");
            } else {
                System.out.println("\nFiltrētie automobiļi:\n");
                displayCarList(filteredCars);
            }
        } while (true);
    }

    public static List<Car> filterByDynamicOption(Scanner scanner, String optionName, Function<Car, String> getter) {
        // Dinamiski ģenerē unikālu vērtību sarakstu no automašīnu datiem
        Set<String> options = Car.cars.stream()
            .map(getter)
            .collect(Collectors.toCollection(TreeSet::new)); // TreeSet nodrošina kārtotu sarakstu

        // Parāda izvēlni
        System.out.println("\nPieejamās " + optionName + " vērtības:");
        int index = 1;
        Map<Integer, String> optionMap = new LinkedHashMap<>();
        for (String option : options) {
            optionMap.put(index++, option);
            System.out.println((index - 1) + " - " + option);
        }
        System.out.println("0 - Atgriezties uz filtrēšanas izvēlni");
        System.out.print("Ievadiet izvēli: ");
        int optionChoice = scanner.nextInt();
        scanner.nextLine();
        Loading.LoadingScreen();

        if (optionChoice == 0) {
            return Collections.emptyList(); // Atgriežas uz filtrēšanas izvēlni
        }

        ClearConsole.clearConsole();
        String selectedOption = optionMap.get(optionChoice);
        if (selectedOption == null) {
            System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            return Collections.emptyList();
        }

        // Filtrē automašīnas pēc izvēlētās vērtības
        return Car.cars.stream()
            .filter(car -> getter.apply(car).equalsIgnoreCase(selectedOption))
            .collect(Collectors.toList());
    }

    // Administratora metode jaunas mašīnas (markas/modela) pievienošanai
    protected static void addNewCar(Scanner scanner) {
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
        Car.cars.add(newCar);
        saveCars(); // Saglabā izmaiņas CSV failā
        System.out.println("Jauna mašīna veiksmīgi pievienota!");
    
        // Atjauno kolekcijas skatu
        displayCarCollection(scanner);
    }

    // Administratora metode automašīnas dzēšanai
    protected static void deleteCar(Scanner scanner) {
        if (Car.cars.isEmpty()) {
            Empty.EmptyScreen();
            System.out.println("Nav pieejamu automašīnu, ko dzēst.");
            return;
        }
    
        // Dinamiski ģenerē unikālu marku sarakstu
        Map<Integer, String> brandMap = new LinkedHashMap<>();
        int index = 1;
        for (Car car : Car.cars) {
            if (!brandMap.containsValue(car.getBrand())) {
                brandMap.put(index++, car.getBrand());
            }
        }
    
        // Parāda marku izvēlni
        System.out.println("\nPieejamās markas:");
        for (Map.Entry<Integer, String> entry : brandMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println(index + " - Atgriezties uz administratora izvēlni");
        System.out.print("Ievadiet markas numuru, lai turpinātu: ");
        int brandChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (brandChoice == index) {
            System.out.println("Atgriežamies uz administratora izvēlni...");
            return;
        }
    
        String selectedBrand = brandMap.get(brandChoice);
        if (selectedBrand == null) {
            System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            return;
        }
    
        // Filtrē modeļus pēc izvēlētās markas
        List<Car> brandCars = new ArrayList<>();
        for (Car car : Car.cars) {
            if (car.getBrand().equalsIgnoreCase(selectedBrand)) {
                brandCars.add(car);
            }
        }
    
        // Parāda modeļu izvēlni
        System.out.println("\n" + selectedBrand + " modeļi:");
        for (int i = 0; i < brandCars.size(); i++) {
            Car car = brandCars.get(i);
            System.out.println((i + 1) + " - " + car.getModel() + " (" + car.getYear() + ")");
        }
        System.out.println((brandCars.size() + 1) + " - Atgriezties uz marku izvēlni");
        System.out.print("Ievadiet modeļa numuru, lai dzēstu: ");
        int modelChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (modelChoice == brandCars.size() + 1) {
            System.out.println("Atgriežamies uz marku izvēlni...");
            return;
        }
    
        if (modelChoice > 0 && modelChoice <= brandCars.size()) {
            Car carToDelete = brandCars.get(modelChoice - 1);
            Car.cars.remove(carToDelete);
    
            // Noņem automašīnu no visiem lietotāju iemīļoto sarakstiem
            for (Person user : UserService.users) {
                user.getFavorites().removeIf(car -> car.getBrand().equals(carToDelete.getBrand())
                        && car.getModel().equals(carToDelete.getModel())
                        && car.getYear() == carToDelete.getYear());
            }
    
            saveCars(); // Saglabā izmaiņas automašīnu sarakstā
            UserService.saveUsers(); // Saglabā izmaiņas lietotāju datos
            System.out.println("Automobilis " + carToDelete.getBrand() + " " + carToDelete.getModel() + " veiksmīgi dzēsts.");
    
            // Atjauno kolekcijas skatu
            displayCarCollection(scanner);
        } else {
            System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
        }
    }

    // Administratora metode automašīnas rediģēšanai
    protected static void editCar(Scanner scanner) {
        if (Car.cars.isEmpty()) {
            Empty.EmptyScreen();
            System.out.println("Nav pieejamu automašīnu, ko rediģēt.");
            return;
        }
    
        // Dinamiski ģenerē unikālu marku sarakstu
        Map<Integer, String> brandMap = new LinkedHashMap<>();
        int index = 1;
        for (Car car : Car.cars) {
            if (!brandMap.containsValue(car.getBrand())) {
                brandMap.put(index++, car.getBrand());
            }
        }
    
        System.out.println("\nPieejamās markas:");
        brandMap.forEach((key, value) -> System.out.println(key + " - " + value));
        System.out.println(index + " - Atgriezties");
        System.out.print("Izvēlieties marku: ");
        int brandChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (brandChoice == index) return;
    
        String selectedBrand = brandMap.get(brandChoice);
        if (selectedBrand == null) {
            System.out.println("Nepareiza izvēle!");
            return;
        }
    
        List<Car> brandCars = Car.cars.stream()
            .filter(c -> c.getBrand().equalsIgnoreCase(selectedBrand))
            .collect(Collectors.toList());
    
        System.out.println("\n" + selectedBrand + " modeļi:");
        for (int i = 0; i < brandCars.size(); i++) {
            Car car = brandCars.get(i);
            System.out.println((i + 1) + " - " + car.getModel() + " (" + car.getYear() + ")");
        }
    
        System.out.print("Izvēlieties modeli: ");
        int modelChoice = scanner.nextInt();
        scanner.nextLine();
    
        if (modelChoice < 1 || modelChoice > brandCars.size()) {
            System.out.println("Nepareiza izvēle!");
            return;
        }
    
        Car originalCar = brandCars.get(modelChoice - 1);
        int carIndex = Car.cars.indexOf(originalCar);
    
        Car updatedCar = originalCar;
    
        int editChoice;
        do {
            System.out.println("\nRediģējat automašīnas informāciju:");
            System.out.println("1 - Marka (" + updatedCar.getBrand() + ")");
            System.out.println("2 - Modelis (" + updatedCar.getModel() + ")");
            System.out.println("3 - Izlaides gads (" + updatedCar.getYear() + ")");
            System.out.println("4 - Zirgspēki (" + updatedCar.getHorsepower() + ")");
            System.out.println("5 - Degvielas tips (" + updatedCar.getFuelType() + ")");
            System.out.println("6 - Piedziņa (" + updatedCar.getDrive() + ")");
            System.out.println("7 - Paaudze (" + updatedCar.getGeneration() + ")");
            System.out.println("8 - Vidējais degvielas patēriņš (" + updatedCar.getFuelConsumption() + " l/100km)");
            System.out.println("9 - Cena (" + updatedCar.getPrice() + " EUR)");
            System.out.println("10 - Apraksts (" + updatedCar.getDescription() + ")");
            System.out.println("11 - Saglabāt izmaiņas un iziet");
            System.out.print("Ievadiet izvēli: ");
            editChoice = scanner.nextInt();
            scanner.nextLine();
    
            switch (editChoice) {
                case 1:
                    System.out.print("Ievadiet jauno marku: ");
                    updatedCar = new Car(scanner.nextLine().trim(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    break;
                case 2:
                    System.out.print("Ievadiet jauno modeli: ");
                    updatedCar = new Car(updatedCar.getBrand(), scanner.nextLine().trim(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    break;
                case 3:
                    System.out.print("Ievadiet jauno izlaides gadu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), scanner.nextInt(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("Ievadiet jaunos zirgspēkus: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            scanner.nextInt(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Ievadiet jauno degvielas tipu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), scanner.nextLine().trim(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    break;
                case 6:
                    System.out.print("Ievadiet jauno piedziņu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), scanner.nextLine().trim(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    break;
                case 7:
                    System.out.print("Ievadiet jauno paaudzi: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            scanner.nextLine().trim(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    break;
                case 8:
                    System.out.print("Ievadiet jauno vidējo degvielas patēriņu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), scanner.nextDouble(), updatedCar.getPrice(),
                            updatedCar.getDescription());
                    scanner.nextLine();
                    break;
                case 9:
                    System.out.print("Ievadiet jauno cenu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), scanner.nextInt(),
                            updatedCar.getDescription());
                    scanner.nextLine();
                    break;
                case 10:
                    System.out.print("Ievadiet jauno aprakstu: ");
                    updatedCar = new Car(updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(),
                            updatedCar.getHorsepower(), updatedCar.getFuelType(), updatedCar.getDrive(),
                            updatedCar.getGeneration(), updatedCar.getFuelConsumption(), updatedCar.getPrice(),
                            scanner.nextLine().trim());
                    break;
                case 11:
                    System.out.println("Saglabājam izmaiņas...");
                    break;
                default:
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (editChoice != 11);
    
        // Aizstāj veco automašīnas objektu ar jauno kolekcijā
        Car.cars.set(carIndex, updatedCar);
    
        // Atjaunina automašīnas informāciju visos lietotāju iemīļotajos sarakstos
        updateCarInFavorites(originalCar, updatedCar);
    
        // Saglabā izmaiņas
        saveCars();
        UserService.saveUsers();
    
        System.out.println("Izmaiņas veiksmīgi saglabātas!");
    }

    public static void updateCarInFavorites(Car originalCar, Car updatedCar) {
        for (Person user : UserService.users) {
            List<Car> favorites = user.getFavorites();
            for (int i = 0; i < favorites.size(); i++) {
                Car favoriteCar = favorites.get(i);
                if (favoriteCar.equals(originalCar)) {
                    favorites.set(i, updatedCar); // Aizstāj veco automašīnas objektu ar jauno
                }
            }
        }
    }
    public static void displayCarList(List<Car> carList) {
        System.out.format("%-15s %-15s %-10s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
            "Marka", "Modelis", "Gads", "Zirgspēki", "Degviela", "Piedziņa", "Paaudze", "Patēriņš", "Cena");
        System.out.println("-".repeat(135));
        for (Car car : carList) {
            System.out.format("%-15s %-15s %-10d %-15d %-15s %-15s %-15s %-15.1f %-15d\n", 
                car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), 
                car.getFuelType(), car.getDrive(), car.getGeneration(), car.getFuelConsumption(), car.getPrice());
        }
    }

    // Ielādē automašīnu datus no CSV faila
    public static void loadCars() {
        File file = new File(Car.CARS_FILE);
        if (!file.exists()) {
            System.out.println("Cars data file (" + Car.CARS_FILE + ") nav atrasts.");
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
                    Car.cars.add(new Car(brand, model, year, horsepower, fuelType, drive, generation, fuelConsumption, price, description));
                }
            }
        } catch (IOException e) {
            System.out.println("Kļūda, ielādējot cars.csv: " + e.getMessage());
        }
    }

    // Saglabā automašīnu datus CSV failā
    public static void saveCars() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(Car.CARS_FILE))) {
            for (Car car : Car.cars) {
                String description = car.getDescription().replace("\"", ""); // Escape double quotes
                String line = String.format("%s,%s,%d,%d,%s,%s,%s,%.1f,%d,\"%s\"",
                    car.getBrand(),
                    car.getModel(),
                    car.getYear(),
                    car.getHorsepower(),
                    car.getFuelType(),
                    car.getDrive(),
                    car.getGeneration(),
                    car.getFuelConsumption(),
                    car.getPrice(),
                    car.getDescription());
                pw.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении автомобилей: " + e.getMessage());
        }
    }

    public static void displayFavorites(Person user, Scanner scanner) {
        List<Car> favorites = user.getFavorites();
        if (favorites.isEmpty()) {
            Empty.EmptyScreen();
            return;
        }
        
        ClearConsole.clearConsole();
        System.out.println("\nIemīļotās mašīnas:");
        System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
        "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
        System.out.println("-".repeat(124));
        int index = 1;
        for (Car car : favorites) {
            System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n", 
                index++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
        }
    
        // Izvēlne, lai atgrieztos
        int choice;
        do {
            System.out.println("\n1 - Atgriezties uz iepriekšējo izvēlni");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            ClearConsole.clearConsole();
            if (choice != 1) {
                ClearConsole.clearConsole();
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (choice != 1);
    }

    public static void addFavoriteCar(Scanner scanner, Person user) {
        int brandChoice;
        do {
            // Dinamiski ģenerē unikālu marku sarakstu no automašīnu datiem
            Map<Integer, String> brandMap = new LinkedHashMap<>();
            int index = 1;
            for (Car car : Car.cars) {
                if (!brandMap.containsValue(car.getBrand())) {
                    brandMap.put(index++, car.getBrand());
                }
            }
    
            // Parāda dinamisko kolekcijas izvēlni
            System.out.println("\nKolekcija:");
            for (Map.Entry<Integer, String> entry : brandMap.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println(index + " - Atgriezties uz lietotāja izvēlni");
            System.out.print("Ievadiet izvēli: ");
            brandChoice = scanner.nextInt();
            scanner.nextLine();
    
            if (brandChoice == index) {
                ClearConsole.clearConsole();
                return;
            }
    
            // Apstrādā izvēlēto marku
            String selectedBrand = brandMap.get(brandChoice);
            if (selectedBrand != null) {
                List<Car> brandCars = new ArrayList<>();
                int carIndex = 1;
                System.out.println("\n" + selectedBrand + " modeļi:");
                System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                        "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
                for (Car car : Car.cars) {
                    if (car.getBrand().equalsIgnoreCase(selectedBrand)) {
                        System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n",
                                carIndex++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(),
                                car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
                        brandCars.add(car);
                    }
                }
    
                if (brandCars.isEmpty()) {
    
                    Empty.EmptyScreen();
                    continue;
                }
    
                int carChoice;
                do {
                    System.out.print("\nIevadiet modeļa numuru, lai pievienotu iemīļotajām (vai 0, lai atgrieztos): ");
                    carChoice = scanner.nextInt();
                    scanner.nextLine();
    
                    if (carChoice > 0 && carChoice <= brandCars.size()) {
                        Car selectedCar = brandCars.get(carChoice - 1);
    
                        // Pārbaude, vai mašīna jau ir iemīļoto sarakstā
                        if (user.getFavorites().stream().anyMatch(car -> car.equals(selectedCar))) {
                            System.out.println("Mašīna " + selectedCar.getBrand() + " " + selectedCar.getModel() + " jau ir iemīļoto sarakstā.");
                        } else {
                            user.addFavorite(selectedCar);
                            UserService.saveUsers(); // Saglabā izmaiņas failā
                            System.out.println("Mašīna " + selectedCar.getBrand() + " " + selectedCar.getModel() + " pievienota iemīļotajām.");
                        }
                    } else if (carChoice != 0) {
                        System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                    }
                } while (carChoice != 0);
            } else {
                ClearConsole.clearConsole();
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (true);
    }

    // Dzēš iemīļoto mašīnu no lietotāja profila
    public static void removeFavoriteCar(Scanner scanner, Person user) {
        List<Car> favorites = user.getFavorites();
        if (favorites.isEmpty()) {
            Empty.EmptyScreen();
            return;
        }
    
        int carIndex;
        do {
            // Parāda iemīļoto mašīnu sarakstu
            ClearConsole.clearConsole();
            System.out.println("\nIemīļotās mašīnas:");
            System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                    "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
            int index = 1;
            for (Car car : favorites) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n",
                        index++, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(),
                        car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
            }
    
            // Lietotājs izvēlas, kuru mašīnu dzēst
            System.out.print("\nIevadiet mašīnas numuru, lai dzēstu no iemīļotajām (vai 0, lai atgrieztos): ");
            carIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            ClearConsole.clearConsole();
    
            if (carIndex >= 0 && carIndex < favorites.size()) {
                Car removedCar = favorites.get(carIndex);
                user.removeFavorite(removedCar);
                UserService.saveUsers(); // Saglabā izmaiņas failā
                System.out.println("Mašīna " + removedCar.getBrand() + " " + removedCar.getModel() + " dzēsta no iemīļotajām.");
            } else if (carIndex == -1) {
                return;
            } else {
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (carIndex != -1);
    }

    public static void manageFavorites(Scanner scanner, Person user) {
        int choice;
        ClearConsole.clearConsole();
        do {
            System.out.println("\nIemīļotās mašīnas:");
            System.out.println("1 - Apskatīt iemīļotās mašīnas");
            System.out.println("2 - Pievienot mašīnu iemīļotajām");
            System.out.println("3 - Dzēst mašīnu no iemīļotajām");
            System.out.println("0 - Atgriezties uz lietotāja izvēlni");
            System.out.print("Ievadiet izvēli: ");
            choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    Loading.LoadingScreen();
                    displayFavorites(user, scanner);
                    break;
                case 2:
                    Loading.LoadingScreen();
                    addFavoriteCar(scanner, user);
                    break;
                case 3:
                    Loading.LoadingScreen();
                    removeFavoriteCar(scanner, user);
                    break;
                case 0:
                    ClearConsole.clearConsole();
                    return; // Atgriežas uz lietotāja izvēlni
                default:
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (true);
    }
    // Kolekcijas apskate – pēc markas izvēles parādās tikai pamatdati un pēc tam iespēja redzēt papildinformāciju
    public static void displayCarCollection(Scanner scanner) {
        int brandChoice;
        do {
            // Dinamiski ģenerē unikālu marku sarakstu no automašīnu datiem
            Map<Integer, String> brandMap = new LinkedHashMap<>();
            int index = 1;
            for (Car car : Car.cars) {
                if (!brandMap.containsValue(car.getBrand())) {
                    brandMap.put(index++, car.getBrand());
                }
            }
    
            // Parāda dinamisko kolekcijas izvēlni
            ClearConsole.clearConsole();
            System.out.println("\nKolekcija:");
            for (Map.Entry<Integer, String> entry : brandMap.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            System.out.println(index + " - Kārtot automašīnas");
            System.out.println((index + 1) + " - Filtrēt automašīnas");
            System.out.println("0 - Atgriezties uz sākumu");
            System.out.print("Ievadiet izvēli: ");
            brandChoice = scanner.nextInt();
            scanner.nextLine();
            Loading.LoadingScreen(); // Ielādēšanas ekrāns
    
            if (brandChoice == 0) {
                return; // Atgriežas uz sākumu
            } else if (brandChoice == index) {
                CarService.sortCars(scanner); // Izsauc kārtošanas izvēlni
            } else if (brandChoice == index + 1) {
                CarService.filterCars(scanner); // Izsauc filtrēšanas izvēlni
            } else {
                // Apstrādā izvēlēto marku
                String selectedBrand = brandMap.get(brandChoice);
                if (selectedBrand != null) {
                    displayCarsByBrand(selectedBrand, scanner);
                } else {
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                }
            }
        } while (true);
    }
    // Parāda tabulu ar mašīnu pamatdatiem pēc izvēlētās markas un ļauj apskatīt detalizētu informāciju
    public static void displayCarsByBrand(String brand, Scanner scanner) {
        List<Car> brandCars = new ArrayList<>();
        int index = 1;
        System.out.println("\n" + brand + " modeļi:");
        System.out.format("%-5s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", 
            "Nr.", "Marka", "Modelis", "Izlaides gads", "Zirgspēki", "Degviela", "Piedziņa", "Patēriņš", "Cena");
        System.out.println("-".repeat(124));
        for (Car car : Car.cars) {
            if (car.getBrand().equalsIgnoreCase(brand)) {
                System.out.format("%-5d %-15s %-15s %-15d %-15d %-15s %-15s %-15.1f %-15d\n", 
                    index, car.getBrand(), car.getModel(), car.getYear(), car.getHorsepower(), car.getFuelType(), car.getDrive(), car.getFuelConsumption(), car.getPrice());
                brandCars.add(car);
                index++;
            }
        }
        if (brandCars.isEmpty()) {
            Empty.EmptyScreen();
            return;
        }
    
        int selection;
        do {
            System.out.print("\nIevadiet modeļa numuru, lai redzētu detalizētu informāciju (vai 0, lai atgrieztos): ");
            selection = scanner.nextInt();

            Loading.LoadingScreen();
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
                    System.out.println("2 - Aprēķināt, cik litrus vajag lai nobrauktu noteikto attālumu:");
                    System.out.println("3 - Aprēķināt, cik kilometrus var nobraukt ar noteikto degvielas litru skaitu:");
                    System.out.print("Ievadiet izvēli: ");
                    int nextChoice = scanner.nextInt();
                    Loading.LoadingScreen();
                    scanner.nextLine(); // Atstarpe ievades lasīšanai
    
                    if (nextChoice == 1) {
                        return; // Atgriežas pie kolekcijas
                    } else if (nextChoice == 2) {
                        double distance = 0;
                        boolean validInput = false;
                        while (!validInput) {
                            System.out.print("Ievadiet attālumu kilometros: ");
                            if (scanner.hasNextDouble()) {
                                distance = scanner.nextDouble();
                                validInput = true;
                            } else {
                                System.out.println("Nepareiza ievade. Lūdzu, ievadiet derīgu skaitli.");
                                scanner.next(); // Clear the invalid input
                            }
                        }
                        scanner.nextLine(); // Atstarpe ievades lasīšanai
                        double fuelNeeded = (selectedCar.getFuelConsumption() * distance) / 100;
                        System.out.printf("Lai nobrauktu %.2f km, būs nepieciešami %.2f litri degvielas.\n", distance, fuelNeeded);
                    } else if (nextChoice == 3) {
                        double fuelAmount = 0;
                        boolean validInput = false;
                        while (!validInput) {
                            System.out.print("Ievadiet degvielas daudzumu litros: ");
                            if (scanner.hasNextDouble()) {
                                fuelAmount = scanner.nextDouble();
                                validInput = true;
                            } else {
                                System.out.println("Nepareiza ievade. Lūdzu, ievadiet derīgu skaitli.");
                                scanner.next(); // Clear the invalid input
                            }
                        }
                        scanner.nextLine(); // Atstarpe ievades lasīšanai
                        double distancePossible = (fuelAmount * 100) / selectedCar.getFuelConsumption();
                        System.out.printf("Ar %.2f litriem degvielas var nobraukt aptuveni %.2f kilometrus.\n", fuelAmount, distancePossible);
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
}
