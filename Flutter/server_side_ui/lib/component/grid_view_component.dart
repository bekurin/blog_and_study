import 'package:flutter/material.dart';
import 'package:server_side_ui/component/component.dart';
import 'package:server_side_ui/component/registry.dart';

class GridViewComponent extends Component {
  @override
  Widget compose(Map<String, dynamic> args, BuildContext context) {
    return Expanded(
      child: GridView.count(
        padding: const EdgeInsets.all(20),
        crossAxisSpacing: 20,
        mainAxisSpacing: 20,
        crossAxisCount: args["columnCount"],
        children: Registry.getComponents(args['children'], context),
      ),
    );
  }
}
