package com.norm.timemall.app.pod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.base.mo.Millstone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * (millstone)数据Mapper
 *
 * @author kancy
 * @since 2022-10-26 19:43:08
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface PodMillstoneMapper extends BaseMapper<Millstone> {
    @Update(value = "update millstone set mark = #{code} where order_id=#{id}")
    void updateWorkflowByIdAndCode(@Param("id") String workflwoId, @Param("code") String code);

}
