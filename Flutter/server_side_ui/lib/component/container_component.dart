import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';
import 'package:server_side_ui/component/registry.dart';

class ContainerComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return Container(
        padding: EdgeInsets.all(args['padding']),
        color: args['color'] == null
            ? null
            : Color(args['color']['value']).withAlpha(args['color']['alpha']),
        child: Registry.getComponent(args['child'], context));
  }
}
