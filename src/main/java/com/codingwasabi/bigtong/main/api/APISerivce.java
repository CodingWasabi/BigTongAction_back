package com.codingwasabi.bigtong.main.api;

import com.codingwasabi.bigtong.main.api.subject.entity.*;
import com.codingwasabi.bigtong.main.api.subject.exception.NoDataInDBandInput;
import com.codingwasabi.bigtong.main.api.subject.exception.NoPreviousDataInTable;
import com.codingwasabi.bigtong.main.api.subject.repository.*;
import com.codingwasabi.bigtong.main.dto.Item;
import com.codingwasabi.bigtong.main.dto.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class APISerivce {

    private final GrainRepository grainRepository;
    private final FruitRepository fruitRepository;
    private final FishRepository fishRepository;
    private final VegetableRepository vegetableRepository;
    private final MeatRepository meatRepository;
    private List<Item> updatedList;
    private int index;

    // 데이터 베이스로 부터 최신 5개 불러오기
    public List<Subject> returnTop5(String subject){
        if(subject.equals("GRAIN"))
            return grainRepository.findTop5ByOrderByBidtimeDesc();
        else if (subject.equals("FRUIT"))
            return fruitRepository.findTop5ByOrderByBidtimeDesc();
        else if (subject.equals("FISH"))
            return fishRepository.findTop5ByOrderByBidtimeDesc();
        else if (subject.equals("VEGETABLE"))
            return vegetableRepository.findTop5ByOrderByBidtimeDesc();
        else if (subject.equals("MEAT"))
            return meatRepository.findTop5ByOrderByBidtimeDesc();

        return null;
    }

    private LocalDateTime dateFormat(String bidtime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
            bidtime = "21-".concat(bidtime);
            bidtime = bidtime.replace("/", "-");

            return LocalDateTime.parse(bidtime, formatter);

    }

    /**
     * 동일한 데이터인지 여부 확인
     * 동일한 데이터 -> 업데이트 안함 : false
     * 동일한 데이터가 아님 -> 업데이트 진행 : true
     * @param itemList
     * @param subject
     * @return
     */
    private Optional<List<Item>> checkUpdated(List<Item> itemList, Subject subject){

        if(!itemList.isEmpty()){

            // 데이터베이스에 저장된 subject가 없다면 들어온 데이터 전부 입력
            if(subject.getBidtime() == null)
                return Optional.of(itemList);

            else{
                // 05/30 HH:mm:ss  -> 21-05-30 HH:mm:ss 로 포맷
                // localdatetime 으로 포매팅

                LocalDateTime subject_bidtime = dateFormat(subject.getBidtime());

                updatedList = new ArrayList<>();

                for(Item item : itemList){

                    LocalDateTime item_bidtime = dateFormat(item.bidtime);

                    // item의 날짜가 더 최신이라면
                    if(item_bidtime.isAfter(subject_bidtime))
                        updatedList.add(item);

                }
                return Optional.of(updatedList);
            }
        }
        return Optional.empty();
    }

    @Transactional
    public List<Subject> manageSubject(String[] subjectNum,String subject ){
        String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if(subject.equals("GRAIN")){

            // null이면 값이 없는 빈 객체 입력 -> exception 발생 안하는 이유는, 업데이트로 값이 채워지기 때문이다.
            Grain grain = grainRepository.findFirstByOrderByBidtimeDesc()
                    .orElse(new Grain());

            // optional 로 바꾸기
            List<Item> itemList = checkUpdated(apiEndPoint(now,subjectNum),grain)
                    .orElseThrow(NoDataInDBandInput::new);

            // 업데이트가 되었다면
            if(!itemList.isEmpty()){

                // 리턴할 리스트
                List<Subject> updateSubjectList = new ArrayList<>();
                index=0;

                for(Item item : itemList){
                        Grain new_grain = new Grain(item.bidtime,item.mclassname,item.price,item.unitname);
                        grainRepository.save(new_grain);
                        updateSubjectList.add(new_grain);
                    index++;
                }
                return updateSubjectList;
            }

        }



        else if (subject.equals("FISH")){

            Fish fish = fishRepository.findFirstByOrderByBidtimeDesc()
                    .orElse(new Fish());

            List<Item> itemList = checkUpdated(apiEndPoint(now,subjectNum),fish)
                    .orElseThrow(NoDataInDBandInput::new);

            // update 가 되었다면
            if(!itemList.isEmpty()) {

                if(!itemList.isEmpty()) {
                    index = 0;
                    List<Subject> updateSubjectList = new ArrayList<>();

                    for (Item item : itemList) {
                        if (index > 5)
                            break;
                        else {
                            Fish new_fish = new Fish(item.bidtime, item.mclassname, item.price, item.unitname);
                            fishRepository.save(new_fish);
                            updateSubjectList.add(new_fish);
                        }
                        index++;
                    }
                    return updateSubjectList;
                }
            }
        }


        else if (subject.equals("FRUIT")){

            Fruit fruit = fruitRepository.findFirstByOrderByBidtimeDesc()
                    .orElse(new Fruit());

            List<Item> itemList = checkUpdated(apiEndPoint(now,subjectNum),fruit)
                    .orElseThrow(NoDataInDBandInput::new);

            // update 가 되었다면
            if(!itemList.isEmpty()) {
                index= 0;
                List<Subject> updateSubjectList = new ArrayList<>();

                for(Item item : itemList){
                    if(index>5)
                        break;
                    else{
                        Fruit new_fruit = new Fruit(item.bidtime,item.mclassname,item.price,item.unitname);
                        fruitRepository.save(new_fruit);
                        updateSubjectList.add(new_fruit);
                    }
                    index++;
                }
                return updateSubjectList;
            }
        }


        else if (subject.equals("VEGETABLE")){

            Vegetable vegetable = vegetableRepository.findFirstByOrderByBidtimeDesc()
                    .orElse(new Vegetable());

            List<Item> itemList = checkUpdated(apiEndPoint(now,subjectNum),vegetable)
                    .orElseThrow(NoDataInDBandInput::new);

            // update 가 되었다면
            if(!itemList.isEmpty()) {
                index = 0;
                List<Subject> updateSubjectList = new ArrayList<>();

                for(Item item : itemList){
                    if(index>5)
                        break;
                    else{
                        Vegetable new_vegetable = new Vegetable(item.bidtime,item.mclassname,item.price,item.unitname);
                        vegetableRepository.save(new_vegetable);
                        updateSubjectList.add(new_vegetable);
                    }
                    index++;
                }
                return updateSubjectList;
            }
        }

        else if(subject.equals("MEAT")){


            Meat meat = meatRepository.findFirstByOrderByBidtimeDesc()
                    .orElse(new Meat());

            List<Item> itemList = checkUpdated(apiEndPoint(now,subjectNum),meat)
                    .orElseThrow(NoDataInDBandInput::new);

            // update 가 되었다면
            if(!itemList.isEmpty()) {
                index=0;
                List<Subject> updateSubjectList = new ArrayList<>();

                for(Item item : itemList){
                    if(index>5)
                        break;
                    else{
                        Meat new_meat = new Meat(item.bidtime,item.mclassname,item.price,item.unitname);
                        meatRepository.save(new_meat);
                        updateSubjectList.add(new_meat);
                    }
                    index++;
                }
                return updateSubjectList;
            }
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

                apiEndPoint = apiEndPoint + "&dates="+ now + "&lcode=" + object +"&numOfRows=2";

                Response response = parser(get(apiEndPoint));

                // item들이 비어 있지 않으면 add
                if (response.body.items != null) {
                    for (Item item : response.body.getItems()){
                        objectItemList.add(item);
                    }
                }

            }

        }catch (Exception e ){
            e.printStackTrace();
        }

        return objectItemList;
    }


    private String get(String url){
        // time out 5초 설정
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5 * 1000);
        factory.setReadTimeout(5* 1000);

        RestTemplate restTemplate = new RestTemplate(factory);

        // XML 한글 깨짐 방지
        // restTemplate 기본 인코딩을 UTF-8 로 바꿔줌
        restTemplate.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));


        String response = null;
        try{
        response = restTemplate.getForObject(url,String.class);
        }catch (ResourceAccessException r){
            log.error("timeout");
        }catch (IllegalArgumentException i){
            log.error("timeout");
        }

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
