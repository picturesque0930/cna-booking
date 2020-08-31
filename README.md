# 주제 - 회의실 시스템

회의실 예약, 컨펌 관리 시스템 입니다.

# 구현 Repository

총 4개
1. https://github.com/aimmvp/cna-booking
2. https://github.com/aimmvp/cna-confirm
3. https://github.com/aimmvp/cna-notification
4. https://github.com/aimmvp/cna-gateway

# 서비스 시나리오

## 기능적 요구사항

1. 사용자가 회의실을 예약한다.(bookingCreate)
2. 사용자는 회의실 예약을 취소 할 수 있다.(bookingCancel)
3. 회의실을 예약하면 관리자에게 승인요청이 간다.
4. 관리자는 승인을 할 수 있다.(confirmComplete)
5. 관리자는 승인 거절 할 수 있다.(confirmDeny)
6. 관리자가 승인 거절하면 예약취소한다.(bookingCancel)
7. 예약취소하면 예약정보는 삭제한다. 
8. 에약/승인 상태가 바뀔때마다 이메일로 알림을 준다.
9. 예약이 취소(bookingCancelled) 되면 컴펌 내역이 삭제 된다. (confirmDelete)

## 비기능적 요구사항
1. 트랜잭션
  - 승인거절(confirmDenied) 되었을 경우 예약을 취소한다.(Sync 호출)
  
2. 장애격리
  - 알림기능이 취소되더라도 예약과 승인 기능은 가능하다.
  - Circuit Breaker, fallback
  
3. 성능
  - 예약/승인 상태는 예약목록 시스템에서 확인 가능하다.(CQRS)
  - 예약/승인 상태가 변경될때 이메일로 알림을 줄 수 있다.(Event Driven)
  
# 분석 설계
* 이벤트스토밍 결과: http://www.msaez.io/#/storming/mOaNWpsERuRRDFTdm37r55hNZTm1/mine/595fb092dd58662b3447b8cc4f33f1e5/-MG1NAFxiCO7t8IK7chh

## 이벤트 도출

![이벤트 스토밍](https://user-images.githubusercontent.com/67448171/91698324-7e5b3e80-ebad-11ea-8b16-48120bf8e92a.jpg)

## 기능적 요구사항을 커버하는지 검증
![기능적_비기능적 요구사항을 커버하는지 검증](https://user-images.githubusercontent.com/67448171/91702344-903fe000-ebb3-11ea-9a46-f0b949d454e3.JPG)

<기능적 요구사항 검증>

1. 사용자가 회의실을 예약한다.(bookingCreate) ok
2. 사용자는 회의실 예약을 취소 할 수 있다.(bookingCancel) ok
3. 회의실을 예약하면 관리자에게 승인요청이 간다. ok
4. 관리자는 승인을 할 수 있다.(confirmComplete) ok
5. 관리자는 승인 거절 할 수 있다.(confirmDeny) ok
6. 관리자가 승인 거절하면 예약취소한다.(bookingCancel) ok
7. 예약취소하면 예약정보는 삭제한다.  ok
8. 에약/승인 상태가 바뀔때마다 이메일로 알림을 준다.  ok
9. 예약이 취소(bookingCancelled) 되면 컴펌 내역이 삭제 된다. (confirmDelete)  ok

## 비 기능적 요구사항을 커버하는지 검증
1. 트랜잭션
  - 승인거절(confirmDenied) 되었을 경우 예약을 취소한다.(Sync 호출)  ok
  
2. 장애격리
  - 알림기능이 취소되더라도 예약과 승인 기능은 가능하다.  ok
  - Circuit Breaker, fallback   ok
  
3. 성능
  - 예약/승인 상태는 예약목록 시스템에서 확인 가능하다.(CQRS)   ok
  - 예약/승인 상태가 변경될때 이메일로 알림을 줄 수 있다.(Event Driven)   ok
  
## 헥사고날 아키텍처 다이어그램 도출  
![핵사고날](https://user-images.githubusercontent.com/67448171/91702775-2f64d780-ebb4-11ea-9a18-5ce245db3691.jpg)

- Chris Richardson, MSA Patterns 참고하여 Inbound adaptor와 Outbound adaptor를 구분함
- 호출관계에서 PubSub 과 Req/Resp 를 구분함
- 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐

# 구현:

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현함. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)
booking/  confirm/  gateway/  notification/  view/

```
cd booking
mvn spring-boot:run

cd confirm
mvn spring-boot:run 

cd gateway
mvn spring-boot:run  

cd notification
mvn spring-boot:run

cd view
mvn spring-boot:run
```

## DDD 의 적용

- 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언. 이때 가능한 현업에서 사용하는 언어 (유비쿼터스 랭귀지)를 그대로 사용함.
