@echo off
setlocal

set BIN=bin
set SRC=src
set TEST=test\java
set JUNIT_JAR=lib\junit-platform-console-standalone.jar

if not exist "%BIN%" mkdir %BIN%

echo [1/3] Compiling source files...
javac -d %BIN% ^
  %SRC%\container\MyLinkedList.java ^
  %SRC%\prisoners\Prisoner.java ^
  %SRC%\prisoners\PrisonerAnalyzer.java ^
  %SRC%\prisoners\PrisonerUtils.java
if errorlevel 1 ( echo ERROR: Source compilation failed & exit /b 1 )

echo [2/3] Compiling test files...
javac -cp "%BIN%;%JUNIT_JAR%" -d %BIN% ^
  %TEST%\container\MyLinkedListTest.java ^
  %TEST%\prisoners\PrisonerUtilsTest.java ^
  %TEST%\prisoners\PrisonerAnalyzerTest.java
if errorlevel 1 ( echo ERROR: Test compilation failed & exit /b 1 )

echo [3/3] Running tests...
java -jar %JUNIT_JAR% ^
  --class-path %BIN% ^
  --scan-class-path ^
  --details verbose

endlocal
