import 'package:flutter/material.dart';

class DetailScreen extends StatefulWidget {
  const DetailScreen({super.key, required this.city, required this.info});

  final String city;
  final List<dynamic> info;

  @override
  State<DetailScreen> createState() => _DetailScreenState();
}

class _DetailScreenState extends State<DetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.city),
      ),
      body: ListView.builder(
        itemBuilder: (_, index) => ListTile(
          title: Text(widget.info[index]["name"] ?? ""),
          subtitle: Text(
              "${widget.info[index]["description"] ?? ""}\n위치: ${widget.info[index]["address"] ?? ""}\n비용: ${widget.info[index]["cost"] ?? ""}"),
        ),
        itemCount: widget.info.length,
      ),
    );
  }
}
