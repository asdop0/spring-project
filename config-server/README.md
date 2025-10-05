## 프로젝트 소개
Spring Cloud Config 서버를 활용하여 중앙에서 설정을 관리하고, dev/prod 환경을 분리하여 적용하는 실습 프로젝트입니다.  
기존 프로젝트의 공통 설정(yml 등)을 환경별로 관리하여 유지보수를 간편하게 하는 것이 목표입니다.

## 적용 기술
- Spring Cloud Config
- Git 연동 (공개 repository)
- Spring Profiles (dev, prod)

## 주요 기능 및 작동 방식
- **설정 중앙 관리**
  - Config 서버가 Git 저장소의 설정 파일을 읽어 서비스 서버에 제공
  - 서비스 서버는 Config 서버에서 제공된 설정을 `spring.config.import`를 통해 적용
 
- **환경별 설정 분리**
  - 서비스 서버 실행 시 `spring.profiles.active`로 환경 지정
  - 각 환경에 맞는 yml 파일 읽어오기
 
