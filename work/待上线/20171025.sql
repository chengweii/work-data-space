USE zntg_jxweb;

# 1.商品同步默认规格设置
ALTER TABLE `core_goods`
MODIFY COLUMN `Ctn`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1*1' COMMENT '箱规' AFTER `Degree`;

# 2.创建代理商所关联的代理商品修改日志表
CREATE TABLE `agent_agent_product_log` (
`id`  int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID' ,
`agent_agent_product_id`  int(11) NOT NULL COMMENT '代理商所关联的代理商品ID' ,
`record`  varchar(500) NOT NULL DEFAULT '新增' COMMENT '操作记录' ,
`operator_id`  int(11) NOT NULL COMMENT '操作人ID' ,
`deleted`  bit(1) DEFAULT b'0' COMMENT '是否已删除（0未删除，1已删除）' ,
`create_time`  datetime NOT NULL COMMENT '创建时间' ,
`modify_time`  datetime DEFAULT NULL COMMENT '修改时间' ,
PRIMARY KEY (`id`),
INDEX `index_agent_agent_product_id` (`agent_agent_product_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='代理商所关联的代理商品修改日志';

# 3.修改返利比例字段类型
ALTER TABLE `agent_agent_product`
MODIFY COLUMN `rebate_percentage_q`  decimal(10,4) NOT NULL COMMENT '季度考核指标达到后的返利比例' AFTER `kpi_y`,
MODIFY COLUMN `rebate_percentage_y`  decimal(10,4) NOT NULL COMMENT '年度考核达标后的返利比例' AFTER `rebate_percentage_q`;

USE zntg_admin;

# 4.添加权限
INSERT INTO `zntg_admin`.`adm_Power` (`ID`, `ParentID`, `PowerName`, `Url`, `Sort`, `Type`, `State`, `CreateTime`, `ModifyTime`, `Ext1`) VALUES ('1328', '757', '搜索订单可用优惠券', '/orderAdd/getIntegral.htm', '0', '3', '1', '2017-08-01 17:39:51', NULL, '');
INSERT INTO `zntg_admin`.`adm_Power` (`ID`, `ParentID`, `PowerName`, `Url`, `Sort`, `Type`, `State`, `CreateTime`, `ModifyTime`, `Ext1`) VALUES ('1329', '757', '搜索订单可用积分', '/orderAdd/getCoupons.htm', '0', '3', '1', '2017-08-01 17:39:51', NULL, '');
INSERT INTO `zntg_admin`.`adm_Power` (`ID`, `ParentID`, `PowerName`, `Url`, `Sort`, `Type`, `State`, `CreateTime`, `ModifyTime`, `Ext1`) VALUES ('1330', '1278', '修改代理商品', '/proxyManage/proxy/proxyModify.htm', '0', '3', '1', '2017-09-22 09:45:23', NULL, '');
INSERT INTO `zntg_admin`.`adm_Power` (`ID`, `ParentID`, `PowerName`, `Url`, `Sort`, `Type`, `State`, `CreateTime`, `ModifyTime`, `Ext1`) VALUES ('1331', '1278', '查看代理商代理商品修改日志', '/proxyManage/proxy/proxyProductLog.htm', '0', '3', '1', '2017-09-22 09:45:23', NULL, '');