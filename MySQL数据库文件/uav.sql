/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : uav

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-05-05 23:21:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `reason` text NOT NULL,
  `date` varchar(50) NOT NULL,
  `forum` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL DEFAULT 'unread',
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('1', '4', '徐小刚', '<p>&nbsp; 尊敬的管理员您好！<img src=\"http://localhost:8090/UAV/layui/images/face/0.gif\" alt=\"[微笑]\"></p><p>来UAV社区三年了，今天申请成为大疆板块管理员，还望予以批准。</p><p><br></p>', '2018-12-07 00:36:13', '大疆', 'unread');

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('48', '1');
INSERT INTO `collect` VALUES ('49', '2');
INSERT INTO `collect` VALUES ('40', '3');
INSERT INTO `collect` VALUES ('41', '3');
INSERT INTO `collect` VALUES ('42', '3');
INSERT INTO `collect` VALUES ('43', '3');
INSERT INTO `collect` VALUES ('34', '4');

-- ----------------------------
-- Table structure for discuss
-- ----------------------------
DROP TABLE IF EXISTS `discuss`;
CREATE TABLE `discuss` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `discuss` varchar(255) NOT NULL,
  `unlike` int(11) DEFAULT '0',
  `pid` int(11) NOT NULL,
  `time` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`did`),
  KEY `uid` (`uid`),
  KEY `pid` (`pid`),
  CONSTRAINT `discuss_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `discuss_ibfk_2` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of discuss
-- ----------------------------
INSERT INTO `discuss` VALUES ('7', '4', 'wo!wo!wo!', '9', '34', '2018-12-03');
INSERT INTO `discuss` VALUES ('10', '4', '期待', '14', '34', '2018-12-11');
INSERT INTO `discuss` VALUES ('11', '4', '得举报', '47', '34', '2018-12-10');
INSERT INTO `discuss` VALUES ('13', '6', '好的，期待的很', '14', '38', '2018-12-11');
INSERT INTO `discuss` VALUES ('14', '6', '优秀', '4', '39', '2018-12-11');
INSERT INTO `discuss` VALUES ('17', '6', 'null', '202', '39', '2018-12-11');
INSERT INTO `discuss` VALUES ('18', '4', '给我来一打！！', '14', '44', '2018-12-11');
INSERT INTO `discuss` VALUES ('19', '1', '奈斯！！', '15', '48', '2018-12-11');
INSERT INTO `discuss` VALUES ('20', '1', '奈斯！！', '114', '48', '2018-12-11');
INSERT INTO `discuss` VALUES ('21', '4', 'hahaha', '33', '46', '2018-12-12');
INSERT INTO `discuss` VALUES ('22', '4', 'haha', '14', '46', '2018-12-12');
INSERT INTO `discuss` VALUES ('23', '4', 'NB,厉害了', '25', '49', '2018-12-14');
INSERT INTO `discuss` VALUES ('24', '1', '谢谢夸奖。', '22', '49', '2018-12-14');
INSERT INTO `discuss` VALUES ('25', '2', '我也想要一个', '3', '49', '2018-12-14');
INSERT INTO `discuss` VALUES ('26', '2', '不愧是宁波！', '13', '41', '2018-12-18');
INSERT INTO `discuss` VALUES ('27', '2', '喜欢', '46', '41', '2018-12-18');
INSERT INTO `discuss` VALUES ('28', '1', '辣鸡就是辣鸡', '252', '49', '2018-12-18');

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum` (
  `vendor` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `section` varchar(255) NOT NULL,
  `uid` int(11) NOT NULL DEFAULT '4',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `section` (`section`),
  CONSTRAINT `forum_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of forum
-- ----------------------------
INSERT INTO `forum` VALUES ('大疆', '御系类', '1', '3');
INSERT INTO `forum` VALUES ('大疆', '精灵系列', '3', '6');
INSERT INTO `forum` VALUES ('大疆', '晓Spark', '4', '11');
INSERT INTO `forum` VALUES ('零度', 'DOBBY', '5', '12');
INSERT INTO `forum` VALUES ('零度', 'DOLLCAP', '6', '13');
INSERT INTO `forum` VALUES ('亿航', 'GHOSTDRONE 2.0', '7', '14');
INSERT INTO `forum` VALUES ('亿航', 'EHANG184', '8', '15');
INSERT INTO `forum` VALUES ('美嘉欣', 'X系列', '9', '16');
INSERT INTO `forum` VALUES ('美嘉欣', 'GUGS系列', '10', '17');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `uid` int(11) NOT NULL,
  `fid` int(11) NOT NULL,
  `bakname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`,`fid`),
  CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('1', '1', null);
INSERT INTO `friend` VALUES ('1', '3', null);
INSERT INTO `friend` VALUES ('2', '1', null);
INSERT INTO `friend` VALUES ('3', '1', null);
INSERT INTO `friend` VALUES ('3', '4', null);
INSERT INTO `friend` VALUES ('3', '5', null);
INSERT INTO `friend` VALUES ('4', '1', null);
INSERT INTO `friend` VALUES ('4', '4', null);
INSERT INTO `friend` VALUES ('4', '6', null);
INSERT INTO `friend` VALUES ('5', '4', null);

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
  `pid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`uid`),
  KEY `uid` (`uid`),
  CONSTRAINT `like_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `post` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `like_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of like
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `fromid` int(11) NOT NULL,
  `toid` int(11) NOT NULL,
  `content` text,
  `date` datetime NOT NULL,
  `look` int(11) DEFAULT '0',
  PRIMARY KEY (`mid`),
  KEY `fromid` (`fromid`),
  KEY `toid` (`toid`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`fromid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`toid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('1', '4', '5', '明天风大改天去试飞', '2018-08-08 00:00:00', '0');
INSERT INTO `message` VALUES ('2', '5', '4', '      	  ', '2018-12-05 00:33:56', '1');
INSERT INTO `message` VALUES ('3', '5', '4', '你好', '2018-12-05 01:21:35', '1');
INSERT INTO `message` VALUES ('4', '5', '4', '明天约飞吗？', '2018-12-05 01:26:08', '1');
INSERT INTO `message` VALUES ('5', '5', '4', '明天约飞吗？', '2018-12-05 01:27:04', '1');
INSERT INTO `message` VALUES ('6', '4', '5', '约起来', '2018-12-05 01:29:15', '0');
INSERT INTO `message` VALUES ('7', '4', '5', '可以', '2018-12-05 01:30:36', '0');
INSERT INTO `message` VALUES ('8', '4', '5', '阿道夫', '2018-12-05 01:33:09', '0');
INSERT INTO `message` VALUES ('9', '4', '5', '阿道夫', '2018-12-05 01:34:30', '0');
INSERT INTO `message` VALUES ('10', '4', '5', '123', '2018-12-05 01:36:09', '0');
INSERT INTO `message` VALUES ('11', '4', '5', 'fas', '2018-12-05 01:39:49', '0');
INSERT INTO `message` VALUES ('12', '4', '5', '拒绝空回复', '2018-12-05 13:40:53', '0');
INSERT INTO `message` VALUES ('13', '4', '5', '你好', '2018-12-05 13:42:54', '0');
INSERT INTO `message` VALUES ('14', '4', '5', '123', '2018-12-05 13:45:23', '0');
INSERT INTO `message` VALUES ('15', '4', '5', '123', '2018-12-05 13:46:29', '0');
INSERT INTO `message` VALUES ('16', '4', '5', '明天你好！', '2018-12-06 01:58:11', '0');
INSERT INTO `message` VALUES ('17', '4', '5', '嗯', '2018-12-06 01:58:37', '0');
INSERT INTO `message` VALUES ('18', '4', '4', '自己', '2018-12-10 22:35:30', '1');
INSERT INTO `message` VALUES ('19', '4', '4', '没毛病', '2018-12-10 22:35:41', '1');
INSERT INTO `message` VALUES ('20', '4', '6', '在吗？', '2018-12-10 23:58:37', '1');
INSERT INTO `message` VALUES ('21', '6', '4', '好的', '2018-12-10 23:59:07', '1');
INSERT INTO `message` VALUES ('22', '4', '4', '我明天去找老师', '2018-12-13 21:17:14', '1');
INSERT INTO `message` VALUES ('23', '4', '4', '一起吗', '2018-12-13 21:17:27', '1');
INSERT INTO `message` VALUES ('24', '2', '1', '你好', '2018-12-14 15:24:11', '1');
INSERT INTO `message` VALUES ('25', '4', '1', '喜欢你', '2018-12-18 16:38:53', '1');
INSERT INTO `message` VALUES ('26', '1', '4', '我也是', '2018-12-18 16:39:54', '1');
INSERT INTO `message` VALUES ('27', '4', '1', '你好', '2018-12-18 17:57:39', '1');
INSERT INTO `message` VALUES ('28', '1', '1', '1', '2018-12-19 15:07:08', '1');
INSERT INTO `message` VALUES ('29', '1', '1', '213', '2018-12-19 15:21:20', '1');
INSERT INTO `message` VALUES ('30', '1', '1', '22', '2018-12-19 15:21:33', '1');
INSERT INTO `message` VALUES ('31', '1', '1', 'w', '2018-12-19 15:23:10', '1');
INSERT INTO `message` VALUES ('32', '1', '1', 'w', '2018-12-19 15:23:20', '1');
INSERT INTO `message` VALUES ('33', '1', '1', '1', '2018-12-19 15:25:52', '1');
INSERT INTO `message` VALUES ('34', '1', '1', '123', '2018-12-19 15:27:14', '1');
INSERT INTO `message` VALUES ('35', '1', '1', '你好！！', '2018-12-19 15:27:40', '1');
INSERT INTO `message` VALUES ('36', '4', '1', '1', '2018-12-19 15:49:33', '1');
INSERT INTO `message` VALUES ('37', '4', '4', '1', '2018-12-19 15:49:39', '1');
INSERT INTO `message` VALUES ('38', '1', '1', '你好', '2019-03-26 04:27:12', '1');
INSERT INTO `message` VALUES ('39', '1', '3', '你好！！', '2019-03-26 04:27:35', '0');
INSERT INTO `message` VALUES ('40', '3', '1', '你好', '2019-03-26 04:29:00', '1');
INSERT INTO `message` VALUES ('41', '1', '3', '嗯嗯，你好！', '2019-03-26 04:37:31', '0');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `subjects` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mydate` varchar(30) NOT NULL,
  `uid` int(11) NOT NULL,
  `forum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `like` int(11) DEFAULT '0',
  `grade` int(1) DEFAULT '0',
  `postimg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/postimg.jpg',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'unread',
  PRIMARY KEY (`pid`),
  KEY `uid` (`uid`),
  KEY `forum` (`forum`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_ibfk_2` FOREIGN KEY (`forum`) REFERENCES `forum` (`section`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('34', '云上漫步，魔都的早晨', '<p><span>最近几天魔都的上空一直云雾缭绕，昨天早上起了个早，六点赶到苏州河上的法师桥，这里已经聚集了七、八个飞友。从这里起飞，视觉好，正对着陆家嘴的四件套，也有不如意的地方，就是附近商务楼宇多，信号干扰大，一般是飞出300米就受到强烈干扰。下面这个视频，我用了三个机器，分别是</span><a href=\"http://store.dji.com/cn/product/mavic-2?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">MAVIC 2 PRO</a><span>,ZOOM和1PRO。</span><br><br><span>飞2PRO时，除了到三百米距离出现常规性的信号干扰外，一切正常。飞ZOOM时就奇怪了，刚上升，才飞出几十米，高度也只有三十多米，信号一下子不稳定起来，还断过两次，正纳闷是什么原因，抬头一看，我的左右两边又多出两个飞友在操控，估计是小范围内遥控器太多的缘故，于是我跑到桥的另一头，那里人少，之后就比较顺利了。最让人意外的是1PRO，平时信号不太好，昨天不知什么情况，信号比另两个飞机都强，飞了一千米信号没有断过。</span><br><br><p><span>昨天早晨的云很漂亮，就是风比较大，穿云的过程很快。需要注意的是，附近高楼多，一不注意就会越过头顶飞到身后。<img src=\"http://localhost:8090/UAV/layui/images/face/14.gif\" alt=\"[亲亲]\"></span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/4/postimg/132918icc4684qzb5bdb4h.png\" alt=\"undefined\"><br></span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/4/postimg/132918xs9ycy6th5xcscz9.png\" alt=\"undefined\"><br></span></p></p>', '2018-12-06 00:58:55', '4', '精灵系列', '8', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/4/postimg/132918xs9ycy6th5xcscz9.png', 'pass');
INSERT INTO `post` VALUES ('38', 'Select会员Care五折优惠券支持\"御\" Mavic 2 系列！', '<table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\"><tbody><tr><td class=\"t_f\" id=\"postmessage_2670650\"><p>\"御\" Mavic 2 系列终于跟大家见面了，相信有很多尊贵的会员都已经准备好钱包，就等着下单了……<img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/6/postimg/180911vkbqww8whnzcypcq.jpg\" alt=\"undefined\"></p></td></tr></tbody></table><span>也就是说，只要你同时购买\"御\" Mavic 2 专业版/变焦版主机及DJI Care 随心换（Mavic 2），再在下单时选择使用Care五折优惠券，就能马上节省人民币399元！</span><br><br><span>马上把\"御\" Mavic 2加进购物车吧，别忘了用上Care优惠券哦~</span><br><div align=\"center\"><font size=\"4\"><font color=\"#ff0000\"><br></font></font></div><div align=\"center\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://store.dji.com/cn/product/mavic-2?utm_medium=bbs&amp;utm_content=mavic-2-care-coupon&amp;utm_source=djibbs&amp;utm_campaign=bbs-select&amp;vid=45291\" target=\"_blank\"><font size=\"4\"><font color=\"#ff0000\"><span>点击购买\"御\" Mavic 2</span></font></font></a></div><br><span>还不是会员的朋友也不要感到羡慕嫉妒恨，现在加入Select会员，你也可以获得Care五折优惠券，更有配件八折优惠券（暂不支持\"御\" Mavic 2 系列）、生日惊喜、新品优先发货等会员尊享权益，快来看看吧！</span><br><br><div align=\"center\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://store.dji.com/cn/select?utm_medium=bbs&amp;utm_content=mavic-2-care-coupon&amp;utm_source=djibbs&amp;utm_campaign=bbs-select\" target=\"_blank\"><font size=\"4\"><font color=\"#ff0000\"><span>点击进入Select会员主页</span></font></font></a></div><p><br></p>', '2018-12-11 00:04:28', '6', '御系类', '0', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/6/postimg/180911vkbqww8whnzcypcq.jpg', 'pass');
INSERT INTO `post` VALUES ('39', '精灵Phantom 4 Pro暗夜版开箱图赏来袭', '<div class=\"text\"><div name=\"_1_1\">8月31日，在柏林国际电子消费品展览会（IFA）上，精灵<a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Phantom 4 Pro</a>暗夜版以全新的黑色一体化机身亮相。</div></div><div class=\"text\"><p><a class=\"nopicture\" rel=\"noopener\" href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=Social&amp;utm_medium=BBS&amp;utm_campaign=BBS&amp;utm_term=BBS&amp;utm_content=BBS\" target=\"_blank\">点此购买精灵Phantom 4 Pro暗夜版</a>！<img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/6/postimg/142757ruh36jraa9a6ww63.jpg\" alt=\"undefined\"></p><p><span>作为准专业级航拍设备，</span><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版的机身外壳是科技感十足的曜石黑，不仅给足你酷炫的视觉体验，也为广大摄影爱好者和从业者提供了更专业的外形选择。</span><br><br><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版发布后，疆哥就邀请了部分用户体验新品。下面让我们跟随Evan老师的开箱亲测，一探</span><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版的真面目。<img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/6/postimg/142832qo44sbg5sys7ee7g.jpg\" alt=\"undefined\"></span></p></div>', '2018-12-11 00:18:15', '6', '精灵系列', '22', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/6/postimg/142832qo44sbg5sys7ee7g.jpg', 'unread');
INSERT INTO `post` VALUES ('40', '今天是大雾天，小飞了一下拍点重庆的雾景', '<p>字不重要看图：<img src=\"http://localhost:8090/UAV/layui/images/face/2.gif\" alt=\"[哈哈]\"></p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/115413iorj7cccc7e33ha7.jpg\" alt=\"undefined\"><br></p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/115344w6x8ah6vlvlxl5lv.jpg\" alt=\"undefined\"><br></p>', '2018-12-11 01:15:07', '1', 'DOBBY', '0', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/115344w6x8ah6vlvlxl5lv.jpg', 'pass');
INSERT INTO `post` VALUES ('41', '有一座城市叫做宁波', '<p><span>入手两个月了，陆陆续续拍了一些作品。</span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/230030fbodvo121rrrfnl9.jpeg\" alt=\"undefined\"><br></span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/230033k30h3f1e84ewzhk1.jpeg\" alt=\"undefined\"><br></span></p>', '2018-12-11 01:19:35', '1', '晓Spark', '0', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/230033k30h3f1e84ewzhk1.jpeg', 'pass');
INSERT INTO `post` VALUES ('42', '千岛湖——寻皇大草原', '<p><br></p><p><br></p><table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\"><tbody><tr><td class=\"t_f\" id=\"postmessage_66320\">好宁静，好舒服，真的很好的地方！！！</td></tr></tbody></table><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/100547yy3g33mada0j0jfm.jpg\" alt=\"undefined\"></p>', '2018-12-11 01:21:04', '1', 'DOLLCAP', '1', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/100547yy3g33mada0j0jfm.jpg', 'unread');
INSERT INTO `post` VALUES ('43', '《四明山秋色》-航拍画面带给你点颜色看看！', '<p><span>秋天是四明山最美的季节,秋高气爽,艳丽多彩。</span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/140739p4hga9gd5ka11dap.jpg\" alt=\"undefined\"><br></span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/140745a7sanlgpbgpwthkm.jpg\" alt=\"undefined\"><br></span></p>', '2018-12-11 11:55:43', '3', '御系类', '1', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/140745a7sanlgpbgpwthkm.jpg', 'pass');
INSERT INTO `post` VALUES ('44', '小相机更有大学问，大疆产品经理亲述口袋灵眸诞生的故事', '<table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\"><tbody><tr><td class=\"t_f\" id=\"postmessage_2859673\"><div align=\"left\">过往数十年，在大众消费领域把产品做小、做强一直都是电子产品从未停止过的进步趋势。很荣幸，大疆创新的 Mavic 系列无人机也被不少媒体和用户给予了高度的肯定，成为消费级航拍无人机领域里“做小做强”的代表。</div><br><p><font face=\"微软雅黑\"><font size=\"3\">就在 Mavic 系列好评如潮的同时，我们的研发团队也没有停下脚步。今天刚刚发布的<a href=\"http://store.dji.com/cn/product/osmo-pocket?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">灵眸 Osmo 口袋云台相机</a>也是这样一款具有里程碑意义的产品。在此之前，还没有厂商成功把 4K 60fps 100 Mbps 码率高清视频、高精度三轴机械云台、高清触摸取景屏这些功能集成在口红大小的设备中并成功投入量产。就像&nbsp;<a href=\"http://store.dji.com/cn/product/mavic-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Mavic Pro</a>&nbsp;一样，拥有紧凑体积与旗舰性能的<a href=\"http://store.dji.com/cn/product/osmo-pocket?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">口袋灵眸</a>，也将让其他手持云台相机一夜成为过去时。</font></font></p><p><font face=\"微软雅黑\"><font size=\"3\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/192232ks4sx90wjns3j2wj.jpg\" alt=\"undefined\"><br></font></font></p></td></tr></tbody></table>', '2018-12-11 12:09:07', '3', '精灵系列', '0', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/192232ks4sx90wjns3j2wj.jpg', 'unread');
INSERT INTO `post` VALUES ('45', '【征集】全民晒单 发帖有礼！', '<div align=\"center\"><span><font color=\"#696969\">一波儿年终福利闪亮登场~</font></span></div><div align=\"center\"><span><font color=\"#696969\">针对第一时间购买新版电池的老铁们，</font></span></div><div align=\"center\"><font size=\"3\"><font color=\"#ff8c00\"><span>收货晒单、分享心得，<br>就有机会获得购买</span><span>新版电池的</span><span>“</span><span>折扣劵</span><span>”，</span></font></font></div><p><span></span><span></span></p><p style=\"text-align: left;\"><strong><font color=\"#696969\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 事不宜迟，名额有限，先到先得咯！</font></strong></p><p><strong><font color=\"#696969\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/2d62f99afcf4c89108532ef0f729f236.jpg\" alt=\"undefined\"><br></font></strong></p><p><strong><font color=\"#696969\"><br></font></strong></p><p><strong><font color=\"#696969\"><br></font></strong></p>', '2018-12-11 12:12:10', '3', 'DOLLCAP', '66', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/2d62f99afcf4c89108532ef0f729f236.jpg', 'unread');
INSERT INTO `post` VALUES ('46', '大疆12.12年终盛典-疆有心意，最高直降600元，六期免息！', '<table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\" style=\"text-align: center; \"><tbody><tr><td class=\"t_f\" id=\"postmessage_2865439\"><p><br><font face=\"微软雅黑\"><font size=\"3\"><br></font></font><font face=\"微软雅黑\"><font size=\"3\">冬天注定是一个剁手的季节，错过双</font></font><font face=\"微软雅黑\"><font size=\"3\">11</font></font><font face=\"微软雅黑\"><font size=\"3\">的朋友不用担心，这不，双</font></font><font face=\"微软雅黑\"><font size=\"3\">12</font></font><font face=\"微软雅黑\"><font size=\"3\">来啦。接近年底了，不管你是想换点新装备，还是想为爸妈、伴侣、朋友送双旦礼物，来大疆就对了，新意和心意都有了</font></font><font face=\"微软雅黑\"><font size=\"3\">~</font></font><br><span><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#9932cc\">新品抢购，“御”见<a href=\"http://store.dji.com/cn/product/osmo-pocket?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">口袋灵眸</a></font></font></font></span><br></p><div align=\"left\"><font face=\"微软雅黑\"><font size=\"3\"></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"></font></font></div><p><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#2e8b57\">灵眸<a href=\"http://store.dji.com/cn/product/osmo?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Osmo</a><a href=\"http://store.dji.com/cn/product/osmo-pocket?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">口袋云台</a>相机</font></font></font></p><p><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#2e8b57\"><br></font></font></font></p><p><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#2e8b57\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/190233crsj1hst50nr12tr.jpg\" alt=\"undefined\"><br></font></font></font></p></td></tr></tbody></table>', '2018-12-11 12:16:13', '3', '晓Spark', '0', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/190233crsj1hst50nr12tr.jpg', 'lost');
INSERT INTO `post` VALUES ('47', 'DJI大疆创新发布灵眸 Osmo 口袋云台相机', '<div align=\"center\"><span><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#ffff00\"><font>DJI Mimo App</font></font></font></font></span></div><div align=\"center\">**即将上线，敬请期待</div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"4\"><span>下载Mimo：</span></font></font><span><a class=\"nopicture\" rel=\"noopener\" href=\"http://www.dji.com/mimo\" target=\"_blank\"><font color=\"#419ce2\"><div align=\"left\">www.dji.com/mimo</div></font></a></span></div><p><br></p><div align=\"left\"><font face=\"微软雅黑\"><font size=\"3\">作为DJI为手持稳定设备打造的专属应用，DJI Mimo 不仅是一款简洁易用的视频编辑器，更是优质的影像分享平台，我们崇尚创造，鼓励分享，无数旅游达人、专业摄影师与你一同交流，共同成长。</font></font></div><div align=\"left\"><font><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#ffff00\"><br></font></font></font></font></div><div align=\"left\"><font face=\"微软雅黑\"><font size=\"4\"><font color=\"#ffff00\"><span>精准操控，实时预览</span></font></font></font></div><p><font face=\"微软雅黑\"><font size=\"3\">通过 Mimo 可自由控制云台运动，实时预览拍摄画面，调节相机参数。不仅如此，Mimo 还有过曝显示、直方图等进阶选项，助你掌控拍摄细节，释放设备的全部潜能</font></font></p><p><font face=\"微软雅黑\"><font size=\"3\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/030119qz21j1979mz8mi0h.jpg\" alt=\"undefined\"><br></font></font></p>', '2018-12-11 12:18:54', '3', '精灵系列', '55', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/030119qz21j1979mz8mi0h.jpg', 'unread');
INSERT INTO `post` VALUES ('48', '预定口袋灵眸，享线下专属好礼！', '<div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\">灵眸<a href=\"http://store.dji.com/cn/product/osmo?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Osmo</a><a href=\"http://store.dji.com/cn/product/osmo-pocket?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">口袋云台</a>相机现于线下旗舰店预售中</font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\">到店抢先上手体验真机，参与预售活动有好礼！</font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\">更多活动详情请咨询店铺。</font></font></div><p><br></p><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#4169e1\"><span><a class=\"nopicture\" rel=\"noopener\" href=\"https://www.dji.com/cn/where-to-buy/flagship?utm_source=dji-forum&amp;utm_medium=referral&amp;utm_campaign=TinyLaunch\" target=\"_blank\">DJI大疆创新线下旗舰店地址：</a></span></font></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><br></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#800080\">深圳欢乐海岸旗舰店：南山区白石路东8号</font></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#800080\">&nbsp; &nbsp;&nbsp; &nbsp; TEL：0755-86665246</font></font></font></div><p><br></p><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#800080\">&nbsp; &nbsp;&nbsp;&nbsp;上海新天地旗舰店：黄浦区马当路232号</font></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#800080\">&nbsp; &nbsp;&nbsp; &nbsp; TEL：021-53882588</font></font></font></div><p><br></p><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\">&nbsp;&nbsp;<font color=\"#800080\">&nbsp; &nbsp;南京新街口直营店：玄武区中山路132号艾尚天地B座一楼5102号</font></font></font></div><div align=\"center\"><font color=\"#800080\"><font face=\"微软雅黑\"><font size=\"3\">&nbsp; &nbsp;&nbsp; &nbsp; TEL：</font></font><font face=\"微软雅黑\"><font size=\"3\">025-85656101</font></font></font></div><div align=\"center\"><font color=\"#800080\"><font face=\"微软雅黑\"><font size=\"3\"><br></font></font></font></div><div align=\"center\"><font face=\"微软雅黑\"><font size=\"3\"><font color=\"#800080\"><div align=\"left\"><div align=\"center\">香港铜锣湾旗舰店：谢斐道535号Tower 535 地下G07号铺</div></div><div align=\"left\"><p>TEL:852 2639 3122</p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/113142m1b8qk1dqgq6vc8q.jpg\" alt=\"undefined\"><br></p></div></font></font></font></div>', '2018-12-11 12:22:09', '3', 'DOLLCAP', '1', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/113142m1b8qk1dqgq6vc8q.jpg', 'unread');
INSERT INTO `post` VALUES ('49', '新-\"晓\" Spark 操控套装', ' <p style=\"text-align: center;\"><b>参数信息</b></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIM20181213145250.png\" alt=\"undefined\"></p><p><br></p><p style=\"text-align: center;\"><b>商品展示</b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/6be79673-1ac1-48f1-b9c1-af43e6607701.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/1960bd96-62ba-4a9b-a8cc-8272a38fdff7.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIcc20181219144842.png\" alt=\"undefined\"><br></b></p>', '2018-12-13 14:58:05', '1', '晓Spark', '5', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/1960bd96-62ba-4a9b-a8cc-8272a38fdff7.jpg', 'pass');
INSERT INTO `post` VALUES ('50', '精灵 Phantom 4 Pro V2.0', '<p style=\"text-align: center;\"><b>主要参数</b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIMcans20181213194840.png\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/bf9c9e48-05ff-4353-a378-3c8dba212a25.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIM11120181213194804.png\" alt=\"undefined\"><br></b></p>', '2018-12-13 19:53:16', '1', '精灵系列', '11', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIM11120181213194804.png', 'unread');
INSERT INTO `post` VALUES ('52', '\"御\" Mavic 2 专业版', '<p style=\"text-align: center;\"><b>主要参数</b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIMyuxilei20181213195436.png\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/yuxilac81f46b-18a1-4e9e-b49e-154e5860c35c.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/yuxileiaebbdc1c-1540-4f30-82f8-b264a7d02f90.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/xuxileitou20181213195618.png\" alt=\"undefined\"><br></b></p>', '2018-12-13 20:00:43', '1', '御系类', '123', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/xuxileitou20181213195618.png', 'pass');
INSERT INTO `post` VALUES ('53', '\"晓\" Spark 操控套装', '<p style=\"text-align: center;\"><b>主要参数</b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIMsparkcan20181213200248.png\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/spark8f77af42-33d5-406a-b0f0-1db109c56b7b.jpg\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIspark20181213200227.png\" alt=\"undefined\"><br></b></p><p style=\"text-align: center;\"><b><br></b></p>', '2018-12-13 20:05:30', '1', '晓Spark', '22', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/1/postimg/TIspark20181213200227.png', 'unread');
INSERT INTO `post` VALUES ('54', '御2载重', '<p><span>我准备给</span><a href=\"http://store.dji.com/cn/product/mavic-2?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">御2</a><span>装个抛投器，请问下社区里的大佬</span><a href=\"http://store.dji.com/cn/product/mavic-2?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">御2</a><span>最大能挂多重</span></p><p><span><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/22115656oguegm69obucgzg0.jpg\" alt=\"undefined\"><br></span></p>', '2018-12-18 14:59:08', '2', '御系类', '77', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/22115656oguegm69obucgzg0.jpg', 'unread');
INSERT INTO `post` VALUES ('55', '布朗熊版“晓”SPARK 无人机来啦！', '<p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/12114425pwmwfxwvf1d6ex1m.jpg\" alt=\"undefined\"></p><p><br></p><div align=\"center\">DJI 与&nbsp;<a href=\"http://store.dji.com/cn/product/spark-line-friends-brown?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">LINE FRIENDS</a>&nbsp;合作，推出<a href=\"http://store.dji.com/cn/product/spark-line-friends-brown?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">布朗熊版“晓”SPARK 无人机</a></div><div align=\"center\">自带天然萌气质</div><p><br></p><div align=\"center\"><span><font color=\"#ff0000\">现已在大疆官方商城和深圳/上海旗舰店同步限量发售</font></span></div><div align=\"center\"><span><font color=\"#ff0000\"><br></font></span></div><p><span><font size=\"5\"><font color=\"#ff0000\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://store.dji.com/cn/product/spark-line-friends-brown?pbc=dapcnbbs&amp;utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=bbs_Sidebar&amp;utm_term=BBS&amp;utm_content=BBS\" target=\"_blank\">点此抢购LINE FRIENDS（布朗熊）| “晓” Spark 操控套装</a></font></font></span></p><p><span><font size=\"5\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/21112609h886e8p2ytexf8xo.jpg\" alt=\"undefined\"><br></font></span></p><p><span><font size=\"5\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/TIMjt20181218150207.png\" alt=\"undefined\"><br></font></span></p>', '2018-12-18 15:02:31', '2', '晓Spark', '15', '0', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/TIMjt20181218150207.png', 'unread');
INSERT INTO `post` VALUES ('56', '精灵Phantom 4 Pro暗夜版开箱图赏来袭', '<div class=\"text\"><div name=\"_1_1\">8月31日，在柏林国际电子消费品展览会（IFA）上，精灵<a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Phantom 4 Pro</a>暗夜版以全新的黑色一体化机身亮相。</div></div><div class=\"text\"><p><a class=\"nopicture\" rel=\"noopener\" href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=Social&amp;utm_medium=BBS&amp;utm_campaign=BBS&amp;utm_term=BBS&amp;utm_content=BBS\" target=\"_blank\">点此购买精灵Phantom 4 Pro暗夜版</a>！</p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/33143419w9k8sqb9w8kvunnk.jpg\" alt=\"undefined\"><br></p><p><span>作为准专业级航拍设备，</span><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版的机身外壳是科技感十足的曜石黑，不仅给足你酷炫的视觉体验，也为广大摄影爱好者和从业者提供了更专业的外形选择。</span><br><br><p><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版发布后，疆哥就邀请了部分用户体验新品。下面让我们跟随Evan老师的开箱亲测，一探</span><a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a><span>&nbsp;Pro暗夜版的真面目。</span></p></p><div name=\"_1_11\">镜头方面，保留了精灵系列无人机的最高配置，配备了1英寸2000万像素CMOS影像传感器以及7组8片全玻璃镜片组成的高解析度镜头。</div><div class=\"text\"></div><div class=\"text\"><div name=\"_1_12\">以上就是Evan老师的全部分享，<a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a>&nbsp;Pro暗夜版作为准专业级航拍设备不仅满足准专业航拍摄影需求，更为用户提供非一般的飞行体验，疆粉们对<a href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">精灵4</a>&nbsp;Pro暗夜版有何高见？可以回帖讨论，技术大大们会密切关注各位的建议和意见滴~</div></div><div class=\"text\"><p><a class=\"nopicture\" rel=\"noopener\" href=\"http://store.dji.com/cn/product/phantom-4-pro?utm_source=Social&amp;utm_medium=BBS&amp;utm_campaign=BBS&amp;utm_term=BBS&amp;utm_content=BBS\" target=\"_blank\">点此购买精灵Phantom 4 Pro暗夜版</a>！</p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/TIM12320181218150539.png\" alt=\"undefined\"><br></p></div></div>', '2018-12-18 15:06:01', '2', '精灵系列', '11', '1', 'F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/TIM12320181218150539.png', 'unread');

-- ----------------------------
-- Table structure for punch
-- ----------------------------
DROP TABLE IF EXISTS `punch`;
CREATE TABLE `punch` (
  `uid` int(11) NOT NULL,
  `punchtime` date NOT NULL,
  `sum` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`),
  CONSTRAINT `punch_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of punch
-- ----------------------------
INSERT INTO `punch` VALUES ('1', '2018-12-19', '1');
INSERT INTO `punch` VALUES ('2', '2018-12-18', '0');
INSERT INTO `punch` VALUES ('3', '2018-12-10', '1');
INSERT INTO `punch` VALUES ('4', '2018-12-26', '0');
INSERT INTO `punch` VALUES ('5', '2018-12-05', '1');
INSERT INTO `punch` VALUES ('6', '2018-12-11', '1');
INSERT INTO `punch` VALUES ('33', '2018-12-18', '0');
INSERT INTO `punch` VALUES ('99999', '2008-01-08', '1');

-- ----------------------------
-- Table structure for teach
-- ----------------------------
DROP TABLE IF EXISTS `teach`;
CREATE TABLE `teach` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text NOT NULL,
  `uid` int(11) NOT NULL,
  `date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `videopath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `uid` (`uid`),
  CONSTRAINT `teach_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teach
-- ----------------------------
INSERT INTO `teach` VALUES ('1', '无人机冬季低温安全飞行指南', '<p style=\"text-align: center;\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/4/postimg/jiaoc115942ewj6gx9gz6wq1qjw.jpg\" alt=\"undefined\"></p><p style=\"text-align: center;\"><br style=\"text-align: start;\"><span style=\"text-align: start;\">“广东下雪了”上热搜了，连广东的飞友们都已经开始吐槽：出去飞飞机手好冷。。。。北方的飞友们：你们还好吗？</span><br style=\"text-align: start;\"><font color=\"#303030\" style=\"text-align: start;\"><font face=\"微软雅黑\"><font size=\"3\">由于近期有用户遇到各种冬季飞行的问题，如电压骤降、飞行动力不足，甚至更为严重的飞行器坠机情况。为此，我们推出冬季安全飞行指引，帮助大家更好地拍摄冬日美景。</font></font></font><br style=\"text-align: start;\"><br style=\"text-align: start;\"><span style=\"text-align: start;\">当然，大疆社区里有大量的无人机冬季飞行指南，其实把现有的教程和飞行知识消化了，已经能让你在冬季爽飞了。为了能更系统的学习，所有的无人机冬季飞行注意事项已经汇总在这里，请大家收藏学习哦！</span><br style=\"text-align: start;\"><br style=\"text-align: start;\"></p><div align=\"center\"><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#ff0000\"><span>官方篇</span></font></font></font></div><p style=\"text-align: center;\"><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#0000ff\">1. 低温安全飞行指南</font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-36357-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-36357-1-1.html</a></font></font></font><br style=\"text-align: start;\"><span style=\"text-align: start;\">简介：</span><font color=\"#000000\" style=\"text-align: start;\">介绍了<font face=\"微软雅黑\"><font size=\"3\">锂电池工作机理、</font></font><font face=\"微软雅黑\"><font size=\"3\">无人机锂电池飞行原理、</font></font><font face=\"微软雅黑\"><font size=\"3\">电池冬季使用建议。从理论到实践，一定要看！</font></font></font><br style=\"text-align: start;\"><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#0000ff\">2. 冬季安全飞行贴士</font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-32515-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-32515-1-1.html</a></font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\">简介：<font color=\"#303030\">当电池暴露低于15℃的环境下时，电池的化学物质活性显著降低。其内阻增大导致放电能力降低。电池放电时电压降加大。此时，电池的输出功率受限，飞行的机动性能下降，如果强制飞行会有追加的风险。千万别贪飞哦！！</font></font></font><br style=\"text-align: start;\"><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#0000ff\">3.小兴安岭伊春 -&nbsp;<a href=\"http://store.dji.com/cn/product/mavic-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\" class=\"relatedlink\">Mavic Pro</a>冰雪航拍实战（附冬季航拍攻略）</font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-173657-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-173657-1-1.html</a></font></font></font><br style=\"text-align: start;\"><span style=\"text-align: start;\">简介：</span><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\">隆冬时节伊春气温基本在-20°C以下，冰雪时期的伊春最具魅力。《焦点》杂志本次带着便携无人机&nbsp;</font><a class=\"nopicture\" rel=\"noopener\" href=\"http://store.dji.com/cn/product/mavic-pro?utm_source=djibbs&amp;utm_medium=bbs&amp;utm_campaign=keywords&amp;pbc=dapcnbbs\" target=\"_blank\">Mavic Pro</a><font color=\"#303030\">，来到这片冬天的故土进行拍摄。</font></font></font><br style=\"text-align: start;\"></p><div align=\"center\"><span><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#ff0000\"><br></font></font></font></span></div><div align=\"center\"><span><font face=\"微软雅黑\"><font size=\"5\"><font color=\"#ff0000\">飞友篇</font></font></font></span></div><p style=\"text-align: center;\"><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#0000ff\">1. 冬季飞行，不光是要预热飞机</font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\">作者：<a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/home.php?mod=space&amp;uid=40707&amp;do=index&amp;view=admin&amp;uuid=648349827f2c068d0b324afd32bae07ddc7cfc1f2db747d28a\" target=\"_blank\">ZDD</a></font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\">链接：<a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-41028-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-41028-1-1.html</a></font></font></font><br style=\"text-align: start;\"><span style=\"text-align: start;\">简介：南方飞友去东北后，悟出的冬季航拍秘籍。实践出真知~</span><br style=\"text-align: start;\"><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#0000ff\">2.无人机冬季飞行技巧总结</font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\">作者：<a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/home.php?mod=space&amp;uid=14665&amp;do=index&amp;view=admin&amp;uuid=c545e0e11e1dcab611ce7bae951437524db3218d586a4c82a6\" target=\"_blank\">不会飞的舒克</a></font></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\">链接：<a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-35679-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-35679-1-1.html</a></font></font><br style=\"text-align: start;\"><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\">简介：冬季航拍会遇到哪些诡异的问题？</font></font></font><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#000000\">飞控的电子设备可能会工作不正常、机架的塑料件可能变脆，强度降低.....</font></font></font><br style=\"text-align: start;\"><br style=\"text-align: start;\"><font color=\"#0000ff\" style=\"text-align: start;\"><font face=\"微软雅黑\"><font size=\"3\">3.</font></font><font face=\"微软雅黑\"><font size=\"3\">冬季航拍有哪些注意事项？</font></font></font><br style=\"text-align: start;\"><font color=\"#303030\" style=\"text-align: start;\"><font face=\"微软雅黑\"><font size=\"3\">链接：</font></font></font><font face=\"微软雅黑\" style=\"text-align: start;\"><font size=\"3\"><font color=\"#303030\"><a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-103779-1-1.html\" target=\"_blank\">https://bbs.dji.com/thread-103779-1-1.html</a></font></font></font><br style=\"text-align: start;\"><span style=\"text-align: start;\">简介：来自多位飞友分享的冬季航拍注意事项。</span><br style=\"text-align: start;\"><br style=\"text-align: start;\"><font color=\"#0000ff\" style=\"text-align: start;\">4.冬季的汽车短片可以这么拍——零下30℃与冰雪共舞的台前幕后</font><br style=\"text-align: start;\"><span style=\"text-align: start;\">作者：在身边</span><br style=\"text-align: start;\"><span style=\"text-align: start;\">链接：</span><a class=\"nopicture\" rel=\"noopener\" href=\"https://bbs.dji.com/thread-41171-1-1.html\" target=\"_blank\" style=\"text-align: start;\">https://bbs.dji.com/thread-41171-1-1.html</a><br style=\"text-align: start;\"><br style=\"text-align: start;\"><br style=\"text-align: start;\"><span style=\"text-align: start;\">附上优质的冬季航拍作品，祝大家冬季爽飞！</span><br style=\"text-align: start;\"><br style=\"text-align: start;\"></p><p style=\"text-align: center; \"><font size=\"5\"><font color=\"#ff0000\"><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 飞友作品</span></font></font></p><p><font size=\"5\"><font color=\"#ff0000\"><span><br></span></font></font></p>', '4', '2018-12-13 21:34:30', 'F:/workspace/UAV/WebContent/video/sd (6).mp4');
INSERT INTO `teach` VALUES ('2', '无人机旅游大片怎么拍？记住这几个要点就行，抓紧看', '<p style=\"text-align: center;\"><br></p><p style=\"text-align: center;\">嘿大家好我是阿岛<br>没想到吧这么快又见面了<br>有段时间没给大家做投稿点评了<br>这回点评有所不同的是我仅点评一部作品<br>而不是像往期一样把几个投稿合起来<br>这样做的好处是可以更深度的剖析且给出建议<br><br>这是一部旅游短片<br>也是咱们大家最常会拍摄到的主题<br>但如果能够再优化一些细节品质则会进一步提升<br>以下是我的点评正片</p><p style=\"text-align: center;\"><br></p><p style=\"text-align: center;\">今天就由我来给大家做一期</p><p style=\"text-align: center;\"><br></p><p style=\"text-align: center;\">事实上我认为这部短片质量已经OK</p>', '4', '2018-12-17 18:03:55', 'F:/workspace/UAV/WebContent/video/sd (7).mp4');
INSERT INTO `teach` VALUES ('3', '无人机航线规划软件Altizure操作介绍', '<table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\"><tbody><tr><td class=\"t_f\" id=\"postmessage_2854867\">利用Altizure航线规划软件，设置倾斜摄影外业建模航线，全自动航线数据采集，实现快速倾斜摄影数据采集</td></tr></tbody></table><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/JC192424iwp3nj398zu4c94j.jpg\" alt=\"undefined\"></p><p><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/3/postimg/192425nmemzzvezbrb6z1w.jpg\" alt=\"undefined\"><br></p><p><br></p><br><br>', '3', '2018-12-18 14:38:11', 'null');
INSERT INTO `teach` VALUES ('4', '手把手教你装机（F450机架+Naza-M Lite ）', '<table cellspacing=\"0\" cellpadding=\"0\" class=\"layui-table\"><tbody><tr><td class=\"t_f\" id=\"postmessage_856711\"><div align=\"left\"><div align=\"left\"><font face=\"微软雅黑\"><font size=\"4\">不少新手模友在购买F450和Naza-M系列飞控时，表示从安装调试到飞机成功起飞的一套流程太难、太繁琐。楼主想说其实并不难，只是你装机的姿势不对。</font></font></div><div align=\"left\"><font face=\"微软雅黑\"><font size=\"4\"><br></font></font></div><div align=\"left\"><font face=\"微软雅黑\"><font size=\"4\">以下面三套装备为模友详细介绍</font></font><font color=\"#8b0000\"><font face=\"微软雅黑\"><font size=\"4\">F450机架+Naza-M Lite组合</font></font></font><font face=\"微软雅黑\"><font size=\"4\">从装机到FLY的正确姿势。</font></font></div></div><div align=\"left\"><font size=\"4\"><br></font></div><div align=\"left\"><font face=\"微软雅黑, sans-serif\"><font size=\"5\"><span>装备如下：</span></font></font></div><div align=\"left\"><font face=\"微软雅黑, sans-serif\"><font size=\"4\">F450机架、脚架、NAZA-M LITE飞控、GPS、格氏电池及充电器、动力套装、乐迪AT9遥控器及R9DS接收机。</font></font></div><div align=\"left\"><font face=\"微软雅黑, sans-serif\"><font size=\"4\"><br></font></font></div><p><font face=\"微软雅黑, sans-serif\"><font size=\"4\">F450机架</font></font></p><p><font face=\"微软雅黑, sans-serif\"><font size=\"4\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/4170207z65w475h5uk776ho.jpg\" alt=\"undefined\"><br></font></font></p><p><font face=\"微软雅黑, sans-serif\"><font size=\"4\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/3164748zhl76izpzybhbabb.jpg\" alt=\"undefined\"><br></font></font></p><div align=\"left\"><font face=\"微软雅黑, sans-serif\"><font size=\"5\"><span>进入正题</span></font></font></div><div align=\"left\"><font face=\"微软雅黑, sans-serif\"><font size=\"4\"><span>首先进行F450机架安装。</span></font></font></div><p><font size=\"4\">1.将四个电调焊接至机身下板，<font color=\"#8b0000\">红正黑负</font>。</font></p><p><span>2.并焊好动力电接口，再将多功能模块的电源接口焊接至电源焊盘上。</span></p><p><span>焊接完成</span></p><div align=\"left\"><font size=\"4\">3.用万用表检查电路是否连通。</font></div><p><font size=\"4\">4.</font><font size=\"4\">用螺丝将电机固定在机臂上，并将机臂安装在机身下板上。</font></p><p><font size=\"4\"><img src=\"UpAdown?method=postimgs&amp;pathname=F:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/UAV/WEB-INF/images/2/postimg/11164750pu9gygwg5lyszc6z.jpg\" alt=\"undefined\"><br></font></p></td></tr></tbody></table>', '2', '2018-12-18 15:12:18', 'null');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nick` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'hello',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '男',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '福州',
  `sign` varchar(255) DEFAULT NULL,
  `uauth` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'comm',
  `fraction` int(11) DEFAULT '0',
  `imgname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'dh.jpg',
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '008', '123456', '徐小樱', '女', '乌拉尔', '明媚如风，清风似我。', 'admin', '30', '1headimg.jpg', null);
INSERT INTO `user` VALUES ('2', '123123', '123123', '徐sir', '男', null, null, 'comm', '210', 'dh.jpg', '904212688@qq.com');
INSERT INTO `user` VALUES ('3', '888888', '888888', '八爷', '男', '北京故宫', '人拥有的东西没有比光明更加贵重的了，所以请不要浪费光阴。', 'moder', '505', '3headimg.jpg', null);
INSERT INTO `user` VALUES ('4', 'testes', 'testes', '陈小宝', '男', '南街', '你好啊！', 'moder', '100', '4headimg.jpg', null);
INSERT INTO `user` VALUES ('5', '20181116', '20181116', '小王吧', '男', null, null, 'moder', '1', 'dh.jpg', null);
INSERT INTO `user` VALUES ('6', '20181118', '20181118', 'UAV大哥', '男', '福州', null, 'comm', '5', '6headimg.jpg', null);
INSERT INTO `user` VALUES ('7', 'xufengnian', '123456', 'hello', '男', '福州', null, 'moder', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('8', '001', '123456', 'hello', '男', '福州', null, 'moder', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('9', '002', '123456', 'hello', '男', '福州', null, 'moder', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('10', '003', '123456', 'hello', '男', '福州', null, 'moder', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('28', 'qwewqe', 'qweqwe', 'hello', '男', '福州', null, 'comm', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('30', 'xufugang', 'xufugang', 'hello', '男', '福州', null, 'none', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('32', '王五', '123456', 'hello', '男', '福州', null, 'comm', '0', 'dh.jpg', null);
INSERT INTO `user` VALUES ('33', 'lisi', '123456', 'hello', '男', '福州', null, 'comm', '10', 'dh.jpg', null);
INSERT INTO `user` VALUES ('99999', '1', '1', 'hello', '男', '福州', null, 'comm', '0', 'dh.jpg', null);

-- ----------------------------
-- Table structure for wdiscuss
-- ----------------------------
DROP TABLE IF EXISTS `wdiscuss`;
CREATE TABLE `wdiscuss` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `discuss` varchar(255) NOT NULL,
  `uid` int(11) NOT NULL,
  `report` int(11) DEFAULT NULL,
  `wid` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of wdiscuss
-- ----------------------------

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works` (
  `workpath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `uid` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'unread',
  `like` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  CONSTRAINT `works_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES ('12', '1', '1', '23', '3113', 'daf', 'lost', '0');
INSERT INTO `works` VALUES ('F:/workspace/UAV/WebContent/video/720.mp4', '4', '2', '2018 In My Eyes ---2018年度个人延时&航拍视频集', '大家请看，我花了半年的杰作。多多观赏多多点赞。<img src=\"http://localhost:8090/UAV/layui/images/face/46.gif\" alt=\"[互粉]\">', '2018-12-09 22:08:29', 'unread', '0');
INSERT INTO `works` VALUES ('F:/workspace/UAV/WebContent/video/sd (3).mp4', '6', '3', '穿越机视角的速度与激情，你航拍机永远不懂', '<div align=\"left\"><font><font face=\"-apple-system-font, BlinkMacSystemFont, &amp;quot\"><font>大家好，我是HeyDrones首席电工。先前飞哥给大家讲过几期穿越无人机，大家可能还以为穿越无人机只能用来竞速？</font></font></font></div><div align=\"left\"><font><font face=\"-apple-system-font, BlinkMacSystemFont, &amp;quot\"><font>不要小看穿越机，其实，<span>穿越机也能用来航拍</span>。</font></font></font></div><p><br></p><div align=\"center\"><font><font face=\"-apple-system-font, BlinkMacSystemFont, &amp;quot\"><font>不信来看</font></font></font></div><div align=\"center\"><font><font face=\"-apple-system-font, BlinkMacSystemFont, &amp;quot\"><font>▽</font></font></font></div>', '2018-12-11 01:00:26', 'pass', '0');
INSERT INTO `works` VALUES ('F:/workspace/UAV/WebContent/video/sd (4).mp4', '1', '4', '江西·南昌', '<p style=\"text-align: center; \"><span>新手帖--江西·南昌市<img src=\"http://localhost:8090/UAV/layui/images/face/2.gif\" alt=\"[哈哈]\"></span></p><p style=\"text-align: center; \"><span><br></span></p>', '2018-12-11 01:27:15', 'unread', '0');
