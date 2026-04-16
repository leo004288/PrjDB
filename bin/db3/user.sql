/* 계정은 sky */

/* TMEMBER
회원관리
번호     숫자(6)   기본키   번호자동증가
이름     문자(30)  필수입력
아이디   문자(20)  필수입력 중복방지
암호     문자(20)  필수입력
이메일   문자(320) 중복방지
가입일   날짜      기본값   오늘
*/

/* TUSER
아이디   문자(20)  필수입력 중복방지
이름     문자(30)  필수입력
이메일   문자(320) 중복방지
*/

CREATE TABLE TUSER (
    USERID   VARCHAR2(20)  NOT NULL PRIMARY KEY,   
    USERNAME VARCHAR2(30)  NOT NULL,
    EMAIL    VARCHAR2(320) UNIQUE
);

INSERT INTO TUSER VALUES ('sky1', '스카이1', 'sky@green.com');
INSERT INTO TUSER VALUES ('sky2', '스카이2', 'sky2@green.com');
INSERT INTO TUSER VALUES ('sky3', '스카이3', 'sky3@green.com');
INSERT INTO TUSER VALUES ('sky4', '스카이4', 'sky4@green.com');
INSERT INTO TUSER VALUES ('sky5', '스카이5', 'sky4@green.com');

COMMIT;