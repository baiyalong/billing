@echo off
:dynClasspath
rem set BILLING_HOME=.\..

set JAVA_HOME=D:\Java\jdk1.6
set Path=%Path%;%JAVA_HOME%/bin;
set BILLING_HOME=D:/zhcs/billing
set LIB_BASE_DIR=%BILLING_HOME%

echo BILLING_HOME=%BILLING_HOME%
echo LIB_BASE_DIR=%LIB_BASE_DIR%

set _LIBJARS=.;%LIB_BASE_DIR%\build\classes;%LIB_BASE_DIR%\config;%LIB_BASE_DIR%\scripts;%LIB_BASE_DIR%\bin

for %%i in (%LIB_BASE_DIR%\lib\*.jar) do call %BILLING_HOME%\shell\cpappend.bat %%i
set BILLING_CP=%_LIBJARS%
echo %BILLING_CP%
set _LIBJARS=
