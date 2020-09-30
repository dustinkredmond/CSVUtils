## CSVUtils

Small, simple library (in pure Java with no dependencies) for working with CSV (or other delimited files).
Written for Java 8, but should work equally well with newer versions of the JDK. 

Below is a description of the various API classes and their functionality, this is a lightweight
library, so no need to sift through tons of classes to find all of the API.

| Class                  | Functionality                                                                     |
| -----------------------| ----------------------------------------------------------------------------------|
| CSVParts               | Easily get data or columns from delimited files                                   | 
| CSVToObjectMapper      | Transform a CSV file into a list of Java Objects                                  |
| IterableCSV            | Easily iterate through a CSV, line by line                                        |
| ObjectDelimitedReport  | Generate delimited reports from Java Objects with an easy-to-use Builder syntax   |
| ObjectToCSVMapper      | Serializes Java Objects into a CSV file or String                                 |

Examples of the API usage are in the [test directory](/src/test/java/com/dustinredmond/csv/test). 
Each API class has a similarly-named class there that demonstrates its usage. 

---

### Download
Simply clone the repository and run `mvn clean package` to build the project from source
using Apache Maven.

Infrequent releases will also be added to GitHub Releases, but for the latest features, I highly
recommend you to build from source. If you want to build for JDKs after JDK8, simply change the 
source and target versions in `pom.xml` then run `mvn clean package`.

---

Feel free to fork the repository, or submit a pull request. 
If you like or use CSVUtils, please star the repo, that lets me know to continue
maintaining and improving it.