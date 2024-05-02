int a = 2;
int b = 4;

void main() {
  print("$a + $b = ${a + b}");
  print("$a - $b = ${a - b}");
  print("-$a = ${-a}");

  print("$a * $b = ${a * b}");
  print("$a / $b = ${a / b}");
  
  // 몫 연산자
  print("$a ~/ $b = ${a ~/ b}");
  print("$a % $b = ${a % b}");

  print("++$a = ${++a}");
  print("$a++ = ${a++}");
  print("--$b = ${--b}");
  print("$b-- = ${b--}");

  print("$a == $b = ${a == b}");
  print("$a != $b = ${a != b}");

  print("$a > $b = ${a > b}");
  print("$a >= $b = ${a >= b}");
  print("$a < $b = ${a < b}");
  print("$a <= $b = ${a <= b}");
  
  a = 1000;
  print("$a = $a");
  
  print("!($a == $b) = ${!(a == b)}");
}
