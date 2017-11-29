USE jkd_user;

# 1.添加用户微信会员卡绑定关系表
ALTER TABLE `user_card`
MODIFY COLUMN `wx_code`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信会员卡code' AFTER `create_time`;

USE jiukuaidao;
# 2.整合微信优惠券核销记录
ALTER TABLE `act_coupon_detail`
MODIFY COLUMN `sn`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '优惠券序列号' AFTER `act_coupon_id`,
MODIFY COLUMN `sn_no`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号' AFTER `sn`;

USE jkd_order;
# 3.添加折扣金额
ALTER TABLE `ord_pay_coupon`
ADD COLUMN `discount_price`  decimal(10,2) NULL DEFAULT 0.00 AFTER `shop_coupon_price`;