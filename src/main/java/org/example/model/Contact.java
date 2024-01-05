package org.example.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Contact {
    private String fullName;
    private String phoneNumber;
    private String email;
}