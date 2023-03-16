# gascharge-app-reservation

예약 컨트롤러, 전역 예외처리, 스웨거, 초기화 데이터 관련 모듈

* charge, reservation, user, token 컨트롤러
* 전역 예외 처리, 예외 클래스
* 스웨거 설정
* 스프링 컨테이너 초기화시 데이터 추가하는 빈
* 컨트롤러 유닛 테스트

*common-common, module-redis, module-security, service-reservation, service-token 의존, 독립적으로 실행 가능*

로컬, 원격 메이븐 레포지토리
```groovy
implementation 'com.gascharge.taemin:gascharge-app-reservation:0.0.1-SNAPSHOT'
```

멀티 모듈 프로젝트
```groovy
// settings.gradle
includeProject("reservation", "app")
```
```groovy
// build.gradle
implementation project(":gascharge-app-reservation")
```

YAML 파일 설정은 https://github.com/illinia/gascharge-module-yml 참조