package com.transform.main.models;
import java.util.List;

public class ContactActivityModel {

    private Integer id;

    private Integer contactId;

    private List<String> activityIdList;

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

    public List<String> getActivityIdList() {
        return activityIdList;
    }

    public void setActivityIdList(List<String> activityIdList) {
        this.activityIdList = activityIdList;
    }
}
