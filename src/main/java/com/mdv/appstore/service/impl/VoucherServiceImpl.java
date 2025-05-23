package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.appstore.dto.request.VoucherCreateRequest;
import com.mdv.appstore.dto.request.VoucherUpdateRequest;
import com.mdv.appstore.dto.response.VoucherResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.VoucherMapper;
import com.mdv.appstore.service.VoucherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {
    private final VoucherMapper voucherMapper;
    private static final String VOUCHER_NOT_FOUND = "Voucher not found";
    private static final String VOUCHER_ALREADY_EXISTS = "Voucher already exists";

    @Override
    @Transactional
    public void createVoucher(VoucherCreateRequest request) {
        voucherMapper.selectVoucherByCode(request.getCode()).ifPresent(voucher -> {
            throw new DuplicateEntryException(VOUCHER_ALREADY_EXISTS);
        });
        voucherMapper.insertVoucher(request);
    }

    @Override
    public VoucherResponse getVoucherById(Long id) {
        return voucherMapper.selectVoucherById(id).orElseThrow(() -> new DataNotFoundException(VOUCHER_NOT_FOUND));
    }

    @Override
    public VoucherResponse getVoucherByCode(String code) {
        return voucherMapper.selectVoucherByCode(code).orElseThrow(() -> new DataNotFoundException(VOUCHER_NOT_FOUND));
    }

    @Override
    public List<VoucherResponse> getAllVouchers() {
        return voucherMapper.selectAllVouchers();
    }

    @Override
    @Transactional
    public void updateVoucher(Long id, VoucherUpdateRequest request) {
        voucherMapper.selectVoucherById(id).orElseThrow(() -> new DataNotFoundException(VOUCHER_NOT_FOUND));
        voucherMapper.updateVoucherById(id, request);
    }

    @Override
    @Transactional
    public void deleteVoucher(Long id) {
        voucherMapper.selectVoucherById(id).orElseThrow(() -> new DataNotFoundException(VOUCHER_NOT_FOUND));
        voucherMapper.deleteVoucherById(id);
    }

    @Override
    @Transactional
    public int updateVoucherActiveStatus() {
        return voucherMapper.activateEligibleVouchers();
    }
}
