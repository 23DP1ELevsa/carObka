package lv.rvt.tools;

public class Menu {
    public static void StartMenu() {
        // Izvada ASCII ART mašīnu
        System.out.println("        __---------__");
        System.out.println("      / _-----------_ \\");
        System.out.println("     / /             \\ \\");
        System.out.println("     | |             | |");
        System.out.println("     |_|_____________|_|");
        System.out.println(" /-\\|                   |/-\\");
        System.out.println("| _ |\\        0        /| _ |");
        System.out.println("|(_)| \\       !       / |(_) |");
        System.out.println("|___|__\\______!______/__|___|");
        System.out.println("[_________|CarObka|_________]");
        System.out.println(" ||||     ~~~~~~~~~     ||||");
        System.out.println(" `--'                   `--'");

        // Izvada izvēlni
        System.out.println("\nCarObka - automobiļu kolekcija");
        System.out.println("1 - Kolekcijas apskate");
        System.out.println("2 - Ieiet profilā vai reģistrēties");
        System.out.println("3 - Sazināties ar mums");
        System.out.println("0 - Iziet no programmas");
    }

    public static void userMenu() {
        // Izvada izvēlni
        System.out.println("\nCarObka - automašīnu kolekcija");
        System.out.println("1 - Kolekcija");
        System.out.println("2 - Iemīļotās mašīnas");
        System.out.println("3 - Sazināties ar mums");
        System.out.println("4 - Iziet no profila");
    }

    public static void adminMenu() {
        // Izvada izvēlni
        System.out.println("\nAdministrācijas izvēlne:");
        System.out.println("1 - Dzēst profilu");
        System.out.println("2 - Parādīt profilu sarakstu");
        System.out.println("3 - Pievienot jaunu mašīnu (marku/modeli)");
        System.out.println("4 - Dzēst mašīnu (marku/modeli)");
        System.out.println("5 - Rediģēt mašīnu (marku/modeli)");
        System.out.println("6 - Paplašinātās datu operācijas");
        System.out.println("7 - Apskatīt saziņas datus");
        System.out.println("0 - Iziet no administratora izvēlnes");
    }
}
