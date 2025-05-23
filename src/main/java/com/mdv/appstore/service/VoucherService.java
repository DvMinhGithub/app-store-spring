package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.VoucherCreateRequest;
import com.mdv.appstore.dto.request.VoucherUpdateRequest;
import com.mdv.appstore.dto.response.VoucherResponse;

public interface VoucherService {

    void createVoucher(VoucherCreateRequest request);

    VoucherResponse getVoucherById(Long id);

    VoucherResponse getVoucherByCode(String code);

    List<VoucherResponse> getAllVouchers();

    void updateVoucher(Long id, VoucherUpdateRequest request);

    void deleteVoucher(Long id);

    int updateVoucherActiveStatus();
}
