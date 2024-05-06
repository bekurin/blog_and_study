// number type
int year = 2024;
double pi = 3.14;
num year_num = 2023;
num pi_num = 3.14;

// string type
String name = "eui seong";

// boolean
bool is_true = true;

// collection
List<String> fruits = ["오렌지", "귤", "레몬"];
Set<int> odds = {1, 3, 3, 3, 5, 5, 7, 7, 9};
Map<String, int> region = {"서울": 1000, "인천": 2000, "경기": 3000};

// date time
DateTime date_time = DateTime.now();

void main() {
  print('number type');
  print("$year, $year_num");
  print('$pi, $pi_num');
  print('-----------\n');

  print('string type');
  print(name);
  print('-----------\n');

  print('boolean');
  print(is_true);
  print('-----------\n');

  print('collection');
  print(fruits);
  print(odds);
  print(region);
  print('-----------\n');

  print('date time');
  print(date_time);
  print('-----------\n');
}
