package com.norm.timemall.app.affiliate.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchInfluencerProductPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchInfluencerProductRO;
import com.norm.timemall.app.base.mo.AffiliateInfluencerProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 橱窗表(affiliate_influencer_product)数据Mapper
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface AffiliateInfluencerProductMapper extends BaseMapper<AffiliateInfluencerProduct> {

    IPage<FetchInfluencerProductRO> selectPageByDTO(IPage<FetchInfluencerProductRO> page,@Param("dto") FetchInfluencerProductPageDTO dto);
}