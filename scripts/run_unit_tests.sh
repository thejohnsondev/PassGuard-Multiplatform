#!/bin/bash

# Change to the directory where the script is located (e.g., 'scripts' folder)
# Then navigate up one level to the project root where gradlew is located.
cd "$(dirname "$0")/.." || exit

# Define the modules to test
modules=(
  ":core:common:test"
  ":core:network:test"
  ":feature:auth:domain:test"
  ":feature:tools:data:test"
  ":feature:tools:domain:test"
  ":feature:vault:domain:test"
  ":feature:settings:domain:test"
)

chmod +x gradlew

echo "Starting Android Unit Tests..."
echo "---------------------------------"

# Initialize total duration
total_duration=0

# Loop through each module and run its tests
for module in "${modules[@]}"; do
  echo "Running tests for module: $module"
  start_time=$(date +%s) # Get start time in seconds

  # Execute the Gradle command
  ./gradlew "$module"

  end_time=$(date +%s) # Get end time in seconds
  duration=$((end_time - start_time)) # Calculate duration

  echo "Tests for $module completed in $duration seconds."
  echo "---------------------------------"

  total_duration=$((total_duration + duration))
done

echo "All specified unit tests completed."
echo "Total execution time: $total_duration seconds."
echo "---------------------------------"

# You can add a check for test failures here if needed
# For example, by checking the exit code of gradlew, though it might
# be more complex to parse detailed results directly in a simple bash script.
# Gradle typically exits with a non-zero code on test failures.
if [ $? -ne 0 ]; then
  echo "WARNING: One or more test runs might have failed. Please check the output above."
fi