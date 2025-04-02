package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.VoucherRequest;
import com.mdv.appstore.dto.response.VoucherResponse;

@Mapper
public interface VoucherMapper {
    void insertVoucher(@Param("voucherRequest") VoucherRequest voucherRequest);

    List<VoucherResponse> selectAllVouchers();

    VoucherResponse selectVoucherById(@Param("id") Long id);

    VoucherResponse selectVoucherByCode(String code);

    List<VoucherResponse> selectVouchersToActivate();

    List<VoucherResponse> selectVouchersToDeactivate();

    void updateVoucherById(@Param("id") Long id, @Param("voucherRequest") VoucherRequest voucherRequest);

    void updateUsedQuantity(@Param("id") Long id);

    Integer batchUpdateVoucherStatus(@Param("vouchers") List<VoucherResponse> vouchers);

    void deleteVoucherById(@Param("id") Long id);
}
