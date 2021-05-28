package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.main.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/object")
@RequiredArgsConstructor
public class APIController {

    private final APISerivce apiSerivce;

    // GRAIN,FRUIT,VEGETABLE,MEAT,FISH
    private String[] GRAIN = {"01","03","04","05"};
    private String[] FRUIT = {"06","09"};
    private String[] VEGETABLE = {"10","11","12"};
    private String[] MEAT = {"41","43"};
    private String[] FISH = {"61","63"};


    // 잡곡류
    @GetMapping("/grain")
    public List<Item> callGrainApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,GRAIN);
    }

    // 과일류
    @GetMapping("/fruit")
    public List<Item> callFruitApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,FRUIT);
    }

    // 채소류
    @GetMapping("/vegetable")
    public List<Item> callVegetableApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,VEGETABLE);
    }

    // 육류
    @GetMapping("/meat")
    public List<Item> callMeatApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,MEAT);
    }

    // 생선류
    @GetMapping("/fish")
    public List<Item> callFishApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiSerivce.apiEndPoint(now,FISH);
    }



}
