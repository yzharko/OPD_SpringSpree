#!/bin/bash
set -e

CONTAINER_NAME="postgres_container"
DB_USER="postgres"
DB_NAME="postgres"
BACKUP_BEFORE_RESET="../src/main/resources/sql/backup/backup_before_reset.sql"
# You can view the volume name using the "docker volume ls" command.
PG_VOLUME="opd_springspree_pg_data"

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
