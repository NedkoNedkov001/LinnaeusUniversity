import 'dart:convert';
import 'package:assignment3/info_item.dart';
import 'package:assignment3/weather_item.dart';
import 'package:assignment3/forecast_item.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:assignment3/location.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Weather App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const WeatherApp(),
    );
  }
}

class WeatherApp extends StatefulWidget {
  const WeatherApp({super.key});

  @override
  _WeatherAppState createState() {
    return _WeatherAppState();
  }
}

class _WeatherAppState extends State<WeatherApp> {
  int _currentIndex = 0;

  final List<Widget> _pages = [
    const CurrentScreen(),
    const ForecastScreen(),
    const AboutScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _currentIndex,
        onTap: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Current',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.bar_chart),
            label: 'Forecast',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.info),
            label: 'About',
          ),
        ],
      ),
    );
  }
}

class CurrentScreen extends StatefulWidget {
  const CurrentScreen({super.key});

  @override
  State<CurrentScreen> createState() => _CurrentScreenState();
}

class _CurrentScreenState extends State<CurrentScreen> {
  WeatherItem? _weatherItem;

  @override
  void initState() {
    _getWeatherInfo();
    super.initState();
  }

  Future<void> _getWeatherInfo() async {
    String url =
        'https://api.openweathermap.org/data/2.5/weather?appid=e1c6e78d0e1d601a3af92becee420f6a&units=metric&';
    Location location = await getLocation();
    url +=
        "lat=${location.latitude.toStringAsFixed(2)}&lon=${location.longitude.toStringAsFixed(2)}";
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      setState(() {
        _weatherItem = WeatherItem.fromJson(json.decode(response.body));
      });
    } else {
      throw Exception('Failed to load JSON data');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: _weatherItem != null
            ? getWeatherScreen()
            : const CircularProgressIndicator(),
      ),
    );
  }

  AssetImage _getWeatherBackground() {
    List<String> atmosphereConditions = [
      "mist",
      "smoke",
      "haze",
      "dust",
      "fog",
      "sand",
      "dust",
      "ash",
      "squall",
      "tornado"
    ];

    if (atmosphereConditions.contains(_weatherItem?.weather.toLowerCase())) {
      return const AssetImage("resources/img/atmosphere.jpg");
    }

    return AssetImage(
        "resources/img/${_weatherItem?.weather.toLowerCase()}.jpg");
  }

  String _getLocationAsString() {
    return '${_weatherItem?.city}, ${_weatherItem?.country}';
  }

  Container getWeatherScreen() {
    return Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: _getWeatherBackground(),
            fit: BoxFit.cover,
          ),
        ),
        child: Center(
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
            getWeatherIcon(_weatherItem as InfoItem, 70),
            const SizedBox(height: 10),
            Text(
              _getLocationAsString(),
              style: const TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                  fontWeight: FontWeight.w400),
            ),
            const SizedBox(height: 14),
            Text(
              getDateAsString(DateTime.now()),
              style: const TextStyle(
                color: Colors.white,
              ),
            ),
            const SizedBox(height: 6),
            Text(
              getWeather(_weatherItem as InfoItem),
              style: const TextStyle(
                  color: Colors.white,
                  fontStyle: FontStyle.italic,
                  fontSize: 14),
            ),
            const SizedBox(height: 24),
            Text(getTemperatureAsString(_weatherItem!.temperature),
                style: const TextStyle(
                    color: Colors.white,
                    fontSize: 28,
                    fontWeight: FontWeight.w900)),
            const SizedBox(
              height: 100,
            ),
          ]),
        ));
  }
}

class ForecastScreen extends StatefulWidget {
  const ForecastScreen({super.key});

  @override
  State<ForecastScreen> createState() => _ForecastScreenState();
}

class _ForecastScreenState extends State<ForecastScreen> {
  late List<ForecastItem> forecast = [];
  @override
  void initState() {
    _getForecastInfo();
    super.initState();
  }

  Future<void> _getForecastInfo() async {
    String url =
        'https://api.openweathermap.org/data/2.5/forecast?&appid=e1c6e78d0e1d601a3af92becee420f6a&units=metric&';
    Location location = await getLocation();
    url +=
        "lat=${location.latitude.toStringAsFixed(2)}&lon=${location.longitude.toStringAsFixed(2)}";
    final response = await http.get(Uri.parse(url));

    if (response.statusCode == 200) {
      Map<String, dynamic> jsonBody = json.decode(response.body);
      var forecastList = jsonBody['list'];
      for (var forecastItem in forecastList) {
        forecast.add(ForecastItem.fromJson(forecastItem));
      }
      setState(() {});
    } else {
      throw Exception('Failed to load JSON data');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: forecast.isNotEmpty
            ? _getForecastScreen()
            : const CircularProgressIndicator(),
      ),
    );
  }

  Container _getForecastScreen() {
    return Container(
        padding: const EdgeInsets.fromLTRB(30, 70, 30, 0),
        child: SingleChildScrollView(child: _getForecastItems()));
  }

  Column _getForecastItems() {
    List<Widget> items = [];
    for (var item in forecast) {
      DateTime time = DateTime.parse(item.date);

      items.add(Row(children: [
        getWeatherIcon(item, 20),
        const SizedBox(width: 30),
        Text(
            softWrap: true,
            style: const TextStyle(
              fontSize: 16,
            ),
            '${getDateAsString(time)} - ${_getTimeAsString(time)} \n${getTemperatureAsString(item.temperature)} - ${item.weatherDescription}')
      ]));
      items.add(const SizedBox(height: 12));
    }
    return Column(children: items);
  }

  String _getTimeAsString(DateTime time) {
    String formattedHours = time.hour.toString().padLeft(2, '0');
    String formattedMinutes = time.minute.toString().padLeft(2, '0');
    return '$formattedHours:$formattedMinutes';
  }
}

class AboutScreen extends StatelessWidget {
  const AboutScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Container(
      margin: const EdgeInsets.symmetric(horizontal: 30),
      child: const Center(
          child: Column(mainAxisAlignment: MainAxisAlignment.center, children: [
        Text(
            style: TextStyle(fontSize: 26, fontWeight: FontWeight.bold),
            'Project Weather'),
        SizedBox(
          height: 12,
        ),
        Text(
            textAlign: TextAlign.center,
            'This is an app that is developed by the course 1DV535 at Linnaeus University using Flutter and the OpenWeatherMap API.'),
        SizedBox(
          height: 12,
        ),
        Text('Developed by Nedko Nedkov')
      ])),
    ));
  }
}

Future<Location> getLocation() async {
  PermissionStatus permissionStatus = await Permission.location.request();

  if (permissionStatus.isGranted) {
    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);
    return Location(latitude: position.latitude, longitude: position.longitude);
  } else {
    return Location(latitude: 0, longitude: 0);
  }
}

IconData getWeatherIconData(condition) {
  switch (condition) {
    case 'thunderstorm':
      return Icons.thunderstorm;
    case 'drizzle':
      return Icons.water_drop;
    case 'rain':
      return Icons.water_drop;
    case 'snow':
      return Icons.cloudy_snowing;
    case 'clouds':
      return Icons.cloud;
    case 'clear':
      return Icons.sunny;
    default:
      return Icons.foggy;
  }
}

Icon getWeatherIcon(InfoItem infoItem, double iconSize) {
  return Icon(
      color: Colors.grey,
      getWeatherIconData(infoItem.weather.toLowerCase()),
      size: iconSize);
}

String getDateAsString(DateTime date) {
  return '${DateFormat('EEEE').format(date)}, ${date.day} ${DateFormat('MMMM').format(date)} ${date.year}';
}

String getTemperatureAsString(String temperatureString) {
  return '${double.parse(temperatureString).roundToDouble().toInt()} °C';
}

String getWeather(InfoItem infoItem) {
  return infoItem.weatherDescription;
}
