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
