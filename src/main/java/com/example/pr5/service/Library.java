package com.example.pr5.service;

import com.example.pr5.exception.DuplicateContactException;
import com.example.pr5.model.*;
import com.example.pr5.storage.ArrayRepository;
import java.time.LocalDate;

public class Library {
    private final ArrayRepository publications = new ArrayRepository(); // Хранит контакты
    private final ArrayRepository readers = new ArrayRepository();      // Хранит группы
    private final ArrayRepository loans = new ArrayRepository();        // Хранит взаимодействия
    private final FineCalculator fineCalculator = new FineCalculator();

    public ArrayRepository getPublications() { return publications; }
    public ArrayRepository getReaders() { return readers; }
    public ArrayRepository getLoans() { return loans; }

    public void addPublication(Contact pub) {
        publications.add(pub);
        Logger.info("Dobavlen Contact: " + pub.getContactId() + " - " + pub.getName());
    }

    public void registerReader(Reader r) {
        readers.add(r);
        Logger.info("Zaregestrirovanna Gruppa: " + r.getGroupName());
    }

    public void registerPhoneNumber(String id, String phone) throws DuplicateContactException {
        Contact c = findContactById(id);
        if (c != null) {
            String clean = phone.replaceAll("[^0-9+]", "");
            c.addPhoneNumber(clean);
            Logger.info("Contactu " + id + " dobavlen nomer " + clean);
        }
    }

    public void addInteraction(Loan inter) {
        loans.add(inter);
        Logger.info("Dobavlenno vzaimodeystvie dlya contacta: " + inter.getContactId());
    }

    public Contact findContactById(String id) {
        for (int i = 0; i < publications.size(); i++) {
            Contact c = (Contact) publications.get(i);
            if (c.getContactId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    public void sortById() {
        for (int i = 0; i < publications.size() - 1; i++) {
            for (int j = 0; j < publications.size() - i - 1; j++) {
                if (((Contact)publications.get(j)).getContactId().compareToIgnoreCase(((Contact)publications.get(j+1)).getContactId()) > 0) {
                    publications.swap(j, j + 1);
                }
            }
        }
    }

    public void sortByName() {
        for (int i = 0; i < publications.size() - 1; i++) {
            for (int j = 0; j < publications.size() - i - 1; j++) {
                if (((Contact)publications.get(j)).getName().compareToIgnoreCase(((Contact)publications.get(j+1)).getName()) > 0) {
                    publications.swap(j, j + 1);
                }
            }
        }
    }

    public ArrayRepository searchByMask(String mask) {
        ArrayRepository results = new ArrayRepository();
        for (int i = 0; i < publications.size(); i++) {
            Contact c = (Contact) publications.get(i);
            if (matchPattern(c.getName().toLowerCase(), mask.toLowerCase())) results.add(c);
        }
        return results;
    }

    private boolean matchPattern(String text, String pattern) {
        int n = text.length(), m = pattern.length();
        int i = 0, j = 0, ast = -1, match = -1;
        while (i < n) {
            if (j < m && (pattern.charAt(j) == '?' || pattern.charAt(j) == text.charAt(i))) { i++; j++; }
            else if (j < m && pattern.charAt(j) == '*') { ast = j; match = i; j++; }
            else if (ast != -1) { j = ast + 1; match++; i = match; }
            else return false;
        }
        while (j < m && pattern.charAt(j) == '*') j++;
        return j == m;
    }

    public void printBirthdayRemindersReport(LocalDate now) {
        System.out.println("\n=== OTCHET 1: DNI ROJDENIYA V BLIJAYSHEE VREMYA 30 ===");
        for (int i = 0; i < publications.size(); i++) {
            Contact c = (Contact) publications.get(i);
            if (c instanceof PersonContact && fineCalculator.checkBirthdayIn30Days((PersonContact) c, now)) {
                System.out.printf("Contact: %-20s | %s%n", c.getName(), c.getSpecificMetadata());
            }
        }
    }

    public void printTopFiveActiveContactsReport() {
        System.out.println("\n=== OTCHET 2: STATISTIKA VZAIMODEYSTVIY ===");
        System.out.printf("Vsego zafiksirovanno CRM sobutiy: %d%n", loans.size());
    }

    public void printCategorySummaryReport() {
        int p = 0, c = 0, v = 0;
        for (int i = 0; i < publications.size(); i++) {
            Contact contact = (Contact) publications.get(i);
            if (contact.getCategory() == PublicationCategory.PERSON) p++;
            else if (contact.getCategory() == PublicationCategory.COMPANY) c++;
            else v++;
        }
        System.out.println("\n=== OTCHET 3: STRUKTURA SISTEMS СRМ ===");
        System.out.printf("Fizlitsa: %d | Companii: %d | VIP Clients: %d%n", p, c, v);
    }
}
