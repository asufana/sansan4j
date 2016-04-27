package com.github.asufana.sansanapi.model.response.models;

import lombok.Value;
import lombok.experimental.Accessors;

/** 名刺データ */
@Value
@Accessors(fluent = true)
public class BizCard {
    private final String id;
    private final String personId;
    private final String exchangeDate;
    private final String registeredTime;
    private final Owner owner;
    private final String lastName;
    private final String firstName;
    private final String lastNameReading;
    private final String firstNameReading;
    private final String departmentName;
    private final String title;
    private final String email;
    private final String mobile;
    private final String companyName;
    private final String postalCode;
    private final String prefecture;
    private final String city;
    private final String street;
    private final String building;
    private final String tel;
    private final String fax;
    private final String url;
    private final String entryStatus;
}
