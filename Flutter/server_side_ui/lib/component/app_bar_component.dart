import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';

class AppBarComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return AppBar(title: Text(args['title']));
  }
}
