package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.VoucherRequest;
import com.mdv.appstore.dto.response.VoucherResponse;

public interface VoucherService {

    void createVoucher(VoucherRequest request);

    List<VoucherResponse> getAllVouchers();

    VoucherResponse getVoucherById(Long id);

    VoucherResponse getVoucherByCode(String code);

    void updateVoucher(Long id, VoucherRequest request);

    int updateVoucherActiveStatus();

    VoucherRequest toVoucherRequest(VoucherResponse voucherResponse);

    void deleteVoucher(Long id);
}
