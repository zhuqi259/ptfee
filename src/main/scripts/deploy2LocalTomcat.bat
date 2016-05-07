@echo off
set PROJECT_DIR="F:\workspace\java\web\idea\ws_ptfee\ptfee"
set TOMCAT_HOME="E:\development\Tomcat7_2016_5_7"
set WAR_NAME=ptfee.war
cd /d %PROJECT_DIR%
call mvn clean package
cd /d %TOMCAT_HOME%\webapps
del /q %WAR_NAME%
copy %PROJECT_DIR%\target\%WAR_NAME% .
