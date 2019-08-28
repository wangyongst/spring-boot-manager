/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : purch

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-28 15:39:21
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
  `confirmtime` varchar(255) DEFAULT NULL,
  `overtime` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ask
-- ----------------------------
INSERT INTO `ask` VALUES ('267', '77', '系统管理员', '2019-08-28 14:23:57', null, null, '1', '1');

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `billtime` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `supplierid` int(11) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill
-- ----------------------------

-- ----------------------------
-- Table structure for billdetail
-- ----------------------------
DROP TABLE IF EXISTS `billdetail`;
CREATE TABLE `billdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `billid` int(11) DEFAULT NULL,
  `purchid` int(11) DEFAULT NULL,
  `billno` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of billdetail
-- ----------------------------

-- ----------------------------
-- Table structure for deliver
-- ----------------------------
DROP TABLE IF EXISTS `deliver`;
CREATE TABLE `deliver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `purchid` int(11) DEFAULT NULL,
  `delivernum` int(11) DEFAULT NULL,
  `confirmnum` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `accepttime` varchar(255) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------
INSERT INTO `material` VALUES ('273', 'ST001', '纸箱');
INSERT INTO `material` VALUES ('300', 'ST002', '包装盒');
INSERT INTO `material` VALUES ('301', 'ST003', '胶带');
INSERT INTO `material` VALUES ('302', 'ST004', '杯子');
INSERT INTO `material` VALUES ('303', 'ST005', '盖子');
INSERT INTO `material` VALUES ('304', 'ST006', '电子秤');
INSERT INTO `material` VALUES ('305', 'ST007', '充电线');
INSERT INTO `material` VALUES ('306', 'ST008', '手机壳');
INSERT INTO `material` VALUES ('307', 'ST009', '手机膜');
INSERT INTO `material` VALUES ('308', 'ST010', '电插板');
INSERT INTO `material` VALUES ('309', 'ST011', '电脑桌');
INSERT INTO `material` VALUES ('310', 'ST012', '泡沫柱');
INSERT INTO `material` VALUES ('311', 'ST013', '泡沫柱');
INSERT INTO `material` VALUES ('312', 'ST014', '泡沫柱');
INSERT INTO `material` VALUES ('313', 'ST014', '充气葫芦泡');
INSERT INTO `material` VALUES ('314', 'ST015', '护角条');

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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

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
INSERT INTO `permission` VALUES ('44', '查询', 'purch-query', '2', '18');
INSERT INTO `permission` VALUES ('45', '导出', 'purch-export', '2', '18');
INSERT INTO `permission` VALUES ('46', '新增', 'purch-create', '2', '18');
INSERT INTO `permission` VALUES ('47', '删除', 'purch-delete', '2', '18');
INSERT INTO `permission` VALUES ('48', '修改', 'purch-update', '2', '18');
INSERT INTO `permission` VALUES ('49', '查询', 'finance-query', '2', '24');
INSERT INTO `permission` VALUES ('50', '导出', 'finance-export', '2', '24');
INSERT INTO `permission` VALUES ('51', '填写发票号', 'finance-fapiao', '2', '24');
INSERT INTO `permission` VALUES ('52', '新建', 'permission-create', '2', '28');
INSERT INTO `permission` VALUES ('53', '查询', 'permission-query', '2', '28');
INSERT INTO `permission` VALUES ('54', '修改', 'permission-update', '2', '28');
INSERT INTO `permission` VALUES ('55', '删除', 'permission-delete', '2', '28');
INSERT INTO `permission` VALUES ('56', '撤回', 'purch-back', '2', '18');
INSERT INTO `permission` VALUES ('57', '审核', 'finance-yes', '2', '24');
INSERT INTO `permission` VALUES ('58', '发起', 'purch-start', '2', '18');
INSERT INTO `permission` VALUES ('59', '价格系数', 'purch-price', '2', '18');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `supplierid` int(11) NOT NULL,
  `materialid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('71', '60', '273');
INSERT INTO `product` VALUES ('91', '66', '300');
INSERT INTO `product` VALUES ('92', '67', '273');
INSERT INTO `product` VALUES ('93', '65', '301');
INSERT INTO `product` VALUES ('94', '65', '306');
INSERT INTO `product` VALUES ('95', '64', '273');
INSERT INTO `product` VALUES ('96', '64', '301');
INSERT INTO `product` VALUES ('102', '69', '273');
INSERT INTO `product` VALUES ('103', '69', '310');
INSERT INTO `product` VALUES ('109', '70', '273');
INSERT INTO `product` VALUES ('110', '70', '302');
INSERT INTO `product` VALUES ('111', '70', '310');
INSERT INTO `product` VALUES ('116', '68', '301');
INSERT INTO `product` VALUES ('117', '68', '304');
INSERT INTO `product` VALUES ('118', '68', '307');
INSERT INTO `product` VALUES ('119', '68', '310');
INSERT INTO `product` VALUES ('127', '73', '273');
INSERT INTO `product` VALUES ('128', '73', '300');
INSERT INTO `product` VALUES ('129', '71', '273');
INSERT INTO `product` VALUES ('130', '71', '301');
INSERT INTO `product` VALUES ('131', '72', '273');
INSERT INTO `product` VALUES ('132', '72', '301');
INSERT INTO `product` VALUES ('133', '59', '273');

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
) ENGINE=InnoDB AUTO_INCREMENT=183 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('145', '大金公司', '一百个纸箱', 'DJGS', '系统管理员', '2019-07-03 14:26:56');
INSERT INTO `project` VALUES ('171', '黄金公司', '二百个螺丝', 'LBGLS', '系统管理员', '2019-08-10 09:54:50');
INSERT INTO `project` VALUES ('173', '倪瓒公司', '胶带', 'JD', '系统管理员', '2019-08-10 16:17:11');
INSERT INTO `project` VALUES ('174', 'dh', 'DH的项目', 'dh', '系统管理员', '2019-08-10 22:12:18');
INSERT INTO `project` VALUES ('175', '新公司', '新项目', 'XXM', '系统管理员', '2019-08-17 10:38:32');
INSERT INTO `project` VALUES ('176', '打样公司', '打样公司', 'DYGS', '系统管理员', '2019-08-17 20:13:56');
INSERT INTO `project` VALUES ('177', '堆糖', '堆糖', 'DT', '系统管理员', '2019-08-18 07:08:18');
INSERT INTO `project` VALUES ('178', '上海宝时供应链', '宝时', 'BSH', '系统管理员', '2019-08-18 07:09:03');
INSERT INTO `project` VALUES ('179', '红泉', '红泉', 'HQ', '系统管理员', '2019-08-18 07:09:43');
INSERT INTO `project` VALUES ('180', '米多', '米多', 'MD', '系统管理员', '2019-08-21 09:31:04');
INSERT INTO `project` VALUES ('181', '智驭', '太极禅', 'TJC', '系统管理员', '2019-08-21 09:31:21');
INSERT INTO `project` VALUES ('182', '鼎动', '北京宏享', 'HX', '系统管理员', '2019-08-21 09:31:35');

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
  `islower` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=825 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purch
-- ----------------------------
INSERT INTO `purch` VALUES ('816', '267', '60', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('817', '267', '67', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('818', '267', '64', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('819', '267', '69', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('820', '267', '70', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('821', '267', '73', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('822', '267', '71', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('823', '267', '72', null, null, null, '1', null);
INSERT INTO `purch` VALUES ('824', '267', '59', null, null, null, '1', null);

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
  `createusermobile` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `status` int(10) DEFAULT NULL,
  `type` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES ('77', '69', null, null, null, null, 'admin', '系统管理员', '2019-08-28 14:23:36', '1', '1');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `materialid` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `special` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `file` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('69', '182', '273', '632627', '11212', '121', '212121212', null, '系统管理员', '2019-08-28 13:58:24');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `projectid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('13', '系统管理员', null);
INSERT INTO `role` VALUES ('48', '测试角色', '145');
INSERT INTO `role` VALUES ('49', '普通用户', '145');
INSERT INTO `role` VALUES ('51', '开发', '175');
INSERT INTO `role` VALUES ('52', '采购经理', null);
INSERT INTO `role` VALUES ('53', '业务员', '177');

-- ----------------------------
-- Table structure for role2permission
-- ----------------------------
DROP TABLE IF EXISTS `role2permission`;
CREATE TABLE `role2permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) DEFAULT NULL,
  `permissionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=480 DEFAULT CHARSET=utf8;

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
INSERT INTO `role2permission` VALUES ('330', '13', '51');
INSERT INTO `role2permission` VALUES ('331', '13', '56');
INSERT INTO `role2permission` VALUES ('332', '13', '58');
INSERT INTO `role2permission` VALUES ('333', '13', '59');
INSERT INTO `role2permission` VALUES ('360', '48', '13');
INSERT INTO `role2permission` VALUES ('361', '48', '38');
INSERT INTO `role2permission` VALUES ('362', '48', '39');
INSERT INTO `role2permission` VALUES ('363', '48', '40');
INSERT INTO `role2permission` VALUES ('364', '48', '41');
INSERT INTO `role2permission` VALUES ('365', '48', '42');
INSERT INTO `role2permission` VALUES ('366', '48', '43');
INSERT INTO `role2permission` VALUES ('367', '48', '18');
INSERT INTO `role2permission` VALUES ('368', '48', '44');
INSERT INTO `role2permission` VALUES ('369', '48', '45');
INSERT INTO `role2permission` VALUES ('370', '48', '46');
INSERT INTO `role2permission` VALUES ('371', '48', '47');
INSERT INTO `role2permission` VALUES ('372', '48', '48');
INSERT INTO `role2permission` VALUES ('373', '48', '56');
INSERT INTO `role2permission` VALUES ('374', '48', '58');
INSERT INTO `role2permission` VALUES ('375', '48', '59');
INSERT INTO `role2permission` VALUES ('376', '48', '24');
INSERT INTO `role2permission` VALUES ('377', '48', '49');
INSERT INTO `role2permission` VALUES ('378', '48', '50');
INSERT INTO `role2permission` VALUES ('379', '48', '51');
INSERT INTO `role2permission` VALUES ('380', '48', '57');
INSERT INTO `role2permission` VALUES ('381', '48', '28');
INSERT INTO `role2permission` VALUES ('382', '48', '52');
INSERT INTO `role2permission` VALUES ('383', '48', '53');
INSERT INTO `role2permission` VALUES ('384', '48', '54');
INSERT INTO `role2permission` VALUES ('385', '48', '55');
INSERT INTO `role2permission` VALUES ('388', '49', '38');
INSERT INTO `role2permission` VALUES ('389', '49', '39');
INSERT INTO `role2permission` VALUES ('390', '49', '44');
INSERT INTO `role2permission` VALUES ('391', '49', '49');
INSERT INTO `role2permission` VALUES ('392', '49', '53');
INSERT INTO `role2permission` VALUES ('408', '51', '13');
INSERT INTO `role2permission` VALUES ('409', '51', '38');
INSERT INTO `role2permission` VALUES ('410', '51', '18');
INSERT INTO `role2permission` VALUES ('411', '51', '44');
INSERT INTO `role2permission` VALUES ('412', '51', '24');
INSERT INTO `role2permission` VALUES ('413', '51', '49');
INSERT INTO `role2permission` VALUES ('414', '51', '28');
INSERT INTO `role2permission` VALUES ('415', '51', '52');
INSERT INTO `role2permission` VALUES ('452', '52', '13');
INSERT INTO `role2permission` VALUES ('453', '52', '38');
INSERT INTO `role2permission` VALUES ('454', '52', '39');
INSERT INTO `role2permission` VALUES ('455', '52', '40');
INSERT INTO `role2permission` VALUES ('456', '52', '41');
INSERT INTO `role2permission` VALUES ('457', '52', '42');
INSERT INTO `role2permission` VALUES ('458', '52', '43');
INSERT INTO `role2permission` VALUES ('459', '52', '18');
INSERT INTO `role2permission` VALUES ('460', '52', '44');
INSERT INTO `role2permission` VALUES ('461', '52', '45');
INSERT INTO `role2permission` VALUES ('462', '52', '46');
INSERT INTO `role2permission` VALUES ('463', '52', '47');
INSERT INTO `role2permission` VALUES ('464', '52', '48');
INSERT INTO `role2permission` VALUES ('465', '52', '56');
INSERT INTO `role2permission` VALUES ('466', '52', '58');
INSERT INTO `role2permission` VALUES ('467', '52', '59');
INSERT INTO `role2permission` VALUES ('468', '52', '24');
INSERT INTO `role2permission` VALUES ('469', '52', '49');
INSERT INTO `role2permission` VALUES ('470', '52', '50');
INSERT INTO `role2permission` VALUES ('471', '52', '51');
INSERT INTO `role2permission` VALUES ('472', '52', '57');
INSERT INTO `role2permission` VALUES ('473', '53', '44');
INSERT INTO `role2permission` VALUES ('474', '53', '45');
INSERT INTO `role2permission` VALUES ('475', '53', '46');
INSERT INTO `role2permission` VALUES ('476', '53', '47');
INSERT INTO `role2permission` VALUES ('477', '53', '48');
INSERT INTO `role2permission` VALUES ('478', '53', '56');
INSERT INTO `role2permission` VALUES ('479', '53', '58');

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
INSERT INTO `setting` VALUES ('1', '1', '1.30');
INSERT INTO `setting` VALUES ('2', '2', '4.00');
INSERT INTO `setting` VALUES ('3', '3', '1.00');

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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES ('59', '纸箱厂', '汪泳', '18119445588', '纸箱也是厂', '中国银行', '15648795852', '兰州支行');
INSERT INTO `supplier` VALUES ('60', '新纸箱厂', '李四', '18111111', '新纸箱厂', '中国银行', '155115151', '兰州支行');
INSERT INTO `supplier` VALUES ('64', '商办供应商', '商办', '13698765623', 'TS', '农业银行', '4242456754555543454332', '农业银行');
INSERT INTO `supplier` VALUES ('65', '供货商', '供货商', '13698765623', 'JIT', '邮政', '345343645645645634543', '邮政银行');
INSERT INTO `supplier` VALUES ('66', '供货商2', '供应商', '1234543534', 'KST', '招商银行', '5465475684745635', '招商银行');
INSERT INTO `supplier` VALUES ('67', 'DH供应商', 'dh', '18671479311', '123456', '中国银行', '123456', '中国银行');
INSERT INTO `supplier` VALUES ('68', '新供应商', '新人', '13023456789', 'DZ', '农行', '252452343414241414', '农业银行支行');
INSERT INTO `supplier` VALUES ('69', '测试供应商1', '测试供应商1', '12222255555', '上海测试供应商1有限公司', '中共农业银行', '31022255515556656565585', '宝山支行');
INSERT INTO `supplier` VALUES ('70', '测试01', '测试01', '12344422223', '1223332', '1212', '121233223', '32212121');
INSERT INTO `supplier` VALUES ('71', '邓婷婷', '邓婷婷', '18221486392', '121212', '121212', '121212', '121212');
INSERT INTO `supplier` VALUES ('72', '葛付晖', '葛付晖', '13761108797', '222222', '222222', '222222', '222222');
INSERT INTO `supplier` VALUES ('73', '张书恒', '张书恒', '13515667556', '33333333', '3333333', '3333333', '3333333');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `createusername` varchar(255) DEFAULT NULL,
  `createtime` varchar(255) DEFAULT NULL,
  `roleid` int(32) DEFAULT NULL,
  `supplierid` int(32) DEFAULT NULL,
  `deliver` int(11) DEFAULT NULL,
  `ischange` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('23', '系统管理员', '21232f297a57a5a743894a0e4a801fc3', 'admin', null, '', '2019-06-14 13:56:29', '13', null, null, '1');
INSERT INTO `user` VALUES ('68', '倪瓒', 'e10adc3949ba59abbe56e057f20f883e', '123456', null, '系统管理员', '2019-08-09 21:06:10', null, null, '1', '0');
INSERT INTO `user` VALUES ('71', '供应商', 'e10adc3949ba59abbe56e057f20f883e', '13098765432', '', '系统管理员', '2019-08-10 15:13:05', null, '64', null, '0');
INSERT INTO `user` VALUES ('72', '供应商1', 'e10adc3949ba59abbe56e057f20f883e', '13012345678', '', '系统管理员', '2019-08-10 15:30:27', null, '65', null, '0');
INSERT INTO `user` VALUES ('73', '供应商2', 'e10adc3949ba59abbe56e057f20f883e', '13987654321', '', '系统管理员', '2019-08-10 16:11:50', null, '66', null, '0');
INSERT INTO `user` VALUES ('74', '倪瓒', 'e10adc3949ba59abbe56e057f20f883e', '13909876543', '', '系统管理员', '2019-08-10 18:44:27', null, null, '1', '0');
INSERT INTO `user` VALUES ('75', 'dh', 'e10adc3949ba59abbe56e057f20f883e', '18671479311', '', '系统管理员', '2019-08-10 22:09:52', null, '59', null, '0');
INSERT INTO `user` VALUES ('76', '新供应商', 'e10adc3949ba59abbe56e057f20f883e', '13023456789', '', '系统管理员', '2019-08-17 10:41:56', null, '68', null, '0');
INSERT INTO `user` VALUES ('77', '施尧', 'e10adc3949ba59abbe56e057f20f883e', '13482498847', null, '系统管理员', '2019-08-18 08:39:38', '13', null, null, '1');
INSERT INTO `user` VALUES ('78', '测试01', 'e10adc3949ba59abbe56e057f20f883e', '12344422223', '', '系统管理员', '2019-08-18 09:28:58', null, '70', null, '0');
INSERT INTO `user` VALUES ('79', '开发', '25d55ad283aa400af464c76d713c07ad', '12345678', null, '系统管理员', '2019-08-18 10:02:40', '51', null, null, '1');
INSERT INTO `user` VALUES ('80', '张三', 'f379eaf3c831b04de153469d1bec345e', '189000010001', null, '系统管理员', '2019-08-18 10:08:58', null, null, '1', '0');
INSERT INTO `user` VALUES ('81', '张书恒', 'e10adc3949ba59abbe56e057f20f883e', '13515667556', '', '系统管理员', '2019-08-21 09:21:13', null, '73', null, '0');
INSERT INTO `user` VALUES ('82', '葛付晖', 'e10adc3949ba59abbe56e057f20f883e', '13761108797', '', '系统管理员', '2019-08-21 09:22:13', null, '72', null, '0');
INSERT INTO `user` VALUES ('83', '邓婷婷', 'd4e89857100bf292557362775a6518d0', '18221486392', 'o6kUd5A1CKjSmMOicIVQslLtFqj8', '系统管理员', '2019-08-21 09:22:58', null, '71', null, '1');
INSERT INTO `user` VALUES ('84', '汪泳', 'e10adc3949ba59abbe56e057f20f883e', '18119445588', '', '系统管理员', '2019-08-21 10:50:05', null, '71', null, '0');
