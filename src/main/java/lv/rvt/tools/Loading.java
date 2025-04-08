package lv.rvt.tools;

public class Loading {
    public static void LoadingScreen() { 
        // Clear the console before starting the loop
        ClearConsole.clearConsole();

        for (int i = 0; i < 8; i++) { // Loop 5 times for demonstration
            String dots = ".".repeat(i % 4);
            System.out.println();
            System.out.println(ConsoleColors.YELLOW +"                .      ..");
            System.out.println("        __..---/______//-----. ");
            System.out.println("      .\".--.```|    - /.--.  =:");
            System.out.println("     (.: {} :__L______: {} :__; "+ ConsoleColors.RESET +"  Loading"+ dots);
            System.out.println(ConsoleColors.YELLOW + "        *--*           *--*   " + ConsoleColors.RESET);

            try {
                Thread.sleep(100); // Pause for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Loading interrupted");
            }
            // Clear the console
            ClearConsole.clearConsole();
        }
    }
}