package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class ContactService {
    // Metode, lai saglabātu kontaktinformāciju CSV failā
    private static final String CONTACT_FILE = "/workspaces/carObka/data/contact.csv";

    public static void saveContact(String username, String contactType, String message) {
        File file = new File(CONTACT_FILE);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            String timestamp = LocalDateTime.now().toString(); // Ziņojuma nosūtīšanas laiks
            pw.println(username + "," + contactType + "," + message.replace(",", " ") + ",unread," + timestamp + ",,"); // Lasīšanas laiks un dzēšanas datums sākotnēji tukši
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot saziņu: " + e.getMessage());
        }
    }

    // Metode, lai apskatītu kontaktinformāciju
    public static void viewContacts(Person user) {
        File file = new File(CONTACT_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Nav pieejamu saziņas datu.");
            return;
        }
    
        List<String> updatedLines = new ArrayList<>();
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter isoFormatterT = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .toFormatter();
        DateTimeFormatter spaceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println(user.getColor() + "\nSaziņas dati:");
            System.out.println("+---------------+---------------+----------------------------------------------------+----------+-------------------------+-------------------------+-------------------------+");
            System.out.printf("| %-13s | %-13s | %-50s | %-8s | %-23s | %-23s | %-23s |\n",
                "Lietotājs", "Veids", "Ziņojums", "Statuss", "Nosūtīts", "Izlasīts", "Dzēšanas datums");
            System.out.println("+---------------+---------------+----------------------------------------------------+----------+-------------------------+-------------------------+-------------------------+");
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 7);
                if (parts.length >= 5) {
                    String username = parts[0];
                    String contactType = parts[1];
                    String message = parts[2];
                    String status = parts[3];
                    String sentTime = parts[4];
                    String readTime = parts.length > 5 ? parts[5] : "";
                    String deleteTime = parts.length > 6 ? parts[6] : "";
    
                    if ("unread".equalsIgnoreCase(status.trim())) {
                        LocalDateTime nowUTC = LocalDateTime.now(ZoneId.of("UTC"));
                        readTime = nowUTC.format(isoFormatterT);
                        deleteTime = nowUTC.plusHours(24).format(isoFormatterT);
                        status = "read";
                    }
    
                    if (!deleteTime.isEmpty()) {
                        try {
                            LocalDateTime deleteDateTime = LocalDateTime.parse(deleteTime, isoFormatterT);
                            if (deleteDateTime.isBefore(LocalDateTime.now(ZoneId.of("UTC")))) {
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Kļūda, parsējot deleteTime: " + e.getMessage());
                        }
                    }
    
                    String displayedSentTime = convertToRigaTime(sentTime, isoFormatterT, spaceFormatter, targetFormatter);
                    String displayedReadTime = readTime.isEmpty() ? "" : convertToRigaTime(readTime, isoFormatterT, spaceFormatter, targetFormatter);
                    String displayedDeleteTime = deleteTime.isEmpty() ? "" : convertToRigaTime(deleteTime, isoFormatterT, spaceFormatter, targetFormatter);
    
                    List<String> formattedMessage = formatMessage(message, 50, user);
    
                    System.out.printf("| %-13s | %-13s | %-50s | %-8s | %-23s | %-23s | %-23s |\n",
                        username, contactType, formattedMessage.get(0), status, displayedSentTime, displayedReadTime, displayedDeleteTime);
    
                    for (int i = 1; i < formattedMessage.size(); i++) {
                        System.out.printf("| %-13s | %-13s | %-50s |\n", "", "", formattedMessage.get(i));
                    }
    
                    updatedLines.add(String.join(",", username, contactType, message.replace(",", " "), status, sentTime, readTime, deleteTime));
                }
            }
            System.out.println("+---------------+---------------+----------------------------------------------------+----------+-------------------------+-------------------------+-------------------------+");
        } catch (IOException e) {
            System.out.println("Kļūda, lasot saziņas datus: " + e.getMessage());
            return;
        }
    
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String updatedLine : updatedLines) {
                pw.println(updatedLine);
            }
        } catch (IOException e) {
            System.out.println("Kļūda, saglabājot atjauninātos saziņas datus: " + e.getMessage());
        }
    
        System.out.println("\nNospiediet Enter, lai atgrieztos uz administratora izvēlni...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Kļūda, gaidot ievadi: " + e.getMessage());
        }
    }

    /**
     * Konvertē laika virkni, kas saglabāta UTC, uz Rīgas laiku izvadē.
     * Ja laika virknē ir "T", tiek izmantots isoFormatterT, citādi – spaceFormatter.
     */
    public static String convertToRigaTime(String timeStr,
                                            DateTimeFormatter isoFormatterT,
                                            DateTimeFormatter spaceFormatter,
                                            DateTimeFormatter targetFormatter) {
        if (timeStr == null || timeStr.isBlank()) {
            return "";
        }
        try {
            LocalDateTime ldt;
            // Izvēlamies pareizo parsēšanas formātu
            if (timeStr.contains("T")) {
                ldt = LocalDateTime.parse(timeStr, isoFormatterT);
            } else {
                ldt = LocalDateTime.parse(timeStr, spaceFormatter);
            }
            // Pieņemam, ka saglabātais laiks ir UTC, un konvertējam uz Rīgas laiku (Europe/Riga)
            ZonedDateTime utcZdt = ldt.atZone(ZoneId.of("UTC"));
            ZonedDateTime rigaZdt = utcZdt.withZoneSameInstant(ZoneId.of("Europe/Riga"));
            return rigaZdt.format(targetFormatter);
        } catch (Exception e) {
            return timeStr;
        }
    }

    // Palīgmetode, lai sadalītu ziņojumu vairākās rindās
    public static List<String> formatMessage(String message, int maxLineLength, Person user) {
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        System.out.println(user.getColor()+"");
        for (String word : message.split(" ")) {
            if (currentLine.length() + word.length() + 1 > maxLineLength) {
                // Pārbaudām, vai rinda beidzas ar teikuma beigām (piemēram, ".")
                if (currentLine.length() > 0 && currentLine.charAt(currentLine.length() - 1) == '.') {
                    lines.add(currentLine.toString().trim());
                    currentLine.setLength(0);
                } else {
                    // Ja nav teikuma beigas, pievienojam rindu un sākam jaunu
                    lines.add(currentLine.toString().trim());
                    currentLine.setLength(0);
                }
            }
            currentLine.append(word).append(" ");
        }
    
        // Pievienojam pēdējo rindu, ja tā nav tukša
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
    
        return lines;
    }
}
