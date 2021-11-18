CREATE TABLE tbl_basic_surcharges (
    route_id varchar(26) NOT NULL PRIMARY KEY,
    amount integer NOT NULL,
    created_at timestamp NOT NULL default CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL default CURRENT_TIMESTAMP,
    FOREIGN KEY (route_id) REFERENCES tbl_routes(id)
);