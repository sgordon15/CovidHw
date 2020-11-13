package com.example.covid.service;

import com.example.covid.model.TimeServerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class CovidService {
    private final RestTemplate restTemplate;

    public long getQuarantineTime(int year, int month, int day) {
        TimeServerResponse responseCurrentTime = restTemplate.exchange("http://localhost:8382/api/v1/getTime",
                HttpMethod.GET,
                null, TimeServerResponse.class).getBody();
        String exposureYear = Integer.toString(year);
        String exposureMonth = Integer.toString(month);
        String exposureDay = Integer.toString(day);
        String exposureTime= exposureYear + "-" +exposureMonth + "-" +exposureDay;
        String currentTime = responseCurrentTime.getLocalTime();

        //Parsing the date
        DateTimeFormatter dateFormat =DateTimeFormatter.ofPattern("yyyy-MM-dd 'T' HH:mm:ss:SSS 'Z' ZZZ");
        LocalDate dateExposure= LocalDate.parse(exposureTime,dateFormat);
        System.out.println(dateExposure);
        LocalDate dateAfter = LocalDate.parse(currentTime);
        System.out.println(dateAfter);

       // create formatter
        //calculating number of days in between
        long noOfDaysBetween = ChronoUnit.DAYS.between(dateExposure, dateAfter);

        return noOfDaysBetween;

    } //current time and second parameter for date formatter
}



