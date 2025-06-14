@echo off
set "PROJECT_DIR=C:\Users\Тимур\Documents\GitHub\multithreading"
cd /d "%PROJECT_DIR%"

mvn exec:java -Dexec.mainClass="org.example.Main"

pause