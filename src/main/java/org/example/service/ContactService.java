package org.example.service;

import org.example.model.Contact;
import org.example.service.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) { this.contactRepository = contactRepository; }

    public void call() {
        Contact contact1 = new Contact("Alexander", "+79008889999", "alexander@example.com");
        Contact contact2 = new Contact("Ivan", "+79002225555", "ivan@example.com");
        Contact contact3 = new Contact("Alexey", "+79001117777", "alexey@example.com");

        System.out.println("<===>");
        System.out.println("Добавление контактов");
        addContact(contact1);
        addContact(contact2);
        addContact(contact3);
        System.out.println("Контакты успешно сохранены");
        System.out.println("<===>");

        System.out.println("Удаление контактов");
        deleteContactByEmail("alexander@example.com");
        System.out.println("<===>");

        System.out.println("Список контактов");
        for (Contact contact : contactRepository.getAllContacts()) {
            System.out.println(contact.getFullName() + " | " + contact.getPhoneNumber() + " | " +
                    contact.getEmail());
        }
        System.out.println("<===>");

        try {
            System.out.println("Сохранение контактов");
            saveContactToFile("contact.txt");
            System.out.println("<===>");
        } catch (IOException e) {
            throw new RuntimeException();
        }
        System.out.println("Перечень списка контактов");
        printContact();
    }

    public void addContact(Contact contact) {
        contactRepository.addContact(contact);
    }

    public void deleteContactByEmail(String email) {
        contactRepository.deleteContactByEmail(email);
    }

    public List<Contact> getAllContacts() { return contactRepository.getAllContacts(); }

    public void saveContactToFile(String fileName) throws IOException { contactRepository.saveContactsToFile(fileName); }

    public List<Contact> synchronizationFile(String fileName) throws IOException { return contactRepository.synchronizationFile(fileName); }

    public void printContact() {
        for (Contact contact : contactRepository.getAllContacts()) {
            String formattedContact = contact.getFullName() + " | " + contact.getPhoneNumber() + " | " +
                    contact.getEmail();
            System.out.println(formattedContact);
        }
    }
}