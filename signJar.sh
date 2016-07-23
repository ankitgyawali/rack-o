#!/bin/bash
jarName="rackoapp.jar"
keyName="myKeyStore"
keyPassword="ankitgyawali"
javac *.java
jar cvf $jarName *.class *.jpg *.png *.wav
echo $keyPassword | jarsigner -keystore $keyName $jarName me