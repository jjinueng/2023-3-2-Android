import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

void main() {
  enableFlutterDriverExtension();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'ListView Demo',
      home: Scaffold(
          appBar: AppBar(title: Text('ListView Suhan')), body: MyBody()),
    );
  }
}

class MyBody extends StatelessWidget {
  Widget _buildList(BuildContext context) {
    return ListView(children: [
      _tile(context, 'Roxie Theater', '3117 16th St', Icons.food_bank,
          const Key('list_item1')),
      _tile(context, 'United Artists Stonestown Twin', '501 Buckingham Way',
          Icons.food_bank, const Key('list_item2')),
      _tile(context, 'AMC Metreon 16', '135 4th St #3000', Icons.food_bank,
          const Key('list_item3')),
      _tile(context, 'K\'s Kitchen', '757 Monterey Blvd', Icons.food_bank,
          const Key('list_item4')),
      _tile(context, 'La Ciccia', '291 30th St', Icons.food_bank,
          const Key('list_item5')),
      _tile(context, 'Roxie Theater', '3117 16th St', Icons.food_bank,
          const Key('list_item6')),
      _tile(context, 'United Artists Stonestown Twin', '501 Buckingham Way',
          Icons.food_bank, const Key('list_item7')),
      _tile(context, 'AMC Metreon 16', '135 4th St #3000', Icons.food_bank,
          const Key('list_item8')),
    ]);
  }

  Widget _tile(BuildContext context, String title, String subtitle,
      IconData icon, Key key_) {
    return ListTile(
        title: Text(title,
            style: const TextStyle(fontWeight: FontWeight.w500, fontSize: 20)),
        subtitle: Text(subtitle),
        leading: Icon(icon, color: Colors.blue.shade500),
        onTap: () => Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => SubPage(title, subtitle)),
            ),
        key: key_);
  }

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return _buildList(context);
  }
}

class SubPage extends StatelessWidget {
  String title;
  String subtitle;

  SubPage(this.title, this.subtitle);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(title, key: Key('title'))),
        body: Text(subtitle));
  }
}
