package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.VoucherCreateRequest;
import com.mdv.appstore.dto.request.VoucherUpdateRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.VoucherResponse;
import com.mdv.appstore.service.VoucherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private static final String VOUCHER_CREATED_SUCCESSFULLY = "Voucher created successfully";
    private static final String VOUCHER_UPDATED_SUCCESSFULLY = "Voucher updated successfully";
    private static final String VOUCHER_DELETED_SUCCESSFULLY = "Voucher deleted successfully";
    private static final String VOUCHER_FETCHED_SUCCESSFULLY = "Voucher retrieved successfully";
    private final VoucherService voucherService;

    @PostMapping
    public ApiResponse<Void> createVoucher(@RequestBody @Valid VoucherCreateRequest voucher) {
        voucherService.createVoucher(voucher);
        return ApiResponse.success(VOUCHER_CREATED_SUCCESSFULLY);
    }

    @GetMapping
    public ApiResponse<List<VoucherResponse>> selectAllVouchers() {
        return ApiResponse.success(voucherService.getAllVouchers(), VOUCHER_FETCHED_SUCCESSFULLY);
    }

    @GetMapping("/{id}")
    public ApiResponse<VoucherResponse> selectVoucherById(@PathVariable("id") Long id) {
        return ApiResponse.success(voucherService.getVoucherById(id), VOUCHER_FETCHED_SUCCESSFULLY);
    }

    @GetMapping("/code/{code}")
    public ApiResponse<VoucherResponse> selectVoucherByCode(@PathVariable("code") String code) {
        return ApiResponse.success(voucherService.getVoucherByCode(code), VOUCHER_FETCHED_SUCCESSFULLY);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateVoucher(
            @PathVariable("id") Long id, @RequestBody @Valid VoucherUpdateRequest voucher) {
        voucherService.updateVoucher(id, voucher);
        return ApiResponse.success(VOUCHER_UPDATED_SUCCESSFULLY);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVoucher(@PathVariable("id") Long id) {
        voucherService.deleteVoucher(id);
        return ApiResponse.success(VOUCHER_DELETED_SUCCESSFULLY);
    }
}
