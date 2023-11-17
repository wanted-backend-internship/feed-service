<div align="center">
  <img src="https://github.com/wanted-backend-internship/feed-service/assets/119668620/39d1e679-0388-442d-994f-88d0c45c593f" width=600/>
</div>

<br>
<br>
<br>
<br>

# 소셜 미디어 통합 𝙁𝙚𝙚𝙙 𝙎𝙚𝙧𝙫𝙞𝙘𝙚
## 서비스 개요

유저 계정의 해시태그(`#sieunnnn`) 를 기반으로 `인스타그램`, `스레드`, `페이스북`, `트위터` 등 복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 통합 Feed 어플리케이션 입니다. 이를 통해 고객은 하나의 채널로 유저(`#sieunnnn`), 또는 브랜드(`#sieunshop`) 의 SNS 노출 게시물 및 통계를 확인할 수 있습니다.

<br>

## 서비스 플로우
- 유저는 계정(추후 해시태그로 관리), 비밀번호, 이메일로 가입요청을 진행합니다.
- 가입 요청 시, 이메일로 발송된 코드를 입력하여 이메일 인증을 받고 서비스 이용이 가능합니다.
- 서비스 로그인 시, 메뉴는 통합 Feed 단일 입니다.
- 통합 Feed 에선 `인스타그램`, `스레드`, `페이스북`, `트위터` 에서 유저의 계정이 태그된 글들을 확인합니다.
- 또는, 특정 해시태그(1건)를 입력하여, 해당 해시태그가 포함된 게시물들을 확인합니다.
- 유저는 본인 계정명 또는 특정 해시태그 일자별, 시간별 게시물 갯수 통계를 확인할 수 있습니다.

<br>

## 수행 목표
### Restful 한 API 설계 도전하기
이전에 프로젝트를 하면서 느낀점은 REST API 설계를 했다고 했는데 전혀 RESTFul 하지 않다는 점 이였습니다. 강사님의 수업을 듣고 부족한 점을 개선해보기로 했습니다. 2 주차 세션 정리본을 바탕으로 더 나은 설계를 향해 가보도록 하려 합니다. 😋

### JWT 인증 체계 이해하기
이번 프로젝트의 주제는 SNS 서버에 특정 게시글에 관한 정보를 요청하고 해당하는 자원을 받아오는 것입니다. 하지만 여기서 의문점이 들었습니다. **SNS 서버는 요청만 하면 무작정 자원을 건내줄까요?** 
이전 프로젝트에서 Oauth2.0 프로토콜을 사용하여 소셜 로그인을 구현 했을 때를 생각해보면 인증 요청을 하고, 구글에서 어세스토큰과 리프레시토큰을 받았습니다. 그리고 이를 사용하여 인증 절차를 밟았었죠. 이런식으로 요청자에 대한 확인이 필요하다는 생각이 들어 나름의 인증 체계를 세워보려 합니다.
아직 부족한 점이 많아 맞는 방식인지는 의문이 들지만! 토큰을 통한 인증 방식을 이해하고, 사용하는 프로젝트가 될 것 같습니다. 😇

### QueryDSL 이용하기
이전부터 써보고 싶었지만 적당한 이유를 찾지못해 미뤄 뒀지만, 이번에는 사용하기에 좋을 것 같아 사용해보기로 결정 하였습니다. <br>
이용하게 된 이유와, 개발하면서 발생한 트러블, 그리고 이를 해결하는 과정을 [QueryDSL 로 알맞은 데이터 뽑아내기](https://www.notion.so/QueryDSL-50fe41426de945de89bf027f3d7a2f9b?pvs=21) 에 담아두었습니다.

<br>

## 사용 기술
<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white" height="25px">  <img src="https://img.shields.io/badge/Mysql-4479A1?style=flat&logo=mysql&logoColor=white" height="25px"> <img src="https://img.shields.io/badge/queryDSL-0769AD?style=flat&logo=java&logoColor=white" height="25px">
    <img src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white" height="25px"> 
    <img src="https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white" height="25px"> <img src="https://img.shields.io/badge/JWT Webtoken-black?style=flat&logo=JSON%20web%20tokens" height="25px"> <img src="https://img.shields.io/badge/IntelliJ IDEA-000000.svg?style=flat&logo=intellij-idea&logoColor=white" height="25px"> <br>
    <img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=white" height="25px"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springBoot&logoColor=white" height="25px"> 
    <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springSecurity&logoColor=white" height="25px"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=Postman&logoColor=white" height="25px">
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat&logo=Redis&logoColor=white" height="25px">
  
<br>

## 링크
### 🔗 피드 서비스 포스트맨 api 문서
https://documenter.getpostman.com/view/26140052/2s9YXo2fQY
### sns 서비스 포스트맨 api 문서
https://documenter.getpostman.com/view/26140052/2s9YXo2fQV
### 프로젝트 노션 링크
https://sieun96.notion.site/Feed-262f912764a148e7a025723ebe040ef4?pvs=4
- 각종 다이어그램을 확인할 수 있어요.
- 프로젝트를 진행하면서 한 고찰을 확인할 수 있어요.
- 프로젝트를 마치며 회고한 내용을 볼 수 있어요.

<br>
<br>


