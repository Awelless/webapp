create table user (
    id bigint not null auto_increment,
    username varchar(63) not null,
    password varchar(63) not null,
    role enum('ADMIN', 'USER') not null,

    primary key (id)
);

create table room (
    id bigint not null auto_increment,
    number_of_beds int not null,
    cost int not null,
    rating int not null,

    primary key (id)
);

create table room_reservation (
    id bigint not null auto_increment,
    user_id bigint not null,
    room_id bigint,
    check_in date not null,
    check_out date not null,
    number_of_beds int not null,
    cost bigint,
    rating int not null,
    status enum('PENDING', 'REJECTED', 'APPROVED', 'PAID', 'CANCELED') not null,

    primary key (id),
    foreign key (user_id) references user (id),
    foreign key (room_id) references room (id)
);

create table payment (
    id bigint not null auto_increment,
    room_reservation_id bigint not null,

    primary key (id),
    foreign key (room_reservation_id) references room_reservation (id)
);
