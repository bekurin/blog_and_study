import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';

class TextButtonComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return TextButton(
        onPressed: args['route'] == null
            ? null
            : () => Navigator.pushNamed(context, args['route']),
        child: Text(args['text']));
  }
}
