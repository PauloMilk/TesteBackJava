FROM mysql
COPY ./sql/gastos/init.sql /docker-entrypoint-initdb.d/
EXPOSE 6034