package org.example.service.impl;

import org.example.model.Contact;
import org.example.service.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryContactRepositoryImpl implements ContactRepository {
    private final List<Contact> contacts = new ArrayList<>();

    @Value("${contacts.file.path}")
    private String contactFilePath;

    @Override
    public List<Contact> getAllContacts() { return new ArrayList<>(contacts); }

    @Override
    public void addContact(Contact contact) { contacts.add(contact); }

    @Override
    public void deleteContactByEmail(String email) {
        Contact contactToDelete = null;
        for (Contact contact : contacts) {
            if (contact.getEmail().equals(email)) {
                contactToDelete = contact;
                break;
            }
        }
        if (contactToDelete != null) {
            contacts.remove(contactToDelete);
            System.out.println("Данные успешно удалены");
        } else {
            System.out.println("Ошибка. Not Found");
        }
    }
    @Override
    public void saveContactsToFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            List<Contact> contacts = getAllContacts();
            for (Contact contact : contacts) {
                String fullName = contact.getFullName();
                String phoneNumber = contact.getPhoneNumber();
                String email = contact.getEmail();
                String contactString = fullName + "; " + phoneNumber + "; " + email + "\n";
                fileWriter.write(contactString);
            }
            System.out.println("Операция успешна завершена. Сохранение файла");
        } catch (IOException e) {
            System.out.println("Операция не может быть выполнена. Сохранение файла");
            e.printStackTrace();
        }
    }
    @Override
    public List<Contact> synchronizationFile(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 3) {
                    String name = fields[0].trim();
                    String phoneNumber = fields[1].trim();
                    String email = fields[2].trim();
                    contacts.add(new Contact(name, phoneNumber, email));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Данные успешно обновлены");
        return contacts;
    }
}