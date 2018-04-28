# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID = '733' and t.status = 4;
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID = '3110' and t.MerchantFlag = 2;

# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID IN ('988','703');
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID  IN ('4501','3038');

# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID = '852';
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID  = '3344';

# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID = '1041';
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID  = '8576';​​

# 修改店铺状态由 限制权限 到 审核通过
update zntg_jxweb.tg_Merchant t set t.status = 2 where t.ID IN ('915','838','439');
# 修改用户资质状态由 准备取消合作的商家 到 正常商家
update zntg_user.tg_UserQualifications t set t.MerchantFlag = 1 where t.ID  IN ('3524','3306','1528');
