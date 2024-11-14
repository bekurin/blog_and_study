import 'package:flutter/material.dart';
import 'package:hive_flutter/hive_flutter.dart';

class DataPage extends StatefulWidget {
  const DataPage({super.key});

  @override
  State<DataPage> createState() => _DataPageState();
}

class _DataPageState extends State<DataPage> {
  var mybox = Hive.box('hivebox');
  List mydata = [];

  var myText = TextEditingController();
  var myValue = TextEditingController();

  @override
  void initState() {
    super.initState();
    getItem();
  }

  void addItem(data) async {
    await mybox.add(data);
    print(mybox.values);
  }

  void getItem() {
    mydata = mybox.keys.map((e) {
      var res = mybox.get(e);
      return {'key': e, 'title': res['title'], 'value': res['value']};
    }).toList();
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Item database"),
        centerTitle: true,
      ),
      body: Center(
        child: Column(
          children: [
            const SizedBox(
              height: 30,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: TextField(
                controller: myText,
                decoration: const InputDecoration(hintText: 'item'),
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: TextField(
                controller: myValue,
                decoration: const InputDecoration(hintText: 'number'),
              ),
            ),
            const SizedBox(
              height: 20,
            ),
            ElevatedButton.icon(
              onPressed: () {
                Map<String, String> d = {
                  'title': myText.text,
                  'value': myValue.text
                };
                addItem(d);
                myText.clear();
                myValue.clear();
              },
              icon: const Icon(Icons.save),
              label: const Text("Save"),
            ),
            const SizedBox(
              height: 20,
            ),
            Expanded(
              child: ListView.builder(
                itemCount: mydata.length,
                itemBuilder: ((context, index) {
                  return ListTile(
                    title: Text("${mydata[index]['title']}"),
                    subtitle: Text("${mydata[index]['value']}"),
                  );
                }),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
