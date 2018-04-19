# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID = '733' and t.status = 4;
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID = '3110' and t.MerchantFlag = 2;
