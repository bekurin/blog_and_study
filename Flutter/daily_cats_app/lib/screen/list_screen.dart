import 'package:daily_cats_app/model/cat.dart';
import 'package:daily_cats_app/screen/detail_screen.dart';
import 'package:daily_cats_app/screen/upload_screen.dart';
import 'package:flutter/material.dart';

class ListScreen extends StatefulWidget {
  const ListScreen({super.key});

  @override
  State<ListScreen> createState() => _ListScreenState();
}

class _ListScreenState extends State<ListScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("간단 SNS 앱"),
        actions: [
          IconButton(icon: const Icon(Icons.camera_alt), onPressed: () {
            showDialog(context: context, builder: (_) => const UploadScreen());
          })
        ],
      ),
      body: GridView.builder(
        padding: const EdgeInsets.only(top: 10.0, left: 10.0, right: 10.0),
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 3,
            mainAxisSpacing: 10.0,
            crossAxisSpacing: 10.0,
            childAspectRatio: 1.0),
        itemCount: cats.length,
        itemBuilder: (_, int index) => GestureDetector(
          onTap: () {
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => DetailScreen(cat: cats[index])));
          },
          child: Image.asset(
            cats[index].link,
            fit: BoxFit.cover,
          ),
        ),
      ),
    );
  }
}

// temporary use
final List<Cat> cats = [
  Cat(
      id: 0,
      name: "임시 이미지 01",
      title: "임시 이미지 01 제목",
      link: "assets/images/image01.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
  Cat(
      id: 1,
      name: "임시 이미지 02",
      title: "임시 이미지 02 제목",
      link: "assets/images/image02.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
  Cat(
      id: 2,
      name: "임시 이미지 03",
      title: "임시 이미지 03 제목",
      link: "assets/images/image03.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
  Cat(
      id: 3,
      name: "임시 이미지 04",
      title: "임시 이미지 04 제목",
      link: "assets/images/image04.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
  Cat(
      id: 4,
      name: "임시 이미지 05",
      title: "임시 이미지 05 제목",
      link: "assets/images/image05.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
  Cat(
      id: 5,
      name: "임시 이미지 06",
      title: "임시 이미지 06 제목",
      link: "assets/images/image06.png",
      likeCount: 0,
      replyCount: 0,
      createdAt: DateTime.now()),
];
