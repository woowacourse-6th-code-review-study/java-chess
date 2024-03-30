# 체스 게임

## 요구사항 정리

### 체스 판

1. 체스 판의 크기는 8 * 8 의 정사각형이다.
    - 체스 판의 가로 위치는 왼쪽 부터 a ~ h 로 표현한다.
    - 체스 판의 세로 위치는 아래 부터 1 ~ 8 로 표현한다.

### 체스 말

1. 체스 말에는 폰, 나이트, 비숍, 룩, 퀸, 킹 총 6개의 종류가 있다.
2. 체스 말마다 이동 규칙이 다르다.
    - 폰
        - 한 번도 이동한 적 없는 경우 상대 진영 방향으로 한 칸 혹은 두 칸 직진할 수 있다.
        - 이동한 적 있는 경우 상대 진영 방향으로 한 칸 직진할 수 있다.
        - 상대 진영 방향 대각선 한칸 위치에 상대 진영의 말이 있는 경우, 그 위치로 이동할 수 있다.
    - 나이트
        - 자신의 위치에서 가로 ± 2, 세로 ± 1 혹은 가로 ± 1, 세로 ± 2 한 위치로 이동할 수 있다.
    - 비숍
        - 대각선 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 룩
        - 가로 혹은 세로 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 퀸
        - 가로 혹은 세로 혹은 대각선 방향으로 몇 칸이든 움직일 수 있다.
        - 이동하고자 하는 위치와, 현재 위치 사이에 다른 말이 있는 경우 그 위치로 이동할 수 없다.
    - 킹
        - 가로 혹은 세로 혹은 대각선 방향으로 한 칸 움직일 수 있다.
3. 맵 밖으로는 움직일 수 없다.
4. 체스 말은 소속된 진영이 하나 있다.
    - 한번 체스 말의 진영이 정해지면 변경되지 않는다.
5. 체스 말이 이동할 수 있는 위치에 상대 진영의 말이 있는 경우, 그 말은 체스 게임에서 제거된다.
    - 단, 폰은 이동할 수 있는 위치에 상대 진영의 말이 있는 경우, 그 위치로 이동할 수 없다.
6. 체스 말이 이동할 수 있는 위치에 같은 진영의 말이 있는 경우, 그 위치로 이동할 수 없다.

### 게임 종료 조건

1. 킹이 잡히는 팀이 게임에서 진다. 킹이 잡히 게임이 종료된다.

### 점수

1. 각 팀에는 점수가 있다. 팀의 점수는 살아있는 체스 말의 점수의 합이다.
2. 각 말의 점수는 다음과 같다.
    - 퀸 : 9점
    - 룩 : 5점
    - 비숍 : 3점
    - 나이트 : 2.5점
    - 폰 : 1점
        - 같은 세로 위치에 같은 색의 폰이 여러개 있는 경우, 그 폰들은 점수가 0.5점이다.

## 기능 목록

- [x] 체스 말
    - [x] 체스 말 이동
        - [x] 이동 위치에 아무 말도 없는 경우 이동할 수 있다
        - [x] 이동 위치에 같은 진영 말이 있는 경우 이동할 수 없다
        - [x] 이동 경로에 다른 말이 있는 경우 이동할 수 없다
        - [x] 이동 위치에 상대 진영 말이 있는 경우 이동할 수 있다
        - [x] 특별한 이동 방법이 있는 경우 (폰)
- [x] 체스 보드
    - [x] 올바른 턴인지 확인한다
    - [x] 잡힌 말을 제거한다
- [x] 종료 조건
    - [x] 킹이 잡히면 게임이 종료되는 기능
- [x] 점수
    - [x] 각 말의 점수 계산 기능
    - [x] 폰의 점수 보정 기능
    - [x] 각 팀의 점수 계산 기능
- [x] 사용자 입력 기능
    - [x] 이동 위치 명령어 입력 기능
    - [x] 게임 시작 명령어 입력 기능
    - [x] 게임 종료 명령어 입력 기능
    - [x] 점수 조회 명령어 입력 기능
- [x] 출력 기능
    - [x] 체스 판 출력 기능
    - [x] 안내 문구 출력 기능
    - [x] 점수 출력 기능
- [x] 데이터 베이스에 저장하는 기능
    - [x] 기물을 저장하는 기능
    - [x] 턴을 저장하는 기능
- [x] 데이터베이스에 저장된 게임을 불러오는 기능

## 데이터베이스

### 저장해야 할 데이터

1. 각 기물의 위치
2. 현재 기물을 움직일 수 있는 팀

### 테이블 스키마

``` SQL
create table piece (
    piece_type varchar(16),
    primary key(piece_type)
);

create table position (
	`name` char(2),
	primary key(`name`)
);

create table team (
    team_name varchar(16),
    primary key(team_name)
);

CREATE TABLE `game` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `current_team_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fj_team_game` (`current_team_name`),
  CONSTRAINT `fj_team_game` FOREIGN KEY (`current_team_name`) REFERENCES `team` (`team_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `pieces_on_board` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `piece_type` varchar(16) NOT NULL,
  `team_name` varchar(16) NOT NULL,
  `position_name` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk-piece-pieces_on_board` (`piece_type`),
  KEY `fk-position-pieces_on_board` (`position_name`),
  KEY `fk-team-pieces_on_board` (`team_name`),
  CONSTRAINT `fk-piece-pieces_on_board` FOREIGN KEY (`piece_type`) REFERENCES `piece` (`piece_type`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-position-pieces_on_board` FOREIGN KEY (`position_name`) REFERENCES `position` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk-team-pieces_on_board` FOREIGN KEY (`team_name`) REFERENCES `team` (`team_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

### 데이터 저장시 고려해야 하는 것

1. 데이터가 저장된 적이 없는 경우 혹은 복구를 하지 않겠다고 하는 경우 => 데이터를 모두 지우고, 초기 체스 판을 저장한다. 최초 턴을 white로 지정한다.
2. 말을 움직인 경우, 시작 위치의 말을 지우고, 도착위치의 말을 지운 뒤, 도착 위치로 이동한 기물을 새로 저장한다. 그리고 턴을 바꿔준다.

## 피드백 반영 예정 목록

### DB 관련

- [x] 트랜잭션 적용
- [ ] TurnDAO에서 비즈니스 로직 상위로 이동
- [ ] DAO에 boolean을 반환하는 메서드 추가
- [ ] 구체적인 예외를 던지도록 수정
- [ ] DAO 테스트 추가
- [ ] 킹이 잡힌 경우 데이터를 초기화하도록 수정
- [ ] 폰이 데이터베이스에서 로드된 뒤에 폰이 2칸 움직일 수 있는 버그 수정

### 도메인 로직 관련

- [x] 킹이 잡히는 경우 실제로 킹이 보드에서 삭제되지 않는 부분 수정

