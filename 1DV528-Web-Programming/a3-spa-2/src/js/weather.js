/**
 * Initiates a new weather window
 * @param {Element} applicationWindow window to contain the weather window
 */
export async function initWeatherWindow (applicationWindow) {
  const apiKey = 'e1c6e78d0e1d601a3af92becee420f6a'
  let latitude
  let longitude
  const weatherWindow = document.createElement('div')
  weatherWindow.classList.add('application-content')
  weatherWindow.classList.add('application-content-weather')
  applicationWindow.appendChild(weatherWindow)
  updateTime()
  setInterval(() => {
    updateTime()
  }, 300000)

  /**
   *
   */
  function updateTime () {
    navigator.geolocation.getCurrentPosition(async function (position) {
      latitude = position.coords.latitude
      longitude = position.coords.longitude
      const openweatherMapUrl = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${apiKey}&units=metric`
      const fetchRes = await fetch(openweatherMapUrl)
      const resJson = await fetchRes.json()

      const city = resJson.name
      const countryCode = resJson.sys.country
      const weatherId = resJson.weather[0].id
      const weatherDesc = resJson.weather[0].description
      const temperature = Math.round(parseFloat(resJson.main.temp))
      const temperatureFeel = Math.round(parseFloat(resJson.main.feels_like))
      const humidity = resJson.main.humidity
      const pressure = resJson.main.pressure
      const sunriseTime = resJson.sys.sunrise
      const sunsetTime = resJson.sys.sunset

      const location = document.createElement('h2')
      location.textContent = `${city}, ${countryCode}`
      weatherWindow.appendChild(location)

      const weatherImageDiv = document.createElement('div')
      weatherImageDiv.classList.add('application-weather-icon')
      console.log(weatherId)
      if (weatherId > 800) {
        weatherImageDiv.classList.add('application-weather-icon-clouds')
      } else if (weatherId === 800) {
        weatherImageDiv.classList.add('application-weather-icon-clear')
      } else if (weatherId >= 700) {
        weatherImageDiv.classList.add('application-weather-icon-atmosphere')
      } else if (weatherId >= 600) {
        weatherImageDiv.classList.add('application-weather-icon-snow')
      } else if (weatherId >= 300) {
        weatherImageDiv.classList.add('application-weather-icon-rain')
      } else if (weatherId >= 200) {
        weatherImageDiv.classList.add('application-weather-icon-thunderstorm')
      }
      weatherWindow.appendChild(weatherImageDiv)

      const weatherDescText = document.createElement('p')
      weatherDescText.textContent = weatherDesc
      weatherWindow.appendChild(weatherDescText)

      const temperatureText = document.createElement('p')
      temperatureText.classList.add('application-weather-temperature')
      temperatureText.textContent = `${temperature}°`
      weatherWindow.appendChild(temperatureText)

      const temperatureFeelText = document.createElement('p')
      temperatureFeelText.classList.add('application-weather-temperature-feel')
      temperatureFeelText.textContent = `Feels like: ${temperatureFeel}°`
      weatherWindow.appendChild(temperatureFeelText)

      const extraInfo = document.createElement('div')
      extraInfo.classList.add('application-weather-extras')
      weatherWindow.appendChild(extraInfo)

      const humidityText = document.createElement('p')
      humidityText.textContent = `Humidity: ${humidity}%`
      extraInfo.appendChild(humidityText)

      const pressureText = document.createElement('p')
      pressureText.textContent = `Pressure: ${pressure}hPa`
      extraInfo.appendChild(pressureText)
      const lineBreak = document.createElement('br')
      extraInfo.appendChild(lineBreak)

      const sunriseDate = new Date(sunriseTime * 1000)
      const sunriseText = document.createElement('p')
      sunriseText.textContent = `Sunrise: ${sunriseDate
        .getHours()
        .toString()
        .padStart(2, '0')}:${sunriseDate
        .getMinutes()
        .toString()
        .padStart(2, '0')}`
      extraInfo.appendChild(sunriseText)

      const sunsetDate = new Date(sunsetTime * 1000)
      const sunsetText = document.createElement('p')
      sunsetText.textContent = `Sunset: ${sunsetDate
        .getHours()
        .toString()
        .padStart(2, '0')}:${sunsetDate
        .getMinutes()
        .toString()
        .padStart(2, '0')}`
      extraInfo.appendChild(sunsetText)
    })
  }
}
