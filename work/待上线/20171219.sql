USE jkd_scm;
# 1.新增银联支付绑定表
CREATE TABLE `tg_TokenPayBindingCard` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `identify_code` varchar(100) NOT NULL COMMENT '银行卡唯一标识',
  `bank_card_type` varchar(4) NOT NULL COMMENT '银行卡类型(01借记卡;02贷记卡)',
  `bank_card_tail_no` varchar(20) NOT NULL COMMENT '银行卡尾号',
  `mobile` varchar(11) NOT NULL COMMENT '银行预留手机号',
  `bank_name` varchar(20) NOT NULL COMMENT '银行名称',
  `bank_code` varchar(20) NOT NULL COMMENT '银行简码',
  `token_code` varchar(50) NOT NULL COMMENT '支付Token号',
  `order_no` varchar(50) NOT NULL COMMENT '开通支付订单号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除（0未删除，1已删除）',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Token支付绑定银行卡信息';