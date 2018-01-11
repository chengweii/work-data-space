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

# 1.1 新增支付方式ID字段
ALTER TABLE `sel_sell`
ADD COLUMN `pay_way_id`  tinyint(4) NULL DEFAULT 0 COMMENT '支付方式id(手机银联：16，微信支付：24，手机支付宝：26,线下汇款：31)' AFTER `process_type`;
ALTER TABLE `sel_sell`
MODIFY COLUMN `pay_type`  tinyint(4) NOT NULL COMMENT '支付方式（0：支付宝支付；1：网银；2：线下汇款；3：微信支付；4：手机银联Token支付）' AFTER `zip_code`;

USE jiukuaidao;
# 2.修改汇款账号信息
UPDATE `jiukuaidao`.`article` SET `content`='<style>p {margin: 0; font-size: 14px; line-height: 2;} body {margin:0;} .wrap{padding: 10px;}</style><h2 style=\" margin: 10px 0 0 0; text-align: center; font-size: 18px; font-weight:bold;\">如何汇款</h2><div class=\"wrap\">            <p>进货单提交成功！</p>        <p>为了保证您能够尽快收到货物，请于三日内通过以下方式汇款到酒快到账户：</p><p>公司名称：北京酒仙网新零售有限公司</p>    <p>开户行：中国银行股份有限公司北京中银大厦支行</p>    <p>银行账号：341566661646</p>    <p>公司地址：北京市北京经济技术开发区经海五路58号院10号楼3层</p></div>', `update_time`='2017-12-26 10:21:14' WHERE `id`='16';
# 3.支付方式可用开关配置
INSERT INTO `jiukuaidao`.`m_config` (`key`, `value`, `type`, `is_delete`, `name`) VALUES ('available_payway_list', '[{\"payWayId\":\"16\",\"payWayName\":\"手机银联\",\"payType\":\"2\",\"isEnabled\":\"0\"},{\"payWayId\":\"24\",\"payWayName\":\"微信支付\",\"payType\":\"2\",\"isEnabled\":\"1\"},{\"payWayId\":\"26\",\"payWayName\":\"手机支付宝\",\"payType\":\"2\",\"isEnabled\":\"1\"},{\"payWayId\":\"31\",\"payWayName\":\"线下汇款\",\"payType\":\"1\",\"isEnabled\":\"1\"}]', '0', '0', '可用支付方式列表');


