package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.response.AddressResponse;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.CreateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.GetAddressRequest;
import com.example.coffeeshopmanagementandroid.data.dto.address.resquest.UpdateAddressRequest;
import com.example.coffeeshopmanagementandroid.data.mapper.AddressMapper;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.AddressUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.AddressSortBy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddressViewModel extends ViewModel {
    private final AddressUseCase addressUseCase;
    private final MutableLiveData<List<AddressModel>> addressesLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<AddressModel> selectedAddressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> totalAddresses = new MutableLiveData<>(0);

    @Inject
    public AddressViewModel(AddressUseCase addressUseCase) {
        this.addressUseCase = addressUseCase;
    }

    public MutableLiveData<List<AddressModel>> getAddressesLiveData() {
        return addressesLiveData;
    }

    public MutableLiveData<AddressModel> getSelectedAddressLiveData() {
        return selectedAddressLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void setSelectedAddress(AddressModel address) {
        selectedAddressLiveData.postValue(address);
    }

    public void fetchAddresses(int page, int limit, SortType sortType, AddressSortBy sortBy) {
        isLoading.postValue(true);
        new Thread(() -> {
            try {
                GetAddressRequest request = new GetAddressRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<AddressResponse>> result = addressUseCase.getAddresses(request);

                if (result != null && result.getData() != null) {
                    totalAddresses.postValue(result.getPaging().getTotal());
                    List<AddressModel> addresses = AddressMapper.mapToAddressModelList(result.getData());
                    addressesLiveData.postValue(addresses);

                    // Select default address if available
                    for (AddressModel address : addresses) {
                        if (address.getAddressIsDefault()) {
                            selectedAddressLiveData.postValue(address);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.e("AddressViewModel", "Fetch addresses failed: " + e.getMessage(), e);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }

    public void createAddress(String addressLine, String addressDistrict, String addressCity, boolean isDefault) {
        isLoading.postValue(true);
        new Thread(() -> {
            try {
                CreateAddressRequest request = new CreateAddressRequest(addressLine, addressDistrict, addressCity, isDefault);
                BaseResponse<AddressResponse> response = addressUseCase.createAddress(request);

                if (response != null && response.getData() != null) {
                    AddressModel newAddress = AddressMapper.mapToAddressModel(response.getData());

                    List<AddressModel> currentAddresses = addressesLiveData.getValue();
                    if (currentAddresses != null) {
                        currentAddresses.add(newAddress);
                        addressesLiveData.postValue(currentAddresses);
                    }

                    if (isDefault) {
                        selectedAddressLiveData.postValue(newAddress);
                    }
                }
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.e("AddressViewModel", "Create address failed: " + e.getMessage(), e);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }

    public void updateAddress(UUID addressId, String addressLine, String addressCity,
                              String addressDistrict, boolean isDefault) {
        isLoading.postValue(true);
        new Thread(() -> {
            try {
                UpdateAddressRequest request = new UpdateAddressRequest(
                        addressId, addressLine, addressCity, addressDistrict, isDefault);

                BaseResponse<AddressResponse> response = addressUseCase.updateAddress(request);

                if (response != null && response.getData() != null) {
                    AddressModel updatedAddress = AddressMapper.mapToAddressModel(response.getData());

                    List<AddressModel> currentAddresses = addressesLiveData.getValue();
                    if (currentAddresses != null) {
                        // Replace the old address with updated one
                        for (int i = 0; i < currentAddresses.size(); i++) {
                            if (currentAddresses.get(i).getAddressId().equals(updatedAddress.getAddressId())) {
                                currentAddresses.set(i, updatedAddress);
                                break;
                            }
                        }
                        addressesLiveData.postValue(currentAddresses);
                    }

                    if (isDefault) {
                        selectedAddressLiveData.postValue(updatedAddress);
                    }
                }
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.e("AddressViewModel", "Update address failed: " + e.getMessage(), e);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }

    public void deleteAddress(UUID addressId) {
        isLoading.postValue(true);
        new Thread(() -> {
            try {
                addressUseCase.deleteAddress(addressId);

                List<AddressModel> currentAddresses = addressesLiveData.getValue();
                if (currentAddresses != null) {
                    // Remove the deleted address
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        currentAddresses.removeIf(address -> address.getAddressId().equals(addressId));
                    }
                    addressesLiveData.postValue(currentAddresses);

                    // If selected address was deleted, clear selection
                    AddressModel selectedAddress = selectedAddressLiveData.getValue();
                    if (selectedAddress != null && selectedAddress.getAddressId().equals(addressId)) {
                        selectedAddressLiveData.postValue(null);
                    }
                }
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
                Log.e("AddressViewModel", "Delete address failed: " + e.getMessage(), e);
            } finally {
                isLoading.postValue(false);
            }
        }).start();
    }
}