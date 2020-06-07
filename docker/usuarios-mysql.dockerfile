FROM mysql
COPY ./sql/usuarios/init.sql /docker-entrypoint-initdb.d/
EXPOSE 6033