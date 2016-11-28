Roulette
Program has Main.class with main method. This method can get file name with information about users. If method takes empty string or null, for 
some reason, it'd apply default file (bonusInputFile). You can change default file in application.properties.
Command "mvn clean verify" will clean, run tests and compile project into jar. 
But 'java -jar  /home/rado/Development/Roulette/target/roulette-1.0-SNAPSHOT.jar' will gives you error:
Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/context/support/AbstractApplicationContext
Program needs Spring libs.
Information about how methods working described in project interfaces.
