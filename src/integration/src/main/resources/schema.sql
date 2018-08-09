create table tableorder
(
   id varchar(100),
   clientId varchar(100),
   restaurantId varchar(100),
   createdAt varchar(100),
   confirmedAt varchar(100)
);

create table tableclient
(
   id varchar(100),
   clientId varchar(100),
   restaurantId varchar(100),
   createdAt varchar(100),
   confirmedAt varchar(100)
);

create table tableitem
(
   _id varchar(100),
   description varchar(255),
   quantity int,
   price double(100)

);
