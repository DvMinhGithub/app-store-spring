package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.VoucherDTO;
import com.mdv.appstore.model.request.VoucherRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoucherMapper {
    void insertVoucher(@Param("voucherRequest") VoucherRequest voucherRequest);

    List<VoucherDTO> selectAllVouchers();

    VoucherDTO selectVoucherById(@Param("id") Long id);

    VoucherDTO selectVoucherByCode(String code);

    List<VoucherDTO> selectVouchersToActivate();

    List<VoucherDTO> selectVouchersToDeactivate();

    void updateVoucherById(
            @Param("id") Long id, @Param("voucherRequest") VoucherRequest voucherRequest);

    void batchUpdateVoucherStatus(@Param("vouchers") List<VoucherDTO> vouchers);

    void deleteVoucherById(@Param("id") Long id);
}
