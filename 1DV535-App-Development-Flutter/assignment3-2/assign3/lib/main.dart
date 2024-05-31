import 'dart:io';

import 'package:assign3/weather_record.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:geolocator/geolocator.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

double latitude = 0;
double longitude = 0;
bool _nightMode = false;
Color themeColorMain = Colors.white;
Color themeColorSecond = Colors.black;

void main() {
  final goRouter = GoRouter(
    routes: [
      GoRoute(
        path: '/',
        pageBuilder: (context, state) {
          return const MaterialPage(child: HomeScreen());
        },
      ),
      GoRoute(
        path: '/forecast',
        pageBuilder: (context, state) {
          return const MaterialPage(child: ForecastScreen());
        },
      ),
      GoRoute(
        path: '/about',
        pageBuilder: (context, state) {
          return const MaterialPage(child: AboutScreen());
        },
      ),
    ],
  );

  runApp(MyApp(goRouter: goRouter));
}

class MyApp extends StatelessWidget {
  final GoRouter goRouter;

  const MyApp({Key? key, required this.goRouter}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      routerConfig: goRouter,
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  WeatherRecord? weatherRecord;

  Future<void> _fetchJsonData() async {
    if (latitude == 0 && longitude == 0) {
      await getCurrentLocation();
    }

    String url =
        'https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=27afea0530f42bf998f03905c2ec73c7&units=metric';
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      setState(() {
        weatherRecord = WeatherRecord.fromJson(json.decode(response.body));
      });
    } else {
      throw Exception('Failed to load JSON data');
    }
  }

  @override
  void initState() {
    _fetchJsonData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return weatherRecord != null ? getHomeScreen() : getLoadingScreen();
  }

  Scaffold getHomeScreen() {
    return Scaffold(
      body: Center(
          child: Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(getBackgroundImage()),
                  fit: BoxFit.cover,
                ),
              ),
              child: Column(
                children: [
                  const SizedBox(height: 80),
                  getMainInfo(),
                  const SizedBox(
                    height: 50,
                  ),
                  getAdvancedInfo(),
                  const SizedBox(height: 195),
                  getNavigationRow(
                      this,
                      getCustomButton(context, 'Forecast', '/forecast'),
                      getCustomButton(context, 'About', '/about')),
                ],
              ))),
    );
  }

  Center getMainInfo() {
    return Center(
        child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
      Text(
        '${weatherRecord!.city}, ${weatherRecord?.countryCode}',
        style: TextStyle(
            fontSize: 24, color: themeColorMain, fontWeight: FontWeight.bold),
      ),
      const SizedBox(height: 6),
      Text(
        convertDateToString(weatherRecord!.date),
        style: TextStyle(fontSize: 14, color: themeColorMain),
      ),
      const SizedBox(height: 80),
      getWeatherIcon(weatherRecord!, 40),
      const SizedBox(height: 6),
      Text(
        '${weatherRecord?.weatherDescription}',
        style: TextStyle(fontSize: 14, color: themeColorMain),
      ),
      const SizedBox(height: 20),
      Text(' ${weatherRecord!.temperature.toString()} °C',
          style: TextStyle(
              fontSize: 64,
              color: themeColorMain,
              fontWeight: FontWeight.bold)),
    ]));
  }

  Container getAdvancedInfo() {
    return Container(
        margin: const EdgeInsets.symmetric(horizontal: 70),
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(25),
          color: themeColorSecond.withOpacity(0.2),
        ),
        child: Center(
            child:
                Column(mainAxisAlignment: MainAxisAlignment.center, children: [
          const SizedBox(height: 6),
          Text(
            'Humidity: ${weatherRecord?.humidity}%',
            style: TextStyle(fontSize: 18, color: themeColorMain),
          ),
          const SizedBox(height: 8),
          Text(
            'Pressure: ${weatherRecord?.pressure}hPa',
            style: TextStyle(fontSize: 18, color: themeColorMain),
          ),
          const SizedBox(height: 24),
          Text('Sunrise: ${convertDateToHourString(weatherRecord!.sunrise)}',
              style: TextStyle(fontSize: 18, color: themeColorMain)),
          const SizedBox(height: 8),
          Text('Sunset: ${convertDateToHourString(weatherRecord!.sunset)}',
              style: TextStyle(fontSize: 18, color: themeColorMain)),
          const SizedBox(height: 20),
        ])));
  }
}

class ForecastScreen extends StatefulWidget {
  const ForecastScreen({super.key});

  @override
  State<ForecastScreen> createState() => _ForecastScreenState();
}

class _ForecastScreenState extends State<ForecastScreen> {
  List<WeatherRecord> weatherRecords = [];

  Future<void> _fetchJsonData() async {
    String url =
        'https://api.openweathermap.org/data/2.5/forecast?lat=$latitude&lon=$longitude&appid=27afea0530f42bf998f03905c2ec73c7&units=metric';
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      Map<String, dynamic> jsonResult = json.decode(response.body);
      List recordsList = jsonResult['list'];
      for (Map<String, dynamic> record in recordsList) {
        record.putIfAbsent("name", () => jsonResult['city']['name']);
        Map<String, dynamic> sys = {
          'country': jsonResult['city']['country'],
          'sunrise': jsonResult['city']['sunrise'],
          'sunset': jsonResult['city']['sunset'],
        };
        record.update('sys', (value) => sys);
        weatherRecords.add(WeatherRecord.fromJson(record));
      }
      setState(() {});
    } else {
      throw Exception('Failed to load JSON data');
    }
  }

  @override
  void initState() {
    _fetchJsonData();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return weatherRecords.isNotEmpty ? getForecastScreen() : getLoadingScreen();
  }

  Scaffold getForecastScreen() {
    return Scaffold(
      body: Center(
          child: Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(getBackgroundImage()),
                  fit: BoxFit.cover,
                ),
              ),
              child: Column(
                children: [
                  const SizedBox(height: 80),
                  Text(
                    '${weatherRecords.elementAt(0).city}, ${weatherRecords.elementAt(0).countryCode}',
                    style: TextStyle(
                        fontSize: 24,
                        color: themeColorMain,
                        fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 25),
                  getMainInfo(),
                  const SizedBox(height: 35),
                  getNavigationRow(this, getCustomButton(context, 'Home', '/'),
                      getCustomButton(context, 'About', '/about')),
                ],
              ))),
    );
  }

  Container getMainInfo() {
    return Container(
        margin: const EdgeInsets.symmetric(horizontal: 20),
        height: 600,
        width: 400,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(25),
          color: themeColorSecond.withOpacity(0.2),
        ),
        child: getWeatherRecordsScrollView());
  }

  SingleChildScrollView getWeatherRecordsScrollView() {
    return SingleChildScrollView(
        padding: const EdgeInsets.all(20), child: getWeatherRecords());
  }

  Column getWeatherRecords() {
    List<Widget> weatherRecordWidgets = [];
    for (var record in weatherRecords) {
      weatherRecordWidgets.add(Row(
        children: [
          getWeatherIcon(record, 20),
          const SizedBox(width: 20),
          Text(
              '${convertDateToString(record.date)} - ${convertDateToHourString(record.date)} \n ${record.temperature} °C - ${record.weatherDescription}',
              style: TextStyle(color: themeColorMain, fontSize: 16)),
        ],
      ));
      weatherRecordWidgets.add(const SizedBox(height: 20));
    }
    return Column(children: weatherRecordWidgets);
  }
}

class AboutScreen extends StatefulWidget {
  const AboutScreen({super.key});

  @override
  State<AboutScreen> createState() => _AboutScreenState();
}

class _AboutScreenState extends State<AboutScreen> {
  @override
  Widget build(BuildContext context) {
    return getAboutScreen();
  }

  Scaffold getAboutScreen() {
    return Scaffold(
      body: Center(
          child: Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(getBackgroundImage()),
                  fit: BoxFit.cover,
                ),
              ),
              child: Column(
                children: [
                  const SizedBox(height: 250),
                  Container(
                      margin: const EdgeInsets.symmetric(horizontal: 20),
                      padding: const EdgeInsets.all(20),
                      height: 200,
                      width: 400,
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(25),
                        color: themeColorSecond.withOpacity(0.2),
                      ),
                      child: Center(
                        child: Text(
                            style: TextStyle(color: themeColorMain),
                            textAlign: TextAlign.center,
                            'This is a weather app developed for the course 1DV535 at Linnaeus University using flutter and the openweathermap api\n\nDeveloped by Ali Shakouri'),
                      )),
                  const SizedBox(height: 315),
                  getNavigationRow(this, getCustomButton(context, 'Home', '/'),
                      getCustomButton(context, 'Forecast', '/forecast')),
                ],
              ))),
    );
  }
}

GestureDetector getModeSwitch(State state) {
  return GestureDetector(
      onTap: () {
        state.setState(() {
          _nightMode = !_nightMode;
          Color placeholderColor = themeColorMain;
          themeColorMain = themeColorSecond;
          themeColorSecond = placeholderColor;
        });
      },
      child: getModeSwitchContainer());
}

Row getNavigationRow(State state, elevatedButtonOne, elevatedButtonTwo) {
  return Row(
    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
    children: [elevatedButtonOne, getModeSwitch(state), elevatedButtonTwo],
  );
}

ElevatedButton getCustomButton(BuildContext context, String name, String path) {
  return ElevatedButton(
    onPressed: () {
      GoRouter.of(context).go(path);
    },
    style: ElevatedButton.styleFrom(
        backgroundColor: themeColorSecond.withOpacity(0.2),
        shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(24)))),
    child: Text(
      name,
      style: TextStyle(color: themeColorMain),
    ),
  );
}

Container getModeSwitchContainer() {
  return Container(
    width: 70,
    height: 32,
    decoration: BoxDecoration(
      borderRadius: BorderRadius.circular(16),
      color: themeColorMain,
    ),
    padding: const EdgeInsets.symmetric(horizontal: 4),
    child: Row(
      mainAxisAlignment:
          _nightMode ? MainAxisAlignment.end : MainAxisAlignment.start,
      children: [
        Container(
          width: 24,
          height: 24,
          decoration: BoxDecoration(
              shape: BoxShape.circle,
              image: DecorationImage(
                  image: _nightMode
                      ? const AssetImage('images/weather/Clear_night.png')
                      : const AssetImage('images/weather/Clear.png'))),
          margin: const EdgeInsets.symmetric(horizontal: 4),
        ),
      ],
    ),
  );
}

Future<void> getCurrentLocation() async {
  PermissionStatus permissionStatus = await Permission.location.request();

  if (permissionStatus.isGranted) {
    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);
    latitude = position.latitude;
    longitude = position.longitude;
  }
}

Scaffold getLoadingScreen() {
  return const Scaffold(
      body: Center(
          child: Column(
    mainAxisAlignment: MainAxisAlignment.center,
    children: [
      CircularProgressIndicator(),
      SizedBox(height: 16),
      Text('Fetching data')
    ],
  )));
}

String convertDateToString(DateTime date) {
  List<String> daysList = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
  List<String> monthsList = [
    'Jan',
    'Feb',
    'Mar',
    'Apr',
    'May',
    'Jun',
    'Jul',
    'Aug',
    'Sep',
    'Oct',
    'Nov',
    'Dec'
  ];

  return '${daysList.elementAt(date.weekday - 1)}, ${monthsList.elementAt(date.month - 1)} ${date.day}, ${date.year}';
}

String convertDateToHourString(DateTime date) {
  int hours = date.hour;
  int minute = date.minute;

  return '${hours.toString().padLeft(2, '0')}:${minute.toString().padLeft(2, '0')}';
}

String getBackgroundImage() {
  // int timeHour = weatherRecord.date.hour;

  // if (timeHour > 21 || timeHour < 6) {
  if (_nightMode) {
    return 'images/background/background_night.jpg';
  }

  return 'images/background/background.jpg';
}

Image getWeatherIcon(WeatherRecord weatherRecord, double size) {
  String weatherCondition = weatherRecord.weatherMain;
  // int timeHour = weatherRecord!.date.hour;
  List<String> nightConditionsList = ['Clear', 'Clouds', 'Rain'];
  String filePath;

  if (File('images/weather/$weatherCondition.png').existsSync()) {
    filePath = 'images/weather/Mist.png';
  } else if (nightConditionsList.contains(weatherCondition) &&
      // (timeHour > 21 || timeHour < 6)) {
      (_nightMode)) {
    filePath = 'images/weather/${weatherCondition}_night.png';
  } else {
    filePath = 'images/weather/$weatherCondition.png';
  }

  return Image.asset(filePath, width: size, fit: BoxFit.fill);
}
