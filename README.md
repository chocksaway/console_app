# Welcome to the console application

This application uses gradle (https://gradle.org/) to build, and run it's implementation.  The gradle configuration file is build.gradle

The implementation is unit-tested using JUnit (http://junit.org/)

The single Unit test file is src/test/java/com.chocksaway.HTMLPageWrapperTest

This can be run from any SDK (such as Eclipse or Intellij), or command line.

Github is used for version control.

The implementation use the JSoup (http://jsoup.org/) HTML parser / library to extract documents (consisting of elements - made up of HTML).

The implementation runs as a console (assumption being a command-line application), which parses a parent URL,
extracting title, and unit price.  Each child page (HTML link), is also visited, with description, and page size being extracted.
  
This information is represented by a list of StockItem objects (main/java/com.chocksaway.StockItem). 

This stock list is printed in JSON (using the Google GSon API), using the layout, and total (described in the requirements).

### To install this application

Ensure that git and gradle binaries are installed.

It is important to install a recent version (2.x) of gradle.  Ubuntu by default is quite old.
The following will install a recent version of Gradle  on Ubuntu:

```
$ sudo add-apt-repository ppa:cwchien/gradle

$ sudo apt-get update

$ sudo apt-get install gradle

```

### Clone the public git repository:

    git clone https://github.com/chocksaway/console_app.git
    
### Build the application:

    gradle build


### To run the unit-tests (which runs the console, and displays the JSon output)

    gradle test
    
    
    # gradle test
    :compileJava
    :processResources UP-TO-DATE
    :classes
    :compileTestJava
    :processTestResources UP-TO-DATE
    :testClasses
    :test
    
    com.chocksaway.HTMLPageWrapperTest STANDARD_ERROR
        SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
        SLF4J: Defaulting to no-operation (NOP) logger implementation
        SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
    
    com.chocksaway.HTMLPageWrapperTest > buildStocksItems STANDARD_OUT
        ---------- Welcome to the JSon Console:--------{
          "results": [
            {
              "title": "Sainsbury's Avocado Ripe & Ready XL Loose 300g",
              "size": "41kb",
              "unit_price": 1.50,
              "description": "Extra Large Avocado"
            },
            {
              "title": "Sainsbury's Avocado, Ripe & Ready x2",
              "size": "43kb",
              "unit_price": 1.80,
              "description": "Avocados"
            },
            {
              "title": "Sainsbury's Avocados, Ripe & Ready x4",
              "size": "43kb",
              "unit_price": 3.20,
              "description": "Avocados"
            },
            {
              "title": "Sainsbury's Conference Pears, Ripe & Ready x4 (minimum)",
              "size": "43kb",
              "unit_price": 2.00,
              "description": "Conference"
            },
            {
              "title": "Sainsbury's Kiwi Fruit, Ripe & Ready x4",
              "size": "43kb",
              "unit_price": 1.80,
              "description": "Kiwi"
            },
            {
              "title": "Sainsbury's Mango, Ripe & Ready x2",
              "size": "43kb",
              "unit_price": 2.00,
              "description": "by Sainsbury's Ripe and Ready Mango"
            },
            {
              "title": "Sainsbury's Nectarines, Ripe & Ready x4",
              "size": "42kb",
              "unit_price": 2.00,
              "description": "Class 1 Film - Plastic - not currently recycled Tray - Paper - widely recycled"
            },
            {
              "title": "Sainsbury's Peaches Ripe & Ready x4",
              "size": "43kb",
              "unit_price": 2.00,
              "description": "by Sainsbury's Ripe and Ready Peach"
            },
            {
              "title": "Sainsbury's Pears, Ripe & Ready x4 (minimum)",
              "size": "43kb",
              "unit_price": 2.00,
              "description": "Pear"
            },
            {
              "title": "Sainsbury's Plums Ripe & Ready x5",
    
    ▽
              "size": "43kb",
              "unit_price": 2.50,
              "description": "Plums"
            },
            {
              "title": "Sainsbury's Ripe & Ready Golden Plums x6",
              "size": "43kb",
              "unit_price": 2.50,
              "description": "Plums"
            },
            {
              "title": "Sainsbury's White Flesh Nectarines, Ripe & Ready x4",
              "size": "43kb",
              "unit_price": 2.00,
              "description": "by Sainsbury's Ripe and Ready White Flesh Nectarines"
            }
          ],
          "total": 25.30
        }
        ---------- End of Console Output --------
    
    com.chocksaway.HTMLPageWrapperTest > buildStocksItems PASSED
    
    com.chocksaway.HTMLPageWrapperTest > getMainPageIndividualProductTitleInformation PASSED
    
    com.chocksaway.HTMLPageWrapperTest > getMainPageUnitPrices PASSED
    
    com.chocksaway.HTMLPageWrapperTest > getChildPageProductDescription PASSED
    
    com.chocksaway.HTMLPageWrapperTest > getChildPageLength PASSED
    
    BUILD SUCCESSFUL
    
    Total time: 46.844 secs
 
  
    
### To review (view) the JSon

A console.json file has been created which contains the "console output" (from running with gradle test).



    
    

    
    



 





