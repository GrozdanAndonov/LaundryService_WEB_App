drop database if exists laundry_service;
create database laundry_service
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;
use laundry_service;

CREATE TABLE user(
id_user int not null auto_increment primary key,
first_name varchar(50) not null,
last_name varchar(50) not null,
password varchar(50) not null,
email varchar(300) not null,
city varchar(50) not null,
bulstat varchar(10) not null default '',
streetAddress varchar(100),
zipCode int default 0,
telNum varchar(300) default '',
default_language varchar(20) default '',
rating int default 0,
avatar_url varchar(100) default '',
last_login_date DATETIME not null default NOW(),
isAdmin bool default 0,
date_created DATETIME not null default NOW()
);


CREATE TABLE orders(
id_order int not null auto_increment primary key,
date_creation DATETIME not null default NOW(),
date_finished DATETIME,
first_name varchar(50) not null,
last_name varchar(50) not null,
email varchar(50) not null,
city varchar(50) not null,
streetAddress varchar(100),
telNum varchar(15) not null default '',
cost double(16,2) default 0,
note text default '',
discount double(16,2) default 0,
type_of_order bool default 0,
is_accepted bool default 0,
id_user int not null, 
constraint foreign key(id_user) references user(id_user)
on delete cascade on update cascade
);

CREATE TABLE discount(
id_discount INT NOT NULL PRIMARY KEY,
date_started DATETIME default NOW(),
date_ended Date,
value double(16,2) not null
);

CREATE TABLE order_discount(
id_order int not null,
id_discount int not null,
constraint foreign key(id_order) references orders(id_order)
on delete cascade on update cascade,
constraint foreign key(id_discount) references discount(id_discount)
on delete cascade on update cascade,
primary key(id_order, id_discount)
);


CREATE TABLE comments(
id_comment INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
text_comment VARCHAR(500) NOT NULL,
points INT NOT NULL DEFAULT 0,
date_created DATETIME NOT NULL DEFAULT NOW(),
id_user INT NOT NULL,
CONSTRAINT FOREIGN KEY(id_user) REFERENCES user(id_user)
ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE likes(
id_user INT NOT NULL,
id_comment INT NOT NULL,
CONSTRAINT FOREIGN KEY(id_user) REFERENCES user(id_user)
ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT FOREIGN KEY (id_comment) REFERENCES comments(id_comment)
ON DELETE CASCADE ON UPDATE CASCADE,
value INT NOT NULL
);

