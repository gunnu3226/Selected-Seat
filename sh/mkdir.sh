if [ ! -d "./infrastructure/persistence-database/src/main/resources/" ]; then
    mkdir -p "./infrastructure/persistence-database/src/main/resources/"
fi


if [ ! -d "./infrastructure/persistence-redis-adapter/src/main/resources/" ]; then
    mkdir -p "./infrastructure/persistence-redis-adapter/src/main/resources/"
fi

if [ ! -d "./infrastructure/persistence-elasticsearch/src/main/resources/" ]; then
    mkdir -p "./infrastructure/persistence-elasticsearch/src/main/resources/"
fi

if [ ! -d "./queue/src/main/resources" ]; then
    mkdir -p "./queue/src/main/resources/"
fi

if [ ! -d "./batch/src/main/resources" ]; then
    mkdir -p "./batch/src/main/resources/"
fi
