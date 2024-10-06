import 'package:flutter/material.dart';
import 'package:server_side_ui/component/app_bar_component.dart';
import 'package:server_side_ui/component/column_component.dart';
import 'package:server_side_ui/component/container_component.dart';
import 'package:server_side_ui/component/grid_view_component.dart';
import 'package:server_side_ui/component/text_button_component.dart';
import 'package:server_side_ui/component/text_field_component.dart';

import 'component.dart';

class Registry {
  static final Map<String, Component> _dictionary = {
    'AppBar': AppBarComponent(),
    'TextField': TextFieldComponent(),
    'TextButton': TextButtonComponent(),
    'GridView': GridViewComponent(),
    'Container': ContainerComponent(),
    'Column': ColumnComponent()
  };

  static Widget getComponent(dynamic args, BuildContext context) {
    var matchedComponent = _dictionary[args['type']];
    if (matchedComponent != null) {
      return matchedComponent.compose(args, context);
    } else {
      return Container();
    }
  }

  static List<Widget> getComponents(dynamic args, BuildContext context) {
    var matchedComponent = args as List<dynamic>;
    return matchedComponent
        .map((component) => getComponent(component, context))
        .toList();
  }
}
