package lv.rvt.tools;

public class Loading {
    public void LoadingScreen() { 
        // Clear the console before starting the loop
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 8; i++) { // Loop 5 times for demonstration
            String dots = ".".repeat(i % 4);
            System.out.println();
            System.out.println("                .      ..");
            System.out.println("        __..---/______//-----. ");
            System.out.println("      .\".--.```|    - /.--.  =:");
            System.out.println("     (.: {} :__L______: {} :__;   Loading" + dots);
            System.out.println("        *--*           *--*   ");

            try {
                Thread.sleep(100); // Pause for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Loading interrupted");
            }
            // Clear the console
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}