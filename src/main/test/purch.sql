/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : purch

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-07-09 17:40:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ask
-- ----------------------------
DROP TABLE IF EXISTS `ask`;
CREATE TABLE `ask` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requestid` int(11) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ask
-- ----------------------------
INSERT INTO `ask` VALUES ('72', '7', '系统管理员', '2019-07-08 17:05:04', '1', '1');

-- ----------------------------
-- Table structure for deliver
-- ----------------------------
DROP TABLE IF EXISTS `deliver`;
CREATE TABLE `deliver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchid` int(11) DEFAULT NULL,
  `acceptnum` int(11) DEFAULT NULL,
  `delivernum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deliver
-- ----------------------------

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('273', 'ST001', '纸箱');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `parentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('13', '首页', 'index', '0', '0');
INSERT INTO `permission` VALUES ('18', '采购管理', 'purch', '0', '0');
INSERT INTO `permission` VALUES ('24', '财务管理', 'finance', '0', '0');
INSERT INTO `permission` VALUES ('28', '权限中心', 'permission', '0', '0');
INSERT INTO `permission` VALUES ('38', '新建', 'index-create', '2', '13');
INSERT INTO `permission` VALUES ('39', '查询', 'index-query', '2', '13');
INSERT INTO `permission` VALUES ('40', '修改', 'index-update', '2', '13');
INSERT INTO `permission` VALUES ('41', '删除', 'index-delete', '2', '13');
INSERT INTO `permission` VALUES ('42', '导出', 'index-export', '2', '13');
INSERT INTO `permission` VALUES ('43', '耗材类型', 'index-material', '2', '13');
INSERT INTO `permission` VALUES ('44', '新增', 'purch-create', '2', '18');
INSERT INTO `permission` VALUES ('45', '查询', 'purch-query', '2', '18');
INSERT INTO `permission` VALUES ('46', '修改', 'purch-update', '2', '18');
INSERT INTO `permission` VALUES ('47', '删除', 'purch-delete', '2', '18');
INSERT INTO `permission` VALUES ('48', '导出', 'purch-export', '2', '18');
INSERT INTO `permission` VALUES ('49', '查询', 'finance-query', '2', '24');
INSERT INTO `permission` VALUES ('50', '导出', 'finance-export', '2', '24');
INSERT INTO `permission` VALUES ('51', '填写发票号', 'finance-fapiao', '2', '24');
INSERT INTO `permission` VALUES ('52', '新建', 'permission-create', '2', '28');
INSERT INTO `permission` VALUES ('53', '查询', 'permission-query', '2', '28');
INSERT INTO `permission` VALUES ('54', '修改', 'permission-update', '2', '28');
INSERT INTO `permission` VALUES ('55', '删除', 'permission-delete', '2', '28');
INSERT INTO `permission` VALUES ('56', '发起', 'purch-start', '2', '18');
INSERT INTO `permission` VALUES ('57', '审核', 'finance-yes', '2', '24');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplierid` int(11) NOT NULL,
  `materialid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('70', '59', '273');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `zimu` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('145', '大金公司', '一百个纸箱', 'DJGS', '系统管理员', '2019-07-03 14:26:56');

-- ----------------------------
-- Table structure for purch
-- ----------------------------
DROP TABLE IF EXISTS `purch`;
CREATE TABLE `purch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `askid` int(11) DEFAULT NULL,
  `supplierid` int(11) DEFAULT NULL,
  `acceptprice` decimal(10,2) DEFAULT NULL,
  `acceptnum` int(11) DEFAULT NULL,
  `accepttime` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purch
-- ----------------------------
INSERT INTO `purch` VALUES ('90', '72', '59', null, null, null, '1');

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resourceid` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `sellnum` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES ('6', '34', '1', '0', '0.00', '0.00', '系统管理员', '2019-07-03 14:32:11', '1');
INSERT INTO `request` VALUES ('7', '34', '0', '0', '0.00', '0.00', '系统管理员', '2019-07-03 16:17:37', '1');
INSERT INTO `request` VALUES ('8', '34', '100', '110', '13.50', '1350.00', '系统管理员', '2019-07-03 16:18:00', '1');
INSERT INTO `request` VALUES ('9', '34', '1', '1001', '1.53', '1531.53', '系统管理员', '2019-07-08 16:30:46', '1');
INSERT INTO `request` VALUES ('10', '34', '1', '1', '122.00', '122.00', '系统管理员', '2019-07-08 16:50:30', '1');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `materialid` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `special` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('34', '145', '273', '100*100*300', '0.5厘米厚的纸', '必须是纸箱，不能是木箱', '34-snap106.tif', '系统管理员', '2019-07-03 14:29:33');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `supplierid` int(11) DEFAULT NULL,
  `projectid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('13', '系统管理员', null, null);
INSERT INTO `role` VALUES ('47', '测试角色', '59', '145');

-- ----------------------------
-- Table structure for role2permission
-- ----------------------------
DROP TABLE IF EXISTS `role2permission`;
CREATE TABLE `role2permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `permissionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=330 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role2permission
-- ----------------------------
INSERT INTO `role2permission` VALUES ('266', '13', '13');
INSERT INTO `role2permission` VALUES ('267', '13', '38');
INSERT INTO `role2permission` VALUES ('268', '13', '39');
INSERT INTO `role2permission` VALUES ('269', '13', '40');
INSERT INTO `role2permission` VALUES ('270', '13', '41');
INSERT INTO `role2permission` VALUES ('271', '13', '42');
INSERT INTO `role2permission` VALUES ('272', '13', '43');
INSERT INTO `role2permission` VALUES ('273', '13', '18');
INSERT INTO `role2permission` VALUES ('274', '13', '44');
INSERT INTO `role2permission` VALUES ('275', '13', '45');
INSERT INTO `role2permission` VALUES ('276', '13', '46');
INSERT INTO `role2permission` VALUES ('277', '13', '47');
INSERT INTO `role2permission` VALUES ('278', '13', '48');
INSERT INTO `role2permission` VALUES ('279', '13', '56');
INSERT INTO `role2permission` VALUES ('280', '13', '24');
INSERT INTO `role2permission` VALUES ('281', '13', '49');
INSERT INTO `role2permission` VALUES ('282', '13', '50');
INSERT INTO `role2permission` VALUES ('283', '13', '51');
INSERT INTO `role2permission` VALUES ('284', '13', '57');
INSERT INTO `role2permission` VALUES ('285', '13', '28');
INSERT INTO `role2permission` VALUES ('286', '13', '52');
INSERT INTO `role2permission` VALUES ('287', '13', '53');
INSERT INTO `role2permission` VALUES ('288', '13', '54');
INSERT INTO `role2permission` VALUES ('289', '13', '55');
INSERT INTO `role2permission` VALUES ('306', '47', '13');
INSERT INTO `role2permission` VALUES ('307', '47', '38');
INSERT INTO `role2permission` VALUES ('308', '47', '39');
INSERT INTO `role2permission` VALUES ('309', '47', '40');
INSERT INTO `role2permission` VALUES ('310', '47', '41');
INSERT INTO `role2permission` VALUES ('311', '47', '42');
INSERT INTO `role2permission` VALUES ('312', '47', '43');
INSERT INTO `role2permission` VALUES ('313', '47', '18');
INSERT INTO `role2permission` VALUES ('314', '47', '44');
INSERT INTO `role2permission` VALUES ('315', '47', '45');
INSERT INTO `role2permission` VALUES ('316', '47', '46');
INSERT INTO `role2permission` VALUES ('317', '47', '47');
INSERT INTO `role2permission` VALUES ('318', '47', '48');
INSERT INTO `role2permission` VALUES ('319', '47', '56');
INSERT INTO `role2permission` VALUES ('320', '47', '24');
INSERT INTO `role2permission` VALUES ('321', '47', '49');
INSERT INTO `role2permission` VALUES ('322', '47', '50');
INSERT INTO `role2permission` VALUES ('323', '47', '51');
INSERT INTO `role2permission` VALUES ('324', '47', '57');
INSERT INTO `role2permission` VALUES ('325', '47', '28');
INSERT INTO `role2permission` VALUES ('326', '47', '52');
INSERT INTO `role2permission` VALUES ('327', '47', '53');
INSERT INTO `role2permission` VALUES ('328', '47', '54');
INSERT INTO `role2permission` VALUES ('329', '47', '55');

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `value` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES ('1', '1', '1.33');
INSERT INTO `setting` VALUES ('2', '2', '8.00');
INSERT INTO `setting` VALUES ('3', '3', '8.00');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `contacts` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `fapiao` varchar(255) DEFAULT NULL,
  `zhanghu` varchar(255) DEFAULT NULL,
  `shoukuan` varchar(255) DEFAULT NULL,
  `kaihu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('59', '纸箱厂', '李某', '19999999999', '纸箱也是厂', '中国银行', '15648795852', '兰州支行');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `roleid` int(32) DEFAULT NULL,
  `supplierid` int(32) DEFAULT NULL,
  `deliver` int(11) DEFAULT NULL,
  `ischange` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('23', '系统管理员', '21232f297a57a5a743894a0e4a801fc3', 'admin', '', '2019-06-14 13:56:29', '13', null, null, '1');
INSERT INTO `user` VALUES ('59', '大中公司', 'b7bc2a2f5bb6d521e64c8974c143e9a0', '18812345678', '系统管理员', '2019-07-03 14:37:33', null, '59', null, '0');
INSERT INTO `user` VALUES ('60', '111', '2be9bd7a3434f7038ca27d1918de58bd', '111', '系统管理员', '2019-07-08 14:47:54', '47', null, null, '0');
INSERT INTO `user` VALUES ('61', '222', '310dcbbf4cce62f762a2aaa148d556bd', '222', '系统管理员', '2019-07-08 14:57:08', null, '59', null, '0');
INSERT INTO `user` VALUES ('62', '222', '2be9bd7a3434f7038ca27d1918de58bd', '222', '系统管理员', '2019-07-08 14:57:29', null, null, '1', '0');
