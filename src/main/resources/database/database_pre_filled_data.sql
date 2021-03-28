insert into user (id, username, password, role) values
(1, 'admin', md5('admin'), 'ADMIN'),
(2, 'user',  md5('user'),  'USER');

insert into room (id, number_of_beds, cost, rating) values
(1, 1, 10, 3),
(2, 2, 18, 3),
(3, 2, 18, 3),
(4, 1, 15, 4),
(5, 1, 15, 4),
(6, 2, 26, 4),
(7, 2, 26, 4),
(8, 2, 36, 5),
(9, 2, 36, 5);

insert into room_reservation (id, user_id, room_id, check_in, check_out, number_of_beds, cost, rating, status) values
(1, 2, 2, '20210303', '20210310', 2, 126, 3, 'APPROVED');
