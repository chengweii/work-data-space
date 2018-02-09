USE jkd_user;
# 1.POS收银注册会员绑定店铺ID
ALTER TABLE `user_ext`
ADD COLUMN `shop_id`  int(11) NOT NULL DEFAULT 0 COMMENT '店铺id' AFTER `address`;