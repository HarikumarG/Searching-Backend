export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64;
rm -rf /usr/lib/x86_64-linux-gnu/jni/libfolder.so;
g++ -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o /usr/lib/x86_64-linux-gnu/jni/libfolder.so /home/harikumar_g/Documents/projects/Searching-Backend/src/main/java/com/web/foldersearch/FolderSearch.cpp;