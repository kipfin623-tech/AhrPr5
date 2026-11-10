package com.example.pr5.main;

import com.example.pr5.model.*;
import com.example.pr5.service.Library;
import com.example.pr5.ui.ConsoleUI;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();


        for (int i = 1; i <= 5; i++) {
            library.addPublication(new PersonContact("C" + i, "Ivanov Petr " + i, LocalDate.of(1990, 11, i + 5)));
            library.addPublication(new CompanyContact("C" + (i + 5), "OOO Vektor " + i, "INN-7701" + i));
            library.addPublication(new VIPContact("C" + (i + 10), "CEO Company " + i, "Menedger Alexey"));
        }

        library.registerReader(new Reader("G1", "Clients"));
        library.registerReader(new Reader("G2", "Partners"));

        ConsoleUI ui = new ConsoleUI(library);
        ui.run();
    }
}
