package com.example.coffeeshopmanagementandroid.data.mapper;

import android.os.Build;

import com.example.coffeeshopmanagementandroid.data.dto.discount.response.DiscountResponse;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DiscountMapper {
    public static DiscountModel mapToDiscountModel(DiscountResponse response) {
        if (response == null) return null;
        DiscountModel model = new DiscountModel();
        model.setDiscountId(response.getId());
        model.setDiscountName(response.getDiscountName());
        model.setDiscountDescription(response.getDiscountDescription());
        model.setDiscountType(response.getDiscountType());
        model.setDiscountValue(response.getDiscountValue());
        model.setDiscountCode(response.getDiscountCode());

        // Handle date conversions safely
        model.setDiscountStartDate(response.getDiscountStartDate());
        model.setDiscountEndDate(response.getDiscountEndDate());

        model.setDiscountMaxUses(response.getDiscountMaxUsers());
        model.setDiscountUserCount(response.getDiscountUserCount());
        model.setDiscountMaxPerUser(response.getDiscountMaxPerUser());
        model.setDiscountMinOrderValue(response.getDiscountMinOrderValue());
        model.setDiscountIsActive(response.isDiscountIsActive());
        model.setDiscountBranchId(response.getBranchId());
        model.setDiscountBranchName(response.getBranchName());
        model.setProducts(response.getProducts());
        return model;
    }

    public static List<DiscountModel> mapToDiscountModels(List<DiscountResponse> responses) {
        List<DiscountModel> models = new ArrayList<>();
        if (responses != null) {
            for (DiscountResponse response : responses) {
                DiscountModel model = mapToDiscountModel(response);
                if (model != null) {
                    models.add(model);
                }
            }
        }
        return models;
    }

    // Similar to BranchDetailFragment's formatToVietnameseDate
    public static String formatToVietnameseDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Get day of week in Vietnamese
            String dayOfWeek;
            switch (dateTime.getDayOfWeek()) {
                case MONDAY: dayOfWeek = "Thứ Hai"; break;
                case TUESDAY: dayOfWeek = "Thứ Ba"; break;
                case WEDNESDAY: dayOfWeek = "Thứ Tư"; break;
                case THURSDAY: dayOfWeek = "Thứ Năm"; break;
                case FRIDAY: dayOfWeek = "Thứ Sáu"; break;
                case SATURDAY: dayOfWeek = "Thứ Bảy"; break;
                case SUNDAY: dayOfWeek = "Chủ Nhật"; break;
                default: dayOfWeek = ""; break;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = dateTime.format(formatter);
            return dayOfWeek + ", ngày " + formattedDate;
        }

        return "-";
    }
}