package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.discount.response.DiscountResponse;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiscountMapper {
    private static final String DATE_FORMAT = "dd-MM-yyyy'T'HH:mm:ss";
    public static DiscountModel mapToDiscountModel(DiscountResponse response) {
        if (response == null) return null;
        DiscountModel model = new DiscountModel();
        model.setDiscountId(response.getId());
        model.setDiscountName(response.getDiscountName());
        model.setDiscountDescription(response.getDiscountDescription());
        model.setDiscountType(response.getDiscountType());
        model.setDiscountValue(response.getDiscountValue());
        model.setDiscountCode(response.getDiscountCode());
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

    private static Timestamp parseTimestamp(String dateString) {
        if (dateString == null) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            return new Timestamp(sdf.parse(dateString).getTime());
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}