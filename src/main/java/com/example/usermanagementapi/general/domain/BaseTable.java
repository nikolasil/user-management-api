package com.example.usermanagementapi.general.domain;

import java.sql.Timestamp;

public interface BaseTable {
    Long getId();

    Timestamp getDateUpdated();

    Timestamp getDateCreation();
}
