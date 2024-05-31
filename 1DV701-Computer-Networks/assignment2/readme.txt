INSTRUCTIONS TO RUN THE WEB SERVER

STARTING THE SERVER
We sent the compiled ".class" files with in the ZIP, so there is no need to compile. In case there are no ".class" files, open a terminal in the folder containing the .java files and write "javac Server.java" and "javac ServerConn.java".
To start the server open a terminal in the folder containing the .class files and write "java Server [port] [path]", i.e. "java Server 80 public".


STATUS CODES
200 OK - Just open an existing .html or .png file or directory in the root.
302 Found - Navigate to "/redirect.html". It will redirect you to the index.html using the "location" in the response header. and show 302 Found status.
404 Not Found - Open any non-existent file.
500 Internal Server Error -  Navigate to "/500.html". It will show 500 Internal Service Error status.

POST METHOD
Disclaimer: Not working... We tried but no success
To try to post a file, go to "/upload.html", put the file in the form and submit.

WORK EFFORT
50% Ibrahim Groshar (ig222je)
50% Nedko Nedkov (nn222mx)
We have written 50-50, as we have only worked on the assignment together, both on the programming part, and on the report & instructions.