# TimeTravel_Server

<img width="1309" alt="image" src="https://github.com/Neordinary4th-TimeTravel/TimeTravel_Server/assets/103025266/1d3cd606-98d0-45f4-9627-c7e43f151cdb">

# [2023 4th Ne(o)rdinary HACKATHON]

## 우리들의 추억여행

#### Project Name : TimeTravel

#### Subject : 네버랜드 신드롬, openAI를 활용해 과거와 현재를 이어주자

#### Project execution period : 2023.06.10 ~ 2023.06.11

#### Contributors : [@이지호](https://github.com/destiny3912) | [@길지운](https://github.com/wldns2577) | [@유종건](https://github.com/mycookie1) 
------------------------
## 개발 환경
- JDK 8
- Springboot
- Spring Data JPA + JPQL
- Swagger
- Docker
- AWS EC2 + RDS
- MySQL
- OpenAI
----------------------

# 주요 기능
 <img src = "https://github.com/Neordinary4th-TimeTravel/TimeTravel_Server/assets/103025266/2b2eb108-2f38-4b92-8d60-4e08d1c064e2" width = "100%" height = "100%">
 
- 캡슐(게시글) ..
- 커뮤니티 ..
- OpenAI를 활용한..

---------------
## OpenAI 활용
ChatGpt와 한 번 대화하는 기능 구현 

https://platform.openai.com/docs/api-reference/completions/create 를 참고하여 java로 작성
<pre>
<code>
 // header 등록: Content-Type, Authorization(API KEY), Message
 HttpHeaders headers = new HttpHeaders();
 headers.setContentType(MediaType.APPLICATION_JSON);
 headers.set("Authorization", "Bearer " + Secret.OPEN_API_SECRET_KEY);

 Map<String, Object> requestBody = new HashMap<>();

 // Message: {role, content}, Message 등록
 requestBody.put("model", "gpt-3.5-turbo");
 requestBody.put("messages", messages);
 
 // respone 구하기
 // ENDPOINT = "https://api.openai.com/v1/chat/completions"
 RestTemplate restTemplate = new RestTemplate();
 ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);
 
</code>
</pre>

--------------------
### 파일 구조

<pre>
<code>
 demo
    ├── DemoApplication.java
    ├── common
    │   ├── Constant.java
    │   ├── config
    │   │   ├── RestTemplateConfig.java
    │   │   ├── SwaggerConfig.java
    │   │   └── WebConfig.java
    │   ├── entity
    │   │   └── BaseEntity.java
    │   ├── exceptions
    │   │   ├── BaseException.java
    │   │   └── ExceptionAdvice.java
    │   ├── response
    │   │   ├── BaseResponse.java
    │   │   └── BaseResponseStatus.java
    │   ├── scroll
    │   │   └── ScrollPaginationCollection.java
    │   └── secret
    │       └── Secret.java
    ├── src
    │   ├── Open
    │   │   ├── CategoryOpenRepositroy.java
    │   │   ├── MemberOpenRepository.java
    │   │   ├── OpenController.java
    │   │   ├── OpenService.java
    │   │   ├── PostOpenRepository.java
    │   │   └── dto
    │   │       ├── GetOpenAIReqDto.java
    │   │       ├── Message.java
    │   │       ├── PostCapsuleReqDto.java
    │   │       └── PostCapsuleResDto.java
    │   ├── member
    │   │   ├── MemberController.java
    │   │   ├── MemberRepository.java
    │   │   ├── MemberService.java
    │   │   ├── dto
    │   │   │   ├── CapDto.java
    │   │   │   ├── CapResDto.java
    │   │   │   ├── CapSightResDto.java
    │   │   │   ├── EmailCheckReqDto.java
    │   │   │   ├── EmailCheckResDto.java
    │   │   │   ├── FindScrapCategoryResDto.java
    │   │   │   ├── JoinReqDto.java
    │   │   │   ├── JoinResDto.java
    │   │   │   ├── LoginReqDto.java
    │   │   │   ├── LoginResDto.java
    │   │   │   ├── PatchNicknameReqDto.java
    │   │   │   └── PatchNicknameResDto.java
    │   │   └── entity
    │   │       └── Member.java
    │   └── post
    │       ├── PostController.java
    │       ├── PostService.java
    │       ├── dto
    │       │   ├── CreateCommentReqDto.java
    │       │   ├── FindPostByCategoryResDto.java
    │       │   ├── FindPostByTextResDto.java
    │       │   ├── FindPostByYearResDto.java
    │       │   ├── ScrapPostCategoryReqDto.java
    │       │   ├── ToggleCapsuleLikeReqDto.java
    │       │   ├── ToggleCapsuleLikeResDto.java
    │       │   ├── ViewImminentCapsuleResDto.java
    │       │   ├── ViewPostCategoryResDto.java
    │       │   └── ViewPostResDto.java
    │       ├── entity
    │       │   ├── Category.java
    │       │   ├── CategoryScrap.java
    │       │   ├── Comment.java
    │       │   ├── Post.java
    │       │   ├── PostLike.java
    │       │   └── PostTag.java
    │       └── repository
    │           ├── CategoryRepository.java
    │           ├── CategoryScrapRepository.java
    │           ├── CommentRepository.java
    │           ├── PostLikeRepository.java
    │           ├── PostRepository.java
    │           └── PostTagRepository.java
    └── utils
        ├── JwtService.java
        ├── SHA256.java
        └── ValidationRegex.java
        
        </code>
</pre>
