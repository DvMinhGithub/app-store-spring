package com.mdv.mybatis.service;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.VoucherMapper;
import com.mdv.mybatis.model.dto.VoucherDTO;
import com.mdv.mybatis.model.request.VoucherRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherMapper voucherMapper;

    public void createVoucher(VoucherRequest request) {
        VoucherDTO voucherDTO = voucherMapper.selectVoucherByCode(request.getCode());
        if (voucherDTO != null) {
            throw new DataNotFoundException("Voucher already exists");
        }
        voucherMapper.createVoucher(request);
    }

    public List<VoucherDTO> selectAllVouchers() {
        return voucherMapper.selectAllVouchers();
    }

    public VoucherDTO selectVoucherById(Long id) {
        VoucherDTO voucher = voucherMapper.selectVoucherById(id);
        if (voucher == null) {
            throw new DataNotFoundException("Voucher not found");
        }
        return voucher;
    }

    public VoucherDTO selectVoucherByCode(String code) {
        VoucherDTO voucher = voucherMapper.selectVoucherByCode(code);
        if (voucher == null) {
            throw new DataNotFoundException("Voucher not found");
        }
        return voucher;
    }

    public void updateVoucher(Long id, VoucherRequest request) {
        selectVoucherById(id);
        voucherMapper.updateVoucher(id, request);
    }

    public void deleteVoucher(Long id) {
        selectVoucherById(id);
        voucherMapper.deleteVoucher(id);
    }
}
