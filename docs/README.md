# 체스 미션

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
* [x] end 가 입력되면 프로그램 종료

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

## 리팩터링

* [ ] 테스트 코드 보충
* [ ] 프로그래밍 요구 사항 지키기
* [ ] 예외 메시지를 커스텀 예외로 만들기
* [ ] 예외 시 재입력 구현하기