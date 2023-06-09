# Just a Moment (세계여행 플래너)

---

## 국내/해외 여행을 계획하는 사람들을 위한 경로 추천 여행 플래너 서비스

### 팀 구성

- 최범선(팀장)
- 이형석(팀원)

### 프로젝트 배경

<img width="356" alt="스크린샷 2023-06-13 오후 4 29 18" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/a20c6462-9192-47ba-adeb-77b1f2ac0c22">

<img width="313" alt="2" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/f9644b3a-fd7e-4927-aa42-2a5c29e0cbeb">

### 프로젝트 구조

- trip : Back-end
- plan : Front-end

### 개발환경

| 언어 | Java, HTML/CSS, Javascript |
| --- | --- |
| 프레임워크 | Spring Boot, Vue.js, MyBatis |
| DB | MySQL |
| API | Restful API, Google API, Amadeus API, Kakao Ouath 2.0 |
| IDE & Tool | IntelliJ IDEA, Visual Studio Code, MySQL Workbench |

### 역할

| 이형석 - 팀원(백엔드 및 프론트엔드) | DB 설계 및 구축, DTO 설계 및 수정, Kakao API를 이용한 소셜 로그인 기능, 파일 업로드 및 파일 저장 기능, 화면 디자인 , 마이페이지 기능, 후기 작성 기능, 팔로우, 팔로워 기능 |
| --- | --- |
| 최범선 - 팀장(백엔드 및 프론트엔드) | DB 설계 및 구축, DTO 설계 및 수정, Google API를 이용한 지도, 위치 데이터 처리, Amadeus API를 이용한 항공권 데이터 처리, 순열 알고리즘을 이용 한 경로 추천 기능, Axios를 이용한 비동기 통신, 여행 계획 작성 기능 |

### 시연영상

- [https://youtu.be/kwwB9A2RGJU](https://youtu.be/kwwB9A2RGJU)

### 요구사항 정의서

## 1) 회원관리

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 회원가입 | 회원가입에 필요한 정보를 입력한 후, 회원가입한다. | 아이디, 비밀번호, 이름, 이메일 |  |
|  | 로그인 | 로그인에 필요한 정보를 입력한 뒤, 로그인을 진행한다. | 아이디, 비밀번호 |  |
|  | 회원 정보 조회 | 로그인한 유저가 자신의 회원 정보를 조회한다. | 아이디 |  |
|  | 회원 정보 수정 | 로그인한 유저가 자신의 회원 정보를 수정한다. | 수정 비밀번호, 수정 이름, 수정 이메일 |  |
|  | 회원 탈퇴 | 로그인한 유저가 회원탈퇴를 진행한다. | 아이디 |  |
|  | 아이디 찾기 | 아이디를 찾기 위해 회원 가입할 떄 입력한 이메일을 입력한다. | 이메일 |  |
|  | 비밀번호 찾기 | 비밀번호를 찾기 위해 회원 가입할 때 입력한 아이디와 이메일을 입력한다. | 아이디 |  |
|  | 소셜 로그인 |  |  |  |
|  | 팔로우하기 | 팔로우를 원하는 회원을 팔로잉한다. | 자신의 아이디, 팔로잉 회원의 아이디 |  |
|  | 마이 페이지 조회 - 팔로워 회원 조회 | 마이 페이지에 사용할 자신의 팔로워 회원을 조회한다. | 자신의 아이디, 팔로워 회원 아이디 |  |
|  | 마이 페이지 조회 - 팔로잉 회원 조회 | 마이 페이지에 사용할 자신의 팔로잉 회원을 조회한다. | 자신의 아이디, 팔로잉 회원 아이디 |  |
|  | 마이 페이지 조회 - 내 글만 조회 (여행 후기, 계획 게시판) | 마이 페이지에 사용할 자신이 작성한 글만 가져온다. | 자신의 아이디 |  |
|  | 마이 페이지 조회 - 팔로잉 글 조회 | 자신이 팔로잉한 회원의 글을 조회한다. | 자신의 아이디, 팔로잉 회원 아이디 |  |

## 2) 여행 계획 게시판

- 여행 계획 생성
    
    
    | 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
    | --- | --- | --- | --- | --- |
    |  | 항공권 조회 | 사용자가 여행 계획을 세우기 위해 장소와 시간 정보를 통해 해당하는 항공권 정보를 보여준다.(항공사 정보, 비행 시간, 가격, 예매 가능 url 링크) (시간 , 가격 순으로 정렬) | 출발지 데이터, 도착지 데이터, 출발 날짜, 도착 날짜 | 출발 시간 |
    |  | 키워드 기반 검색 | 사용자가 입력하는 키워드를 기반으로 그에 해당하는 정보들을 보여준다. (선택 데이터들을 통해, 사용자는 원하는 데이터를 필터링할 수 있다.) | 키워드 정보 | 음식점, 관광지, 편의 시설 |
    |  | 숙박정보 조회 | 사용자가 설정한 위치(없다면 현재 위치)를 통해, 주변의 숙박 시설 정보를 보여준다.(설정한 위치로 부터 떨어진 거리, 원하는 금액 정보를 통해 데이터를 필터링 할 수 있다.) | 위치 정보 데이터 (기본 : 현재 위치) | 금액 필터링, 위치 필터링 |
    |  | 지도 기능 | 사용자의 현재 위치 데이터를 통해 지도 화면을 보여준다(Google API사용), 키워드로 검색된 결과를 통해 지도 화면을 움직여 준다. 가장 처음 나온 데이터의 위치를 기준으로 보여주고, 사용자가 리스트의 정보를 클릭할 경우 그에 맞는 위치로 이동시켜준다. | 사용자의 현재 위치 데이터 | 키워드 정보 |
    |  | 여행 계획 생성 | 사용자가 조회된 정보들을 바탕으로 계획 내용과 제목을 작성할 수 있다. 조회된 데이터들을 계획에 포함시킬 수 있고, 데이터들 안에 코멘트를 남길 수 있다. | 사용자 정보, 계획 제목, 계획 내용, 여행지 정보, 항공권 정보, 숙박시설 정보, 날짜 정보 |
    |  | 게시글 좋아요 | 계획 게시판 글에 ‘좋아요’ 를 클릭한 경우, 해당 유저 테이블에 후기 글 번호를 추가한다. | 후기 글 번호 |  |
    |  | 게시글 스크랩 | 다른 사용자의 게시글을 스크랩 해서 자신의 여행 계획에 추가할 수 있다. | 사용자 정보, 여행 계획 정보
- 여행 계획 조회
    
    
    | 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
    | --- | --- | --- | --- | --- |
    |  | 내 여행 계획 조회 | 자신이 작성한 여행 계획들을 한 곳에서 확인할 수 있다. (작성한 시간 순으로 정렬해서 화면에 보여준다), 글에 제목, 등록 시간, 사진을 함께 보여준다. | 사용자 정보 |  |
    |  | 다른 사용자 여행 계획 조회 | 다른 사용자가 작성한 여행 계획들을 조회할 수 있는 기능 (글에 제목, 등록 시간, 사진을 함께 보여준다.) |  |  |
    |  | 여행 계획 세부 조회 | 사용자/다른 사용자가 작성한 여행 계획에 대해 세부 내용을 조회한다. 사용자가 작성한 제목, 내용, 여행지, 항공권, 숙박시설, 날짜 정보를 보여준다. | 사용자 정보, 여행 계획 정보 |  |
    
- 여행 계획 수정
    
    
    | 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
    | --- | --- | --- | --- | --- |
    |  | 여행 계획 수정 | 자신이 작성한 여행 계획들 중 수정할 부분이 있는 계획들을 선택해 수정할 수 있는 기능 | 사용자 정보, 여행 계획 정보 | 수정할 정보 |

- 여행 계획 삭제
    
    
    | 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
    | --- | --- | --- | --- | --- |
    |  | 여행 계획 삭제 | 자신이 작성한 여행 계획들 중 삭제하고 싶은 계획을 삭제할 수 있는 기능 | 사용자 정보, 여행 계획 정보 |  |

- 여행 계획 댓글
    
    
    | 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
    | --- | --- | --- | --- | --- |
    |  | 댓글 작성 | 모든 게시글에 댓글을 작성한다. | 댓글 작성자 정보, 게시글 작성자 정보| 댓글 내용 |
    |  | 댓글 조회 | 세부내용 조회 게시글에 작성된 모든 댓글을 조회한다. | 게시글 번호 |  |
    |  | 댓글 수정 | 자신이 작성한 댓글을 수정한다. | 게시글 번호, 댓글 번호, 수정 내용 |  |
    |  | 댓글 삭제 | 자신이 작성한 댓글을 삭제한다. | 게시글 번호, 댓글 번호 |  |
    |  | 댓글 좋아요 | 다른 회원이 작성한 댓글에 좋아요를 클릭한다. | 게시글 번호, 댓글 번호 |  |

## 3) 항공권

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 항공 정보 조회 기능 | 사용자가 설정한 날짜, 위치 정보를 기준으로 항공 데이터를 보여주는 기능(항공기 정보, 출발 시간, 걸리는 시간, 가격 정보, 예매 가능 url 정보), 시간 , 가격 순으로 정렬해서 조회 | 출발지 데이터,도착지 데이터,출발 날짜,도착 날짜, | 출발 시간 |

## 4) 숙박

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 숙박 정보 조회 | 사용자가 설정한 위치를 기반으로 거리순, 가격순으로 정보를 조회하는 기능 (거리와, 금액으로 정보들을 필터링 할 수 있다.) | 위치 데이터 | 거리 정보,금액 정보 |

## 5) 여행 후기 게시판

- 여행 후기 생성

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 글 작성 | 로그인된 경우, 후기 게시판 글을 등록할 수 있게 한다. | 제목, 내용, 작성자, 작성 일자, 로그인 여부 | 계획 글 번호 |
|  | 계획 게시판 불러오기 | 작성한 후기와 관련된 자신이 작성한 계획 게시판 글이 있는 경우 해당 계획 게시판 글의 북마크를 가져온다. | 계획 글 번호, 작성자 확인 |  |
|  | 사진 업로드 | 유저가 여행지에 대한 사진을 추가한다. | 계획 글 번호, 사진 정보 |  |
- 여행 후기 조회

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 글 전체 조회 | 후기 게시판 글을 모두 조회한다. |  |  |
|  | 글 세부내용 조회 | 후기 게시판 글 하나의 내용을 상세히 조회힌다. | 후기 글 번호 |  |
|  | 내 글만 조회 | 작성자 아이디와 유저 아이디가 일치한 후기 게시판 글을 모두 조회한다. | 유저 아이디 |  |
|  | 팔로워 글 조회 | 로그인한 유저가 팔로우한 유저의 후기 게시판 글을 모두 조회한다. | 유저 아이디, 팔로워 아이디 |  |
|  | 좋아요 많은 순으로 글 조회 | 후기 게시판 글들을 ‘좋아요 수’ 오름차순으로 정렬한 뒤, 조회한다. | 좋아요 수 |  |
|  | 게시글 좋아요 | 후기 게시판 글에 ‘좋아요’ 를 클릭한 경우, 해당 유저 테이블에 후기 글 번호를 추가한다. | 유저 아이디, 후기 글 번호 |  |
- 여행 후기 수정

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 글 수정 | 해당 글 작성자인 것이 확인된 경우, 후기 게시판 글을 수정할 수 있게 한다. | 수정 제목, 수정 내용, 작성자 확인 |  |
- 여행 후기 삭제

| 기능 ID | 기능명 | 상세 설명 | 필수 데이터 | 선택 데이터 |
| --- | --- | --- | --- | --- |
|  | 글 삭제 | 해당 글 작성자인 것이 확인된 경우, 후기 게시판 글을 삭제할 수 있게 한다. | 후기 글 번호, 작성자 확인 |  |

# ERD

![3](https://github.com/gudtjr2949/WorldTrip/assets/108784138/d5d52730-6daa-47d9-8946-7efa36ae25f8)

# 주요 상세 페이지

---

### 로그인 및 회원가입 화면 - 자체 로그인 및 소셜 로그인 기능

<img width="1440" alt="4" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/eb274b94-25a9-4675-96b8-a6528cd1311d">

### 여행 계획 화면 - 정보를 조회하며 계획을 작성

<img width="1440" alt="5" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/e65b7027-3399-4223-acd1-99d9513476d1">

### 항공권 조회 화면 - 항공권 정보를 조회

<img width="1440" alt="6" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/e58dc4ed-4c95-4901-a2cd-8cf950840eba">

### 추천 경로 조회 화면 - 장소들 정보를 통해 경로 추천

<img width="1440" alt="7" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/c71bdbe1-89fe-493f-ba77-b4f6bd536325">

### 계획 게시판 화면 - 전체/팔로우/자신 별로 계획 글 조회

<img width="1440" alt="8" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/032f0e7c-e2da-491c-87f5-e706c8aa4d7b">

### 계획 글 - 상세 화면

<img width="1440" alt="9" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/79f3c2f6-50a3-4050-b74f-802754985273">

### 후기 글 작성 화면 - 자신의 계획에 대한 후기 작성

<img width="1440" alt="10" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/9ba1d287-e499-4a2d-a0ff-19cb9c41203b">

### 후기 글 - 상세 화면

<img width="1440" alt="11" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/94402ad6-2470-49d9-b055-01fb52eefc07">

### 마이페이지 화면 - 팔로워/팔로잉 사용자들 조회

<img width="1440" alt="12" src="https://github.com/gudtjr2949/WorldTrip/assets/108784138/afa2ab5c-edaf-48da-b0ca-987ce798cd5a">
