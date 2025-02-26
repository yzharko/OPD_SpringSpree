#!/bin/bash
set -e

echo "Select configuration:"
echo "1 - Main postgres container"
echo "2 - Testing postgres container"
read -p "Enter your choice (1 or 2): " choice

case $choice in
    1)
        CONTAINER_NAME="postgres_container"
        DB_USER="postgres"
        DB_NAME="postgres"
        PG_VOLUME="opd_springspree_pg_data"
        ;;
    2)
        CONTAINER_NAME="postgres_testing_container"
        DB_USER="postgresTest"
        DB_NAME="postgresTest"
        PG_VOLUME="opd_springspree_pg_data2"
        ;;
    *)
        echo "Invalid choice! Please select 1 or 2."
        exit 1
        ;;
esac

BACKUP_BEFORE_RESET="../src/main/resources/sql/backup/backup_before_reset.sql"

echo "Using configuration:"
echo "Container: $CONTAINER_NAME"
echo "User: $DB_USER"
echo "DB: $DB_NAME"
echo "Volume: $PG_VOLUME"

echo "Creating a backup before reset..."
docker exec "$CONTAINER_NAME" pg_dump -U "$DB_USER" -d "$DB_NAME" > "$BACKUP_BEFORE_RESET"

if [ $? -ne 0 ]; then
    echo "Error creating backup! Operation aborted."
    exit 1
fi

echo "Backup saved as $BACKUP_BEFORE_RESET."

echo "Stopping the container..."
docker-compose down

echo "Removing old data..."
docker volume rm "$PG_VOLUME"

echo "Restarting the container..."
docker-compose up -d

echo "Database reset complete!"
