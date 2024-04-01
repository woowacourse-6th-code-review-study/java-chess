# 체스 미션

## DB 연동

### Docker 실행하기

```bash
docker-compose -p chess up -d
```

### DB 스키마

```sql
use chess;

drop table if exists board;

create table board
(
    position   varchar(2)  not null,
    piece_type varchar(6) not null,
    camp       varchar(5)  not null

);

drop table if exists moving;

create table moving
(
  movement_id INT primary key auto_increment,
  camp        varchar(5)  not null,
  start       varchar(2) not null,
  destination varchar(2) not null
);

drop table if exists turn;

create table turn
(
  camp varchar(5) not null,
  count int not null
);
```


## 기능 요구 사항

* [x] 8 * 8 의 체스 판 생성
* [x] 체스 말 배치
* [x] 검은색 말은 대문자, 흰색 말은 소문자로 표현
* [x] 체스 게임 시작 메시지 출력
* [x] 게임 시작(start)/종료(end) 입력 받기
  * [x] start/end 가 아닐 시 예외 발생
  * [x] 예외시 재입력 받기
* [x] move 기능 구현
  * [x] 이동할 말의 현재 위치, 이동할 위치 입력 받기
  * [x] 말의 위치 이동
  * [x] 유효하지 않은 위치를 입력한 경우 예외 발생 (ex) z1
  * [x] 기물의 이동 규칙을 벗어나는 경우 예외 발생
  * [x] 현재 위치로 빈 칸을 입력받은 경우 예외 발생
  * [x] 현재 턴이 아닌 기물을 이동하는 경우 예외 발생
* [x] status 기능 구현
  * [x] 남아있는 기물의 점수 계산
    * queen: 9
    * rook: 5
    * bishop 3
    * knight: 2.5
    * pawn: 1
    * [x] 같은 세로줄에 있는 같은 pawn인 경우 0.5
  * [x] 진영의 점수를 출력
  * [x] 승리한 진영의 결과 확인
* [x] 킹이 잡히면 게임 종료
* [x] end 가 입력되면 프로그램 종료
* [x] 예외 시 재입력
* [x] 진행중인 게임 end 입력 후 재시작시 이전 게임 실행
* [x] quit 입력시 저장하지 않고 게임 종료
* [x] db 예외 처리

## 기물의 이동 기능

* [x] 현재 턴의 해당하는 진영만 이동 가능
  * 시작은 WHITE

<br>

* [x] Pawn
  * [x] 대각선에 상대방 기물이 있다면 이동 가능
* [x] Knight
* [x] King
* [x] Bishop
* [x] Queen
* [x] Rook

## 명령어

* start: 게임을 시작하는 명령어
  * 게임 시작은 start 명령어만 가능하다
* end: 게임을 저장 후 종료하는 명령어
* quit: 게임을 저장하지 않고 종료하는 명령어
* move: 게임 중 기물을 움직이는 명령어
  * 게임이 시작되지 않았는데 move 명령어를 입력한다면 예외가 발생한다.
  * 형식은 move [a-h1-8] [a-h1-8] 형태이고 아닐시 예외가 발생한다.
* status: 현재 점수를 확인하는 명령어

## 우선순위가 낮지만 구현해보고 싶은 기능

* 특수 행마법
  * [ ] 앙파상
  * [ ] 프로모션
  * [ ] 캐슬링
* 체크 상황
  * [ ] 체크 상황이면 체크를 피하는 방향으로만 이동 가능
* [ ] log 입력시 현재까지 기보 출력
