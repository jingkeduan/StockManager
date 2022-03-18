drop table if exists products CASCADE;
create table products (id bigint AUTO_INCREMENT, category varchar(255), name varchar(255), price double not null, quantity integer not null, primary key (id));