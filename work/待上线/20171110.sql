USE jkd_user;

# 1.添加用户微信会员卡绑定关系表
CREATE TABLE `user_weixin_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `open_id` varchar(50) NOT NULL COMMENT '联合登录的唯一标示',
  `open_union_id` varchar(64) DEFAULT NULL COMMENT '第三方多平台绑定后唯一ID',
  `user_card_code` varchar(50) NOT NULL COMMENT '微信会员卡编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户微信会员卡绑定关系表';

USE jiukuaidao;
# 2.整合微信优惠券核销记录
ALTER TABLE `act_coupon_detail`
MODIFY COLUMN `sn`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券序列号' AFTER `act_coupon_id`,
MODIFY COLUMN `sn_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号' AFTER `sn`;