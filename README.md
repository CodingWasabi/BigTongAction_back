# BigTongAction_back
# 통큰 경매 백엔드 

프로젝트 **"통큰 경매"**
경매 속보 API를 활용한 실시간 경매 정보 출력 웹 서비스로,<br>
농수산물 도매 시장에서 일어나는 경매 정보를 실시간으로 보여주는 웹이다. <br>

## 개요
---
 우리나라 농수산물 도매시장에서 일어나는 경매 정보를 
 시장상인, 자영업자들에게 실시간으로 전달.

## 사용자 특성
- 원재료를 유통받아 식당 장사를하는 자영업자
 - 국내산 농수산물 도소매업자
 - 유통 중간단계의 시장상인
 - 사용자의 주된 나이는 50~60대 (추정치)

## 디자인과 배포제약사항
---
- SPA, WebSocket, REST 기반의 실시간 웹서비스
- Spring boot, PostgreSQL
- Docker와 AWS EC2를 활용한 서버 운영

>> 웹 구성은 전체적으로 대형마트 전단지의 느낌을 살려서
>> 시장 친화적인 시각적 효과와
>> 더불어 사용자의 연령층이 50~60로 추정한것을 미루어
>> 전체적인 글씨 크기를 평균 이상으로 설정
>> 글씨체는 '궁서체'를 메인으로 잡아
>> 1970-80년대 식 고전의 느낌을 살린다

<u>다음과 같은 기능을 제공한다.</u>

  - 품목 별 경매 정보 출력
  - 품목 별 실시간 경매 정보 알림
  - 품목 별 채팅 방

## UseCase 정리
 https://github.com/CodingWasabi/BigTongAction_back/wiki/UseCase-정리


---

<u>**백엔드 에서 구현해야 할 기능은**</u>
- [ ] 일일 품목별 경매 정보 리스트 최신화
- [ ] Web socket을 활용한 실시간 알림
- [ ] Web socket을 활용한 실시간 채팅

<u>**백엔드 개발과 배포를 위해 사용할 기술들**</u>

  - Spring boot 2.4.5
  - Java 11
  - PostgreSQL
  - docker
