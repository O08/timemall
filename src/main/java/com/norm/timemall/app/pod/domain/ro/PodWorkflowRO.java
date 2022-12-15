package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PodWorkflowRO {
    // The cel title for cell, ref: cell.title
    private String service;
    // The brand_name for brand, ref: brand.brand_name
    private String supplier;
    // The total fee for order,ref: order_details.total
    private BigDecimal fee;
    // Create date for order, ref: order_details.create_at
    private Date added;
    // The id for workflow, ref: order_details.id
    private String id;
}