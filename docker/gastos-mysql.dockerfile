FROM mysql
COPY ./sql/gastos/ /docker-entrypoint-initdb.d/
EXPOSE 6037