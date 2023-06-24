package com.transform.main.rest;

import com.transform.main.entity.Contacts;
import com.transform.main.models.ApiResponse;
import com.transform.main.models.ContactsModel;
import com.transform.main.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContactsRestController {

    private final ContactsService contactsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ContactsRestController(
            ContactsService contactsService,
            PasswordEncoder passwordEncoder
    ) {
        this.contactsService = contactsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody ContactsModel contactsModel) {
        try {
            String hashedPassword = passwordEncoder.encode(contactsModel.getPassword());
            Contacts contact = populateContacts(contactsModel);

            Contacts predefinedContact = contactsService.findByAccessName(contact.getAccessName());

            if (predefinedContact != null) {
                ApiResponse response = new ApiResponse(false, "User Name is already present", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            predefinedContact = contactsService.findByAccessEmail(contact.getAccessEmail());

            if (predefinedContact != null) {
                ApiResponse response = new ApiResponse(false, "User Email is already present", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            contact.setPassword(hashedPassword);

            Contacts resContact = contactsService.save(contact);

            ApiResponse response = new ApiResponse(true, "Registration successful",
                    resContact.getContactId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Registration failed " + e, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody ContactsModel contactsModel) {
        Contacts contact;
        try {
            contact = contactsService.findByAccessName(contactsModel.getAccessName());

            if (contact != null && passwordEncoder.matches(contactsModel.getPassword(), contact.getPassword())) {
                // Successful login
                Authentication authentication = new UsernamePasswordAuthenticationToken(contact, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                ApiResponse response = new ApiResponse(true,
                        "Login successful", contact.getContactId());

                return ResponseEntity.ok(response);

            } else {
                // Invalid credentials
                ApiResponse response = new ApiResponse(false, "Invalid username or password", null);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Login failed", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private Contacts populateContacts(ContactsModel contactsModel) {
        Contacts contact = new Contacts();

        if (contactsModel.getContactId() != null) contact.setContactId(contactsModel.getContactId());
        contact.setAccessName(contactsModel.getAccessName());
        contact.setFirstName(contactsModel.getFirstName());
        contact.setLastName(contactsModel.getLastName());
        contact.setAccessEmail(contactsModel.getAccessEmail());
        contact.setPassword(contactsModel.getPassword());
        contact.setMobile(contactsModel.getMobile());
        contact.setProblemDescription(contactsModel.getProblemDescription());
        contact.setActive(true);
        contact.setEmailVerified(contactsModel.getEmailVerified());

        return contact;

    }


}
