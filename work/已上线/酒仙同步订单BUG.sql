USE jkd_scm;
# 酒仙同步订单状态不一致问题修复
ALTER TABLE `sel_sell_bill`
ADD COLUMN `tansaction_state`  tinyint(1) NOT NULL DEFAULT 0 COMMENT '0：处理中，1：已完成' AFTER `synchronized_state`;
