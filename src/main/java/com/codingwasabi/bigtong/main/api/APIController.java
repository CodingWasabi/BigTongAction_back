package com.codingwasabi.bigtong.main.api;

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
public class APIController {

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

        return apiEndPoint(now,GRAIN);
    }

    // 과일류
    @GetMapping("/fruit")
    public String callFruitApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiEndPoint(now,FRUIT);
    }

    // 채소류
    @GetMapping("/vegetable")
    public String callVegetableApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiEndPoint(now,VEGETABLE);
    }

    // 육류
    @GetMapping("/meat")
    public String callMeatApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiEndPoint(now,MEAT);
    }

    // 생선류
    @GetMapping("/FISH")
    public String callFishApi(){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return apiEndPoint(now,FISH);
    }


    public String apiEndPoint(String now, String[] objects){
        StringBuffer result = new StringBuffer();

        String apiEndPoint = "http://openapi.epis.or.kr/openapi/service/RltmAucBrknewsService/getPrdlstRltmAucBrknewsList?"+
                "serviceKey=dzuiZZbhGGhdYgcvkdDPwvCHAdzZ%2FEkmO0%2BAqtpTaXsZLox1We%2BTJtegsxRak6NRX6gcVpEwrhGKayRbrDfjAQ%3D%3D";

        try{

            //result.append("<xml>\n");

            for(String object : objects) {
                apiEndPoint = apiEndPoint + "&dates=" + now + "&lcode=" + object;

                System.out.println(apiEndPoint+"\n");

                URL url = new URL(apiEndPoint);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

                String resultLine;

                while((resultLine = br.readLine()) != null){
                    result.append(resultLine+"\n");
                }

                urlConnection.disconnect();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return result+"\n";
    }
}
