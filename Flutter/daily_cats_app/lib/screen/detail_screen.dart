import 'package:daily_cats_app/model/cat.dart';
import 'package:flutter/material.dart';

class DetailScreen extends StatefulWidget {
  const DetailScreen({
    super.key,
    required this.cat,
  });

  final Cat cat;

  @override
  State<DetailScreen> createState() => _DetailScreenState();
}

class _DetailScreenState extends State<DetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.cat.title),
      ),
      body: SafeArea(
          child: Stack(
        children: [
          ListView(
            padding: const EdgeInsets.only(top: 10.0, left: 10.0, right: 10.0),
            physics: const ClampingScrollPhysics(),
            children: [
              AspectRatio(
                  aspectRatio: 1,
                  child: Image.asset(
                    widget.cat.link,
                    fit: BoxFit.cover,
                  )),
              Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
                Text(widget.cat.name,
                    style: const TextStyle(
                        fontSize: 20.0, color: Color(0xFF777777)))
              ]),
              Row(
                children: [
                  IconButton(
                    padding: EdgeInsets.zero,
                    icon: const Icon(Icons.thumb_up_outlined),
                    onPressed: () {},
                  ),
                  Text(widget.cat.likeCount.toString()),
                ],
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10.0),
                child: Text(
                  "${widget.cat.createdAt.year}년 ${widget.cat.createdAt.month}월 ${widget.cat.createdAt.day}일",
                  style: const TextStyle(color: Color(0xFFAAAAAA)),
                ),
              ),
              Text("댓글 ${widget.cat.replyCount}개"),
              ...List.generate(
                  replies.length,
                  (int index) => Padding(
                        padding: const EdgeInsets.only(top: 10.0),
                        child: Row(
                          children: [
                            const Text("익명",
                                style: TextStyle(fontWeight: FontWeight.bold)),
                            const Padding(
                                padding: EdgeInsets.symmetric(horizontal: 3.0)),
                            Text(replies[index]),
                          ],
                        ),
                      )),
              Align(
                alignment: Alignment.bottomCenter,
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 10.0),
                  child: Container(
                    padding: const EdgeInsets.only(
                      top: 10.0,
                    ),
                    color: Theme.of(context).canvasColor,
                    child: const TextField(
                      autocorrect: false,
                      decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(
                              top: 5.0, bottom: 5.0, left: 10.0),
                          border: OutlineInputBorder(),
                          hintText: "댓글 작성",
                          suffixIcon: Icon(Icons.send, color: Colors.blue)),
                    ),
                  ),
                ),
              )
            ],
          ),
        ],
      )),
    );
  }
}

final List<String> replies = [
  "테스트 입력 01",
  "테스트 입력 02",
  "테스트 입력 03",
  "테스트 입력 04",
  "테스트 입력 05",
  "테스트 입력 06",
  "테스트 입력 07",
  "테스트 입력 08",
];
