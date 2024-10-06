import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';

class TextFieldComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return TextField(
        decoration: InputDecoration(labelText: args['labelText']),
        enabled: args['enabled']);
  }
}
