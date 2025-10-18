package com.ecommerce.api.basepage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import com.ecommerce.api.routes.Routes;
import com.ecommerce.api.utils.ConfigReader;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class CommonPage {

    protected ConfigReader configReader;

    //For logging
    RequestLoggingFilter requestLoggingFilter;
    ResponseLoggingFilter responseLoggingFilter;


    @BeforeClass
    public void setup() throws FileNotFoundException {
        RestAssured.baseURI = Routes.BASE_URL;

        configReader = new ConfigReader();

        FileOutputStream fos = new FileOutputStream(".\\logs\\test_logging.log");
        PrintStream log = new PrintStream(fos, true);
        requestLoggingFilter = new RequestLoggingFilter(log);
        responseLoggingFilter = new ResponseLoggingFilter(log);
        RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

    }


    protected boolean isSortedDescending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) < list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    protected boolean isSortedAscending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public boolean validateCartDatesWithinRange(List<String> cartDates, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, FORMATTER);
        LocalDate end = LocalDate.parse(endDate, FORMATTER);
        for (String dateTime : cartDates) {
            LocalDate cartDate = LocalDate.parse(dateTime.substring(0, 10), FORMATTER);
            if (cartDate.isBefore(start) || cartDate.isAfter(end)) {
                return false;
            }
        }
        return true;
    }
    public ConfigReader getConfigReader() {
        return configReader;
    }

}
