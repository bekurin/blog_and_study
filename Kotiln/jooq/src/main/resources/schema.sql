CREATE TABLE `product`
(
    id       bigint not null primary key,
    name     text not null,
    quantity int  not null
);

CREATE TABLE `order`
(
    id bigint not null primary key
);

CREATE TABLE `order_item`
(
    order_id   bigint not null,
    product_id bigint not null
);
