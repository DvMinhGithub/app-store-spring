package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.mdv.appstore.dto.request.VoucherRequest;
import com.mdv.appstore.dto.response.VoucherResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.VoucherMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherService {
    private final VoucherMapper voucherMapper;

    public void createVoucher(VoucherRequest request) {
        VoucherResponse voucherResponse = voucherMapper.selectVoucherByCode(request.getCode());
        if (voucherResponse != null) {
            throw new DuplicateEntryException("Voucher already exists");
        }
        voucherMapper.insertVoucher(request);
    }

    public List<VoucherResponse> getAllVouchers() {
        return voucherMapper.selectAllVouchers();
    }

    public VoucherResponse getVoucherById(Long id) {
        VoucherResponse voucher = voucherMapper.selectVoucherById(id);
        if (voucher == null) {
            throw new DataNotFoundException("Voucher not found");
        }
        return voucher;
    }

    public VoucherResponse getVoucherByCode(String code) {
        VoucherResponse voucher = voucherMapper.selectVoucherByCode(code);
        if (voucher == null) {
            throw new DataNotFoundException("Voucher not found");
        }
        return voucher;
    }

    public void updateVoucher(Long id, VoucherRequest request) {
        getVoucherById(id);
        voucherMapper.updateVoucherById(id, request);
    }

    @Transactional
    public int updateVoucherActiveStatus() {
        int updatedCount = 0;

        List<VoucherResponse> vouchersToActivate = voucherMapper.selectVouchersToActivate();
        if (!vouchersToActivate.isEmpty()) {
            vouchersToActivate.forEach(voucher -> voucher.setIsActive(true));
            updatedCount += voucherMapper.batchUpdateVoucherStatus(vouchersToActivate);
            vouchersToActivate.forEach(
                    voucher -> log.info("Voucher activated: {}", voucher.getCode()));
        }

        List<VoucherResponse> vouchersToDeactivate = voucherMapper.selectVouchersToDeactivate();
        if (!vouchersToDeactivate.isEmpty()) {
            vouchersToDeactivate.forEach(voucher -> voucher.setIsActive(false));
            updatedCount += voucherMapper.batchUpdateVoucherStatus(vouchersToDeactivate);
            vouchersToDeactivate.forEach(
                    voucher -> log.info("Voucher deactivated: {}", voucher.getCode()));
        }

        return updatedCount;
    }

    public VoucherRequest toVoucherRequest(VoucherResponse voucherResponse) {
        VoucherRequest voucherRequest = new VoucherRequest();
        voucherRequest.setCode(voucherResponse.getCode());
        voucherRequest.setConditionValue(voucherResponse.getConditionValue());
        voucherRequest.setDiscountPrice(voucherResponse.getDiscountPrice());
        voucherRequest.setEndTime(voucherResponse.getEndTime());
        voucherRequest.setStartTime(voucherResponse.getStartTime());
        voucherRequest.setTotalQuantity(voucherResponse.getTotalQuantity());
        voucherRequest.setUsedQuantity(voucherResponse.getUsedQuantity());
        voucherRequest.setIsActive(voucherResponse.getIsActive());
        return voucherRequest;
    }

    public void deleteVoucher(Long id) {
        getVoucherById(id);
        voucherMapper.deleteVoucherById(id);
    }
}
