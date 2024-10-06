import 'package:flutter/material.dart';

import 'screen/home.dart';
import 'screen/sign_in.dart';

void main() {
  runApp(App());
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(title: 'Server Driven UI', initialRoute: '/', routes: {
      '/': (_) => HomeScreen(),
      '/sign-in': (_) => SignInScreen(),
    });
  }
}
