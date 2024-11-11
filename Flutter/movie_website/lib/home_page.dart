import 'package:flutter/material.dart';
import 'package:movie_website/widget/icon_searchbar.dart';
import 'package:movie_website/widget/main_drawer.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: IconSearchbar(),
      drawer: MainDrawer(),
      body: Center(
        child: Text("Movie Site"),
      ),
    );
  }
}
