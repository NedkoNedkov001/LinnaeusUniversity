import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Add item to the ToDo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
            seedColor: const Color.fromRGBO(32, 148, 239, 1)),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Add item to the ToDo'),
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
  final TextEditingController _controller = TextEditingController();
  final FocusNode _focusNode = FocusNode();

  _add() {
    if (_controller.text.isNotEmpty) {
      setState(() {
        _items.add(_controller.text);
        _controller.clear();
        _focusNode.unfocus();
      });
    }
  }

  _edit(String item) {
    setState(() {
      _items.remove(item);
      _controller.text = item;
      _focusNode.requestFocus();
    });
  }

  // Duplicates an item when you long press it
  _duplicate(String item) {
    setState(() {
      _items.add(item);
    });
  }

  // Deletes all items when you click on the delete icon in the bottom right
  _deleteAll() {
    setState(() {
      while (_items.isNotEmpty) {
        _items.removeLast();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color.fromRGBO(32, 148, 239, 1),
        title: Text(widget.title),
      ),
      body: Column(children: [
        Container(
          padding: const EdgeInsets.fromLTRB(12, 12, 12, 24),
          child: TextFormField(
            controller: _controller,
            decoration: const InputDecoration(
                labelText: 'Enter ToDo item',
                border: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(20))
                    // borderRadius: BorderRadius.all(Radius.circular(10))
                    )),
            focusNode: _focusNode,
          ),
        ),
        Center(
          child: ElevatedButton(
            onPressed: _add,
            style: ElevatedButton.styleFrom(
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(12),
              ),
              fixedSize: const Size.fromWidth(120),
              textStyle: const TextStyle(fontSize: 12),
              backgroundColor: const Color.fromRGBO(32, 148, 239, 1),
            ),
            child: const Text(
              'Add Item',
              style: TextStyle(color: Color.fromRGBO(255, 255, 255, 1)),
              textHeightBehavior:
                  TextHeightBehavior(applyHeightToFirstAscent: true),
            ),
          ),
        ),
        Expanded(
            child: ListView.builder(
          itemCount: _items.length,
          itemBuilder: (BuildContext context, int index) {
            return ListTile(
              splashColor: const Color.fromRGBO(32, 148, 239, 1),
              title: Text(_items[index]),
              onTap: () => _edit(_items[index]),
              onLongPress: () => _duplicate(_items[index]),
            );
          },
        ))
      ]),
      floatingActionButton: FloatingActionButton(
        onPressed: _deleteAll,
        tooltip: 'Delete all',
        backgroundColor: const Color.fromRGBO(32, 148, 239, 1),
        child: const Icon(Icons.delete_outline,
            color: Color.fromRGBO(255, 255, 255, 1)),
      ),
    );
  }
}
