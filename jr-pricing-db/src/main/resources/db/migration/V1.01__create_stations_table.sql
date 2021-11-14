CREATE TABLE tbl_stations (
    id varchar(26) NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL,
    created_at timestamp NOT NULL default CURRENT_TIMESTAMP ,
    updated_at timestamp NOT NULL default CURRENT_TIMESTAMP
);