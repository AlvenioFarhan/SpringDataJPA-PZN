create database spring_data_jpa;

use spring_data_jpa;

create table categories(
id bigint not null auto_increment,
name varchar(100) not null,
primary key (id)
)engine InnoDB;

select * from categories;

CREATE TABLE products
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    price       BIGINT       NOT NULL,
    category_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY fk_products_categories (category_id) REFERENCES categories (id)
) ENGINE InnoDB;

select * from products;

ALTER TABLE categories
    ADD COLUMN created_date TIMESTAMP null;

ALTER TABLE categories
    ADD COLUMN last_modified_date TIMESTAMP null;

SELECT * FROM categories;