import 'package:assignment3/info_item.dart';

class WeatherItem extends InfoItem {
  final String city;
  final String country;

  WeatherItem(
      {required this.city,
      required this.country,
      required weather,
      required weatherDescription,
      required temperature})
      : super(weather, weatherDescription, temperature);

  factory WeatherItem.fromJson(Map<String, dynamic> json) {
    return WeatherItem(
      city: json['name'],
      country: json['sys']['country'],
      weather: json['weather'][0]['main'],
      weatherDescription: json['weather'][0]['description'],
      temperature: json['main']['temp'].toString(),
    );
  }
}
