create table if not exists faculty (
    id integer not null auto_increment,
    name varchar(60) not null,
    phone varchar(30),
    email varchar(30),
    deansAddress varchar(60),
    studentCount integer unsigned not null,
    description varchar(255),
    primary key (id),
    unique (name)
);