#!/bin/bash

chmod +x gradlew

echo "Run build_machelper_executable.sh"
if [ ! -f ../composeApp/src/desktopMain/resources/PassGuardMacOSBiometricCheck.kexe ]; then
  echo "No PassGuardMacOSBiometricCheck.kexe found, building it now..."
  cd ../
  ./gradlew :utils:machelper:linkReleaseExecutableMacosArm64
  cp ./utils/machelper/build/bin/macosArm64/releaseExecutable/PassGuardMacOSBiometricCheck.kexe ./composeApp/src/desktopMain/resources/
else
  echo "PassGuardMacOSBiometricCheck.kexe already exists."
fi