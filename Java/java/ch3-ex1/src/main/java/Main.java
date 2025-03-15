import java.util.List;

public class Main {
  /**
   * 프레임 리셋을 진행하면 게임을 초기화 하듯이 코드 실행을 초기화할 수 있지만 외부 의존성이 있는 경우 완전히 실행이 롤백되지는 않는다.
   * 가령, DB 관련 테스트를 진행하는 경우 Insert 가 일어났다고 하면 해당 데이터를 delete 해주거나 롤백이 되지 않는다.
   * 이점을 유의 하면서 프레임 리셋을 진행해야한다.
   */
  public static void main(String[] args) {
    Decoder d = new Decoder();
    var result = d.decode(List.of("ab1c", "a112c", "abcd", "1234"));
    System.out.println(result);
  }
}
