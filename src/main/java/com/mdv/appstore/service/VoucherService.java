package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.VoucherMapper;
import com.mdv.appstore.model.dto.VoucherDTO;
import com.mdv.appstore.model.request.VoucherRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherService {
    private final VoucherMapper voucherMapper;

    public void createVoucher(VoucherRequest request) {
        VoucherDTO voucherDTO = voucherMapper.selectVoucherByCode(request.getCode());
        if (voucherDTO != null) {
            throw new DuplicateEntryException("Voucher already exists");
        }
        voucherMapper.insertVoucher(request);
    }

    public List<VoucherDTO> getAllVouchers() {
        return voucherMapper.selectAllVouchers();
    }

    public VoucherDTO getVoucherById(Long id) {
        VoucherDTO voucher = voucherMapper.selectVoucherById(id);
        if (voucher == null) {
            throw new DataNotFoundException("Voucher not found");
        }
        return voucher;
    }

    public VoucherDTO getVoucherByCode(String code) {
        VoucherDTO voucher = voucherMapper.selectVoucherByCode(code);
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

        List<VoucherDTO> vouchersToActivate = voucherMapper.selectVouchersToActivate();
        if (!vouchersToActivate.isEmpty()) {
            vouchersToActivate.forEach(voucher -> voucher.setIsActive(true));
            updatedCount += voucherMapper.batchUpdateVoucherStatus(vouchersToActivate);
            vouchersToActivate.forEach(
                    voucher -> log.info("Voucher activated: {}", voucher.getCode()));
        }

        List<VoucherDTO> vouchersToDeactivate = voucherMapper.selectVouchersToDeactivate();
        if (!vouchersToDeactivate.isEmpty()) {
            vouchersToDeactivate.forEach(voucher -> voucher.setIsActive(false));
            updatedCount += voucherMapper.batchUpdateVoucherStatus(vouchersToDeactivate);
            vouchersToDeactivate.forEach(
                    voucher -> log.info("Voucher deactivated: {}", voucher.getCode()));
        }

        return updatedCount;
    }

    public VoucherRequest toVoucherRequest(VoucherDTO voucherDTO) {
        VoucherRequest voucherRequest = new VoucherRequest();
        voucherRequest.setCode(voucherDTO.getCode());
        voucherRequest.setConditionValue(voucherDTO.getConditionValue());
        voucherRequest.setDiscountPrice(voucherDTO.getDiscountPrice());
        voucherRequest.setEndTime(voucherDTO.getEndTime());
        voucherRequest.setStartTime(voucherDTO.getStartTime());
        voucherRequest.setTotalQuantity(voucherDTO.getTotalQuantity());
        voucherRequest.setUsedQuantity(voucherDTO.getUsedQuantity());
        voucherRequest.setIsActive(voucherDTO.getIsActive());
        return voucherRequest;
    }

    public void deleteVoucher(Long id) {
        getVoucherById(id);
        voucherMapper.deleteVoucherById(id);
    }
}
