-- Add user details and netbanking details

USE `mybank` ;

INSERT INTO `users` (`id`, `user_name`) VALUES (1, 'administrator');
INSERT INTO `users` (`id`, `user_name`) VALUES (2, 'firstuser');
INSERT INTO `users` (`id`, `user_name`) VALUES (3, 'seconduser');

INSERT INTO `netbanking_details` (`id`, `nb_user_name`, `password`, `last_login`, `user_id`) VALUES (1, 'administrator', 'pass', '2021-02-12 12:12:12', 1);

INSERT INTO `netbanking_details` (`id`, `nb_user_name`, `password`, `last_login`, `user_id`) VALUES (2, 'first_user', 'pass', '2021-02-12 12:12:12', 2);

INSERT INTO `accounts` (`id`, `account_no`, `total_balance`, `account_type`, `user_id`) VALUES ('1', '9999', '1000', 'savings', 2);

INSERT INTO `accounts` (`id`, `account_no`, `total_balance`, `account_type`, `user_id`) VALUES ('2', '8888', '2000', 'savings', 3);


