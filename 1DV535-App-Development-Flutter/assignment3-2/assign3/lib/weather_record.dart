
class WeatherRecord {
  final String city;
  final String countryCode;
  final String weatherMain;
  final String weatherDescription;
  final int temperature;
  final DateTime date;
  final int humidity;
  final int pressure;
  final DateTime sunrise;
  final DateTime sunset;

  WeatherRecord({
    required this.city,
    required this.countryCode,
    required this.weatherMain,
    required this.weatherDescription,
    required this.temperature,
    required this.date,
    required this.humidity,
    required this.pressure,
    required this.sunrise,
    required this.sunset,
  });

  factory WeatherRecord.fromJson(Map<String, dynamic> json) {
    return WeatherRecord(
      city: json['name'],
      countryCode: json['sys']['country'],
      weatherMain: json['weather'][0]['main'],
      weatherDescription: json['weather'][0]['description'],
      temperature: json['main']['temp'].roundToDouble().toInt(),
      date: DateTime.fromMillisecondsSinceEpoch(json['dt'] * 1000, isUtc: true),
      humidity: json['main']['humidity'],
      pressure: json['main']['pressure'],
      sunrise: DateTime.fromMillisecondsSinceEpoch(json['sys']['sunrise'] * 1000, isUtc: true) ,
      sunset: DateTime.fromMillisecondsSinceEpoch(json['sys']['sunset'] * 1000, isUtc: true) ,
    );
  }
}
