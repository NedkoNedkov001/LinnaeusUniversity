import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ToDo List',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blueAccent),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'ToDo List'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});
  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final List<String> _items = [];
  final TextEditingController _itemController = TextEditingController();
  final FocusNode _focusNode = FocusNode();

  _changeFromList(String item) {
    _focusNode.requestFocus();
    setState(() {
      _items.remove(item);
      _itemController.text = item;
    });
  }

  _addToList() {
    _focusNode.unfocus();
    if (_itemController.text.length > 2) {
      setState(() {
        _items.add(_itemController.text);
        _itemController.clear();
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.primary,
          title: Text(widget.title),
        ),
        body: Container(
            color: const Color.fromARGB(255, 240, 240, 240),
            child: Column(children: [
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 10),
                child: TextFormField(
                  focusNode: _focusNode,
                  controller: _itemController,
                  decoration: const InputDecoration(
                    labelText: 'Enter item',
                    hintText: 'ToDo item',
                  ),
                ),
              ),
              const SizedBox(height: 10),
              Center(
                child: ElevatedButton(
                  onPressed: _addToList,
                  style: ElevatedButton.styleFrom(
                    padding: const EdgeInsets.all(16),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    fixedSize: const Size(100, 50),
                    textStyle: const TextStyle(fontSize: 15),
                    backgroundColor: Colors.blueAccent,
                  ),
                  child: const Text(
                    'Add Item',
                    style: TextStyle(color: Colors.white),
                  ),
                ),
              ),
              Expanded(
                  child: ListView.separated(
                itemCount: _items.length,
                itemBuilder: (BuildContext context, int index) {
                  return ListTile(
                    title: Text('● ${_items[index]}'),
                    onTap: () => _changeFromList(_items[index]),
                  );
                },
                separatorBuilder: (_, __) => const Divider(height: 0),
              ))
            ])));
  }
}
