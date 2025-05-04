package lv.rvt.tools;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void StartMenu() {
        // ASCII ART
        System.out.println("        "+ ConsoleColors.WHITE_BACKGROUND +"__---------__"+ ConsoleColors.RESET);
        System.out.println("      "+ ConsoleColors.WHITE_BACKGROUND +"/ _-----------_ \\" + ConsoleColors.RESET);
        System.out.println("     "+ ConsoleColors.WHITE_BACKGROUND +"/ /"+ConsoleColors.BLUE_BACKGROUND+"             "+ ConsoleColors.WHITE_BACKGROUND +"\\ \\"+ ConsoleColors.RESET);
        System.out.println("     "+ ConsoleColors.WHITE_BACKGROUND +"| |"+ ConsoleColors.BLUE_BACKGROUND +"             "+ ConsoleColors.WHITE_BACKGROUND +"| |"+ ConsoleColors.RESET);
        System.out.println("     "+ConsoleColors.WHITE_BACKGROUND+"|_|"+ ConsoleColors.BLUE_BACKGROUND +"_____________"+ ConsoleColors.WHITE_BACKGROUND +"|_|"+ConsoleColors.RESET);
        System.out.println(" "+ConsoleColors.WHITE_BACKGROUND+"/-\\|                   |/-\\"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_BACKGROUND+"| _ |\\        0        /| _ |"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_BACKGROUND+"|"+ ConsoleColors.YELLOW_BACKGROUND+"(_)"+ ConsoleColors.WHITE_BACKGROUND +"| \\       !       / |"+ ConsoleColors.YELLOW_BACKGROUND+"(_)"+ ConsoleColors.WHITE_BACKGROUND+ "|"+ ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_BACKGROUND+"|___|__\\______!______/__|___|"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.WHITE_BACKGROUND+"[_________|"+ConsoleColors.BLACK_BACKGROUND_BRIGHT+"CarObka"+ConsoleColors.WHITE_BACKGROUND+"|_________]"+ConsoleColors.RESET);
        System.out.println(" "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+"||||"+ConsoleColors.RESET+"     ~~~~~~~~~     "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+"||||"+ConsoleColors.RESET+" ");
        System.out.println(" "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+ "`--'"+ConsoleColors.RESET+"                   "+ConsoleColors.BLACK_BACKGROUND_BRIGHT+"`--'"+ConsoleColors.RESET+" ");

        System.out.println(ConsoleColors.GREEN +"\nCarObka - automobiļu kolekcija");
        System.out.println("1 - Kolekcijas apskate");
        System.out.println("2 - Ieiet profilā vai reģistrēties");
        System.out.println("3 - Sazināties ar mums");
        System.out.println("0 - Iziet no programmas");
    }

    public static void userMenu(Scanner scanner, Person user) {
        int choice = -1;
        do {
            System.out.println(user.getColor() +"\nCarObka - automašīnu kolekcija");
            System.out.println("1 - Kolekcija");
            System.out.println("2 - Iemīļotās mašīnas");
            System.out.println("3 - Sazināties ar mums");
            System.out.println("4 - Iziet no profila");
            System.out.print("Ievadiet izvēli: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                Loading.LoadingScreen();
                switch (choice) {
                    case 1 -> CarService.displayCarCollection(scanner, user);
                    case 2 -> CarService.manageFavorites(scanner, user);
                    case 3 -> contactUs(scanner, user);
                    case 4 -> System.out.println(ConsoleColors.GREEN+"Iziet no profila...");
                    default -> {
                        ClearConsole.clearConsole();
                        System.out.println(user.getColor()+"Nepareiza ievade, mēģiniet vēlreiz.");
                    }
                }
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Notīra kļūdaino ievadi
                ClearConsole.clearConsole();
                System.out.println(user.getColor()+"Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (choice != 4);
    }

    public static void adminMenu(Scanner scanner, Person user) {
        int adminChoice = -1;
        do {
            ClearConsole.clearConsole();
            System.out.println(user.getColor() +"\nAdministrācijas izvēlne:");
            System.out.println("1 - Dzēst profilu");
            System.out.println("2 - Parādīt profilu sarakstu");
            System.out.println("3 - Pievienot jaunu mašīnu (marku/modeli)");
            System.out.println("4 - Dzēst mašīnu (marku/modeli)");
            System.out.println("5 - Rediģēt mašīnu (marku/modeli)");
            int unreadMessages = 0;
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("data/contact.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",", -1);
                    if (data.length > 3 && "unread".equalsIgnoreCase(data[3].trim())) {
                        unreadMessages++;
                    }
                }
            } catch (java.io.IOException e) {
                System.out.println("Kļūda lasot failu: " + e.getMessage());
            }
            if (unreadMessages > 0) {
                System.out.println("6 - Apskatīt saziņas datus "+ConsoleColors.RED_BOLD+"(" + unreadMessages + (unreadMessages == 1 ? " new message)" : " new messages)"+ConsoleColors.RESET));
            } else {
                System.out.println("6 - Apskatīt saziņas datus");
            }
            System.out.println(ConsoleColors.RED+"0 - Iziet no administratora izvēlnes");
            System.out.print("Ievadiet izvēli: ");
            try {
                adminChoice = scanner.nextInt();
                scanner.nextLine();
                Loading.LoadingScreen();
                switch (adminChoice) {
                    case 1 -> UserService.deleteUser(scanner, user);
                    case 2 -> UserService.listUsers(scanner);
                    case 3 -> CarService.addNewCar(scanner, user);
                    case 4 -> CarService.deleteCar(scanner, user);
                    case 5 -> CarService.editCar(scanner, user);
                    case 6 -> ContactService.viewContacts(user);
                    case 0 -> System.out.println(ConsoleColors.GREEN+"Izeja no administratora izvēlnes...");
                    default -> {
                        ClearConsole.clearConsole();
                        System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
                    }
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                ClearConsole.clearConsole();
                System.out.println("Nepareiza ievade, mēģiniet vēlreiz.");
            }
        } while (adminChoice != 0);
    }

    public static void contactUs(Scanner scanner, Person user) {
        int choice = -1;
        do {
            user.getColor();
            System.out.println(user.getColor()+"\nSazināties ar mums:");
            System.out.println("1 - Atstāt atsauksmi");
            System.out.println("2 - Paziņot par kļūdu");
            System.out.println("3 - Iedot savu ideju");
            System.out.println("4 - Atgriezties galvenajā izvēlnē");
            System.out.print("Ievadiet izvēli: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 3) {
                    Loading.LoadingScreen();
                    String contactType = switch (choice) {
                        case 1 -> "Atsauksme";
                        case 2 -> "Kļūda";
                        case 3 -> "Ideja";
                        default -> "";
                    };
                    System.out.println(user.getColor()+"Lūdzu, ievadiet savu ziņojumu:");
                    String message = scanner.nextLine();
                    ContactService.saveContact(user.getUsername(), contactType, message);
                    ClearConsole.clearConsole();
                    System.out.println("Paldies! Jūsu ziņojums ir saglabāts.");
                } else if (choice != 4) {
                    ClearConsole.clearConsole();
                    System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                ClearConsole.clearConsole();
                System.out.println("Nepareiza izvēle, mēģiniet vēlreiz.");
            }
        } while (choice != 4);

        ClearConsole.clearConsole();
    }
}