package com.transform.main.rest;

import com.transform.main.entity.ContactActivity;
import com.transform.main.models.ApiResponse;
import com.transform.main.models.ContactActivityModel;
import com.transform.main.service.ContactActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contact")
public class ContactActivityRestController {

    private final ContactActivityService contactActivityService;

    @Autowired
    public ContactActivityRestController(ContactActivityService contactActivityService) {
        this.contactActivityService = contactActivityService;
    }

    @GetMapping("/activity")
    public ResponseEntity<List<ContactActivityModel>> listContactActivity() {
        List<ContactActivityModel> contactActivityModels = new ArrayList<>();
        try {
            List<ContactActivity> contactActivities = contactActivityService.findAll();

            if (!CollectionUtils.isEmpty(contactActivities)) {
                 contactActivities.stream().
                        forEach(contactActivity -> {
                            contactActivityModels.add(populateContactActivityModel(contactActivity));
                        });

            }
            if (!CollectionUtils.isEmpty(contactActivityModels)) {
                return ResponseEntity.ok(contactActivityModels);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(contactActivityModels);
        }
        return null;
    }

    @GetMapping("/activity/{contactId}")
    public ResponseEntity<ContactActivityModel> getContactActivity(@PathVariable Integer contactId) {
        ContactActivityModel contactActivityModel = new ContactActivityModel();
        try {
            ContactActivity contactActivity = contactActivityService.findByContactId(contactId);

            if (contactActivity != null) {
                contactActivityModel = populateContactActivityModel(contactActivity);
            }
            return ResponseEntity.ok(contactActivityModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(contactActivityModel);
        }
    }

    @PostMapping("/activity")
    public ResponseEntity<ApiResponse> saveContactActivity(@RequestBody ContactActivityModel contactActivityModel) {
        ContactActivity contactActivity = null;
        ContactActivity resContactActivity = null;
        try {
            if (contactActivityModel.getId() != null) {
                contactActivity = contactActivityService.findById(contactActivityModel.getId());
            }
            if (contactActivityModel.getContactId() != null) {
                contactActivity = contactActivityService.findByContactId(contactActivityModel.getContactId());
            }
            if (contactActivity != null) {
                contactActivity.setActivityIds(((BiFunction<List<String>, String, String>) (list, delimiter) ->
                        String.join(delimiter, list)).apply(contactActivityModel.getActivityIdList(), ","));
                resContactActivity = contactActivityService.save(contactActivity);
            } else {
                contactActivity = populateContactActivity(contactActivityModel);

                resContactActivity = contactActivityService.save(contactActivity);
            }

            if (resContactActivity != null) {
                ApiResponse response = new ApiResponse(true, "Contact Activity Saved",
                        resContactActivity.getContactId());

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Error while saving Contact Activity" + e, null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return null;
    }

    private ContactActivityModel populateContactActivityModel(ContactActivity contactActivity) {
        ContactActivityModel contactActivityModel = new ContactActivityModel();

        contactActivityModel.setId(contactActivity.getId());
        contactActivityModel.setContactId(contactActivity.getContactId());

        BiFunction<String, String, List<String>> strToList = (str, delimiter) ->
                Arrays.stream(str.split(delimiter))
                        .map(String::trim)
                        .collect(Collectors.toList());

        contactActivityModel.setActivityIdList(strToList.apply(contactActivity.getActivityIds(), ","));

        return contactActivityModel;
    }

    private ContactActivity populateContactActivity(ContactActivityModel contactActivityModel) {
        ContactActivity contactActivity = new ContactActivity();

        contactActivity.setId(contactActivityModel.getId());
        contactActivity.setContactId(contactActivityModel.getContactId());

        BiFunction<List<String>, String, String> listToStr = (list, delimiter) ->
                String.join(delimiter, list);

        contactActivity.setActivityIds(listToStr.apply(contactActivityModel.getActivityIdList(), ","));

        return contactActivity;
    }
}
