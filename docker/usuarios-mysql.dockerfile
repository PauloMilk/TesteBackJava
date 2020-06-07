FROM mysql
COPY ./sql/usuarios/ /docker-entrypoint-initdb.d/
EXPOSE 6033