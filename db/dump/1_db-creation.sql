DROP TABLE if exists public.account CASCADE;

CREATE TABLE client (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  -- UUID 
	first_name VARCHAR(50)  NOT NULL,
   	last_name VARCHAR(50)  NOT NULL,
   	email VARCHAR(80)  NOT NULL,
   	birth_date DATE NOT NULL
);

CREATE TABLE account (
	id SERIAL PRIMARY KEY,
	-- first_name VARCHAR(100),
	-- last_name VARCHAR(50),
	-- email VARCHAR(50),
	-- birthday date,
	creation_time timestamp,
	balance bigint,
	id_client UUID NOT NULL,
	FOREIGN KEY(id_client) REFERENCES Client(id)
);

CREATE TYPE new_insurance_enum AS ENUM (  -- BIEN METTRE EN MAJ POUR L'ENUM DANS LA BDD
    'HOME_INS', 
    'HEALTH_INS', 
    'LIFE_INS', 
    'SCHOOL_INS', 
    'PERSONAL_CIVIL_LIABILITY', 
    'PROFESSIONAL_CIVIL_LIABILITY'
);

CREATE TABLE insurance(
	id SERIAL PRIMARY KEY,
	insurance_type insurance_enum NOT NULL
);

CREATE TABLE subscribes(
   id_client UUID,
   id_insurance INTEGER,
   PRIMARY KEY(id_insurance, id_client),
   FOREIGN KEY(id_insurance) REFERENCES insurance(id),
   FOREIGN KEY(id_client) REFERENCES client(id)
);


