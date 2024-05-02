String green = "green";
String yellow = "yellow";
String red = "red";

void main() {
  String light = "green";
  print('if');
  if_condition(light);
  print('-------------\n');
  print('switch');
  switch_condition(light);
}

void if_condition(String light) {
  if (light == green) {
    print('$light light');
  } else if (light == yellow) {
    print('$light light');
  } else if (light == red) {
    print('$light light');
  } else {
    print('undefined light type');
  }
}

void switch_condition(String light) {
  switch(light) {
    case "green":
      print('$light light');
    case "yellow":
      print('$light light');
    case "red":
      print('$light light');
    default:
      print('undefined light type');
  }
}
