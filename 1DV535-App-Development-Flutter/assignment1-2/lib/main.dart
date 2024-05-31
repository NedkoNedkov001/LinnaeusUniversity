import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Personal Card'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              Image.asset(
                'images/avatar.jpg',
                width: 400,
                height: 400,
              ),
              const Text(
                'Nedko Nedkov',
                style: TextStyle(fontSize: 40, fontFamily: 'Caveat'),
              ),
              Container(
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(10.0),
                  color: const Color.fromARGB(59, 33, 149, 243),
                ),
                padding: const EdgeInsets.all(15),
                margin: const EdgeInsets.all(15),
                child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text('Student at Linnaeus University',
                          style: TextStyle(
                              fontSize: 18,
                              fontStyle: FontStyle.italic,
                              fontWeight: FontWeight.w500)),
                      const SizedBox(height: 6),
                      getTextWithLeadingIcon(
                          'email.png', 'nn222mx@student.lnu.se'),
                      const SizedBox(height: 6),
                      getTextWithLeadingIcon('phone.png', '+46 76 970 4020'),
                      const SizedBox(height: 6),
                      getTextWithLeadingIcon('location.png', 'Växjö, Sweden'),
                    ]),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Row getTextWithLeadingIcon(String iconPath, String text) {
    return Row(children: [
      Image.asset(
        'images/$iconPath',
        height: 25,
        width: 25,
      ),
      const SizedBox(width: 16),
      Text(text)
    ]);
  }
}
