# Augury
[![Build Status](https://travis-ci.org/4x0l0tl/augury.svg?branch=master)](https://travis-ci.org/4x0l0tl/augury)
[![GitHub Issues](https://img.shields.io/github/issues/4x0l0tl/augury.svg)](https://github.com/4x0l0tl/augury/issues)

Graph based analytics of source code projects

# Project Layout
## Model
Domain model representing source code repository commits as an interaction graph.
## Core
Core persistance and parsing functionality.
## Plugin-API
Extension point for adding support for further Source Control and Issue Tracking systems.
## Rest
CI integration and Analytics REST endpoints

# Build
`mvn clean package spring-boot:repackage`

# Run Importer
`java -Dloader.path='core/plugins/augury-github-plugin-0.0.2-SNAPSHOT.jar!'
-jar core/target/core-0.0.2-SNAPSHOT.jar --db.type=HTTP --db.url=http://120.0.0.1:7474 --db.username=neo4j --db.password=neo4j --scm.provider=GITHUB --scm.url=4x0l0tl --scm.username="" --scm.password="" --scm.project=augury --scm.branch=master --type=import --dateFormat=dd/MM/yyyy --fromDate=01/12/2017 --toDate=02/12/2017`

_Note: If your plugin packages its own dependencies you need to provide an explicit path to the jar with a ! postfix e.g. (-Dloader.path='plugin1.jar!,plugin2.jar!) in order for them to be added to the spring boot classpath correctly_

## License
Augury is made available under [GPLv3 License](http://www.gnu.org/licenses/gpl-3.0.html)

# Creating a Plugin
## Maven
Set plugin-parent as project parent to inherit required dependencies and plugin configuration
```xml
<parent>
  <groupId>com.augury</groupId>
  <artifactId>plugin-parent</artifactId>
  <version>0.0.2-SNAPSHOT</version>
</parent>
```
## Implementation
```java
@Component //For spring to add implementation to PluginRegistry
@Order(999) //Priority for multiple plugins supporting an specific SCM or IT platform. Lower number, higher priority
public class GithubPlugin implements SourceControlPlugin {
	@Autowired
	Environment env;
	
	//Initiasation logic based on parameters supplied by env
	@Override
	public void initialise() {
		//...
	}

	//Specify lookup string to retrieve this implementation from the PluginRegistry
	@Override
	public boolean supports(String delimiter) {
		return "GITHUB".equalsIgnoreCase(delimiter);
	}
	
	//Implement required functionality
	@Override
	public List<String> getBranchList() {
	}
}
```
## Build & Package
Package the plugin jar in a [MODULE layout](https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/htmlsingle/#build-tool-plugins-gradle-configuration-layouts)

`mvn clean package spring-boot:repackage`
