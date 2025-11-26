create table if not exists customer (
  id serial primary key,
  last_name text not null,
  first_name text not null,
  middle_name text default '',
  address text not null,
  phone text default '',
  email text not null unique
);

create table if not exists product (
  id serial primary key,
  code text not null unique,
  name text not null,
  price numeric(12,2) not null check (price >= 0),
  weight_kg numeric(12,3) not null default 0 check (weight_kg >= 0),
  length_cm int not null default 0,
  width_cm int not null default 0,
  height_cm int not null default 0,
  description text not null default ''
);

create table if not exists cart (
  id serial primary key,
  customer_id int not null unique references customer(id) on delete cascade
);

create table if not exists cart_item (
  id serial primary key,
  cart_id int not null references cart(id) on delete cascade,
  product_id int not null references product(id),
  quantity int not null check (quantity > 0),
  unique (cart_id, product_id)
);

create table if not exists "order" (
  id serial primary key,
  customer_id int not null references customer(id),
  order_date timestamp not null unique default current_timestamp,
  delivery_cost numeric(12,2) not null check (delivery_cost >= 0),
  payment_method text not null check (payment_method in ('CASH','CARD','ONLINE')),
  status text not null check (status in ('NEW','PAID','SHIPPED','DELIVERED','CANCELED'))
);

create table if not exists order_item (
  id serial primary key,
  order_id int not null references "order"(id) on delete cascade,
  product_id int not null references product(id),
  product_name_snapshot text not null,
  unit_price numeric(12,2) not null check (unit_price >= 0),
  quantity int not null check (quantity > 0)
);
