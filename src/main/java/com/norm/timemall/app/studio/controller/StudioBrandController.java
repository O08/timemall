package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.pojo.BrandInfo;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.dto.StudioBrandProfileDTO;
import com.norm.timemall.app.base.pojo.BrandContact;
import com.norm.timemall.app.studio.domain.vo.StudioBrandInfoVO;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioBrandController {



    @Autowired
    private StudioBrandService studioBrandService;




    /**
     *
     * 商家资料
     * @param brandId
     * @param dto
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/brand/{brand_id}/profile")
    public SuccessVO modifyBrandProfile(@PathVariable("brand_id") String brandId,
                                        @AuthenticationPrincipal CustomizeUser user,
                                        @RequestBody StudioBrandProfileDTO dto)
    {
        studioBrandService.modifyBrandProfile(brandId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     *
     * 商家联系方式
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/contact")
    public StudioBrandInfoVO getBrandContact(@AuthenticationPrincipal CustomizeUser user){
        BrandInfo brand = studioBrandService.findBrandInfoByUserId(user.getUserId());
        StudioBrandInfoVO vo =new StudioBrandInfoVO();
        vo.setBrand(brand);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }




}
