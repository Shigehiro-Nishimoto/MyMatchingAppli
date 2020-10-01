/*登録者マスター*/

CREATE TABLE IF NOT EXISTS members(
id INT PRIMARY KEY,
name VARCHAR(30),
sex BOOLEAN,
birthday DATE,
mailaddress VARCHAR(30),
password VARCHAR(100),
role VARCHAR(20)
);

/*マッチングトランザクション*/

CREATE TABLE IF NOT EXISTS matchings(
matchingid INT PRIMARY KEY,
maleid INT,
femaleid INT,
state  INT
);

/*メッセージトランザクション*/

CREATE TABLE IF NOT EXISTS message(
matchingid INT,
whospost INT,
number INT,
messagecontent VARCHAR(100)
);

/*ドノメッセージガメンナノカ*/

CREATE TABLE IF NOT EXISTS matchingaite(
matchingid INT
);