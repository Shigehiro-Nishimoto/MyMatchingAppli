/*会員様マスター*/

CREATE TABLE IF NOT EXISTS members(
id INT PRIMARY KEY,
name VARCHAR(30),
sex BOOLEAN,
birthday DATE,
mailaddress VARCHAR(30),
password VARCHAR(100)
);