#!/bin/bash

chmod +x gradlew

echo "Run build_dmg_executable.sh"
cd ./scripts
sh ./helpers/build_machelper_executable.sh
cd ../
echo "Updating the config file..."
sh ./scripts/helpers/update_appconfig.sh config.app_type PROD
echo "Generating BuildKonfig for PROD configuration..."
./gradlew generateBuildKonfig
echo "Starting DMG packaging for Compose Desktop Application..."
./gradlew composeApp:packageDmg
mkdir -p composeApp/release
cp ./composeApp/build/compose/binaries/main/dmg/*.dmg composeApp/release
echo "DMG file copied to composeApp/release folder."