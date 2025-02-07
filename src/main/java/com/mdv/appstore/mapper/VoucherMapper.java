package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.VoucherDTO;
import com.mdv.appstore.model.request.VoucherRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoucherMapper {
    void createVoucher(@Param("v") VoucherRequest voucher);

    List<VoucherDTO> selectAllVouchers();

    VoucherDTO selectVoucherById(@Param("id") Long id);

    VoucherDTO selectVoucherByCode(String code);

    void updateVoucher(@Param("id") Long id, @Param("v") VoucherRequest voucher);

    void deleteVoucher(@Param("id") Long id);
}
