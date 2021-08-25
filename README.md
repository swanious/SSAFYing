# SSAFYing - ssafy人들만의 커뮤니티 사이트


  * [📌프로젝트 소개](#프로젝트-소개)
  * [📌프로젝트 기여](#프로젝트-기여)
  * [📌역할](#역할)
  * [📌개발](#개발)
    + [슬로건](#슬로건)
    + [CSS](#css)
    + [로그인, 회원가입, Admin 페이지](#로그인-회원가입-admin-페이지)
    + [헤더](#헤더)
      - [중간 메뉴 탭](#중간-메뉴-탭)
      - [우측 알림 / 사용자 탭](#우측-알림--사용자-탭)
    + [메인 화면](#메인-화면)
    + [게시판, 정보](#게시판-정보)
    + [오픈 채팅방(모임 게시판)](#오픈-채팅방모임-게시판)
      - [채팅방 외부](#채팅방-외부)
      - [채팅방 내부](#채팅방-내부)
    + [알림](#알림)
  * [📌회고](#회고)
    + [아쉬운 점](#아쉬운점)
    + [만족스러운 점](#만족스러운-점)


## 📌프로젝트 소개

> 삼성 소프트웨어 아카데미(이하 SSAFY) 사람들을 위한 커뮤니티 웹사이트 제작 게시판 / 모임(채팅방) / 정보(IT, 취업정보) / 싸피장터(거래 게시판) 으로 구성



## 📌프로젝트 기여

> 기간 : 21.01.18 - 02.19 
>
> 평가 : 중간(01.19 -30), 최종(02.01 - 02.19) 
>
> 기여도 : 28%(128 / 453 commits)

기여도의 높고 낮음이 개발의 많은 부분, 혹은 적은 부분을 담당했다고 보기는 어렵다.

하지만, 그만큼 열정적으로 개발했고, 또 그만큼 배워가는 부분이 많았다.

프론트엔드를 담당했지만,

- discord/webex로 백엔드 팀원과 소통하면서 효율적인 ERD를 구축했다.
- mobaXterm을 활용하여 AWS EC2 클라우드 서버에 배포하는 경험을 했고,
- 소켓 통신 라이브러리를 활용하여 실시간 채팅을 구현하기도 했다. (backend는 채팅 서버와 기존의 서버를 분리했다.)

------



## 📌역할

- UX/UI 담당
- 페이지 레이아웃 구축
- vue-router(SPA 라우터 설정) / vuex(상태관리)
- restful API를 위한 axios라이브러리 활용 서버와 통신
- backend팀원과 협업, 소켓통신 활용 오픈 채팅 구현(web socket)

------



## 📌개발



### 슬로건

- 프로젝트 네임을 고민하다가 나온 슬로건 "싸핑에서 써핑하자"
- "싸피人들만을 위한 커뮤니티, 그 안에서 마음껏 surfing하라." 라는 의미

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F5f30426c-3aa9-4914-8f65-51533ed99640%2Fssafying_.png?table=block&id=da023d0b-d176-4ea6-9992-c0c56af0108c&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=670&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------



### CSS

- Vanila CSS를 통해 스타일을 적용했다. scss를 적용해보고 싶은 생각도 있었지만, 이번 프로젝트는 css에 대한 기본적인 이해를 높이고 싶어서 순수 css를 통해 스타일을 적용했다.
- vue.js는 컴포넌트들이 css 속성을 공유한다. 이를 모르고 작업하다가 css속성이 제대로 적용되지 않음을 알게됐고, 결국 컴포넌트별 명확한 클래스 네이밍이 절실히 필요했다. 우리는 BEM css방법론을 적용하여 가시성을 높여 개발을 진행했다.
- css 코드를 하나씩 짜다보니 문제점이 보이기 시작했다. 아래와 같이 클래스가 반복되는경우가 굉장히 많았는데, 오히려 이것이 가시성도 낮추고, 피로감을 높였다. scss코드로 변환해보니 훨씬 깔끔하고 보기도 쉽다.

```css
/* css */
.header__contents {
  margin: 0 auto;
  position: relative;
  display: flex;
  justify-content: center;
}

.header__contents > ul {
  margin: 0;
  padding: 0;
  width: 60%;
  height: 100%;
  display: flex;
  align-items: center;
}

.header__contents > ul > li {
  width: 100%;
  height: 100%;
}

.header__contents > ul > li > a {
  display: flex;
  position: relative;
  width: 100%; height: 100%;
  justify-content: center;
  align-items: center;
}

/* scss */
.header__contents {
	margin: 0 auto;
	position: relative;
	display: flex;
	justify-content: center;
	>ul {
		margin: 0;
		padding: 0;
		width: 60%;
		height: 100%;
		display: flex;
		align-items: center;
		>li {
			width: 100%;
			height: 100%;
			>a {
				display: flex;
				position: relative;
				width: 100%;
				height: 100%;
				justify-content: center;
				align-items: center;
			}
		}
	}
}
```

- 색 조합은 **adobe color**를 참고했다. `SSAFYing에서 Surfing하자` 라는 슬로건에 어울리는 색이 뭘까? 고민하다가 시원한 색 + 파스텔의 조합이 좋겠다는 의견을 공유하고, 아래와 같은 색 조합을 root로 설정하여 SSAFYing의 대표 색으로 선정했다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F5b751fb8-4ea9-4f06-b772-c6123eab304d%2FUntitled.png?table=block&id=8f8b4cf8-28cb-4a53-aaf1-64828130da80&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3840&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc220a709-4542-48b1-b875-a75b330f06f2%2FUntitled.png?table=block&id=01d030f0-aaf6-4f37-b84f-0d45d5783d72&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=2170&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------



### 로그인, 회원가입, Admin 페이지

- 로그인 페이지 구성

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F10950d94-c573-4523-8682-915d09f64c55%2FUntitled.png?table=block&id=e24c5c75-77cb-4f94-884c-7e49dd0d52ad&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3780&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)



- 회원가입 페이지 구성
  - 싸피人들만의 커뮤니티 사이트이므로 학생증 사진을 업로드하면 관리자가 승인하는 방식으로 진행했다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1f7229d8-b1a8-4382-8a36-f2471ecab4f1%2FUntitled.png?table=block&id=4fba6bcd-0342-4a4e-bcbe-6df27c35c524&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3840&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)



- 관리자 페이지는 대기유저(인증 전 유저)에 대한 승인/거부를 할 수 있는 페이지이다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F6399648d-7202-4d52-b4e9-0dafccc8ad21%2FUntitled.png?table=block&id=f88f3712-ab30-4dd1-b954-efaf5db12234&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=1770&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------

### 헤더

> 헤더는 `로고 / 메뉴 / 알림, 유저 정보 탭` 으로 나누어진다. 모바일 화면 지원을 위해 `576px이하`에서는 pulldown-menu를 적용하여 사용자 편의성을 높였다.

#### 중간 메뉴 탭

![image](https://user-images.githubusercontent.com/52116767/122575686-f686e800-d08b-11eb-9a71-4eccfa917570.png)

- 각각 `웹 화면`, `576px ≤ x ≤768px`, `576px 이하`
- 576px이하 모바일 화면에서는 풀다운 메뉴를 애니메이션으로 구현했다.



#### 우측 알림 / 사용자 탭

- **알림탭** : 채팅방 방장 → 친구에게 초대 알림

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F5643b614-c3b3-47d5-94ef-d7b7e2ac43b2%2FUntitled.png?table=block&id=bb23c1a5-7ac6-434c-9440-a99788b4ec66&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=970&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- **요청탭**: 사용자 → 채팅방 방장에게 가입요청

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1175d1f4-4906-4ed2-863d-a537cb4d7057%2FUntitled.png?table=block&id=d4a4d437-db31-408b-8d28-445f7402b254&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=990&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 프로필 이미지 클릭 시, 프로필 페이지로 이동
- 로그아웃 시 access-token삭제(로그아웃)

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F825e2889-732c-4bbf-b73c-a2c6614ef832%2FUntitled.png?table=block&id=053bf509-e166-46f1-add3-b5adee10698a&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=860&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------

### 메인 화면

> 레이아웃, 화면 배치(메인, 사이드바)를 개발

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F9c4a6fd7-0d72-4352-8f99-a659be536461%2FUntitled.png?table=block&id=e391875a-bf8e-4558-834f-8a458ad86592&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3840&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------

### 게시판, 정보

> 레이아웃, 화면 배치 서버와 axios 통신 `게시물, 댓글` CRUD 구현

- **게시판** - 자유, 익명, 기수별 게시판

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F457ff8e0-b4f9-425e-8c3b-7f78c04875ad%2FUntitled.png?table=block&id=256c8646-632a-4dbd-a7a3-6b0319269f4d&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3840&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- **정보** - IT, 취업, 생활 정보 게시판

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fcb7fb7f8-c107-4e55-adb6-ff38db625848%2FUntitled.png?table=block&id=8a7f6aaf-13a9-44d0-8bb9-a9788c9da15e&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=3840&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 웹 기반이었지만, 모바일에서 `게시물 생성` 버튼이 짤려 보이지 않았다.
- media쿼리를 활용하여 모바일 환경에서 우측하단의 버튼을 클릭하여 쉽게 게시물을 생성할 수 있도록 변경했다.

![img](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b6d7ebe6-aec1-4454-81ed-5584ee80ad7a/article-animation-1.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAT73L2G45O3KS52Y5%2F20210609%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20210609T043705Z&X-Amz-Expires=86400&X-Amz-Signature=23ad9ec84500a0df57cbb1c6e232b6d32a9fd555308e627e4b9d39049625b510&X-Amz-SignedHeaders=host)

------

### 오픈 채팅방(모임 게시판)

- 자유/익명/스터디 채팅방으로 분류
- 소켓 통신을 활용한 실시간 채팅 서비스
- 자유/스터디 채팅방 - 본인 닉네임으로 입장/ 방장인 경우 친구 초대 기능(알림 기능)
- 익명 - 새로운 닉네임 설정
- 무한 스크롤 구현

#### 채팅방 외부

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffd09c58e-f211-4d0b-a581-144fcd838780%2FUntitled.png?table=block&id=e5d8b036-9fcf-428a-8a31-fd6551a155b9&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=2500&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 비밀방 체크박스를 클릭 후 생성하면 가입이 승인된 유저만 입장이 가능하다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa0510730-30f3-420b-901d-bbc394ce0f9d%2FUntitled.png?table=block&id=83d86220-f062-4306-8f30-8413ea42a758&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=1300&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 공개 채팅방에서는 모든 유저가 자유롭게 입장할 수 있다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F9eb0f3ea-2066-4719-8be3-6eb2d8a4d7fd%2FUntitled.png?table=block&id=11c90e3e-02cb-4f3d-ab84-a75b4f307b48&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=1250&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

------

#### 채팅방 내부

- 비밀방(잠금)은 내 친구를 초대할 수 있으며, 초대받은 사람만 들어올 수 있다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa48f3f04-10cf-4d59-be64-dd8e1a361275%2FUntitled.png?table=block&id=3f69c251-41b5-462c-b104-0d86917d5fbe&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=2580&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 익명 채팅방은 닉네임을 설정할 수 있고, 누구나 편하게 대화할 수 있다.

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffdcc63d9-a3e0-404c-8c57-a30016a34f31%2FUntitled.png?table=block&id=4caa23f4-0a2b-4672-b50c-f5a3ea8179ee&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=2530&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 스크롤이 맨 위에 닿을 때 백엔드에 데이터를 요청하여 이전의 채팅 기록을 불러온다.
- 스크롤의 위치는 유지되도록 `scrollTop` 속성을 활용하여 유지시켰다. 

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F452a7b00-bb61-427b-9f7d-8540400a385d%2FUntitled.png?table=block&id=1d75b9b7-eb9a-4f69-bd44-61506e27cfc5&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=420&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)



------

### 알림

> `유저`가 채팅방에 가입요청 → **요청**탭으로 알림 
>
> `방장`이 친구에게 초대 요청 → **알림**탭으로 알림

- 채팅방 가입요청

![image-20210609135533282](README.assets/image-20210609135533282.png)

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F38753d92-8e3f-4dec-b8da-f1eaf462babf%2FUntitled.png?table=block&id=b24196f5-74c2-41df-a334-fbe08471ae82&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=910&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 채팅방 내부에서 초대 요청

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F4444c92b-0a5a-4aa1-94f1-b9ed3d64b3b1%2FUntitled.png?table=block&id=4874c9ba-95dd-437d-9a99-65ad26335828&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=700&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

![img](https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8300bee4-2fff-474a-8c84-84fa39800312%2FUntitled.png?table=block&id=68734606-ba2d-4071-b6da-a39b49a7897f&spaceId=21f8304b-a655-4262-b272-7225e873f61f&width=850&userId=03f86028-b32c-4b4f-8b72-f34f5cfbc0ec&cache=v2)

- 알람에도 Stomp를 적용하여 실시간으로 알람을 받을 수 있다.
- 비밀 채팅방은 방장의 승인이 된 유저들만 들어올 수 있는데, 
  - 가입 신청 버튼을 통해 방장에게 요청이 `알림탭에서 승인`하거나 
  - 방장이 채팅방 내부에서 초대를 하고 해당 유저가 승인하면 입장할 수 있다.

------

## 📌회고

### 아쉬운 점

사실 vue.js보다 react.js를 사용해보고 싶은 마음이 컸다. 시장에서 react.js의 수요가 더 크기도하고, 새로운 프레임워크를 배우고 또 사용하면서 하나의 기술스택을 늘릴 수 있겠다는 욕심이 있었다. 하지만 팀원과 상의 끝에 vue.js를 사용하기로 했다. 그 이유는,

1. **시간** : 설계부터 개발까지 모든 것을 수행해야했기에 새로운 프레임워크를 배우면서 적용하기에는 시간적으로 한계가 있었다.
2. **익숙함** : vue.js에 대한 지식이 어느정도 있었기 때문에.. 익숙함을 버리고 모험을 감행할만한 여유가 없었다. 시간적 한계와 같은 맥락이라고 볼 수 있겠다.

물론 아쉬움도 있었다. 하지만, 프로젝트를 진행하면서 오히려 vue.js에 대해서도 제대로 모르고 있다는 것을 깨달았다. life cycle hook, axios 등등

이는 프로젝트를 진행하면서 vue.js 공식문서와 팀원과 코드 리뷰를 통해 하나씩 해결해나갈 수 있었다.

### 만족스러운 점

프론트엔드를 담당하여 개발했지만, 오히려 이외에 많은 것을 배울 수 있었다. 백엔드 팀원과 친하기도했고, 때로는 같이 만나서 개발을 하면서

- ERD설계
- spring boot(MVC 패턴)에 대한 구조 이해
- AWS EC2 클라우드 서버 사용
- MobaXterm의 우분투 커널에서의 배포 등..

서당개 3년이면 풍월을 읊는다는 말처럼 옆에서 보고 배운 지식들이 앞으로 개발하는데에 굉장히 많은 도움이 되리라 생각한다.
