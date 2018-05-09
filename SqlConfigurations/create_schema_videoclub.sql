/* DELETE 'videoclub' database*/
DROP SCHEMA IF EXISTS videoclub;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'%';

/* CREATE 'videoclub' DATABASE */
CREATE SCHEMA videoclub;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'%' IDENTIFIED BY 'spq';

GRANT ALL ON videoclub.* TO 'spq'@'%';