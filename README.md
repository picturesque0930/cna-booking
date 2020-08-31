## 서비스 시나리오

### 기능적 요구사항

1. 사용자가 회의실을 예약한다.(bookingCreate)
2. 사용자는 회의실 예약을 취소 할 수 있다.(bookingCancel)
3. 회의실을 예약하면 관리자에게 승인요청이 간다.
4. 관리자는 승인을 할 수 있다.(confirmComplete)
5. 관리자는 승인 거절 할 수 있다.(confirmDeny)
6. 관리자가 승인 거절하면 예약취소한다.(bookingCancel)
7. 예약취소하면 예약정보는 삭제한다. 
8. 에약/승인 상태가 바뀔때마다 이메일로 알림을 준다.
9. 예약이 취소(bookingCancelled) 되면 

### 비기능적 요구사항
1. 트랜잭션
  - 승인거절(confirmDenied) 되었을 경우 예약을 취소한다.(Sync 호출)
  
2. 장애격리
  - 알림기능이 취소되더라도 예약과 승인 기능은 가능하다.
  - Circuit Breaker, fallback
  
3. 성능
  - 예약/승인 상태는 예약목록 시스템에서 확인 가능하다.(CQRS)
  - 예약/승인 상태가 변경될때 이메일로 알림을 줄 수 있다.(Event Driven)
  
### 분석 설계
* 이벤트스토밍 결과: http://www.msaez.io/#/storming/mOaNWpsERuRRDFTdm37r55hNZTm1/mine/595fb092dd58662b3447b8cc4f33f1e5/-MG1NAFxiCO7t8IK7chh
