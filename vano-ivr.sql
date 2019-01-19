-- --------------------------------------------------------
-- Host:                         10.10.1.234
-- Server version:               5.5.50 - MySQL Community Server (GPL) by Remi
-- Server OS:                    Linux
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for vano_ivr
CREATE DATABASE IF NOT EXISTS `vano_ivr` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `vano_ivr`;

-- Dumping structure for table vano_ivr.auth_module
CREATE TABLE IF NOT EXISTS `auth_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL DEFAULT '',
  `class` varchar(256) DEFAULT NULL,
  `desc` varchar(512) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_module: ~0 rows (approximately)
/*!40000 ALTER TABLE `auth_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_module` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_perm
CREATE TABLE IF NOT EXISTS `auth_perm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `display_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `module_id` bigint(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_perm: ~5 rows (approximately)
/*!40000 ALTER TABLE `auth_perm` DISABLE KEYS */;
INSERT INTO `auth_perm` (`id`, `name`, `display_name`, `description`, `module_id`, `status`) VALUES
	(1, 'CREATE', NULL, NULL, NULL, NULL),
	(2, 'READ', NULL, NULL, NULL, NULL),
	(3, 'UPDATE', NULL, NULL, NULL, NULL),
	(4, 'DELETE', NULL, NULL, NULL, NULL),
	(5, 'EXECUTE', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `auth_perm` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_role
CREATE TABLE IF NOT EXISTS `auth_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `display_name` varchar(256) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  `level` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_role: ~7 rows (approximately)
/*!40000 ALTER TABLE `auth_role` DISABLE KEYS */;
INSERT INTO `auth_role` (`id`, `name`, `display_name`, `description`, `status`, `level`) VALUES
	(1, 'Administrators', 'Administrators', 'Administrators', 0, 6),
	(2, 'HrOfficers', 'HrOfficers', 'Hành chính nhân sự', 1, 5),
	(3, 'CEO', 'CEO', 'Ban Lãnh Đạo', 1, 4),
	(4, 'COO', 'COO', 'Giám đốc điều hành', 1, 3),
	(5, 'DepartmentLeaders', 'DepartmentLeaders', 'Trưởng phòng', 1, 2),
	(7, 'Users', 'Users', 'Nhân viên', 1, 1),
	(8, 'AssignView', 'AssignView', 'AssignView', 1, 7);
/*!40000 ALTER TABLE `auth_role` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_role_perm
CREATE TABLE IF NOT EXISTS `auth_role_perm` (
  `role_id` bigint(20) NOT NULL,
  `perm_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`perm_id`),
  KEY `FK_RP_PERM` (`perm_id`),
  CONSTRAINT `FK_RP_PERM` FOREIGN KEY (`perm_id`) REFERENCES `auth_perm` (`id`),
  CONSTRAINT `FK_RP_ROLE` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_role_perm: ~0 rows (approximately)
/*!40000 ALTER TABLE `auth_role_perm` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_role_perm` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_user
CREATE TABLE IF NOT EXISTS `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(15) DEFAULT NULL,
  `middle_name` varchar(15) DEFAULT NULL,
  `last_name` varchar(15) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `is_verified` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `user_type` tinyint(4) DEFAULT NULL COMMENT '0 -  system user; 1 - ; 2- affiliate; 4-sale staff',
  `staff_code` varchar(45) DEFAULT NULL,
  `birth_day` date DEFAULT NULL,
  `identify_card` varchar(255) DEFAULT NULL,
  `date_of_issue` date DEFAULT NULL,
  `place_of_issue` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `current_address` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `skill` varchar(255) DEFAULT NULL,
  `certificate` varchar(255) DEFAULT NULL,
  `language_proficiency` varchar(255) DEFAULT NULL,
  `contract_code` varchar(255) DEFAULT NULL,
  `contract` varchar(255) DEFAULT NULL,
  `contract_day` varchar(255) DEFAULT NULL,
  `sign_date` date DEFAULT NULL,
  `from_date` date DEFAULT NULL,
  `to_date` date DEFAULT NULL,
  `code_timekeeping` varchar(255) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `marital_status_id` int(11) DEFAULT NULL,
  `nation_id` int(11) DEFAULT NULL,
  `academic_id` int(11) DEFAULT NULL,
  `contract_type` int(11) DEFAULT NULL,
  `contract_timeout` int(11) DEFAULT NULL,
  `salary` varchar(100) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL,
  `emp_position_id` int(11) DEFAULT NULL,
  `insurrance` varchar(100) DEFAULT NULL,
  `support` varchar(255) DEFAULT NULL,
  `contract_type_old` int(11) DEFAULT NULL,
  `contract_timeout_old` int(11) DEFAULT NULL,
  `salary_old` varchar(100) DEFAULT NULL,
  `status_id_old` int(11) DEFAULT NULL,
  `emp_position_id_old` int(11) DEFAULT NULL,
  `resident_address` varchar(255) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  `blood_group` int(11) DEFAULT NULL,
  `size_clothes` int(11) DEFAULT NULL,
  `wedding_day` date DEFAULT NULL,
  `day_of_birth` int(11) DEFAULT NULL,
  `month_of_birth` int(11) DEFAULT NULL,
  `training_unit` varchar(255) DEFAULT NULL,
  `business` varchar(255) DEFAULT NULL,
  `home_town` varchar(255) DEFAULT NULL,
  `relative_status` int(11) DEFAULT NULL,
  `join_company_date` date DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `status_activity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4447 DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_user: ~1 rows (approximately)
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` (`id`, `user_name`, `first_name`, `middle_name`, `last_name`, `full_name`, `email`, `gender`, `salt`, `password`, `created_date`, `modified_date`, `is_verified`, `status`, `user_type`, `staff_code`, `birth_day`, `identify_card`, `date_of_issue`, `place_of_issue`, `tel`, `current_address`, `experience`, `skill`, `certificate`, `language_proficiency`, `contract_code`, `contract`, `contract_day`, `sign_date`, `from_date`, `to_date`, `code_timekeeping`, `department_id`, `marital_status_id`, `nation_id`, `academic_id`, `contract_type`, `contract_timeout`, `salary`, `status_id`, `emp_position_id`, `insurrance`, `support`, `contract_type_old`, `contract_timeout_old`, `salary_old`, `status_id_old`, `emp_position_id_old`, `resident_address`, `height`, `weight`, `blood_group`, `size_clothes`, `wedding_day`, `day_of_birth`, `month_of_birth`, `training_unit`, `business`, `home_town`, `relative_status`, `join_company_date`, `company_id`, `status_activity`) VALUES
	(1, 'admin', 'Admin', '', '', 'Admin  ', 'admin@yo.com', '1', '5876695f8e4e1811', '$2a$10$1XV3cAK00ZiyT5f4dxQ1keyMSBme2NRIzLs4W2jIlk3Cir7jeK2aC', '2018-07-17 15:05:55', NULL, 1, 0, 1, 'admin', '2018-07-14', NULL, '2018-07-14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-07-14', '2018-07-14', '2018-07-14', NULL, 358, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-07-14', NULL, 7, NULL, NULL, NULL, NULL, '2015-12-10', 0, 0);
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_usermeta
CREATE TABLE IF NOT EXISTS `auth_usermeta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `meta_key` varchar(255) DEFAULT NULL,
  `meta_value` longtext,
  PRIMARY KEY (`id`),
  KEY `FK_UM_USER_idx` (`user_id`),
  CONSTRAINT `FK_UM_USER` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_usermeta: ~0 rows (approximately)
/*!40000 ALTER TABLE `auth_usermeta` DISABLE KEYS */;
/*!40000 ALTER TABLE `auth_usermeta` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_user_company
CREATE TABLE IF NOT EXISTS `auth_user_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_user_company: ~4 rows (approximately)
/*!40000 ALTER TABLE `auth_user_company` DISABLE KEYS */;
INSERT INTO `auth_user_company` (`id`, `user_id`, `company_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 1, 4);
/*!40000 ALTER TABLE `auth_user_company` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.auth_user_role
CREATE TABLE IF NOT EXISTS `auth_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_UR_ROLE_idx` (`role_id`),
  CONSTRAINT `FK_ROLE_UR` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ROLE_USER` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.auth_user_role: ~1 rows (approximately)
/*!40000 ALTER TABLE `auth_user_role` DISABLE KEYS */;
INSERT INTO `auth_user_role` (`user_id`, `role_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `auth_user_role` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.core_mo_sms
CREATE TABLE IF NOT EXISTS `core_mo_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'So thue bao',
  `short_code` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Dau so dich vu',
  `message` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Noi dung tin nhan',
  `command` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Ma tin nhan theo noi dung',
  `channel` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Ten dich vu',
  `created_date` date DEFAULT NULL COMMENT 'Ngay tao',
  `created_time` timestamp NULL DEFAULT NULL COMMENT 'Ngay tao',
  `sms_id` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Ngay tao dang String yyyyMMddHHmmss',
  `process_status` int(1) DEFAULT '1' COMMENT 'Trang thai xu ly trong tien trinh',
  `status` int(1) DEFAULT '1' COMMENT 'Trang thai',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Dumping data for table vano_ivr.core_mo_sms: ~1 rows (approximately)
/*!40000 ALTER TABLE `core_mo_sms` DISABLE KEYS */;
INSERT INTO `core_mo_sms` (`id`, `msisdn`, `short_code`, `message`, `command`, `channel`, `created_date`, `created_time`, `sms_id`, `process_status`, `status`) VALUES
	(1, '934538590', '5555', 'DK TT', 'REG', 'Dich vu A', '2018-12-25', '2018-12-25 12:05:55', '20181225120555', 2, 1);
/*!40000 ALTER TABLE `core_mo_sms` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.core_subs_blacklist
CREATE TABLE IF NOT EXISTS `core_subs_blacklist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(50) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Dumping data for table vano_ivr.core_subs_blacklist: ~0 rows (approximately)
/*!40000 ALTER TABLE `core_subs_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `core_subs_blacklist` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.core_subs_notify
CREATE TABLE IF NOT EXISTS `core_subs_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(50) NOT NULL,
  `created_date` date NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='Bang luu thong tin cac thue bao nhan tin nhan thong bao khi he thong loi';

-- Dumping data for table vano_ivr.core_subs_notify: ~1 rows (approximately)
/*!40000 ALTER TABLE `core_subs_notify` DISABLE KEYS */;
INSERT INTO `core_subs_notify` (`id`, `msisdn`, `created_date`, `created_time`, `status`) VALUES
	(1, '934538590', '2018-12-25', '2018-12-25 15:50:35', 1);
/*!40000 ALTER TABLE `core_subs_notify` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.core_subs_whitelist
CREATE TABLE IF NOT EXISTS `core_subs_whitelist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msisdn` varchar(50) DEFAULT NULL,
  `created_date` date DEFAULT NULL,
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table vano_ivr.core_subs_whitelist: ~1 rows (approximately)
/*!40000 ALTER TABLE `core_subs_whitelist` DISABLE KEYS */;
INSERT INTO `core_subs_whitelist` (`id`, `msisdn`, `created_date`, `created_time`, `status`) VALUES
	(1, '934538590', '2018-12-25', '2018-12-25 15:50:51', 1);
/*!40000 ALTER TABLE `core_subs_whitelist` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.core_vas_package
CREATE TABLE IF NOT EXISTS `core_vas_package` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `short_code` varchar(10) DEFAULT NULL,
  `vas_package_code` varchar(50) NOT NULL COMMENT 'Ma dich vu',
  `name` varchar(255) NOT NULL COMMENT 'Ten dich vu',
  `description` varchar(2000) NOT NULL COMMENT 'Mo ta ve goi dich vu',
  `price` double NOT NULL DEFAULT '0' COMMENT 'Gia goi cuoc dich vu',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT 'Loai thoi gian cua goi dich vu: 1-Xac dinh thoi gian;2-Khong xac dinh thoi gian',
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` timestamp NULL DEFAULT NULL,
  `duration` int(10) NOT NULL DEFAULT '0' COMMENT 'Chu ky tinh theo ngay: duration=10 - Goi dich vu 10 ngay',
  `status` int(1) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_modified_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.core_vas_package: ~0 rows (approximately)
/*!40000 ALTER TABLE `core_vas_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `core_vas_package` ENABLE KEYS */;

-- Dumping structure for table vano_ivr.sys_param
CREATE TABLE IF NOT EXISTS `sys_param` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `_key` varchar(128) NOT NULL DEFAULT '',
  `_value` text,
  `_type` varchar(64) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `KEY_UNIQ` (`_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table vano_ivr.sys_param: ~1 rows (approximately)
/*!40000 ALTER TABLE `sys_param` DISABLE KEYS */;
INSERT INTO `sys_param` (`id`, `_key`, `_value`, `_type`, `created_time`, `modified_time`, `status`) VALUES
	(1, 'REG', 'Nội dung', '5555', '2018-12-25 15:33:13', '2018-12-25 15:33:14', 1);
/*!40000 ALTER TABLE `sys_param` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
