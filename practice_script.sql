-- 외래키 제약조건 검사 끄기 (DROP 시 FK 문제 방지)
SET FOREIGN_KEY_CHECKS = 0;
-- 테이블 삭제 (존재하면 삭제)
DROP TABLE IF EXISTS resolved_incident;
DROP TABLE IF EXISTS incident_history;
DROP TABLE IF EXISTS damage_type;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS member_type;
DROP TABLE IF EXISTS resolution_type;
-- 외래키 제약조건 검사 켜기
SET FOREIGN_KEY_CHECKS = 1;

-- 1. 회원 유형 테이블 
CREATE TABLE member_type (
    member_type INT NOT NULL AUTO_INCREMENT COMMENT '회원 유형 코드',
    description VARCHAR(20) COMMENT '회원 유형 설명',
    PRIMARY KEY (member_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='회원 유형 테이블';

-- 2. 회원 테이블
CREATE TABLE member (
    member_id INT NOT NULL AUTO_INCREMENT COMMENT '회원번호',
    name VARCHAR(15) NOT NULL COMMENT '성명',
    phone_number VARCHAR(20) NOT NULL COMMENT '전화번호',
    member_type INT NOT NULL COMMENT '회원 유형 코드',
    PRIMARY KEY (member_id),
    CONSTRAINT fk_member_member_type FOREIGN KEY (member_type) REFERENCES member_type(member_type)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='회원 테이블';

-- 3. 피해유형 테이블
CREATE TABLE damage_type (
    damage_type_id INT NOT NULL AUTO_INCREMENT COMMENT '피해 유형번호',
    penalty VARCHAR(50) NOT NULL COMMENT '벌칙',
    score DECIMAL(1) NOT NULL COMMENT '점수',
    member_type INT NOT NULL COMMENT '회원 유형 코드',
    PRIMARY KEY (damage_type_id),
    CONSTRAINT fk_damage_type_member_type FOREIGN KEY (member_type) REFERENCES member_type(member_type)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='피해유형 테이블';

-- 4. 사고 내역 테이블
CREATE TABLE incident_history (
    incident_seq INT NOT NULL AUTO_INCREMENT COMMENT '사고번호',
    incident_timestamp DATE NOT NULL COMMENT '사고일시',
    perpetrator_id INT NOT NULL COMMENT '가해자 회원번호',
    victim_id INT NOT NULL COMMENT '피해자 회원번호',
    damage_score DECIMAL(1) NOT NULL COMMENT '피해점수',
    damage_type_id INT NOT NULL COMMENT '피해유형번호',
    PRIMARY KEY (incident_seq),
    CONSTRAINT fk_incident_perpetrator FOREIGN KEY (perpetrator_id) REFERENCES member(member_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_incident_victim FOREIGN KEY (victim_id) REFERENCES member(member_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_incident_damage_type FOREIGN KEY (damage_type_id) REFERENCES damage_type(damage_type_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사고 내역 테이블';

-- 5. 해소 유형 테이블
CREATE TABLE resolution_type (
    resolution_type_id INT NOT NULL AUTO_INCREMENT COMMENT '해소 유형번호',
    description VARCHAR(50) NOT NULL COMMENT '상세내역',
    PRIMARY KEY (resolution_type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='해소 유형 테이블';

-- 6. 해소 내역 테이블
CREATE TABLE resolved_incident (
    resolution_seq INT NOT NULL AUTO_INCREMENT COMMENT '해소번호',
    resolved_timestamp DATE NOT NULL COMMENT '해소일시',
    is_push_notified CHAR(1) NOT NULL COMMENT 'push알림여부',
    push_notification_timestamp DATE NOT NULL COMMENT 'push알림일시',
    resolution_type_id INT NOT NULL COMMENT '해소 유형번호',
    incident_seq INT NOT NULL COMMENT '사고번호',
    PRIMARY KEY (resolution_seq),
    CONSTRAINT fk_resolved_incident_resolution_type FOREIGN KEY (resolution_type_id) REFERENCES resolution_type(resolution_type_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_resolved_incident_incident FOREIGN KEY (incident_seq) REFERENCES incident_history(incident_seq)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='해소 내역 테이블';


INSERT INTO member_type (member_type, description) VALUES
('1', 'Teacher'),
('2', 'Student');


delete from member;


INSERT INTO member (member_id, name, phone_number, member_type) VALUES
(1,  '남효정', '010XXXX4124', 1),
(6,  '이경철', '010XXXX5915', 2),
(7,  '이나연', '010XXXX1277', 2),
(8,  '이승민', '010XXXX2221', 2),
(9,  '장동건', '010XXXX0735', 2),
(10, '조민식', '010XXXX0638', 2);


INSERT INTO member (member_id, name, phone_number, member_type) VALUES
(1, '남효정', '01076514124', 1),
(2, '이경철', '01033555915', 2),
(3, '이나연', '01093521277', 2),
(4, '이승민', '01026252221', 2),
(5, '장동건', '01088540735', 2),
(6, '조민식', '01027050638', 2);



INSERT INTO damage_type (damage_type_id, penalty, score, member_type)
VALUES
  (1, '수업 중 잡담/방해', 1, 2),
  (2, '수업 중 휴대폰 사용', 2, 2),
  (3, '과제/시험 부정행위', 5, 2),
  (4, '지각/결석', 1, 2),
  (5, '수업 중 무단 이탈', 2, 2),
  (6, '강의실 내 음식물 섭취', 1, 2),
  (7, '강의 준비 부족', 2, 1),
  (8, '강의 중 부적절 발언', 3, 1),
  (9, '강의 계획서 미준수', 3, 1),
  (10, '과도한 숙제/과제 부과', 1, 1);



INSERT INTO resolution_type (resolution_type_id, description)
VALUES
  (1, '구두 경고'),
  (2, '서면 경고'),
  (3, '학부모 상담'),
  (4, '교수 면담'),
  (5, '반성문 제출'),
  (6, '봉사활동'),
  (7, '재시험'),
  (8, '강의 재정비 권고'),
  (9, '징계 위원회 회부'),
  (10, '강의 교체');








