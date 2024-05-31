# A3 SPA

![Background](readme_img/BackgroundImage.png)

The application is a Single Page Application (SPA) Personal Web Desktop (PWD) and imitates an operating system in the browser. It consists of windows (a draggable element containing applications) and applications. My PWD has 3 applications.

## Memory Game
The user chooses the sizes of a matrix (each side can be between 2 and 5) and is presented with a grid of X cards with hidden colors. There's X/2 pairs that the user has to find. The game keeps track of the time and number of attempts.

## Chat App
The user needs to enter a username to access the chat. The user is then connected to a websocket, where he sends and receives messages. Received messages and then visualized in the chat app. I have implemented a channel support, meaning that the user can choose to send/receive messages only to/from a certain channel.

## Quiz App
The user is presented questions from an API and is given the opportunity to answer with either free text or an alternative button. If the answer is wrong, the user loses and restarts the quiz. When the user answers all the questions correctly, they are presented with the time it took to complete the quiz.

To run the app, enter "npm run serve" in the terminal.