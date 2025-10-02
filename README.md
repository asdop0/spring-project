 ## 프로젝트 소개

이 프로젝트는 기존에 진행한 프로젝트 [sw_project](https://github.com/asdop0/sw_project)를 기반으로, 특정 기능을 개선하고 성능 및 안정성을 높이는 것을 목표로 수행한 개인 학습 프로젝트입니다. 각 개선 사항은 일부분씩 적용하여 작업 시간을 효율적으로 관리하면서, 기술 적용에 따른 동작 원리와 효과를 기록으로 남겼습니다. 이를 통해 실무에서 필요한 개선 역량과 기초 기술을 차근차근 쌓아가고 있으며, 앞으로도 관심 있는 기술들을 추가로 실험하고 확장해나갈 예정입니다.

<details>
<summary><h2>SpringConfigServer</h2></summary>
 <h3>프로젝트 소개</h3>
 API 서버들의 환경 설정을 중앙 관리하기 위해 Spring Cloud Config로 설정 서버를 구축한 프로젝트입니다.

 <h3>주요 기능</h3>
 
 - **Git 저장소에 설정 파일을 중앙 집중화**
 - **개발/운영 환경에 따라 다른 설정 파일 제공**
 - **API 서버가 설정 서버를 통해 외부 설정을 동적으로 읽어옴**

 <h3>설정 구조</h3>
 
 ```
 {server-name}.properties      # 공통 설정 
 {server-name}-dev.properties  # 개발 설정 
 {server-name}-prod.properties # 운영 설정 
 ```

<h3>실습 예시</h3>
포스트맨으로 '/hello' 요청을 보내면 Config 서버의 환경 설정 파일에 정의된 메시지(dev/prod)에 따라 다른 응답을 반환합니다. </br>
<img width="278" alt="image" src="https://github.com/user-attachments/assets/541e68ca-1522-4d21-9c99-f527481422b0" />



</details>


## 실습한 기술 목록

<details>
<summary>DB 조회 응답 속도 단축 및 트래픽 안정성 개선</summary>
 
- **목적**: 반복적인 DB 조회와 트래픽 증가 문제를 해결하여, 웹 애플리케이션의 응답 속도와 안정성을 개선

- **적용 기술**
  - Redis 캐싱: 반복 조회 데이터 저장 → DB 조회 횟수 감소, 응답 속도 단축
  - Nginx 로드밸런싱: 단일 서버 집중 부하 분산 → 동시 요청 처리량 증가, 타임아웃 감소

- **주요 성과**
  - 평균 응답 속도 약 50% 단축 (캐시 적용)
  - 동시 요청 처리량 증가 및 타임아웃 0건 달성 (캐싱 + 로드밸런싱)
  
- **실습 코드**: [`sw-camping-redis/`](./sw-camping-redis)  
- **자세히 보기:** [캐싱 및 로드밸런싱 정리](sw-camping-redis/README.md)

</details>


</details>

<details>
<summary>Spring Cloud Config 서버</summary>

- **목적**: 운영(prod)과 개발(dev) 환경 설정 분리  
- **성과**: 환경별 설정 관리 자동화  
- **실습 코드**: [`config-server/`](./config-server)

</details>

<details>
<summary>회원가입 개선</summary>

- **목적**: 이메일 인증을 통해 무분별한 회원가입 방지
 
- **적용 기술**
  - Redis 메시지 큐: 인증 요청과 이메일 발송을 비동기 처리
  
- **주요 성과**
  - 이메일 인증 기능 구현
  - 비동기 메시지 처리로 서버 부하 분산 및 안정성 확보
  
- **실습 코드**: [`email/`](./email)
- **자세히 보기:** [이메일 인증 정리](email/README.md)

</details>

