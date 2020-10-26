# Pay Me!

A Java GUI application utilising MySQL to create a robust banking application enabling the user to make payments.

# How It's Made

We used the [Java Swing Library](https://docs.oracle.com/javase/tutorial/uiswing/index.html) to create a custom GUI from scratch for our application. There is a class for each interface of the application so as to ensure minimal coupling between the modules. The classes interact with each other using the information passed on by one class to the other. The entity which distinctly identifies a user is their `userID` and that is what we pass around and access the MySQL Database and extract information corresponding to the specfic `userID`.

# The Components

The directory structure is as follows:

	├── lib
	│   ├── mysql-connector.jar
	│   ├── swingx-action-1.6.7.jar
	│   ├── swingx-autocomplete-1.6.7.jar
	│   ├── swingx-common-1.6.7.jar
	│   ├── swingx-core-1.6.7.jar
	│   ├── swingx-graphics-1.6.7.jar
	│   ├── swingx-painters-1.6.7.jar
	│   └── swingx-plaf-1.6.7.jar
	├── LICENSE
	├── README.md
	├── src
	│   ├── DetailedStatement.java
	│   ├── HomeScreen.java
	│   ├── MiniStatement.java
	│   ├── MysqlConnection.java
	│   └── Register.java

`src` contains the classes which make up the interfaces of the application. `lib` contains the dependencies utilised by the application. Note that the `lib` directory cannot be discarded unless one wants to fine tune this application as per their needs.
