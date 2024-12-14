import 'package:flutter/material.dart';

class MapPage extends StatefulWidget {
  const MapPage({super.key});

  @override
  State<MapPage> createState() => _MapPageState();
}

class _MapPageState extends State<MapPage> {
  int currentItem = 0;

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('My 부동산'),
        actions: [IconButton(onPressed: () {}, icon: const Icon(Icons.search))],
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            const DrawerHeader(
              decoration: BoxDecoration(
                color: Colors.blue,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    '테스트',
                    style: TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.bold,
                        color: Colors.white),
                  ),
                  Text(
                    'sample@naver.com',
                    style: TextStyle(fontSize: 16.0, color: Colors.white),
                  )
                ],
              ),
            ),
            ListTile(
              title: const Text('선택한 아파트'),
              onTap: () {},
            ),
            ListTile(
              title: const Text('설정'),
              onTap: () {},
            )
          ],
        ),
      ),
      body: currentItem == 0 ? Container() : ListView(),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: currentItem,
        onTap: (value) {
          setState(() {
            currentItem = value;
          });
        },
        items: const [
          BottomNavigationBarItem(label: 'map', icon: Icon(Icons.map)),
          BottomNavigationBarItem(label: 'list', icon: Icon(Icons.list)),
        ],
      ),
      floatingActionButton: FloatingActionButton.extended(
          onPressed: () {}, label: const Text('이 위치로 검색하기')),
    );
  }
}
