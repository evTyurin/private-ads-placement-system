USE private_ads_placement_system;

INSERT INTO role (id,name) VALUES
(1,'USER'),
(2,'ADMIN');

INSERT INTO users (id, name, surname, email, password, balance, rating, reset_password_token, created_at) VALUES
(1,'Billy','Bones','11111@gmail.com','$2a$10$vRAnK2AsofPZZC73WLuXO.3v7PK75unGsTeOvaXarDrdVa4k4E.1q',200.00,0.00,null,'2024-03-03 01:02:45'),
(2,'Jim','Tors','22222@gmail.com','$2a$10$vRAnK2AsofPZZC73WLuXO.3v7PK75unGsTeOvaXarDrdVa4k4E.1q',0.00,0.00,null,'2023-09-03 01:02:45'),
(3,'Bob','Jones','evgturin@gmail.com','$2a$10$vRAnK2AsofPZZC73WLuXO.3v7PK75unGsTeOvaXarDrdVa4k4E.1q',350.00,0.00,null,'2022-02-03 01:02:45'),
(4,'Billy','Bones','11111@gmail.com','$2a$10$vRAnK2AsofPZZC73WLuXO.3v7PK75unGsTeOvaXarDrdVa4k4E.1q',200.00,0.00,null,'2024-01-03 01:02:45'),
(5,'Billy','Bones','11111@gmail.com','$2a$10$vRAnK2AsofPZZC73WLuXO.3v7PK75unGsTeOvaXarDrdVa4k4E.1q',200.00,0.00,null,'2022-10-03 01:02:45');

INSERT INTO user_role (user_id, role_id) VALUES
(1,1),
(2,1),
(3,1),
(3,2),
(4,1),
(5,1);

INSERT INTO advertisement (id,seller_id,customer_id,title,description,price,status,created_at,is_paid,paid_till,paid_sum,sold_at,sold_sum) VALUES 
(1,3,1,'table','big used table',100.00,'CLOSED','2022-02-03 01:02:45',0,null,0.00,'2023-02-03 01:02:45',80.00),
(2,3,2,'send','big pack sand for cats',200.00,'CLOSED','2023-09-03 01:02:45',0,null,0.00,'2024-02-03 01:02:45',200),
(3,3,4,'tnt','it is dynamite',1000.00,'CLOSED','2024-02-03 01:02:45',0,null,0,'2023-02-03 01:02:45',1000),
(4,1,5,'desktop','great condition',500.00,'CLOSED','2023-09-03 01:02:45',0,null,0,'2023-02-03 01:02:45',500),
(5,5,2,'moto','flash sale',3000.00,'OPEN','2024-03-03 01:02:45',0,null,0,'2023-02-03 01:02:45',0);

INSERT INTO message (id,sender_id,receiver_id,message,created_at) VALUES
(1,3,1,'Hi!','2023-09-03 01:02:45'),
(2,1,3,'Hi man!','2023-09-04 01:02:45'),
(3,3,1,'How are you?','2023-09-05 01:02:45'),
(4,1,3,'Good!And you?','2023-09-06 01:02:45'),
(5,3,1,'Nice!','2023-09-06 11:02:45');

INSERT INTO seller_evaluation (id,customer_id,advertisement_id,evaluation,created_at) VALUES
(1,1,1,5,'2023-02-03 01:02:45'),
(2,2,2,4,'2024-02-03 01:02:45'),
(3,4,3,3,'2023-02-03 01:02:45'),
(4,5,4,2,'2023-02-03 01:02:45');

INSERT INTO comments (id,user_id,advertisement_id,comment,created_at) VALUES
(1,2,5,'nice ride!','2023-02-03 01:02:45');

