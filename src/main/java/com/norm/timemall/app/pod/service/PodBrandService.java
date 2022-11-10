package com.norm.timemall.app.pod.service;

import com.norm.timemall.app.pod.domain.pojo.PodBrandContact;
import com.norm.timemall.app.pod.domain.pojo.PodPayway;
import org.springframework.stereotype.Service;

@Service
public interface PodBrandService {
    PodBrandContact findContactById(String brandId);

    PodPayway findPaywayById(String brandId);
}
