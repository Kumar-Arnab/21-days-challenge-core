package com.transform.main.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactid")
    private Integer contactId;

    @Column(name = "access_name")
    private String accessName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "access_email")
    private String accessEmail;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "problem_description")
    private String problemDescription;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    // no arg constructor
    public Contacts() {

    }

    // getters and setters
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccessEmail() {
        return accessEmail;
    }

    public void setAccessEmail(String accessEmail) {
        this.accessEmail = accessEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacts contacts = (Contacts) o;
        return Objects.equals(contactId, contacts.contactId)
                && Objects.equals(accessName, contacts.accessName)
                && Objects.equals(firstName, contacts.firstName)
                && Objects.equals(lastName, contacts.lastName)
                && Objects.equals(accessEmail, contacts.accessEmail)
                && Objects.equals(password, contacts.password)
                && Objects.equals(problemDescription, contacts.problemDescription)
                && Objects.equals(active, contacts.active)
                && Objects.equals(emailVerified, contacts.emailVerified)
                && Objects.equals(mobile, contacts.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, accessName, firstName, lastName, accessEmail, password,
                problemDescription, active, emailVerified, mobile);
    }


    // toString()
    @Override
    public String toString() {
        return "Contacts{" +
                "contactId=" + contactId +
                ", accessName='" + accessName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accessEmail='" + accessEmail + '\'' +
                ", password='" + password + '\'' +
                ", problemDescription='" + problemDescription + '\'' +
                ", active=" + active +
                ", emailVerified=" + emailVerified +
                ", mobile=" + mobile +
                '}';
    }
}
