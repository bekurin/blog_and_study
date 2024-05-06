void main() {
  print('for loop');
  loop_100_using_for();
  print('-----------\n\n');

  print('while loop');
  loop_100_using_while();
  print('-----------\n\n');

  print('object loop');
  loop_object();
  print('-----------\n\n');
}

void loop_100_using_for() {
  for (int i = 0; i < 100; i++) {
    print('i = $i');
  }
}

void loop_object() {
  List<String> fruits = ["귤", "레몬", "라임", "파인애플"];
  for (String fruit in fruits) {
    print('fruit = $fruit');
  }
}

void loop_100_using_while() {
  int i = 0;
  while (i < 100) {
    print('i = $i');
    i = i + 1;
  }
}
