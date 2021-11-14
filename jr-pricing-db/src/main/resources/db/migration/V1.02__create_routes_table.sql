CREATE TABLE tbl_routes (
    id varchar(26) NOT NULL PRIMARY KEY,
    departure_station_id varchar(26) NOT NULL,
    arrival_station_id varchar(26) NOT NULL,
    distance bigint NOT NULL,
    created_at timestamp NOT NULL default CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL default CURRENT_TIMESTAMP,
    FOREIGN KEY (departure_station_id) REFERENCES tbl_stations(id),
    FOREIGN KEY (arrival_station_id) REFERENCES tbl_stations(id)
);

CREATE UNIQUE INDEX uqx_routes_1 ON tbl_routes(departure_station_id, arrival_station_id);