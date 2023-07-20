/*
 Navicat MySQL Data Transfer

 Source Server         : my
 Source Server Type    : MySQL
 Source Server Version : 50742
 Source Host           : localhost:3306
 Source Schema         : doctor-appointment

 Target Server Type    : MySQL
 Target Server Version : 50742
 File Encoding         : 65001

 Date: 15/07/2023 13:07:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` bigint(20) NOT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `deleted_date_time` datetime(6) NULL DEFAULT NULL,
  `finish_date_time` datetime(6) NULL DEFAULT NULL,
  `register_date_time` datetime(6) NULL DEFAULT NULL,
  `start_date_time` datetime(6) NULL DEFAULT NULL,
  `taken_date_time` datetime(6) NULL DEFAULT NULL,
  `uuid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `doctor_id` bigint(20) NOT NULL,
  `patient_id` bigint(20) NULL DEFAULT NULL,
  `version` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK3h2cfsdd219kuw8ux2g55vtw9`(`doctor_id`) USING BTREE,
  INDEX `FKdhn3l72gekxpxmvqlxh7cp1k3`(`patient_id`) USING BTREE,
  INDEX `IDXfjakfcbb99goq2ty08uwa00mx`(`uuid`) USING BTREE,
  CONSTRAINT `FK3h2cfsdd219kuw8ux2g55vtw9` FOREIGN KEY (`doctor_id`) REFERENCES `com_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKdhn3l72gekxpxmvqlxh7cp1k3` FOREIGN KEY (`patient_id`) REFERENCES `com_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (82, b'0', NULL, '2023-07-10 01:00:34.000000', '2023-07-11 23:49:36.451668', '2023-07-10 00:30:34.000000', NULL, 'e1583f2d-e644-4333-bf7a-b148a094468b', 5, NULL, 0);
INSERT INTO `appointment` VALUES (83, b'0', NULL, '2023-07-10 01:30:34.000000', '2023-07-11 23:49:36.459648', '2023-07-10 01:00:34.000000', '2023-07-11 03:30:34.000000', '08409ce9-a0ef-4fc9-98b5-7e12891ad0e8', 5, 80, 0);
INSERT INTO `appointment` VALUES (84, b'0', NULL, '2023-07-11 23:52:23.962754', '2023-07-10 23:52:23.000000', '2023-07-11 02:00:34.000000', NULL, 'fb288e50-4da5-4b18-8204-9b5b78297aab', 67, NULL, 0);
INSERT INTO `appointment` VALUES (85, b'1', NULL, '2023-07-11 23:52:23.962754', '2023-07-11 23:52:23.968750', '2023-07-11 02:30:34.000000', NULL, 'e7f4a87d-bcf6-4e38-a3ed-6e672ae9643a', 67, NULL, 2);
INSERT INTO `appointment` VALUES (86, b'0', NULL, '2023-07-11 23:52:23.962754', '2023-07-12 23:52:23.000000', '2023-07-11 03:00:34.000000', '2023-07-15 12:01:49.374779', '43d5146e-9ced-45e1-b0a1-f9308b4a3708', 67, 80, 4);
INSERT INTO `appointment` VALUES (87, b'0', NULL, '2023-07-10 23:52:23.962754', '2023-07-13 02:33:46.000000', '2023-07-10 14:00:34.000000', '2023-07-14 21:57:11.080525', '7a75be0c-73ed-40e8-b248-f80c3b48fc63', 67, 89, 22);
INSERT INTO `appointment` VALUES (120, b'0', NULL, '2023-07-10 10:30:00.000000', '2023-07-15 13:46:37.530328', '2023-07-10 10:00:00.000000', NULL, '282d69ef-cf99-4acd-bcd3-c4e7f00581e9', 108, NULL, 0);
INSERT INTO `appointment` VALUES (121, b'0', NULL, '2023-07-10 11:00:00.000000', '2023-07-15 13:46:37.613281', '2023-07-10 10:30:00.000000', NULL, '48848f98-23f4-49b2-9364-0dc206905fec', 108, NULL, 0);
INSERT INTO `appointment` VALUES (122, b'0', NULL, '2023-07-10 11:30:00.000000', '2023-07-15 13:46:37.619324', '2023-07-10 11:00:00.000000', '2023-07-15 13:53:20.435176', '42ae4d17-afbe-4194-879f-3ecf68418769', 108, 69, 1);
INSERT INTO `appointment` VALUES (123, b'0', NULL, '2023-07-10 12:00:00.000000', '2023-07-15 13:46:37.629317', '2023-07-10 11:30:00.000000', NULL, 'd7273d9c-5698-43d8-9734-de079bee8f2c', 108, NULL, 0);
INSERT INTO `appointment` VALUES (124, b'0', NULL, '2023-07-10 12:30:00.000000', '2023-07-15 13:46:37.637315', '2023-07-10 12:00:00.000000', '2023-07-15 13:51:52.911580', '350eb679-7fd5-4e34-9a74-e18b858df651', 108, 69, 1);
INSERT INTO `appointment` VALUES (125, b'0', NULL, '2023-07-10 13:00:00.000000', '2023-07-15 13:46:37.646307', '2023-07-10 12:30:00.000000', '2023-07-15 13:50:46.542155', '82a5fc6a-8073-4431-ae59-23d8be064feb', 108, 80, 1);

-- ----------------------------
-- Table structure for com_user
-- ----------------------------
DROP TABLE IF EXISTS `com_user`;
CREATE TABLE `com_user`  (
  `user_type` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `family` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uuid` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UKku0g6dg0jrsdd63vy86jevj2p`(`uuid`) USING BTREE,
  UNIQUE INDEX `UKqim1nc1mrl4fbq1vgpe4bd99u`(`user_type`, `mobile`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of com_user
-- ----------------------------
INSERT INTO `com_user` VALUES (1, 5, 'Mohammadi', '091212345645', 'Ali', '66fa0729-a086-40cc-9575-49e833552d06');
INSERT INTO `com_user` VALUES (1, 67, 'Tehrani', '093512345645', 'Reza', '3cbbca2c-19d5-4b90-a740-0721d8656ce6');
INSERT INTO `com_user` VALUES (2, 69, 'Azari', '090198765431', 'Nahid', '960d8253-8c56-4218-8178-0e47c352a447');
INSERT INTO `com_user` VALUES (2, 80, 'Nasiri', '090198765432', 'Zahra', '57772bc8-38ef-4015-a5c8-f015128920c4');
INSERT INTO `com_user` VALUES (2, 89, 'Irani', '093598765432', 'Nolofar', 'ba8fff4a-7ae3-440b-aefd-0ed2f86f9550');
INSERT INTO `com_user` VALUES (1, 108, 'Ghasemi', '09126300000', 'Mojtaba', 'f13f1da9-02bd-41f4-93a3-95074edb59f3');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (126);

SET FOREIGN_KEY_CHECKS = 1;
