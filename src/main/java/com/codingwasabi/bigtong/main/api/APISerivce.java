package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.main.api.subject.entity.Fruit;
import com.codingwasabi.bigtong.main.api.subject.entity.Grain;
import com.codingwasabi.bigtong.main.api.subject.entity.Subject;
import com.codingwasabi.bigtong.main.api.subject.entity.Vegetable;
import com.codingwasabi.bigtong.main.api.subject.repository.*;
import com.codingwasabi.bigtong.main.dto.Item;
import com.codingwasabi.bigtong.main.dto.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class APISerivce {

    private final GrainRepository grainRepository;
    private final FruitRepository fruitRepository;
    private final FishRepository fishRepository;
    private final VegetableRepository vegetableRepository;
    private final MeatRepository meatRepository;

    private boolean checkUpdated(Item item, Subject subject){
        if(subject == null)
            return true;

        else if(!item.mclassname.equals(subject.getMclassname())
                || !item.bidtime.equals(subject.getBidtime())
                || !item.price.equals(subject.getPrice()))
            return true;

        return false;

    }

    public List<Subject> manageSubject(String[] subjectNum,String subject ){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));


        if(subject.equals("GRAIN")){
            Grain grain = grainRepository.findTop1ByOrderByBidtimeDesc();

            List<Item> itemList = apiEndPoint(now,subjectNum);

            // update 가 되었다면
            if(!itemList.isEmpty())
                if(checkUpdated(itemList.get(0),grain)){
                    log.info("itemList size : " + itemList.size());
                    grainRepository.deleteAll();
                    int index = 0;
                    for(Item item : itemList){
                        if(index>4)
                            break;
                        index++;
                        Grain new_grain = new Grain(item.bidtime,item.mclassname,item.price,item.unitname);
                        log.info("new_grain 이름 : " + new_grain.getMclassname());
                        grainRepository.save(new_grain);
                    }
                }

            return grainRepository.findTop5ByOrderByBidtimeDesc();
        }


        else if (subject.equals("FISH")){

        }
        else if (subject.equals("FRUIT")){

        }
        else if (subject.equals("VEGETABLE")){

        }
        else {

        }

        return null;
    }

    public List<Item> apiEndPoint(String now, String[] objects){
        StringBuffer result = new StringBuffer();
        List<Item> objectItemList = new ArrayList<>();



        try{
            for(String object : objects) {
                String apiEndPoint = "http://openapi.epis.or.kr/openapi/service/RltmAucBrknewsService/getPrdlstRltmAucBrknewsList?"+
                        "serviceKey=dzuiZZbhGGhdYgcvkdDPwvCHAdzZ%2FEkmO0%2BAqtpTaXsZLox1We%2BTJtegsxRak6NRX6gcVpEwrhGKayRbrDfjAQ%3D%3D";

                log.info(object+" 들어옴");
                apiEndPoint = apiEndPoint + "&dates=20210528" + "&lcode=" + object +"&numOfRows=2";

                Response response = parser(get(apiEndPoint));

                // item들이 비어 있지 않으면 add
                if (response.body.items != null) {
                    for (Item item : response.body.getItems()){
                        objectItemList.add(item);

                        log.info("이름 : "+item.mclassname);
                    }
                }
                log.info("개수 : " + objectItemList.size());
                log.info(object+" 완료");
            }

        }catch (Exception e ){
            e.printStackTrace();
        }

        // 날짜 내림차순 (최신순)
        objectItemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                String prefix = "21-";
                if(!o1.bidtime.contains("21-")) {
                    o1.bidtime = prefix.concat(o1.bidtime);
                    o1.bidtime = o1.bidtime.replace("/", "-");
                    log.info("time : " + o1.bidtime);
                }

                if(!o2.bidtime.contains("21-")) {
                    o2.bidtime = prefix.concat(o2.bidtime);
                    o2.bidtime = o2.bidtime.replace("/", "-");
                    log.info("time : " + o2.bidtime);
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
                LocalDateTime o1_date = LocalDateTime.parse(o1.bidtime,formatter);
                LocalDateTime o2_date = LocalDateTime.parse(o2.bidtime,formatter);

                if(o1_date.isEqual(o2_date))
                    return 0;
                else if(o1_date.isAfter(o2_date))
                    return -1;
                else
                    return 1;
            }
        });

        return objectItemList;
    }


    private String get(String url){
        RestTemplate restTemplate = new RestTemplate();

        // XML 한글 깨짐 방지
        // restTemplate 기본 인코딩을 UTF-8 로 바꿔줌
        restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));

        log.info("try to get XML :  " + url );
        String response = restTemplate.getForObject(url,String.class);
        log.info("finish get " + response);

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
