DROP KEYSPACE IF EXISTS accesslogdb;
CREATE KEYSPACE accesslogdb
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE accesslogdb.access_log
(id uuid PRIMARY KEY, ip varchar, time timestamp, method varchar, url varchar,
status int, length bigint, referral varchar, client varchar );