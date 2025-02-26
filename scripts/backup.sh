#!/bin/bash
CONTAINER_NAME="postgres_container"
DB_USER="postgres"
DB_NAME="postgres"
BACKUP_FILE="../src/main/resources/sql/backup/backup.sql"

echo "Creating a backup copy..."

docker exec "$CONTAINER_NAME" pg_dump -U "$DB_USER" -d "$DB_NAME" > "$BACKUP_FILE"

echo "The backup is saved!"
