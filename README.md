> docker kill $(docker ps -aq); docker rm $(docker ps -aq); docker-compose up

> pg_dump -d santa -U postgres

> psql -d santa -U postgres

> DROP SCHEMA public CASCADE;CREATE SCHEMA public;
