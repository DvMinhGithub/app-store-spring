package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.RevenueDTO;

@Mapper
public interface RevenueMapper {
    void insert(RevenueDTO revenue);

    List<RevenueDTO> findByDateRange(
            @Param("startDate") String startDate, @Param("endDate") String endDate);

    Double getTotalRevenue(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
