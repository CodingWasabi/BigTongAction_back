package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.main.dto.Item;
import com.codingwasabi.bigtong.main.dto.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class APISerivce {

    public List<Item> apiEndPoint(String now, String[] objects){
        StringBuffer result = new StringBuffer();
        List<Item> objectItemList = new ArrayList<>();

        String apiEndPoint = "http://openapi.epis.or.kr/openapi/service/RltmAucBrknewsService/getPrdlstRltmAucBrknewsList?"+
                "serviceKey=dzuiZZbhGGhdYgcvkdDPwvCHAdzZ%2FEkmO0%2BAqtpTaXsZLox1We%2BTJtegsxRak6NRX6gcVpEwrhGKayRbrDfjAQ%3D%3D";

        try{
            for(String object : objects) {
                log.info(object+" 들어옴");
                apiEndPoint = apiEndPoint + "&dates=" + now + "&lcode=" + object +"&numOfRows=2";

                Response response = parser(get(apiEndPoint));

                // item들이 비어 있지 않으면 add
                if (! (response.body.items == null)) {
                    for (Item item : (List<Item>) response.body.getItems())
                        objectItemList.add(item);


                }

                log.info(object+" 완료");
            }

        }catch (Exception e ){
            e.printStackTrace();
        }

        return objectItemList;
    }


    private String get(String url){
        RestTemplate restTemplate = new RestTemplate();

        // XML 한글 깨짐 방지
        // restTemplate 기본 인코딩을 UTF-8 로 바꿔줌
        restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));

        //log.info("try to get XML :  " + url );
        String response = restTemplate.getForObject(url,String.class);
        //log.info("finish get " + response);

        return response;
    }

    // String으로 된 XML 을 Response.class 로 parser
    private Response parser(String xml){
        ObjectMapper xmlMapper = new XmlMapper();
        Response response = null;
        try{
            response = xmlMapper.readValue(xml,Response.class);
        }catch (JsonMappingException e){
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return response;
    }

}
