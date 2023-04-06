package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ObjectRecordTagEnum;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamObj2RO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import com.norm.timemall.app.team.domain.vo.TeamObjInfoVO;
import com.norm.timemall.app.team.domain.vo.TeamObjPageVO;
import com.norm.timemall.app.team.domain.vo.TeamObjVO;
import com.norm.timemall.app.team.service.TeamObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamObjController {
    @Autowired
    private TeamObjService teamObjService;
    @Autowired
    private OrderFlowService orderFlowService;
    /**
     * 获取obj列表
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj")
    public TeamObjPageVO retrieveObj(@Validated TeamObjPageDTO dto){
        IPage<TeamObjRO> objRos = teamObjService.findObjs(dto);
        TeamObjPageVO vo = new TeamObjPageVO();
        vo.setObj(objRos);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    /**
     * 获取obj info
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj/by_swapno")
    public TeamObjVO retrieveObj(@Validated TeamObjDTO dto){
        TeamObjRO objRo = teamObjService.findObj(dto);
        TeamObjVO vo = new TeamObjVO();
        vo.setObj(objRo);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    /**
     * 获取obj info
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj/{obj_id}")
    public TeamObjInfoVO retrieveObj(@PathVariable("obj_id") String objId){
        TeamObj2RO objRo = teamObjService.findObjInfo(objId);
        TeamObjInfoVO vo = new TeamObjInfoVO();
        vo.setObj(objRo);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/swap_cell")
    public SuccessVO swapCell(@Validated  @RequestBody TeamSwapCellDTO dto){
        teamObjService.swapCell(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * 获取拥有的objs
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj/me")
    public TeamObjPageVO retrieveObjOwned(@Validated TeamOwnedObjPageDTO dto){
        IPage<TeamObjRO> objRos = teamObjService.findOwnedObjs(dto);
        TeamObjPageVO vo = new TeamObjPageVO();
        vo.setObj(objRos);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/obj/tag")
    public SuccessVO tagObj(@RequestBody  TeamTagObjDTO dto){
        teamObjService.tagObj(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/obj/{obj_id}/using")
    public SuccessVO useObj(@PathVariable("obj_id") String objId ){
        // orderFlow ctl repeat processing
        orderFlowService.insertOrderFlow(objId, ObjectRecordTagEnum.IN_USE.getMark());
                teamObjService.useObj(objId);
        orderFlowService.deleteOrderFlow(objId, ObjectRecordTagEnum.IN_USE.getMark());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/obj/pricing")
    public SuccessVO settingObjSalePrice(@Validated @RequestBody TeamObjPricingDTO dto){
        teamObjService.modifyObjSalePrice(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * 获取需要履约的objs
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/obj/todo")
    public TeamObjPageVO retrieveTodoObj(@Validated TeamTodoObjPageDTO dto){
        IPage<TeamObjRO> objRos = teamObjService.findtodoObjs(dto);
        TeamObjPageVO vo = new TeamObjPageVO();
        vo.setObj(objRos);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/team/obj/mark")
    public SuccessVO markObj(@Validated @RequestBody TeamMarkObjDTO dto){
        teamObjService.markObj(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
