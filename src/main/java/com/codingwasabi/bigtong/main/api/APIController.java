package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
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
        // 잡곡류
    @GetMapping("/grain")
    public List<Subject> callGrainApi(){
        return apiSerivce.returnTop5("GRAIN");
    }

    // 과일류
    @GetMapping("/fruit")
    public List<Subject> callFruitApi(){
        return apiSerivce.returnTop5("FRUIT");
    }

    // 채소류
    @GetMapping("/vegetable")
    public List<Subject> callVegetableApi(){
        return apiSerivce.returnTop5("VEGETABLE");
    }

    // 육류
    @GetMapping("/meat")
    public List<Subject> callMeatApi(){
        return apiSerivce.returnTop5("MEAT");
    }

    // 생선류
    @GetMapping("/fish")
    public List<Subject> callFishApi(){
        return apiSerivce.returnTop5("FISH");
    }



}
