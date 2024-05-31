import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
          useMaterial3: true,
        ),
        home: Scaffold(
          backgroundColor: const Color.fromRGBO(255, 235, 235, 1),
          appBar: AppBar(
              title: const Text('Personal Card'),
              backgroundColor: Theme.of(context).primaryColor),
          body: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                const SizedBox(height: 20),
                ClipOval(
                  child: Image.asset(
                    'resources/img/avatar.png',
                    width: 150,
                    height: 150,
                    fit: BoxFit.cover,
                  ),
                ),
                const SizedBox(height: 5),
                const Text(
                  'Ali Shakouri',
                  style: TextStyle(
                    fontSize: 32,
                    fontFamily: 'Dancing_Script',
                  ),
                ),
                const SizedBox(height: 10),
                Container(
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(15),
                        color: Theme.of(context).primaryColor.withOpacity(0.15),
                        boxShadow: const [
                          BoxShadow(
                              color: Color.fromRGBO(0, 0, 0, 0.582),
                              blurStyle: BlurStyle.outer,
                              blurRadius: 5)
                        ]),
                    padding: const EdgeInsets.symmetric(
                        horizontal: 20, vertical: 10),
                    margin: const EdgeInsets.symmetric(horizontal: 20),
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Text(
                                'Student at Linnaeus University',
                                style: TextStyle(
                                    fontSize: 14, fontWeight: FontWeight.bold),
                              ),
                            ],
                          ),
                          const SizedBox(height: 10),
                          Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Image.asset(
                                  'resources/img/email.png',
                                  height: 25,
                                  width: 25,
                                  color: Colors.black.withOpacity(0.5),
                                ),
                                const SizedBox(width: 16),
                                const Text(
                                  'E-mail: ali@gmail.com',
                                )
                              ]),
                          const SizedBox(height: 5),
                          Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Image.asset(
                                  'resources/img/phone.png',
                                  height: 25,
                                  width: 25,
                                  color: Colors.black.withOpacity(0.5),
                                ),
                                const SizedBox(width: 16),
                                const Text('Phone: 012 345 6789')
                              ]),
                          const SizedBox(height: 5),
                          Row(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Image.asset(
                                  'resources/img/internet.png',
                                  height: 25,
                                  width: 25,
                                  color: Colors.black.withOpacity(0.5),
                                ),
                                const SizedBox(width: 16),
                                const Text('Web: https://lnu.se')
                              ]),
                        ]))
              ],
            ),
          ),
        ));
  }
}
