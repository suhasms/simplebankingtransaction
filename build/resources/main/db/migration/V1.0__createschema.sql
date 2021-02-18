-- MySQL Script generated by MySQL Workbench
-- Tuesday 16 February 2021 08:51:58 PM IST
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `accounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_no` VARCHAR(16) NOT NULL,
  `total_balance` FLOAT NOT NULL,
  `account_type` ENUM('savings', 'current') NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_accounts_users_user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_accounts_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `from_account_no` VARCHAR(45) NOT NULL,
  `to_account_no` VARCHAR(45) NOT NULL,
  `from_bank` VARCHAR(45) NOT NULL,
  `to_bank` VARCHAR(45) NOT NULL,
  `amount` FLOAT NOT NULL,
  `transaction_type` ENUM('debit', 'credit') NOT NULL,
  `running_balance` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_accounts_id_idx` (`account_id` ASC),
  CONSTRAINT `fk_transactions_accounts_id`
    FOREIGN KEY (`account_id`)
    REFERENCES `accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `personal_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `personal_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(500) NOT NULL,
  `phone_number` VARCHAR(45) NOT NULL,
  `email_id` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_personal_details_users_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_personal_details_users_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netbanking_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netbanking_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nb_user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `last_login` TIMESTAMP NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nb_user_name_UNIQUE` (`nb_user_name` ASC),
  INDEX `fk_netbanking_details_users_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_netbanking_details_users_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `transaction_meta_data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `transaction_meta_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `transaction_id` INT NOT NULL,
  `from_bank_ifsc_code` VARCHAR(45) NOT NULL,
  `from_bank_branch_name` VARCHAR(45) NOT NULL,
  `from_bank_bank_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transaction_meta_data_transaction_id_idx` (`transaction_id` ASC),
  CONSTRAINT `fk_transaction_meta_data_transaction_id`
    FOREIGN KEY (`transaction_id`)
    REFERENCES `transactions` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
