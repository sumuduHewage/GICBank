Banking System - Instructions to Run the Application
This document provides step-by-step instructions to set up, run, and test the Banking System application.

1. Prerequisites
Before running the project, ensure you have the following installed:
Java 8+
Maven 
Git

2. Clone or Download the Project
If the project is hosted on GitHub, you can clone it using:
git clone https://github.com/sumuduHewage/GICBank.git

3. Compile & Run the Application
Build the project:

4. Running Unit Tests
mvn test

5. Using the Application
Once the application starts, you will see the following menu:
Welcome to AwesomeGIC Bank! What would you like to do?
[T] Input transactions
[I] Define interest rules
[P] Print statement
[Q] Quit

Example Commands:
Enter a transaction: 20230302 AC001 D 100.00
Define an interest rule: 20230302 RULE03 2.20
Print a statement: AC001 202303
Exit the application: Q


