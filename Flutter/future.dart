import 'dart:async';

void main() {
  Timer(Duration(seconds: 3), () {
    print('3초가 지났습니다.');
  });
  print('타이머를 시작합니다.');
}
