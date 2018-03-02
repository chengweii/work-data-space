USE jiukuaidao;
# 1.新增电子价签同步表
drop table if exists price_tag_change;
CREATE TABLE `price_tag_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NOT NULL COMMENT '业务类型（1：店铺，2：商品）',
  `object_id` varchar(50) NOT NULL COMMENT '变更对象ID（店铺id【shop_base.id】或商品条码【pro_product.bar_code】）',
  `sync_rule` varchar(2000) DEFAULT NULL COMMENT '同步规则',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sync_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步状态（0：待同步，1：同步成功，2：同步失败）',
  `sync_times` tinyint(2) NOT NULL DEFAULT '0' COMMENT '同步次数',
  `last_sync_time` datetime DEFAULT NULL COMMENT '最后同步时间',
  `next_sync_time` datetime DEFAULT NULL COMMENT '下次同步时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子价签同步表';
# 1.1 新增电子价签店铺同步表
drop table if exists price_tag_change_product;
CREATE TABLE `price_tag_change_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `shop_id` int(11) NOT NULL COMMENT '店铺id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sync_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步状态（0：待同步，1：同步成功，2：同步失败）',
  `last_sync_time` datetime DEFAULT NULL COMMENT '最后同步时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子价签商品同步结果表';
# 1.2 新增电子价签商品同步表
drop table if exists price_tag_change_shop;
CREATE TABLE `price_tag_change_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) NOT NULL COMMENT '店铺id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `sync_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步状态（0：待同步，1：同步成功，2：同步失败）',
  `last_sync_time` datetime DEFAULT NULL COMMENT '最后同步时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='电子价签店铺同步结果表';
