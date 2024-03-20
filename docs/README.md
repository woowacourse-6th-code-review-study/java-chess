# 체스 미션

## 기능 요구 사항

* [x] 8 * 8 의 체스 판 생성
* [x] 체스 말 배치
* [x] 검은색 말은 대문자, 흰색 말은 소문자로 표현
* [x] 체스 게임 시작 메시지 출력
* [x] 게임 시작(start)/종료(end) 입력 받기
  * [x] start/end 가 아닐 시 예외 발생
  * [x] 예외시 재입력 받기
* [ ] move 기능 구현
  * [x] 이동할 말의 현재 위치, 이동할 위치 입력 받기
  * [x] 말의 위치 이동
  * [ ] 유효하지 않은 위치를 입력한 경우 예외 발생 (ex) z1
  * [ ] 기물의 이동 규칙을 벗어나는 경우 예외 발생
  * [ ] 현재 위치로 빈 칸을 입력받은 경우 예외 발생
* [x] end 가 입력되면 프로그램 종료

## 기물의 이동 기능
* [x] Pawn
* [x] Knight
* [x] King
* [x] Bishop
* [x] Queen
* [x] Rook