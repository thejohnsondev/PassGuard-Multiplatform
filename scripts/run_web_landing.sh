#!/bin/bash
set -e

echo "Running run_web_landing.sh"

chmod +x gradlew

echo "Stopping existing 'serve' instances..."
pkill -f "serve" || true

echo "Building landing page for Web (WASM + JS)..."
./gradlew :landing:wasmJsBrowserDistribution --no-daemon

if ! command -v serve &> /dev/null
then
    echo "Installing 'serve' (first time only)..."
    npm install -g serve
fi

OUTPUT_DIR="landing/build/dist/wasmJs/productionExecutable"

echo "Starting local server for $OUTPUT_DIR"
serve -s "$OUTPUT_DIR" -l 3000 &

sleep 2

if [[ "$OSTYPE" == "darwin"* ]]; then
  open "http://localhost:3000"
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  xdg-open "http://localhost:3000"
else
  echo "Please open http://localhost:3000 manually (unsupported OS: $OSTYPE)"
fi
