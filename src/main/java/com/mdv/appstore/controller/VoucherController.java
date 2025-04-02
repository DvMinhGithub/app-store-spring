package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.VoucherRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.VoucherResponse;
import com.mdv.appstore.service.VoucherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    public ApiResponse<List<VoucherResponse>> selectAllVouchers() {
        return ApiResponse.success(voucherService.getAllVouchers(), "Vouchers fetched successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<VoucherResponse> selectVoucherById(@PathVariable("id") Long id) {
        return ApiResponse.success(voucherService.getVoucherById(id), "Voucher fetched successfully");
    }

    @GetMapping("/code/{code}")
    public ApiResponse<VoucherResponse> selectVoucherByCode(@PathVariable("code") String code) {
        return ApiResponse.success(voucherService.getVoucherByCode(code), "Voucher fetched successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateVoucher(@PathVariable("id") Long id, @RequestBody @Valid VoucherRequest voucher) {
        voucherService.updateVoucher(id, voucher);
        return ApiResponse.success("Voucher updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVoucher(@PathVariable("id") Long id) {
        voucherService.deleteVoucher(id);
        return ApiResponse.success("Voucher deleted successfully");
    }
}
