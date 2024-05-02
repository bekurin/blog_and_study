class Human {
  String name;
  int age;
  int health = 100;

  Human(this.name, this.age);

  void grow() {
    age = age + 1;
  }

  void run() {
    print('run!');
    health = health - 10;
  }

  void sleep() {
    print('sleep!');
    health = health + 10;
  }

  void print_health() {
    print('health = $health');
  }
}

void main() {
  Human human = Human("의성", 25);

  human.run();
  human.print_health();
  human.sleep();
  human.print_health();

}
