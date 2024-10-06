import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';
import 'package:server_side_ui/component/registry.dart';

class ColumnComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return Column(children: Registry.getComponents(args['children'], context));
  }
}
