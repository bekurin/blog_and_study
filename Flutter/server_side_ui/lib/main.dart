import 'package:flutter/material.dart';
import 'package:server_side_ui/api/screen_controller.dart';

import 'screen/home.dart';
import 'screen/sign_in.dart';

void main() async {
  runApp(App());
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    ScreenController().fetchMainScreen();
    return MaterialApp(title: 'Server Driven UI', initialRoute: '/', routes: {
      '/': (_) => HomeScreen(),
      '/sign-in': (_) => SignInScreen(),
    });
  }
}
