#!/bin/bash
# Simple development script with auto-restart
# Watches source files and restarts Spring Boot on changes

cd "$(dirname "$0")"

echo "🚀 Starting Spring Boot..."
echo "📁 Watching src/ for changes..."
echo ""

# Function to start Spring Boot
start_spring_boot() {
    ./mvnw spring-boot:run &
    SPRING_PID=$!
}

# Start Spring Boot initially
start_spring_boot

# Cleanup on exit
cleanup() {
    echo ""
    echo "🛑 Stopping Spring Boot..."
    kill $SPRING_PID 2>/dev/null
    exit
}
trap cleanup SIGINT SIGTERM

# Watch and restart loop
LAST_CHECK=$(date +%s)
while true; do
    sleep 0.5

    # Check if any source files changed
    if find src/ -type f \( -name "*.java" -o -name "*.properties" -o -name "*.yml" -o -name "*.yaml" \) -newermt "@$LAST_CHECK" 2>/dev/null | grep -q .; then
        echo "🔄 Changes detected, restarting Spring Boot..."
        kill $SPRING_PID 2>/dev/null
        start_spring_boot
        LAST_CHECK=$(date +%s)
    fi
done
