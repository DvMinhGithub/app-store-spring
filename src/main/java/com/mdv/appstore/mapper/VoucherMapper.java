package com.mdv.appstore.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.VoucherCreateRequest;
import com.mdv.appstore.dto.request.VoucherUpdateRequest;
import com.mdv.appstore.dto.response.VoucherResponse;

@Mapper
public interface VoucherMapper {
    void insertVoucher(VoucherCreateRequest request);

    Optional<VoucherResponse> selectVoucherById(@Param("id") Long id);

    Optional<VoucherResponse> selectVoucherByCode(@Param("code") String code);

    List<VoucherResponse> selectAllVouchers();

    void updateVoucherById(@Param("id") Long id, @Param("r") VoucherUpdateRequest request);

    void deleteVoucherById(Long id);

    int activateEligibleVouchers();
}
