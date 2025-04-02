■ 목차
1. 개요
2. 설명
3. API
4. ERD
5. SQL
6. 사용 방식

■ 개요
프로젝트 이름: 일정관리 앱 만들기 (JPA)
프로젝트 기간: 2025.03.28 - 2025.04.02
개발 언어: Spring Boot

■ 설명 : 효율적인 일정 관리를 위해 개인의 일정을 등록하고 관리할 수 있는 앱

■ API
1) 작성자
- 등록: POST (members/signup)
-> {
	  "name": "사용자1",
    "email" : "test1@test.com",
    "password" : "1234"
}

- 조회: GET (members/변수명/{값}?page=?&size=?)
-> x

- 수정: PATCH (/members/값)
-> {
	  "name": "수정된 사용자1",
    "email" : "modifyTest@test.com"
}

- 삭제: DELETE (/members/값)
-> {
    "id":1,
    "password" : "1234"
}

2) 일정
- 등록: POST (schedules/create)
-> {
    "memberId" : "1",
	  "title": "제목1",
    "contents" : "내용1"
}

- 조회: GET (schedules/변수명/{값}?page=?&size=?)
-> x

- 수정: PATCH(/schedules/update/{값})
-> {
    "title":"수정된 제목입니다.2",
    "contents" : "수정된 내용입니다.2"
}

- 삭제: DELETE (/schedules/delete/{값})
-> x


3) 댓글
- 등록: POST (comments/create)
-> {
    "memberId" : "1",
	"scheduleId": "1",
    "comments" : "댓글1"
}

- 조회: GET (comments/변수명/{값})
-> x

- 수정: PATCH(/comments/update/{값})
-> {
    "id" : "1",
    "comments" : "수정1"
}

- 삭제: DELETE (/comments/delete/{값})
-> x


■ ERD 
![image](https://github.com/user-attachments/assets/56b5220e-2871-4fc3-a597-c4eaca53de20)


■ SQL
-- 테이블 생성 member
CREATE TABLE member
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 식별자',
    name VARCHAR(100) COMMENT '사용자 이름',
    email VARCHAR(100) COMMENT '사용자 이메일',
    password VARCHAR(100) COMMENT '사용자 비밀번호', 
    create_date DATE COMMENT '작성일',
    update_date DATE COMMENT '수정일'
);

-- 테이블 생성 (schedule)
CREATE TABLE schedule
(	
	id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자', 
	title VARCHAR(500) NOT NULL COMMENT '제목',
	contents VARCHAR(500) NOT NULL COMMENT '내용',
	member_id BIGINT COMMENT '사용자 id',
	member_name VARCHAR(100) COMMENT '사용자 이름',
	create_date DATE COMMENT '작성일',
	update_date DATE COMMENT '수정일',
	FOREIGN KEY (member_id) REFERENCES member(id),
);

-- 테이블 생성 (comment)
CREATE TABLE comment
(
	id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 식별자', 
	contents VARCHAR(500) NOT NULL COMMENT '내용',
	member_id BIGINT COMMENT '사용자 id',
	schedule_id BIGINT COMMENT '일정 id',
	create_date DATE COMMENT '작성일',
	update_date DATE COMMENT '수정일',
	FOREIGN KEY (member_id) REFERENCES member(id),
	FOREIGN KEY (schedule_id) REFERENCES schedule(id),
);

■ 사용 방식
1) 프로그램 실행
2) 사용자 등록 (API 참조)
3) 사용자 조회 (API 참조)
4) 사용자 수정 (API 참조)
5) 사용자 삭제 (API 참조)
6) 일정 등록 (API 참조)
7) 일정 조회 (API 참조)
8) 일정 수정 (API 참조)
9) 일정 삭제 (API 참조)
10) 댓글 등록 (API 참조)
11) 댓글 조회 (API 참조)
12) 댓글 수정 (API 참조)
13) 댓글 삭제 (API 참조)
