package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.ChannelTypeEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.GroupMemberPolicyRelEnum;
import com.norm.timemall.app.base.enums.OasisJoinTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import com.norm.timemall.app.team.mapper.TeamGroupMemberRelMapper;
import com.norm.timemall.app.team.mapper.TeamOasisJoinMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMemberMapper;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamOasisJoinServiceImpl implements TeamOasisJoinService {
    @Autowired
    private TeamOasisJoinMapper teamOasisJoinMapper;
    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Autowired
    private TeamGroupMemberRelMapper teamGroupMemberRelMapper;
    @Autowired
    private AccountService accountService;



    @Override
    public ArrayList<TeamInviteRO> findInvitedOasis(String brandId) {
        return teamOasisJoinMapper.selectListByUser(brandId);
    }

    @Override
    public void acceptOasisInvitation(String id) {
        // query oasis_join info
        OasisJoin oasisJoin = teamOasisJoinMapper.selectById(id);
        if(oasisJoin ==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // query oasis member info
        Oasis oasis = teamOasisMapper.selectById(oasisJoin.getOasisId());
        Brand memberBrand=accountService.findBrandInfoByBrandId(oasisJoin.getBrandId());

        // if membership < max_members insert oasis_member else deny
        if(oasis != null && oasis.getMembership() < oasis.getMaxMembers()){
            OasisMember member = new OasisMember();
            member.setId(IdUtil.simpleUUID())
                            .setCreateAt(new Date())
                                    .setModifiedAt(new Date())
                                            .setOasisId(oasisJoin.getOasisId())
                                                    .setBrandId(oasisJoin.getBrandId());

            teamOasisMemberMapper.insert(member);
            teamOasisJoinMapper.updateTagById(id, OasisJoinTagEnum.ACCEPT.getMark());

            // update oasis tb member info
            oasis.setMembership(oasis.getMembership()+1);
            teamOasisMapper.updateById(oasis);
            // give member channel
            GroupMemberRel groupMemberRel = new GroupMemberRel();
            groupMemberRel.setId(IdUtil.simpleUUID())
                    .setChannelId(oasis.getId())
                    .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                    .setMemberId(memberBrand.getCustomerId())
                    .setPolicyRel(GroupMemberPolicyRelEnum.READ_WRITE.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamGroupMemberRelMapper.insert(groupMemberRel);

        }
        if(oasis !=null && oasis.getMembership() >= oasis.getMaxMembers()){
            teamOasisJoinMapper.updateTagById(id, OasisJoinTagEnum.DENY.getMark());
        }
    }

    @Override
    public void inviteBrand(TeamInviteToOasisDTO dto) {
        OasisJoin join =new OasisJoin();
        join.setId(IdUtil.simpleUUID())
                .setBrandId(dto.getBrandId())
                .setOasisId(dto.getOasisId())
                .setTag(OasisJoinTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOasisJoinMapper.insert(join);

    }

    @Override
    public ArrayList<TeamJoinedRO> findJoinedOasis(String brandId) {
        return teamOasisJoinMapper.selectJoinedOasesByUser(brandId);
    }

    @Override
    public void followOasis(String oasisId,String brandId) {
        // query oasis member info
        Oasis oasis = teamOasisMapper.selectById(oasisId);


        // if membership < max_members insert oasis_member else deny
        if(oasis != null && oasis.getMembership() < oasis.getMaxMembers()){
            OasisMember member = new OasisMember();
            member.setId(IdUtil.simpleUUID())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date())
                    .setOasisId(oasisId)
                    .setBrandId(brandId);
            teamOasisMemberMapper.insert(member);

            OasisJoin join =new OasisJoin();
            join.setId(IdUtil.simpleUUID())
                    .setBrandId(brandId)
                    .setOasisId(oasisId)
                    .setTag(OasisJoinTagEnum.ACCEPT.getMark())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOasisJoinMapper.insert(join);
            // update oasis tb member info
            oasis.setMembership(oasis.getMembership()+1);
            teamOasisMapper.updateById(oasis);
        }else {
            throw new ErrorCodeException(CodeEnum.MEMBERS_LIMIT);
        }
    }

    @Override
    public void unfollowOasis(String oasisId,String brandId) {


        LambdaQueryWrapper<OasisJoin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OasisJoin::getOasisId,oasisId)
                        .eq(OasisJoin::getBrandId,brandId);

        teamOasisJoinMapper.delete(wrapper);
        // update oasis tb membership
        teamOasisMapper.subtractOneMembershipById(oasisId);

    }

    @Override
    public void removeOasisInvitation(String id) {
        LambdaQueryWrapper<OasisJoin> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OasisJoin::getId,id)
                        .eq(OasisJoin::getTag,OasisJoinTagEnum.CREATED.getMark());
        teamOasisJoinMapper.delete(wrapper);
    }
}
