# SpringBoot Template
본 템플릿은 소프트스퀘어드 서버 교육용 Spring Boot 템플릿 입니다.

## ✨Common
### REST API
REST API를 처리하는 SpringBoot 프로젝트   
동작하는 기본 구성 원리를 반드시 숙지하자.

### Folder Structure
- `src`: 메인 로직
  `src`에는 도메인 별로 패키지를 구성하도록 했다. **도메인**이란 회원(User), 게시글(Post), 댓글(Comment), 주문(Order) 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면 된다. 각자 설계할 APP을 분석하고 필요한 도메인을 도출하여 `src` 폴더를 구성하자.
- `common`: 메인 로직은 아니지만 `src` 에서 필요한 부차적인 파일들을 모아놓은 폴더
  - `config`: 설정 파일 관련 폴더
  - `entity`: 공통 Entity 관리 폴더
  - `exceptions`: 예외처리 관리 폴더
  - `oauth`: Oauth 인증에 필요한 파일 관리 폴더
  - `response`: baseResponse를 관리하는 폴더
  - `secret`: 보안과 관련된 파일 관리 폴더(차후 환경 변수로 분리 추천)
  - `Constant`: 상수와 관련된 내용  


- 도메인 폴더 구조
> Controller - Service - Repository

- `Controller`: Request를 처리하고 Response 해주는 곳. (Service에 넘겨주고 다시 받아온 결과값을 형식화), 형식적 Validation
- `Service`: 비즈니스 로직 처리, 논리적 Validation
- `Repository`: Spring Data JPA Interface를 상속받아 DB 처리를 가능하게 해준다.


### Request & Response Process
다음과 같이 Request에 대해 DB 단까지 거친 뒤, 다시 Controller로 돌아와 Response 해주는 구조를 갖는다. 구조를 먼저 이해하고 템플릿을 사용하자.

#### SpringBoot (빌드 도구 = Gradle)
> Request(시작) / Response(끝) ⇄ Controller ⇄ Service  ⇄ Repository (DB)

### Validation
서버 API 구성의 기본은 Validation을 잘 처리하는 것이다. 외부에서 어떤 값을 날리든 Validation을 잘 처리하여 서버가 터지는 일이 없도록 유의하자.
값, 형식, 길이 등의 형식적 Validation은 Controller에서,
DB에서 검증해야 하는 의미적 Validation은 Service에서 처리하면 된다.

### JPA
자바 진영의 표준 ORM 기술이다.  
어노테이션을 통해 Entity 및 연관관계를 매핑하고, DAO에서 EntityManager를 DI(의존성주입) 받아서 사용한다.

### Spring Data JPA
Spring에서 JPA를 보다 편리하게 사용할 수 있게 제공해주는 라이브러리이다.  
Spring Data JPA는 JpaRepository를 상속받는 인터페이스만 구현해주면 곧바로 활용할 수 있다.  
JpaRepository 인터페이스는 기본적인 CRUD 및 필요한 메서드를 제공해준다.  
자세한 코드는 `test 도메인`을 참고하자.


## ✨Structure
앞에 (*)이 붙어있는 파일(or 폴더)은 추가적인 과정 이후에 생성된다.
```text
api-server-spring-boot
  > * build
  > gradle
  > src.main.java.com.example.demo
    > common
      > config
        | RestTemplateConfig.java // HTTP get,post 요청을 날릴때 일정한 형식에 맞춰주는 template
        | SwaggerConfig.java // Swagger 관련 설정
        | WebConfig.java // Web 관련 설정(CORS 설정 포함)
      > entity
        | BaseEntity.java // create, update, state 등 Entity에 공통적으로 정의되는 변수를 정의한 BaseEntity
      > exceptions
        | BaseException.java // Controller, Service에서 Response 용으로 공통적으로 사용 될 익셉션 클래스
        | ExceptionAdvice.java // ExceptionHandler를 활용하여 정의해놓은 예외처리를 통합 관리하는 클래스
      > oauth
        | GoogleOauth.java // Google OAuth 처리 클래스
        | OAuthService.java // OAuth 공통 처리 서비스 클래스
        | SocialOauth.java // OAuth 공통 메소드 정의 인터페이스
      > response
        | BaseResponse.java // Controller 에서 Response 용으로 공통적으로 사용되는 구조를 위한 모델 클래스
        | BaseResponseStatus.java // Controller, Service에서 사용할 Response Status 관리 클래스 
      > secret
        | Secret.java // jwt 암호키 보관 클래스
      | Constant // 상수 보관 클래스
    > src
      > test
        > entity
          | Comment.java // Comment Entity
          | Memo.java // Memo Entity
        > model
          | GetMemoDto.java
          | MemoDto.java
          | PostCommentDto.java
        | TestController.java // Memo API Controller
        | TestService.java // Memo API Service
        | MemoRepository.java // Memo Spring Data JPA
        | CommentRepository.java // Comment Spring Data JPA
      > user
        > entity
          | User.java // User Entity
        > model
          | GetSocialOAuthRes.java // OAuth 인증 관련 DTO(토튼 정보)
          | GetUserRes.java    
          | GoogleUser.java // OAuth 인증 관련 DTO(유저 정보)
          | PatchUserReq.java
          | PostLoginReq.java
          | PostLoginRes.java 
          | PostUserReq.java 
          | PostUserRes.java 
        | UserController.java
        | UserService.java
        | UserRepository.java
    > utils
      | JwtService.java // JWT 관련 클래스
      | SHA256.java // 암호화 알고리즘 클래스
      | ValidateRegex.java // 정규표현식 관련 클래스
    | DemoApplication // SpringBootApplication 서버 시작 지점
  > resources
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
    | logback-spring.xml // logback 설정 xml 파일
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```
## ✨Description


### Annotation
스프링 부트는 `어노테이션`을 다양하게 아는 것이 중요하다. SpringBoot의 시작점을 알리는 `@SpringBootApplication` 어노테이션 뿐만 아니라 `스프링부트 어노테이션` 등의 키워드로 구글링 해서 **스프링 부트에서 자주 사용되는 다양한 어노테이션을 이해하고 외워두자.**

### Lombok
Java 라이브러리로 반복되는 getter, setter, toString 등의 메서드 작성 코드를 줄여주는 라이브러리이다. 기본적으로 각 도메인의 model 폴더 내에 생성하는 클래스에 lombok을 사용하여 코드를 효율적으로 짤 수 있도록 구성했다. 자세한 내용은 구글링과 model > PostUser, User를 통해 이해하자.

### common - config
프로젝트 동작에 필요한 라이브러리 설정 값 등을 정의하는 클래스들을 모아 놓은 패키지

### @ExceptionHandler
스프링 내부에서 발생한 예외를 잡아서 처리해주는 어노테이션이다.  
기본적으로 Controller에 정의하여 처리할 수 있으며, @ControllerAdvice를 통해 통합적으로 관리할 수 있다.

### common - oauth
소셜 로그인과 관련된 처리를 하는 코드를 작성하는 패키지.  
예제 코드는 Google OAuth를 기반으로 작성되어 있다.


### src - main - resources
템플릿은 크게 log 폴더와 src 폴더로 나뉜다. log는 통신 시에 발생하는 오류들을 기록하는 곳이다. 실제 메인 코드는 src에 담겨있다. src > main > resources를 먼저 살펴보자.

`application.yml`

에서 **포트 번호를 정의**하고 **DataBase 연동**을 위한 값을 설정한다.


### src - main - java

`com.example.demo` 패키지에는 크게 `common` 폴더, `src` 폴더와 이 프로젝트의 시작점인 `DemoApplication.java`가 있다.

`DemoApplication.java` 은 스프링 부트 프로젝트의 시작을 알리는 `@SpringBootApplication` 어노테이션을 사용하고 있다. (구글링 통해 `@SpringBootApplication`의 다른 기능도 살펴보자.)

`src`폴더에는 실제 **API가 동작하는 프로세스**를 담았고 `common` 폴더에는 `src`에서 필요한 Base 클래스, 상수 클래스를, `util` 폴더에는 정규표현식 등의 클래스를 모아놨다.

`src`를 자세하게 살펴보자. `src`는 각 **도메인**별로 패키지를 구분해 놓는다. 현재는 `user` 도메인과 `test` 도메인이 있다. **도메인**이란 게시글, 댓글, 회원, 정산, 결제 등 소프트웨어에 대한 요구사항 혹은 문제 영역이라고 생각하면 된다.

이 도메인들은 API 통신에서 어떤 프로세스로 처리되는가? API 통신의 기본은 Request → Response이다. 스프링 부트에서 **어떻게 Request를 받아서, 어떻게 처리하고, 어떻게 Response 하는지**를 중점적으로 살펴보자. 전반적인 API 통신 프로세스는 다음과 같다.

> **Request** → `XXXController.java` → `Service`(=Business Logic) → `Repository` (DB) → **Response**

#### 1. Controller / `UserController.java`, `TestController.java`  / @RestController

> 1) API 통신의 **Routing** 처리
> 2) Request를 다른 계층에 넘기고 처리된 결과 값을 Response 해주는 로직
>  + Request의 **형식적 Validation** 처리 (DB를 거치지 않고도 검사할 수 있는)

**1) `@AllArgsConstructor`**

UserController의 생성자에 `@AllArgsConstructor` 어노테이션이 붙어있다. 이는 **의존성 주입**을 위한 것으로, `UserController`  뿐만 아니라 다음에 살펴볼 `UserService`, `UserDao`의 생성자에도 각각 붙어 있는 것을 확인할 수 있다. 간단히 요약하면 객체 생성을 자동으로 해주는 역할이다. 자세한 프로세스는 구글링을 통해 살펴보자.

나머지 어노테이션들 역시 구글링을 통해 이해하자.

**2) `BaseResponse`**

Response할 때, 공통 부분은 묶고 다른 부분은 제네릭을 통해 구현함으로써 반복되는 코드를 줄여준다. (`BaseResponse.java` 코드 살펴 볼 것. 여기에 쓰이는`BaseResponseStatus` 는 `enum`을 통해 Status 값을 관리하고 있다.)  
성공했을 때와 실패했을 때의 Response 값을 각각 메소드로 정의하여 내려주고 있다.


**3) `BaseException`**

`BaseException`을 통해 `Service`에서 `Controller`에 Exception을 던진다. 마찬가지로 Status 값은 `BaseResponseStatus` 의 `enum`을 통해 관리한다.

#### 2. Service  / `UserService.java`, `TestService.java`  / @Service

> 1) **비즈니스 로직**을 다루는 곳 (DB 접근[CRUD], DB에서 받아온 것 형식화)
>  + Request의 **논리적** **Validation** 처리 (DB를 거쳐야 검사할 수 있는)

`Service`는 CRUD API의 비즈니스 로직 처리를 다루는 곳이다.


#### 3. Repository / `UserRepository.java`, `MemoRepository.java`, `CommentRepository.java`
Spring Data JPA를 사용하여 DB에 작업을 내리는 메소드로 구성되어 있다.  
Spring Data JPA에 포함된 기본 메소드를 사용할 수 있으며, 쿼리메소드 기능을 통해 메소드를 추가할 수 있다.



## ✨Usage
### API 만들기 예제
로컬에서 DemoApplication을 실행시킨다. (로컬 서버 구동 시)

[DB 연결 없이 TEST]
1. src > test > TestController.java에 구성되어 있는 API를 테스트해보자.
2. 포스트맨을 통해 GET localhost:9000/test/log로 테스트가 잘 되는지 확인한다.

[DB 연결 이후 TEST]
1. resources > application.yml에서 본인의 DB 정보를 입력한다.
2. DB에 TEST를 위한 간단한 테이블을 하나 만든다.
3. UserController.java, UserService.java, UserDao.java를 구성하여 해당 테이블의 값들을 불러오는 로직을 만든다.
4. 포스트맨을 통해 본인이 만든 API 테스트가 잘 되는지 확인한다.

### nohup
SpringBoot 프로젝트를 모두 개발하면 gradle 명령어로 프로젝트를 실행파일로 빌드 할 수 있다.  
빌드한 파일을 실행할 서버로 옮겨서 프로세스로 실행함으로써 서버를 구동시킬 수 있다.  
이 때, 서버환경에서 SpringBoot를 백그라운드 프로세스로 실행할 때 nohup 명령어를 사용한다.

### Error
서버 Error를 마주했다면, 원인을 파악할 수 있는 다양한 방법들을 통해 문제 원인을 찾자.
- 컴파일 에러 확인
- log 폴더 확인
- 그 외 방법들

## ✨License
- 본 템플릿의 소유권은 소프트스퀘어드에 있습니다. 본 자료에 대한 상업적 이용 및 무단 복제, 배포 및 변경을 원칙적으로 금지하며 이를 위반할 때에는 형사처벌을 받을 수 있습니다.