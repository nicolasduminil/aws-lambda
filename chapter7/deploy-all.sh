#!/bin/bash
cd ../chapter6
./deploy.sh
cd ../chapter5
mvn -DskipTests clean install
./deploy.sh
cd ../chapter7
./deploy.sh
cd ../chapter5
./start.sh