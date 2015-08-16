# Welcome to the console application

## This application use gradle to build, and run it's implementation.  The gradle configuration file is build.gradle

### The implementation is unit-tested using JUnit.  
The single Unit test file is src/test/java/com.chocksaway.HTMLPageWrapperTest

This can be run from any SDK (such as Eclipse or Intellij).

The implementation use the JSoup (http://jsoup.org/) HTML parser / library to extract documents (consisting of elements - made up of HTML).

The implementation runs as a console (assumption being a command-line application), which parses a parent URL,
extracting title, and unit price.  Each child page (HTML link), is also visited, with description, and page size being extracted.
  
This information is represented by a list of objects (main/java/com.chocksaway.StockItem). 

This stock list is printed in JSON (using the Google GSon API), using the layout, and total (described in the requirements).

### To install this application

Ensure that git and gradle binaries are installed.

It is important to install a recent version of gradle.  Ubuntu by default is quite old.
The following will install a recent version on Ubuntu:

```
$ sudo add-apt-repository ppa:cwchien/gradle

$ sudo apt-get update

$ sudo apt-get install gradle

```

### Clone the git repository:

    git clone https://github.com/chocksaway/console_app.git
    
### Build the application:

    gradle build


### To run the unit-tests (which displays the JSon output)

    gradle test
    
    
### To run repeatedly

    gradle clean
    
Gradle does not always out 



    
    

    
    



 





