#!/bin/bash
set -euo pipefail

chmod +x gradlew

CONFIG_FILE="appconfig.properties"
KEY="$1"
VALUE="$2"

if [ ! -f "$CONFIG_FILE" ]; then
  echo "Error: $CONFIG_FILE not found!"
  exit 1
fi

if grep -q "^$KEY=" "$CONFIG_FILE"; then
  sed -i.bak "s|^$KEY=.*|$KEY=$VALUE|" "$CONFIG_FILE"
else
  echo "$KEY=$VALUE" >> "$CONFIG_FILE"
fi

echo "Updated $KEY=$VALUE in $CONFIG_FILE"
