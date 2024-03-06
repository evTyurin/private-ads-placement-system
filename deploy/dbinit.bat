@echo off

mysql --host=localhost --user=root --password=ETmysql21@ < ddl.sql

@echo database installed

mysql --host=localhost --user=root --password=ETmysql21@ < dml.sql

@echo data installed

pause