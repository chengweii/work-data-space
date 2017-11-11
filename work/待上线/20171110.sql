USE jkd_user;

# 1.添加绑定的微信会员卡编号字段
ALTER TABLE `user_base`
ADD COLUMN `user_card_code`  varchar(20) NULL COMMENT '微信会员卡编号' AFTER `open_union_id`;