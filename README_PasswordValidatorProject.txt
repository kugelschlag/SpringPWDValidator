

Password Validator - by David Barber - dbarber@kugelschlag.com

THis file: README_PasswordValidatorProject.txt

High level description
Spring Boot project uses gradle for building/running/testing

Tomcat contaner

Can bring into IntelliJ or run from command line


Unzip
./gradlew clean
./gradlew test

To run
./gradlew bootRun
	- Using port 8088 set in application.properties
	- Point something like Postman at http://localhost:8088/v1/users
	-Post a trivial JSON user object to test different conditions:
		{
			"name":"foo bar",
			"pw": "somepasswrd"
		}

		Errors will return a list of validation conditions that failed

Notes:

I leveraged the org.passay validation library and extended it by creating a custom rule to handle requirement changes ... its the "Fubar" rule.  Where the string "fubar" is either allowed or not.

Custom "@CharterPWValid" annotion created (used on the the simple data object User.java for the password field) - this allows us to apply validation rules to various fields on demand

The "CharterPWValidator" class does the validation, with the parameters being externally configurable via properties file, etc.

Bonus (but not implemented here yet) - would be reasonably trivial to create an external configuration service and be able to update the validation input settings/rules without having to restart this service

Java Docs are in Docs dir

Tests are in usual place src/test/

Thanks for the fun project, took a few hours to complete





