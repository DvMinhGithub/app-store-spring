package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.VoucherDTO;
import com.mdv.appstore.model.request.VoucherRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.VoucherService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-url}/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping
    public ApiResponse<Void> createVoucher(@RequestBody @Valid VoucherRequest voucher) {
        voucherService.createVoucher(voucher);
        return ApiResponse.success("Voucher created successfully");
    }

    @GetMapping
    public ApiResponse<List<VoucherDTO>> selectAllVouchers() {
        return ApiResponse.success(
                voucherService.getAllVouchers(), "Vouchers fetched successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<VoucherDTO> selectVoucherById(@PathVariable("id") Long id) {
        return ApiResponse.success(
                voucherService.getVoucherById(id), "Voucher fetched successfully");
    }

    @GetMapping("/code/{code}")
    public ApiResponse<VoucherDTO> selectVoucherByCode(@PathVariable("code") String code) {
        return ApiResponse.success(
                voucherService.getVoucherByCode(code), "Voucher fetched successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateVoucher(
            @PathVariable("id") Long id, @RequestBody @Valid VoucherRequest voucher) {
        voucherService.updateVoucher(id, voucher);
        return ApiResponse.success("Voucher updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVoucher(@PathVariable("id") Long id) {
        voucherService.deleteVoucher(id);
        return ApiResponse.success("Voucher deleted successfully");
    }
}
