package com.codingwasabi.bigtong.main.api;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class APISerivce {

    public String apiEndPoint(String now, String[] objects){
        StringBuffer result = new StringBuffer();

        String apiEndPoint = "http://openapi.epis.or.kr/openapi/service/RltmAucBrknewsService/getPrdlstRltmAucBrknewsList?"+
                "serviceKey=dzuiZZbhGGhdYgcvkdDPwvCHAdzZ%2FEkmO0%2BAqtpTaXsZLox1We%2BTJtegsxRak6NRX6gcVpEwrhGKayRbrDfjAQ%3D%3D";

        try{

            //result.append("<xml>\n");

            for(String object : objects) {
                apiEndPoint = apiEndPoint + "&dates=" + now + "&lcode=" + object +"&numOfRows=5";

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
