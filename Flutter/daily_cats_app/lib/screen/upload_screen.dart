import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';

class UploadScreen extends StatefulWidget {
  const UploadScreen({super.key});

  @override
  State<UploadScreen> createState() => _UploadScreenState();
}

class _UploadScreenState extends State<UploadScreen> {
  String? imagePath;

  Future<String?> selectImage() async {
    final picker = ImagePicker();
    XFile? pickImage = await picker.pickImage(source: ImageSource.gallery);
    if (pickImage == null) {
      return null;
    }
    return pickImage.path;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      padding: const EdgeInsets.only(top: 100.0),
      child: SafeArea(
        bottom: false,
        child: Scaffold(
          appBar: getAppBar(context),
          body: Container(
            color: Colors.white,
            child: Center(
              child: SingleChildScrollView(
                child: GestureDetector(
                  onTap: () {
                    selectImage().then((String? path) {
                      if (path == null) {
                        return;
                      }
                      setState(() {
                        imagePath = path;
                      });
                    });
                  },
                  behavior: HitTestBehavior.translucent,
                  child: Column(
                    children: [
                      Container(
                        width: 300.0,
                        height: 300.0,
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(5.0),
                          border: Border.all(
                              width: 0.5, color: const Color(0xFFAAAAAA)),
                        ),
                        child: imagePath != null
                            ? Image.file(File(imagePath!),
                                width: 200.0, height: 200.0)
                            : const Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Icon(Icons.upload, size: 50.0),
                                  Text("고양이 사진 업로드")
                                ],
                              ),
                      )
                    ],
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

AppBar getAppBar(BuildContext context) {
  return AppBar(
    title: const Text("사진 업로드"),
    leading: IconButton(
      icon: const Text("취소"),
      padding: EdgeInsets.zero,
      onPressed: () {
        Navigator.of(context).pop();
      },
    ),
    actions: <Widget>[
      IconButton(
        icon: const Text("저장"),
        padding: EdgeInsets.zero,
        onPressed: () {
          Navigator.of(context).pop();
        },
      )
    ],
  );
}
