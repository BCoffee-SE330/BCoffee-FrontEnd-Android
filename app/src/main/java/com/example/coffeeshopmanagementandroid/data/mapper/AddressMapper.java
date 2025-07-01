package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
    public static AddressModel mapToAddressModel(AddressResponse response) {
        if (response == null) {
            return null;
        }
        return new AddressModel(
                response.getAddressId(),
                response.getAddressLine(),
                response.getAddressDistrict(),
                response.getAddressCity(),
                response.isAddressIsDefault()
        );
    }

    public static List<AddressModel> mapToAddressModelList(List<AddressResponse> responses) {
        List<AddressModel> addresses = new ArrayList<>();
        for (AddressResponse response : responses) {
            AddressModel model = mapToAddressModel(response);
            if (model != null) {
                addresses.add(model);
            }
        }
        return addresses;
    }
}
