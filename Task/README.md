Instructions on how to run the program on Windows 


I.Initial Setup

1. Download and install JDK (Java Development Kit). Add the "bin" directory to the Path environment
variable (if not installed and added yet). 

2. Download| install Apache Maven| and add the "bin" directory of the Maven installation to the PATH
environment variable. (if not installed and added yet)


II. Run the Main class

1. Navigate the terminal to the directory where the project is stored

2. Compile the maven project by using the `mvn compile` command 

3. Import the required dependencies: 

3.1 Generate a classpath that includes all dependencies located in the POM file (mvn dependency:build-classpath)

3.2 Copy the classpath and use the following command ->
java -cp target/classes;(enter the copied classpath without any space or brackets)
+ org.example.entity.Main (the location of the Main file)


III. Run Tests

1. Run the unit tests using `mvn test` command
