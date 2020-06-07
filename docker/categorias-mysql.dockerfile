FROM mysql
COPY ./sql/categorias/init.sql /docker-entrypoint-initdb.d/
EXPOSE 6035