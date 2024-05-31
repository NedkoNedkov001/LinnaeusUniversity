import 'package:assignment3/info_item.dart';

class ForecastItem extends InfoItem {
  final String date;

  ForecastItem({required this.date, required weather, required weatherDescription, required temperature})
      : super(weather, weatherDescription, temperature);

  factory ForecastItem.fromJson(Map<String, dynamic> json) {
    return ForecastItem(
      date: json['dt_txt'],
      weather: json['weather'][0]['main'],
      weatherDescription: json['weather'][0]['description'],
      temperature: json['main']['temp'].toString(),
    );
  }
}
