WITH random_insurances AS (
    SELECT
        c.id AS client_id,  -- Utilisation de 'id' pour les clients
        i.id AS insurance_id, -- Utilisation de 'id' pour les assurances
        ROW_NUMBER() OVER (PARTITION BY c.id ORDER BY RANDOM()) AS rn
    FROM
        client c
    CROSS JOIN
        insurance i
),
filtered_random_insurances AS (
    SELECT
        client_id,
        insurance_id
    FROM
        random_insurances
    WHERE
        rn <= (FLOOR(RANDOM() * 3) + 1) -- attribue entre 1 et 3 assurances
),
non_existing_insurances AS (
    SELECT
        fri.client_id,
        fri.insurance_id
    FROM
        filtered_random_insurances fri
    LEFT JOIN
        subscribes ci
    ON
        fri.client_id = ci.id_client AND fri.insurance_id = ci.id_insurance
    WHERE
        ci.id_client IS NULL  -- Ne sélectionne que les liaisons qui n'existent pas encore
)
INSERT INTO subscribes (id_client, id_insurance)
SELECT
    client_id,
    insurance_id
FROM
    non_existing_insurances;