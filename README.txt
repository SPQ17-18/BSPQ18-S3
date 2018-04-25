EXECUTION OF RMIREGISTRY, SERVER AND CLIENT AS 3 PROCESSES
----------------------------------------------------------

First, compile the whole code:
1. mvn compile

Then, in three separate cmd windows, run:

1. rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false
2. mvn exec:java -Pserver
3. mvn exec:java -Pclient


EXECUTION OF JUNIT TEST THAT LAUNCHES THE WHOLE THREE PROCESSES
---------------------------------------------------------------
Check that rmiregistry is NOT running.

In one single cmd window, run:
1. mvn test

EXECUTION TESTS
-----------------
This plugin defines the following set of goals:
cobertura:check, check the last Instrumentation Results.
cobertura:clean, clean up rogue files that cobertura maven plugin is tracking.
cobertura:dump-datafile, cobertura Datafile Dump Mojo.
cobertura:instrument, instruments the compiled classes.
cobertura:cobertura, instruments, Tests, and Generates a Cobertura Report.

1. Add the following contents to add Cobertura support to your pom.xml:
<reporting>
 <plugins>
 <plugin>
 <groupId>org.codehaus.mojo</groupId>
 <artifactId>cobertura-maven-plugin</artifactId>
 <version>2.7</version>
 </plugin>
 </plugins>
</reporting>
2. Type in the following in command line: mvn cobertura:cobertura

3.<build>
<plugins>
<plugin>
<groupId>org.codehaus.mojo</groupId>
<artifactId>cobertura-maven-plugin</artifactId>
<version>2.5.1</version>
<configuration>
<check>
<branchRate>85</branchRate>
<lineRate>85</lineRate>
<haltOnFailure>true</haltOnFailure>
<totalBranchRate>85</totalBranchRate>
<totalLineRate>85</totalLineRate>
<packageLineRate>85</packageLineRate>
<packageBranchRate>85</packageBranchRate>
<regexes>
<regex>
<pattern>es.deusto.testing.junit.*</pattern>
<branchRate>90</branchRate>
<lineRate>90</lineRate>
</regex>
</regexes>
</check>
</configuration>
</plugin>
</plugins>
</build>

4.
1. Run the Unit Tests: mvn test
2. Run the cobertura check: mvn cobertura:cobertura
3. Run the CheckStyle plugin: mvn checkstyle:checkstyle
4. Run the JDepend plugin: mvn jdepend:generate
5. Run the Dashboard plugin: mvn dashboard:dashboard


