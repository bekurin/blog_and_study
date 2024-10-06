import 'package:flutter/material.dart';

import '../component/registry.dart';

enum ScreenType {
  HOME,
  SIGN_IN,
}

class ScreenQuery {
  static Future<List<Widget>> fetchComponents(
      BuildContext context, ScreenType screenType) async {
    // TODO: api를 호출하는 코드 추가
    Map<String, dynamic> data = {'screen': "test"};
    return Registry.getComponents(data['screen']['components'], context);
  }
}
