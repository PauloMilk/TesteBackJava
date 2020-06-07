FROM mysql
COPY ./sql/usuarios/init.sql /var/data/
WORKDIR /var/data
RUN /etc/init.d/mysql start
RUN mysql -u root -p${MYSQL_ROOT_PASSWORD} -D lba < init.sql
EXPOSE 6033