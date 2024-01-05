package org.example.service.repository;

import org.example.model.Contact;
import java.io.IOException;
import java.util.List;

public interface ContactRepository {
    List<Contact> getAllContacts();
    void addContact(Contact contact);
    void deleteContactByEmail(String email);
    void saveContactsToFile(String fileName) throws IOException;
    List<Contact> synchronizationFile(String fileName) throws IOException;
}