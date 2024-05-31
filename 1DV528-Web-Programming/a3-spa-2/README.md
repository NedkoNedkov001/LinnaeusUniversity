# A3 SPA

![App](AppImage.png)

The application is on a single page (SPA). It has 3 subapplications - memory game with pairs of cards you need to find, chat where you can chat with other students using a websocket and weather app that shows you the weather at your current location and updates every 5 minutes. There is also a clock on the desktop to make it more interesting.

* Memory Game: You can play the memory game by selecting a NxN board first (note that it cannot be 3x3 because it generates 9 cards and there will be a card without a twin). Then you have to find all pairs. The game records the time and the attempts it takes to finish. Once all the pairs are found you can start over.

* Chat: If you open the app for the first time you have to submit a username. It is then saved to the browser's cache and the chat is open. It connects to a websocket and sends and receives messages from it. Received messages are then shown on your screen. There is an added functionality of emojis in the chat. Just click on the emojis button and when you are done, click on it again to hide them. Another additional functionality is that it shows you the time when a message was sent.


* Weather: If you open the app for the first time you have to allow for it to use your location. Then it will take your coordinates and use an API to get the current weather at your place. It shows the place you are in, an icon and description of the current weather, the actual and feel temperature, humidity and pressure and sunrise and sunset hours. It is autoupdated every 5 minutes to keep it accurate.


To start the application use "npm install", "npm run build" and then "npm run serve"