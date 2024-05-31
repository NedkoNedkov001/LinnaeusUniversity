We have compiled the java file and included the .class file, so there is no need to compile. If for some reason you need to compile again, open a terminal in the same folder as the java file, and write "javac TFTPServer.java"

To run the server, open a terminal in the same folder as the java and class files and write "java TFTPServer"

To test for read/write requests, open another terminal in the same folder as the files and write "tftp -i localhost [get/put] [file name]", i.e. "tftp -i localhost get RFC1350.txt", or "tftp -i localhost put RFC1350.txt".

Reminder:
To run the server, port 69 should be available. If not, it is possible to check which process is using it by typing "netstat -ano | findstr :69" in a terminal. Then you can terminate the process using "taskkill /pid [PID] /F"
To read a file, it needs to be in the folder /resources/read and the main folder (the one with class files) should not have a file with the same name and extension.
To write a file, it needs to be in the main folder (mentioned above) and not be in the folder /resources/write


Contribution:
50/50, as we have only worked together, not individually