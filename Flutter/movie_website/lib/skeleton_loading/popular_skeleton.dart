import 'package:flutter/material.dart';
import 'package:skeletonizer/skeletonizer.dart';

class PopularSkeleton extends StatelessWidget {
  const PopularSkeleton({super.key});

  @override
  Widget build(BuildContext context) {
    return Skeletonizer(
      child: GridView.builder(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: 5, childAspectRatio: 0.8),
        itemCount: 20,
        itemBuilder: (context, index) {
          return Card(
            child: Column(
              children: [
                Expanded(
                    child: Container(
                  color: Colors.grey[800],
                ))
              ],
            ),
          );
        },
      ),
    );
  }
}
