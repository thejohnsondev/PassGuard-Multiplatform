#!/bin/bash

chmod +x gradlew

echo "Run run_desktop.sh"
cd ./scripts
sh ./helpers/build_machelper_executable.sh
cd ../
echo "Starting Compose Desktop Application..."
./gradlew composeApp:run