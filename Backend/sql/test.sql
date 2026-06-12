/*
 Navicat MySQL Data Transfer

 Source Server         : 192.168.213.1
 Source Server Type    : MySQL
 Source Server Version : 80407
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80407
 File Encoding         : 65001

 Date: 11/06/2026 09:39:44
*/
use test;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `isbn` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '国际标准书号',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '书名',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出版社',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类',
  `total_count` int NOT NULL DEFAULT 0 COMMENT '总藏书量',
  `status` tinyint NULL DEFAULT 0 COMMENT '0-正常 1-损坏 2-丢失 3-剔旧',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `isbn`(`isbn`) USING BTREE,
  UNIQUE INDEX `uk_isbn`(`isbn`) USING BTREE,
  INDEX `idx_title`(`title`) USING BTREE,
  INDEX `idx_category`(`category`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '9787020008739', '红楼梦', '曹雪芹', '人民文学出版社', '文学', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (2, '9787020002201', '西游记', '吴承恩', '人民文学出版社', '文学', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (3, '9787020008722', '水浒传', '施耐庵', '人民文学出版社', '文学', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (4, '9787020008746', '三国演义', '罗贯中', '人民文学出版社', '文学', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (5, '9787544253994', '活着', '余华', '作家出版社', '文学', 8, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (6, '9787020132625', '平凡的世界', '路遥', '人民文学出版社', '文学', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (7, '9787536692930', '白鹿原', '陈忠实', '作家出版社', '文学', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (8, '9787506365437', '围城', '钱钟书', '人民文学出版社', '文学', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (9, '9787020114195', '边城', '沈从文', '人民文学出版社', '文学', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (10, '9787544291170', '百年孤独', '加西亚·马尔克斯', '南海出版公司', '文学', 7, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (11, '9787208061644', '三体：黑暗森林', '刘慈欣', '重庆出版社', '科幻', 10, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (12, '9787536692931', '三体：死神永生', '刘慈欣', '重庆出版社', '科幻', 8, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (13, '9787536692932', '三体', '刘慈欣', '重庆出版社', '科幻', 12, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (14, '9787536692933', '流浪地球', '刘慈欣', '中国华侨出版社', '科幻', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (15, '9787513327400', '沙丘', '弗兰克·赫伯特', '江苏凤凰文艺出版社', '科幻', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (16, '9787544285971', '银河帝国', '阿西莫夫', '江苏文艺出版社', '科幻', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (17, '9787544285988', '神们自己', '阿西莫夫', '江苏文艺出版社', '科幻', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (18, '9787208061651', '球状闪电', '刘慈欣', '四川科学技术出版社', '科幻', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (19, '9787115539627', 'Java编程思想', 'Bruce Eckel', '机械工业出版社', '计算机', 8, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (20, '9787121359606', 'Spring实战', 'Craig Walls', '人民邮电出版社', '计算机', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (21, '9787115428029', '深入理解Java虚拟机', '周志明', '机械工业出版社', '计算机', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (22, '9787121349874', 'MySQL必知必会', 'Ben Forta', '人民邮电出版社', '计算机', 7, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (23, '9787121264320', 'Redis深度历险', '钱文品', '电子工业出版社', '计算机', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (24, '9787121312311', 'Python编程从入门到实践', 'Eric Matthes', '人民邮电出版社', '计算机', 9, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (25, '9787115462292', '算法导论', 'Thomas H.Cormen', '机械工业出版社', '计算机', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (26, '9787121326561', '设计数据密集型应用', 'Martin Kleppmann', '中国电力出版社', '计算机', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (27, '9787121337882', '微服务设计', 'Sam Newman', '人民邮电出版社', '计算机', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (28, '9787115489679', '代码大全', 'Steve McConnell', '电子工业出版社', '计算机', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (29, '9787101005570', '史记', '司马迁', '中华书局', '历史', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (30, '9787101005587', '资治通鉴', '司马光', '中华书局', '历史', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (31, '9787108009825', '万历十五年', '黄仁宇', '生活·读书·新知三联书店', '历史', 8, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (32, '9787508657608', '人类简史', '尤瓦尔·赫拉利', '中信出版社', '历史', 10, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (33, '9787508657615', '未来简史', '尤瓦尔·赫拉利', '中信出版社', '历史', 7, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (34, '9787508657622', '今日简史', '尤瓦尔·赫拉利', '中信出版社', '历史', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (35, '9787508649351', '丝绸之路', '彼得·弗兰科潘', '浙江大学出版社', '历史', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (36, '9787547700796', '经济学原理', 'N.格里高利·曼昆', '北京大学出版社', '经济', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (37, '9787508639635', '国富论', '亚当·斯密', '商务印书馆', '经济', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (38, '9787508663401', '穷查理宝典', '查理·芒格', '中信出版社', '管理', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (39, '9787508638218', '从0到1', '彼得·蒂尔', '中信出版社', '管理', 7, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (40, '9787508627670', '精益创业', '埃里克·莱斯', '中信出版社', '管理', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (41, '9787508648040', '原则', '瑞·达利欧', '中信出版社', '管理', 8, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (42, '9787544288590', '乌合之众', '古斯塔夫·勒庞', '中央编译出版社', '心理学', 6, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (43, '9787508658940', '思考快与慢', '丹尼尔·卡尼曼', '中信出版社', '心理学', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (44, '9787531742979', '梦的解析', '弗洛伊德', '中央编译出版社', '心理学', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (45, '9787508655192', '影响力', '罗伯特·西奥迪尼', '中信出版社', '心理学', 7, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (46, '9787100071989', '理想国', '柏拉图', '商务印书馆', '哲学', 4, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (47, '9787100079985', '纯粹理性批判', '康德', '商务印书馆', '哲学', 3, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (48, '9787100075406', '存在与时间', '海德格尔', '商务印书馆', '哲学', 2, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);
INSERT INTO `book` VALUES (49, '9787100076557', '中国哲学简史', '冯友兰', '北京大学出版社', '哲学', 5, 0, '2026-06-08 15:38:09', '2026-06-08 15:38:09', 0);

-- ----------------------------
-- Table structure for borrow_record
-- ----------------------------
DROP TABLE IF EXISTS `borrow_record`;
CREATE TABLE `borrow_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `book_id` bigint NOT NULL COMMENT '图书ID',
  `borrow_time` datetime NOT NULL COMMENT '借书时间',
  `due_time` datetime NOT NULL COMMENT '应还时间',
  `return_time` datetime NULL DEFAULT NULL COMMENT '实际归还时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '0-借出中 1-已归还 2-逾期 3-丢失',
  `renewal_count` int NULL DEFAULT 0 COMMENT '续借次数',
  `overdue_days` int NULL DEFAULT 0 COMMENT '逾期天数（定时任务更新）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_book_id`(`book_id`) USING BTREE,
  INDEX `idx_status_due`(`status`, `due_time`) USING BTREE,
  INDEX `idx_borrow_time`(`borrow_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '借阅记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_record
-- ----------------------------
INSERT INTO `borrow_record` VALUES (1, 3, 1, '2026-05-01 10:30:00', '2026-05-31 10:30:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (2, 3, 20, '2026-05-15 14:20:00', '2026-06-14 14:20:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (3, 4, 3, '2026-05-20 09:15:00', '2026-06-19 09:15:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (4, 4, 21, '2026-05-25 11:00:00', '2026-06-24 11:00:00', NULL, 0, 1, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (5, 5, 11, '2026-06-01 10:00:00', '2026-07-01 10:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (6, 5, 12, '2026-06-01 10:05:00', '2026-07-01 10:05:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (7, 6, 30, '2026-05-28 15:30:00', '2026-06-27 15:30:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (8, 7, 22, '2026-06-05 09:00:00', '2026-07-05 09:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (9, 8, 13, '2026-05-10 14:00:00', '2026-06-09 14:00:00', NULL, 0, 1, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (10, 9, 40, '2026-06-08 16:20:00', '2026-07-08 16:20:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (11, 10, 31, '2026-06-10 11:30:00', '2026-07-10 11:30:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (12, 11, 23, '2026-06-12 09:45:00', '2026-07-12 09:45:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (13, 12, 14, '2026-06-01 10:00:00', '2026-07-01 10:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (14, 13, 32, '2026-06-03 13:20:00', '2026-07-03 13:20:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (15, 14, 41, '2026-06-05 15:00:00', '2026-07-05 15:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (16, 15, 24, '2026-06-07 10:30:00', '2026-07-07 10:30:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (17, 16, 15, '2026-06-09 14:15:00', '2026-07-09 14:15:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (18, 17, 33, '2026-06-11 09:00:00', '2026-07-11 09:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (19, 18, 42, '2026-06-13 16:00:00', '2026-07-13 16:00:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (20, 19, 25, '2026-06-15 11:20:00', '2026-07-15 11:20:00', NULL, 0, 0, 0, '2026-06-08 15:44:54', '2026-06-08 15:44:54');
INSERT INTO `borrow_record` VALUES (21, 3, 2, '2026-04-01 10:00:00', '2026-05-01 10:00:00', NULL, 2, 0, 40, '2026-06-08 15:45:00', '2026-06-11 09:28:44');
INSERT INTO `borrow_record` VALUES (22, 4, 4, '2026-04-15 14:30:00', '2026-05-15 14:30:00', NULL, 2, 1, 26, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (23, 5, 5, '2026-05-01 09:00:00', '2026-05-31 09:00:00', NULL, 2, 0, 11, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (24, 6, 16, '2026-04-20 11:00:00', '2026-05-20 11:00:00', NULL, 2, 0, 21, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (25, 7, 34, '2026-05-10 15:30:00', '2026-06-09 15:30:00', NULL, 2, 0, 1, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (26, 8, 26, '2026-05-15 10:00:00', '2026-06-14 10:00:00', NULL, 2, 0, -3, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (27, 9, 43, '2026-05-20 14:00:00', '2026-06-19 14:00:00', NULL, 2, 0, -8, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (28, 10, 17, '2026-05-25 09:30:00', '2026-06-24 09:30:00', NULL, 2, 0, -13, '2026-06-08 15:45:00', '2026-06-11 09:28:45');
INSERT INTO `borrow_record` VALUES (29, 3, 6, '2026-03-01 10:00:00', '2026-03-31 10:00:00', '2026-03-28 15:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (30, 4, 7, '2026-03-10 14:20:00', '2026-04-09 14:20:00', '2026-04-05 10:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (31, 5, 8, '2026-02-15 09:15:00', '2026-03-16 09:15:00', '2026-03-10 11:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (32, 6, 9, '2026-03-20 11:30:00', '2026-04-19 11:30:00', '2026-04-15 16:20:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (33, 7, 10, '2026-01-05 10:00:00', '2026-02-04 10:00:00', '2026-02-01 09:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (34, 8, 27, '2026-04-01 13:00:00', '2026-05-01 13:00:00', '2026-04-28 14:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (35, 9, 35, '2026-03-15 15:00:00', '2026-04-14 15:00:00', '2026-04-10 10:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (36, 10, 18, '2026-02-20 09:00:00', '2026-03-22 09:00:00', '2026-03-18 16:00:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (37, 11, 36, '2026-04-10 10:30:00', '2026-05-10 10:30:00', '2026-05-05 11:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (38, 12, 28, '2026-03-05 14:00:00', '2026-04-04 14:00:00', '2026-03-30 09:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (39, 13, 37, '2026-01-15 11:00:00', '2026-02-14 11:00:00', '2026-02-10 15:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (40, 14, 19, '2026-02-25 09:30:00', '2026-03-27 09:30:00', '2026-03-22 14:00:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (41, 15, 38, '2026-03-30 16:00:00', '2026-04-29 16:00:00', '2026-04-25 10:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (42, 16, 29, '2026-04-05 10:00:00', '2026-05-05 10:00:00', '2026-05-01 11:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (43, 17, 39, '2026-03-12 13:30:00', '2026-04-11 13:30:00', '2026-04-08 09:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (44, 18, 44, '2026-02-10 15:00:00', '2026-03-12 15:00:00', '2026-03-08 16:30:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (45, 19, 45, '2026-04-20 10:00:00', '2026-05-20 10:00:00', '2026-05-18 14:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (46, 20, 46, '2026-03-25 09:00:00', '2026-04-24 09:00:00', '2026-04-20 11:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (47, 3, 47, '2026-01-10 14:30:00', '2026-02-09 14:30:00', '2026-02-05 10:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (48, 4, 48, '2026-02-05 11:00:00', '2026-03-07 11:00:00', '2026-03-03 15:30:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (49, 5, 49, '2026-03-18 09:30:00', '2026-04-17 09:30:00', '2026-04-12 09:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (50, 6, 50, '2026-04-22 16:00:00', '2026-05-22 16:00:00', '2026-05-18 14:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (51, 7, 1, '2026-03-08 10:00:00', '2026-04-07 10:00:00', '2026-04-03 11:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (52, 8, 2, '2026-02-28 13:00:00', '2026-03-30 13:00:00', '2026-03-25 09:30:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (53, 9, 3, '2026-01-20 15:30:00', '2026-02-19 15:30:00', '2026-02-15 16:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (54, 10, 11, '2026-04-12 09:00:00', '2026-05-12 09:00:00', '2026-05-08 10:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (55, 11, 12, '2026-03-02 14:00:00', '2026-04-01 14:00:00', '2026-03-28 13:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (56, 12, 20, '2026-02-18 10:30:00', '2026-03-20 10:30:00', '2026-03-15 09:00:00', 1, 1, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (57, 13, 21, '2026-04-28 11:00:00', '2026-05-28 11:00:00', '2026-05-24 15:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (58, 14, 22, '2026-03-22 09:30:00', '2026-04-21 09:30:00', '2026-04-17 14:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (59, 15, 30, '2026-01-25 16:00:00', '2026-02-24 16:00:00', '2026-02-20 10:00:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (60, 16, 31, '2026-04-08 10:00:00', '2026-05-08 10:00:00', '2026-05-04 11:30:00', 1, 0, 0, '2026-06-08 15:45:07', '2026-06-08 15:45:07');
INSERT INTO `borrow_record` VALUES (61, 1, 49, '2026-06-08 21:22:54', '2026-09-06 21:22:54', NULL, 0, 0, 0, NULL, NULL);

-- ----------------------------
-- Table structure for fine
-- ----------------------------
DROP TABLE IF EXISTS `fine`;
CREATE TABLE `fine`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `borrow_record_id` bigint NULL DEFAULT NULL COMMENT '关联的借阅记录',
  `amount` decimal(10, 2) NOT NULL COMMENT '罚款金额',
  `paid_amount` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '已付金额',
  `status` tinyint NULL DEFAULT 0 COMMENT '0-未支付 1-已支付',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_status`(`user_id`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '罚款表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fine
-- ----------------------------
INSERT INTO `fine` VALUES (1, 3, 23, 2.20, 6.00, 1, '2026-04-28 15:30:00', '2026-04-28 15:35:00');
INSERT INTO `fine` VALUES (2, 4, 24, 4.20, 12.00, 1, '2026-04-05 10:00:00', '2026-04-05 10:10:00');
INSERT INTO `fine` VALUES (3, 5, 25, 0.20, 4.00, 1, '2026-03-10 11:00:00', '2026-03-10 11:05:00');
INSERT INTO `fine` VALUES (4, 6, 26, -0.60, 8.00, 1, '2026-04-15 16:20:00', '2026-04-15 16:25:00');
INSERT INTO `fine` VALUES (5, 7, 27, -1.60, 2.00, 1, '2026-02-01 09:00:00', '2026-02-01 09:03:00');
INSERT INTO `fine` VALUES (6, 8, 28, -2.60, 10.00, 1, '2026-04-28 14:00:00', '2026-04-28 14:05:00');
INSERT INTO `fine` VALUES (7, 9, 29, 14.00, 14.00, 1, '2026-04-10 10:30:00', '2026-04-10 10:35:00');
INSERT INTO `fine` VALUES (8, 10, 30, 6.00, 6.00, 1, '2026-03-18 16:00:00', '2026-03-18 16:05:00');
INSERT INTO `fine` VALUES (9, 11, 31, 8.00, 8.00, 1, '2026-05-05 11:00:00', '2026-05-05 11:10:00');
INSERT INTO `fine` VALUES (10, 12, 32, 10.00, 10.00, 1, '2026-03-30 09:00:00', '2026-03-30 09:08:00');
INSERT INTO `fine` VALUES (11, 13, 33, 4.00, 4.00, 1, '2026-02-10 15:30:00', '2026-02-10 15:35:00');
INSERT INTO `fine` VALUES (12, 14, 34, 6.00, 6.00, 1, '2026-03-22 14:00:00', '2026-03-22 14:03:00');
INSERT INTO `fine` VALUES (13, 15, 35, 10.00, 10.00, 1, '2026-04-25 10:00:00', '2026-04-25 10:05:00');
INSERT INTO `fine` VALUES (14, 16, 36, 8.00, 8.00, 1, '2026-05-01 11:30:00', '2026-05-01 11:35:00');
INSERT INTO `fine` VALUES (15, 17, 37, 6.00, 6.00, 1, '2026-04-08 09:00:00', '2026-04-08 09:04:00');
INSERT INTO `fine` VALUES (16, 18, 38, 4.00, 4.00, 1, '2026-03-08 16:30:00', '2026-03-08 16:32:00');
INSERT INTO `fine` VALUES (17, 3, 39, 18.00, 0.00, 0, '2026-05-18 14:00:00', NULL);
INSERT INTO `fine` VALUES (18, 4, 40, 24.00, 0.00, 0, '2026-05-20 11:00:00', NULL);
INSERT INTO `fine` VALUES (19, 5, 41, 15.00, 0.00, 0, '2026-05-31 10:00:00', NULL);
INSERT INTO `fine` VALUES (20, 6, 42, 22.00, 0.00, 0, '2026-06-03 09:30:00', NULL);
INSERT INTO `fine` VALUES (21, 7, 43, 8.00, 0.00, 0, '2026-06-05 15:30:00', NULL);
INSERT INTO `fine` VALUES (22, 8, 44, 12.00, 0.00, 0, '2026-06-07 10:00:00', NULL);
INSERT INTO `fine` VALUES (23, 9, 45, 6.00, 6.00, 1, '2026-06-09 14:00:00', '2026-06-08 21:18:19');
INSERT INTO `fine` VALUES (24, 10, 46, 9.00, 9.00, 1, '2026-06-12 09:30:00', '2026-06-08 21:18:11');
INSERT INTO `fine` VALUES (25, 11, 47, 20.00, 10.00, 0, '2026-05-25 11:00:00', '2026-05-25 11:05:00');
INSERT INTO `fine` VALUES (26, 12, 48, 15.00, 5.00, 0, '2026-05-28 14:00:00', '2026-05-28 14:03:00');
INSERT INTO `fine` VALUES (27, 13, 49, 12.00, 8.00, 0, '2026-06-01 09:00:00', '2026-06-01 09:05:00');
INSERT INTO `fine` VALUES (28, 3, 21, 8.00, 0.00, 0, NULL, NULL);
INSERT INTO `fine` VALUES (29, 4, 22, 5.20, 0.00, 0, NULL, NULL);

-- ----------------------------
-- Table structure for reservation
-- ----------------------------
DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `book_id` bigint NOT NULL,
  `reserve_time` datetime NOT NULL COMMENT '预约时间',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '0-等待中 1-已通知 2-已取书 3-已取消 4-过期',
  `queue_number` int NULL DEFAULT NULL COMMENT '排队序号',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_book_status`(`book_id`, `status`) USING BTREE,
  INDEX `idx_user_status`(`user_id`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '预约表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reservation
-- ----------------------------
INSERT INTO `reservation` VALUES (1, 5, 1, '2026-06-10 10:00:00', '2026-06-13 10:00:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (2, 8, 1, '2026-06-11 14:30:00', '2026-06-14 14:30:00', 0, 2, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (3, 12, 11, '2026-06-09 09:15:00', '2026-06-12 09:15:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (4, 15, 11, '2026-06-10 11:00:00', '2026-06-13 11:00:00', 0, 2, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (5, 18, 20, '2026-06-08 16:20:00', '2026-06-11 16:20:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (6, 6, 30, '2026-06-12 10:30:00', '2026-06-15 10:30:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (7, 10, 2, '2026-06-11 09:00:00', '2026-06-14 09:00:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (8, 14, 3, '2026-06-10 14:00:00', '2026-06-13 14:00:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (9, 19, 4, '2026-06-09 11:30:00', '2026-06-12 11:30:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (10, 7, 5, '2026-06-08 15:00:00', '2026-06-11 15:00:00', 0, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (11, 4, 6, '2026-06-05 10:00:00', '2026-06-08 10:00:00', 1, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (12, 9, 7, '2026-06-04 14:30:00', '2026-06-07 14:30:00', 1, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (13, 13, 8, '2026-06-03 09:00:00', '2026-06-06 09:00:00', 1, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (14, 17, 9, '2026-06-02 11:00:00', '2026-06-05 11:00:00', 1, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (15, 11, 10, '2026-05-20 10:00:00', '2026-05-23 10:00:00', 2, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (16, 16, 13, '2026-05-15 14:00:00', '2026-05-18 14:00:00', 2, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (17, 20, 14, '2026-06-01 09:30:00', '2026-06-04 09:30:00', 3, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');
INSERT INTO `reservation` VALUES (18, 2, 15, '2026-05-25 10:00:00', '2026-05-28 10:00:00', 4, 1, '2026-06-08 15:39:06', '2026-06-08 15:39:06');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `role` tinyint NULL DEFAULT 0 COMMENT '0-普通用户 1-管理员',
  `status` tinyint NULL DEFAULT 0 COMMENT '0-正常 1-冻结 2-黑名单',
  `max_borrow_count` int NULL DEFAULT 5 COMMENT '最大借书数量',
  `borrow_days` int NULL DEFAULT 30 COMMENT '最大借书天数',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '账号过期时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  INDEX `idx_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '系统管理员', 'admin@library.com', '13800000000', 1, 0, 20, 90, NULL, '2026-06-08 15:38:19', '2026-06-08 21:16:47', 0);
INSERT INTO `user` VALUES (2, 'librarian1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '张管理员', 'zhang@library.com', '13800000001', 1, 0, 15, 60, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (3, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '张三', 'zhangsan@example.com', '13812340001', 0, 2, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-10 01:30:22', 0);
INSERT INTO `user` VALUES (4, 'lisi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '李四', 'lisi@example.com', '13812340002', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (5, 'wangwu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '王五', 'wangwu@example.com', '13812340003', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (6, 'zhaoliu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '赵六', 'zhao@example.com', '13812340004', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (7, 'sunqi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '孙七', 'sun@example.com', '13812340005', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (8, 'zhouba', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '周八', 'zhou@example.com', '13812340006', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (9, 'wujiu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '吴九', 'wu@example.com', '13812340007', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (10, 'zhengshi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '郑十', 'zheng@example.com', '13812340008', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (11, 'chenyi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '陈一', 'chen@example.com', '13812340009', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (12, 'liner', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '林二', 'lin@example.com', '13812340010', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (13, 'huangshan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '黄三', 'huang@example.com', '13812340011', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (14, 'liusi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '刘四', 'liu@example.com', '13812340012', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (15, 'qiwu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '祁五', 'qi@example.com', '13812340013', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (16, 'fujiu', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '付九', 'fu@example.com', '13812340014', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (17, 'weidong', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '魏东', 'wei@example.com', '13812340015', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (18, 'jiangbei', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '江北', 'jiang@example.com', '13812340016', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (19, 'sunan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '苏南', 'su@example.com', '13812340017', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (20, 'tangyi', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '唐一', 'tang@example.com', '13812340018', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (21, 'songer', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '宋二', 'song@example.com', '13812340019', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);
INSERT INTO `user` VALUES (22, 'hansan', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5E', '韩三', 'han@example.com', '13812340020', 0, 0, 5, 30, NULL, '2026-06-08 15:38:19', '2026-06-08 15:38:19', 0);

SET FOREIGN_KEY_CHECKS = 1;
