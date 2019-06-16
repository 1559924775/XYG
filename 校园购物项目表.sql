create database xyg;
use xyg;
#1
create table user_login(
 id int primary key auto_increment,
 name nvarchar(30),
 pwd varchar(30),
 tel varchar(30),
 email varchar(30),
 adress nvarchar(100)
)auto_increment=10010;
#2
create table seller_login(
 id int primary key auto_increment,
 name nvarchar(30),
 pwd varchar(30),
 tel varchar(30),
 email varchar(30)
)auto_increment=20020;
select *from seller_login;
#3
create table goods(
 id int primary key auto_increment,
 name nvarchar(30),
 src varchar(30),
 price varchar(30),
 describ varchar(200),
 sellerId int
);
select *from goods;
#4
create table orders(
   orderId int primary key  auto_increment,
   userId int,
   userTel int,
   userMail nvarchar(30),
   totalPrice int,
	pStatus boolean,
    dTime datetime
)auto_increment=30030;
select *from orders;
#5
create table order_item(
	orderId int ,
    foreign key(orderId) references orders(orderId),
    goodsId int,
    sellerId int,
    goodsName nvarchar(30),
    num int,
    price varchar(20)
    
);