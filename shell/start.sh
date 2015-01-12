#!/bin/sh

JAVA_HOME=/usr/lib/jvm/jre/bin//java
BILLING_HOME=/opt/billing
MAIN=com.zhcs.billing.quartz.main.Main

CLASSPATH=$BILLING_HOME/bin
for i in "$BILLING_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

$JAVA_HOME -cp $CLASSPATH $MAIN
