


# My-Journy-Life


## 요구사항 분석

테마 - 나눠야 할 큰 주제

 - 사용자
 - 마이페이지
 - 리뷰 검색
 - 통합 리뷰
 - 여행 일기
 - 여행지 리뷰(여행지, 시설, 맛집)
 - 교통편 리뷰(항공, 철도, 버스, 자동차)

사용자 분류

 - 게스트
 - 회원
 - 관리자(필요시)

권한
 - 게스트 : 게시글 열람만 가능
 - 회원 : 모든 기능 사용 가능
 
 
# 사용자 스토리(주제 기반 분류)

## 사용자

 **회원가입** : 게스트는 여러 기능 이용을 위해 소셜 아이디(구글, 네이버, 카카오) 혹은 이메일, 비밀번호, 별명, 이름, 핸드폰번호을 통해 가입할 수 있다.
 
	 제약 사항
	  - 이메일은 이메일 형식으로 이루어져야 한다.
	  - 비밀번호는 영문, 특수문자, 숫자로만 이루어져야 하며 이중 두가지 이상 포함되어야 하고 6자에서 15자 사이여야 한다.
	  - 별명은 한글과 영문으로 이루어져야하고 한글은 최대 8자, 영문은 최대 16자이하여야 한다. 별명을 설정하지 않을 경우 익명으로 설정된다.
	  
	  이름과 핸드폰번호로 실명인증을 수행하여 1인당 하나의 아이디를 가지는 이유는 리뷰의 투명성(바이럴, 댓글조작 등)을 지키기 위해서이다.
	    
 **로그인** : 게스트는 회원 전용 기능 이용을 위해 소셜아이디(구글, 네이버, 카카오) 또는 이메일과 비밀번호를 통해 로그인 할 수 있다. 

**개인 메신저** : 회원은 다른 사용자에게 개인적으로 메세지를 보낼 수 있다.

**알림 확인** : 회원은 자신의 리뷰의 댓글이 달리거나 댓글에 답글이 달리는 등 이벤트가 발생할 경우 알림이 가고 알림을 확인할 수 있다.

**메세지 함** :  회원은 타인이 보낸 개인 메세지를 확인할 수 있다.

## 마이페이지

  **회원정보관리** :  회원은 자신의 정보를 관리할 수 있다.
 
 - 별명 변경 : 회원은 자신의 별명을 언제든 변경할 수 있다.
 - 회원 탈퇴 : 회원은 언제든지 회원을 탈퇴할 수 있으며 재가입에는 1주일의 시간이 필요하다.

**스크랩한 리뷰 확인** : 회원은 자신이 스크랩한 리뷰를 확인할 수 있다.

## 리뷰 검색

 **일반 검색** : 사용자는 리뷰를 찾기 위해 단어 혹은 문장으로 리뷰를 검색할 수 있다.

 **상세 검색** : 사용자는 특정 종류의 리뷰를 찾기 위해 조건을 걸어 리뷰를 검색할 수 있다.


## 통합 리뷰
