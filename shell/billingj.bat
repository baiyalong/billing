@echo off
call billing_env

java -cp %BILLING_CP% -DBILLING_HOME=%BILLING_HOME% %*


