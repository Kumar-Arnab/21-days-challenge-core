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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/access")
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
            ApiResponse response = new ApiResponse(false, "Login failed " + e, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/login/user")
    public ResponseEntity<ContactsModel> getContact(@RequestParam(value = "username", required = false) String accessName,
                                               @RequestParam(value = "useremail", required = false) String accessEmail,
                                               @RequestParam(value = "userid", required = false) Integer contactsId) {
        try {
            Contacts contact = new Contacts();
            if (contactsId != null) {
                contact = contactsService.findById(contactsId);
            } else {
                if (accessName != null) {
                    contact = contactsService.findByAccessName(accessName);
                } else if (accessEmail != null) {
                    contact = contactsService.findByAccessEmail(accessEmail);
                }
            }
            ContactsModel contactsModel = populateContactModel(contact);
            return ResponseEntity.ok(contactsModel);
        } catch (Exception e) {
            ContactsModel response = new ContactsModel();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<ContactsModel>> listContact() {
        List<ContactsModel> contactsModels = new ArrayList<>();
        try {
            List<Contacts> contacts = contactsService.findAll();

            if (!CollectionUtils.isEmpty(contacts)) {
                contacts.stream().forEach(contact -> {
                    contactsModels.add(populateContactModel(contact));
                });

                return ResponseEntity.ok(contactsModels);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(contactsModels);
        }
        return null;
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

    private ContactsModel populateContactModel(Contacts contact) {
        ContactsModel contactsModel = new ContactsModel();

        if (contact.getContactId() != null) contactsModel.setContactId(contact.getContactId());
        contactsModel.setAccessName(contact.getAccessName());
        contactsModel.setFirstName(contact.getFirstName());
        contactsModel.setLastName(contact.getLastName());
        contactsModel.setAccessEmail(contact.getAccessEmail());
        contactsModel.setPassword(contact.getPassword());
        contactsModel.setMobile(contact.getMobile());
        contactsModel.setProblemDescription(contact.getProblemDescription());
        contactsModel.setActive(contact.getActive());
        contactsModel.setEmailVerified(contact.getEmailVerified());

        return contactsModel;

    }

}
