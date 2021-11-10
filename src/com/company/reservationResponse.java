package com.company;

import java.sql.Time;
import java.sql.Timestamp;

public class reservationResponse {

    public String Id;
    public int NumOfTable;
    public Timestamp Date;
    public int NumOfVisitors;

    public reservationResponse(String reservationId, int num_of_tables, Timestamp date_of_reserv, int number_of_visitors) {
        Id = reservationId;
        NumOfTable = num_of_tables;
        Date = date_of_reserv;
        NumOfVisitors = number_of_visitors;
    }
}

