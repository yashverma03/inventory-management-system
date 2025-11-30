#!/bin/bash
# Simple development script with hot reload
# Watches source files and auto-compiles

cd "$(dirname "$0")"

echo "🚀 Starting Spring Boot with hot reload..."
echo "📁 Watching src/ for changes..."
echo ""

# Start Spring Boot in background
./mvnw spring-boot:run &
SPRING_PID=$!

# Cleanup on exit
cleanup() {
    echo ""
    echo "🛑 Stopping Spring Boot..."
    kill $SPRING_PID 2>/dev/null
    exit
}
trap cleanup SIGINT SIGTERM

# Watch and compile loop
LAST_CHECK=$(date +%s)
while true; do
    sleep 2

    # Check if any source files changed
    if find src/ -type f \( -name "*.java" -o -name "*.properties" -o -name "*.yml" -o -name "*.yaml" \) -newermt "@$LAST_CHECK" 2>/dev/null | grep -q .; then
        echo "🔄 Changes detected, compiling..."
        ./mvnw compile -q
        LAST_CHECK=$(date +%s)
    fi
done
