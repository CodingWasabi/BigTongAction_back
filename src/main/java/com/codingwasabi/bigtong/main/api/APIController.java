package com.codingwasabi.bigtong.main.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/object")
@RequiredArgsConstructor
public class APIController {

    private final APISerivce apiSerivce;

    // GRAIN,FRUIT,VEGETABLE,MEAT,FISH
    private String[] GRAIN = {"01","02","03","04","05"};
    private String[] FRUIT = {"06","09"};
    private String[] VEGETABLE = {"10","11","12"};
    private String[] MEAT = {"41","43"};
    private String[] FISH = {"61","63"};


    // 잡곡류
    @GetMapping("/grain")
    public String callGrainApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,GRAIN);
    }

    // 과일류
    @GetMapping("/fruit")
    public String callFruitApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,FRUIT);
    }

    // 채소류
    @GetMapping("/vegetable")
    public String callVegetableApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,VEGETABLE);
    }

    // 육류
    @GetMapping("/meat")
    public String callMeatApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,MEAT);
    }

    // 생선류
    @GetMapping("/fish")
    public String callFishApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,FISH);
    }



}
