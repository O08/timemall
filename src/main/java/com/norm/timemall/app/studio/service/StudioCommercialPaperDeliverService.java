package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.dto.StudioMpsPaperDeliverLeaveMsgDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDeliver;
import org.springframework.stereotype.Service;

@Service
public interface StudioCommercialPaperDeliverService {
    void newDeliver(String paperId, String previewUri, String deliverUri,String previewName,String deliverName);

    StudioFetchMpsPaperDeliver findMpsPaperDeliver(String paperId);

    void leaveMessage(StudioMpsPaperDeliverLeaveMsgDTO dto);

    StudioFetchMpsPaperDeliver findBrandMpsPaperDeliver(String paperId);
}
