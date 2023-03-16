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

## 에러 기록
### 젠킨스 Publish over ssh 플러그인으로 ssh 통신하여 명령어 전달시 실패했던 이유
  1. pipeline 코드중 ssh publisher 에서 execCommand 속성으로 docker 빌드, 실행 명령어 전달시 에러 발생
  2. 원격 서버에는 docker 가 설치되어 있고 환경변수가 등록되어 있는 상황
  3. 문제가 발생한 이유는 젠킨스 서버에 docker 이름의 환경변수가 없었고 ssh publisher 에서 실행한 쉘에서 docker 라는 환경변수를 읽지 못했기 때문
  4. 원격 서버의 docker 환경변수의 주소까지 적어서 execCommand 속성을 실행하니 정상 작동했음

YAML 파일 설정은 https://github.com/illinia/gascharge-module-yml 참조