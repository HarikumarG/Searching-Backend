# Searching-Backend

A java application which consists of two parts.

1. File search, given a pattern and folder path will show all the files matching the pattern in the same hierarchical folder structure
2. Keyword search, given a file name,path and keyword to be searched will show all the lines that matches the given keyword

File search and keyword search both are done in c++ to increase the performance and efficiency. The concept behind calling cpp from java is by using JNI(Java Native Interface).

# Steps to Install and Run

1. Git clone this repository and you will need apache tomcat to run the project
2. Run the command `mvn clean install`
3. Run foldersearch.sh and keyword.sh (this will compile cpp file and store the ./a.out file in java -D library path
4. Run start.sh to copy the maven compiled jar file to tomcat webapps and to run the project

Have a look on Searching-Frontend repository for frontend web app.
