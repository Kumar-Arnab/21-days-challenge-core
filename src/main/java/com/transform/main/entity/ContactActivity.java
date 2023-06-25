package com.transform.main.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "contact_activity")
public class ContactActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_activity_id")
    private Integer id;

    @Column(name = "contactid")
    private Integer contactId;

    @Column(name = "activity_ids")
    private String activityIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactActivity that = (ContactActivity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(contactId, that.contactId)
                && Objects.equals(activityIds, that.activityIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contactId, activityIds);
    }

    @Override
    public String toString() {
        return "ContactActivity{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", activityIds='" + activityIds + '\'' +
                '}';
    }
}
