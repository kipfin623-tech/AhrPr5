package com.example.pr5.ui;

import com.example.pr5.model.*;
import com.example.pr5.service.Library;
import com.example.pr5.service.Logger;
import com.example.pr5.storage.ArrayRepository;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleUI {
    private final Library library;
    private final Scanner scanner = new Scanner(System.in);
    private final LocalDate systemDate = LocalDate.of(2026, 11, 10);

    public ConsoleUI(Library library) { this.library = library; }

    public void run() {
        while (true) {
            System.out.println("\n================ CRM SPRAVOCHNIK ================");
            System.out.println("1. Spisok contaktov i poisk");
            System.out.println("2. Deystvia i telefons");
            System.out.println("3. Analitika");
            System.out.println("0. Exit");
            System.out.print("Choise: ");
            String cmd = scanner.nextLine().trim();
            if ("0".equals(cmd)) return;
            if ("1".equals(cmd)) {
                System.out.println("1. Pokazat vse\n2. Poisk po maske\n3. Sortirovat po ID\n4. Dobavit contact");
                String sub = scanner.nextLine().trim();
                if ("1".equals(sub)) {
                    for(int i=0; i<library.getPublications().size(); i++) System.out.println(library.getPublications().get(i));
                } else if ("2".equals(sub)) {
                    System.out.print("Maska (*, ?): ");
                    ArrayRepository res = library.searchByMask(scanner.nextLine().trim());
                    for(int i=0; i<res.size(); i++) System.out.println(res.get(i));
                } else if ("3".equals(sub)) {
                    library.sortById();
                } else if ("4".equals(sub)) {
                    System.out.print("ID: "); String id = scanner.nextLine();
                    System.out.print("Imya: "); String name = scanner.nextLine();
                    library.addPublication(new PersonContact(id, name, LocalDate.of(1995, 5, 5)));
                }
            } else if ("2".equals(cmd)) {
                System.out.print("ID contact: "); String id = scanner.nextLine();
                System.out.print("Telefon: "); String ph = scanner.nextLine();
                library.registerPhoneNumber(id, ph);
            } else if ("3".equals(cmd)) {
                library.printBirthdayRemindersReport(systemDate);
                library.printCategorySummaryReport();
            }
        }
    }
}
