package com.example.pr5.storage;

import com.example.pr5.model.Contact;
import com.example.pr5.model.PersonContact;
import com.example.pr5.model.CompanyContact;
import com.example.pr5.model.VIPContact;
import com.example.pr5.model.PublicationCategory;
import com.example.pr5.model.EntityComparator;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class ArrayRepository {
    private Object[] items = new Object[16];
    private int count = 0;

    public void add(Object element) {
        if (count == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[count++] = element;
    }

    public Object get(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException();
        return items[index];
    }

    public int size() { return count; }
    public void clear() { Arrays.fill(items, 0, count, null); count = 0; }

    public void swap(int i, int j) {
        Object temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    public void sort(EntityComparator comparator) {
        for (int i = 1; i < count; i++) {
            Object key = items[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(items[j], key) > 0) {
                items[j + 1] = items[j];
                j--;
            }
            items[j + 1] = key;
        }
    }

    public void savePublicationsToCsv(String filePath) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                if (items[i] instanceof Contact) {
                    Contact c = (Contact) items[i];
                    String specField = "N/A";
                    if (c instanceof PersonContact) specField = ((PersonContact) c).getBirthday().toString();
                    else if (c instanceof CompanyContact) specField = ((CompanyContact) c).getInn();
                    else if (c instanceof VIPContact) specField = ((VIPContact) c).getPersonalManager();

                    pw.printf("%s;%s;%s;%s;%s%n", c.getCategory().name(), c.getContactId(), c.getName(), c.getEmail(), specField);
                }
            }
        }
    }

    public void loadPublicationsFromCsv(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] cols = line.split(";");
                    PublicationCategory cat = PublicationCategory.valueOf(cols[0].trim());
                    String id = cols[1].trim();
                    String name = cols[2].trim();
                    String email = cols[3].trim();
                    String spec = cols[4].trim();

                    Contact contact;
                    if (cat == PublicationCategory.PERSON) contact = new PersonContact(id, name, LocalDate.parse(spec));
                    else if (cat == PublicationCategory.COMPANY) contact = new CompanyContact(id, name, spec);
                    else contact = new VIPContact(id, name, spec);
                    contact.setEmail(email);
                    add(contact);
                } catch (Exception e) {
                    System.err.println("[Oshibka CSV]: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("[Oshibka vvoda-vivida]: " + e.getMessage());
        }
    }
}
