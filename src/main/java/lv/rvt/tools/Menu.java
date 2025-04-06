package lv.rvt.tools;

public class Menu {
    public static void StartMenu() {
        // Izvada ASCII ART mašīnu
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

        // Izvada izvēlni

        System.out.println(ConsoleColors.GREEN+"\nCarObka - automobiļu kolekcija");
        System.out.println("1 - Kolekcijas apskate");
        System.out.println("2 - Ieiet profilā vai reģistrēties");
        System.out.println("3 - Sazināties ar mums");
        System.out.println("0 - Iziet no programmas");
    }

    public static void userMenu() {
        // Izvada izvēlni
        System.out.println(ConsoleColors.CYAN+"\nCarObka - automašīnu kolekcija");
        System.out.println("1 - Kolekcija");
        System.out.println("2 - Iemīļotās mašīnas");
        System.out.println("3 - Sazināties ar mums");
        System.out.println("4 - Iziet no profila");
    }

    public static void adminMenu() {
        // Izvada izvēlni
        System.out.println(ConsoleColors.RED+"\nAdministrācijas izvēlne:");
        System.out.println("1 - Dzēst profilu");
        System.out.println("2 - Parādīt profilu sarakstu");
        System.out.println("3 - Pievienot jaunu mašīnu (marku/modeli)");
        System.out.println("4 - Dzēst mašīnu (marku/modeli)");
        System.out.println("5 - Rediģēt mašīnu (marku/modeli)");
        System.out.println("6 - Paplašinātās datu operācijas");
        // Skaita nelasīto ziņu skaitu no contact.csv
        int unreadMessages = 0;
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("data/contact.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
            String[] data = line.split(",", -1); // Use -1 to include trailing empty strings
            if (data.length > 3 && "unread".equalsIgnoreCase(data[3].trim())) {
                unreadMessages++;
            }
            }
        } catch (java.io.IOException e) {
            System.out.println("Kļūda lasot failu: " + e.getMessage());
        }
        if (unreadMessages > 0) {
            System.out.println("7 - Apskatīt saziņas datus "+ConsoleColors.RED_BOLD+"(" + unreadMessages + (unreadMessages == 1 ? " new message)" : " new messages)"+ConsoleColors.RESET));
        }
        else {
            System.out.println("7 - Apskatīt saziņas datus");
        }
        System.out.println(ConsoleColors.RED+"0 - Iziet no administratora izvēlnes");
    }
}
