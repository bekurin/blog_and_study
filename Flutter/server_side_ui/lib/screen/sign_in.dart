import 'package:flutter/material.dart';
import 'package:server_side_ui/api/screen_query.dart';

class SignInScreen extends StatelessWidget {
  Future<List<Widget>> _load(BuildContext context) async {
    return ScreenQuery.fetchComponents(context, ScreenType.SIGN_IN);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Column(
      children: <Widget>[
        FutureBuilder(
            future: _load(context),
            builder: (BuildContext context, AsyncSnapshot snapshot) {
              if (snapshot.hasData == false) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return Expanded(child: Column(children: snapshot.data));
              }
            })
      ],
    ));
  }
}
