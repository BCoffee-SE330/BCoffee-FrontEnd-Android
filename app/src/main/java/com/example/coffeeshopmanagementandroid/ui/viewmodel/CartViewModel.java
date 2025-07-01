package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.BaseResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.GetAllCartItemRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.request.UpdateCartRequest;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartDetailResponse;
import com.example.coffeeshopmanagementandroid.data.dto.cart.response.CartResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.CartDetailMapper;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.CartUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CartSortBy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CartViewModel extends ViewModel {
    private final CartUseCase cartUseCase;
    private final MutableLiveData<List<CartItemModel>> cartItemsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> page = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> limit = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> total = new MutableLiveData<>(null);

    // total price before discount
    private final MutableLiveData<BigDecimal> totalPrice = new MutableLiveData<>(BigDecimal.ZERO);
    // total discount cost
    private final MutableLiveData<BigDecimal> totalDiscountCost = new MutableLiveData<>(BigDecimal.ZERO);
    // total price after discount
    private final MutableLiveData<BigDecimal> totalPriceAfterDiscount = new MutableLiveData<>(BigDecimal.ZERO);


    private final Handler debounceHandler = new Handler(Looper.getMainLooper());
    private final Map<String, Runnable> debounceMap = new HashMap<>();

    public void setIsLoading(Boolean loading) {
        isLoading.postValue(loading);
    }
    private void setPage(int page) {
        this.page.postValue(page);
    }

    private void setTotal(int total) {
        this.total.postValue(total);
    }
    public void setErrorLiveData(String errorMessage) {
        errorLiveData.postValue(errorMessage);
    }
    @Inject
    public CartViewModel(CartUseCase cartUseCase) {
        this.cartUseCase = cartUseCase;
    }

    public MutableLiveData<List<CartItemModel>> getCartItemsLiveData() {
        return cartItemsLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public MutableLiveData<BigDecimal> getTotalPrice() {
        return totalPrice;
    }

    public void fetchAllCartItems(int page, int limit, SortType sortType, CartSortBy sortBy, String branchId) {
        setIsLoading(true);
        new Thread(() -> {
            try {
                GetAllCartItemRequest request = new GetAllCartItemRequest(page, limit, sortType, sortBy);
                BasePagingResponse<List<CartDetailResponse>> result = cartUseCase.getCartItems(request);
                if (result != null && result.getData() != null) {
                    setTotal(result.getPaging().getTotal());
                    List<CartItemModel> cartItems = CartDetailMapper.mapToCartItemModels(result.getData());
                    appendCartItems(cartItems);

                    // Gọi api để lấy thông tin giảm giá
                    if (branchId != null) {
                        // Gọi api để lấy thông tin giảm giá và tính toán tổng giá
                        try {
                            BaseResponse<CartResponse> cartResponseBaseResponse = cartUseCase.applyDiscountToCart(branchId);
                            if (cartResponseBaseResponse != null && cartResponseBaseResponse.getData() != null) {
                                CartResponse cartResponse = cartResponseBaseResponse.getData();
                                totalPrice.postValue(cartResponse.getCartTotalCost());
                                totalDiscountCost.postValue(cartResponse.getCartDiscountCost());
                                totalPriceAfterDiscount.postValue(cartResponse.getCartTotalCostAfterDiscount());
                            } else {
                                setErrorLiveData("Không thể lấy thông tin giảm giá");
                            }
                        } catch (Exception e) {
                            setErrorLiveData("Không thể tính toán tổng giá: " + e.getMessage());
                            Log.e("CartViewModel", "Calculate total price failed: " + e.getMessage(), e);
                        }
                    } else {
                        calculateTotalPrice(cartItems);
                    }

                }
            } catch (Exception e) {
                setErrorLiveData(e.getMessage());
                Log.e("ProductViewModel", "Fetch all products failed: " + e.getMessage(), e);
            } finally {
                setIsLoading(false);
            }
        }).start();
    }
    private void calculateTotalPrice(List<CartItemModel> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemModel item : cartItems) {
            BigDecimal itemTotal = BigDecimal.valueOf(item.getCartDetailUnitPrice()).multiply(BigDecimal.valueOf(item.getCartDetailQuantity()));
            total = total.add(itemTotal);
        }
        totalPrice.postValue(total);
    }

    private void appendCartItems(List<CartItemModel> cartItems) {
        List<CartItemModel> currentCartItems = new ArrayList<>();

        for (CartItemModel newItem : cartItems) {
            if (!currentCartItems.contains(newItem)) {
                currentCartItems.add(newItem);
            }
        }

        cartItemsLiveData.postValue(currentCartItems);
    }

    public void decrementCartItemWithDebounce(CartItemModel item) {
        String cartItemId = item.getVariantId(); // hoặc dùng cartDetailId nếu phù hợp hơn
        if (debounceMap.containsKey(cartItemId)) {
            debounceHandler.removeCallbacks(debounceMap.get(cartItemId));
        }

        Runnable task = () -> decrementCartItem(item);
        debounceMap.put(cartItemId, task);
        debounceHandler.postDelayed(task, 500); // 500ms debounce
    }

    private void decrementCartItem(CartItemModel item) {
        int currentQuantity = item.getCartDetailQuantity();
        int newQuantity = currentQuantity - 1;

        UpdateCartRequest request = new UpdateCartRequest(
                item.getVariantId(),
                newQuantity
        );

        List<CartItemModel> currentCartItems = cartItemsLiveData.getValue();
        if (currentCartItems == null) return;

        if (newQuantity == 0) {
            // Xóa khỏi danh sách hiển thị
            currentCartItems.remove(item);
        } else {
            // Cập nhật số lượng mới cho item
            item.setCartDetailQuantity(newQuantity);
        }

        // Cập nhật lại giao diện
        cartItemsLiveData.postValue(new ArrayList<>(currentCartItems));
        calculateTotalPrice(currentCartItems);

        // Gọi API cập nhật
        new Thread(() -> {
            try {
                cartUseCase.updateCartItem(request);
            } catch (Exception e) {
                errorLiveData.postValue("Không thể cập nhật giỏ hàng: " + e.getMessage());
            }
        }).start();
    }
    public void incrementCartItemWithDebounce(CartItemModel item) {
        String cartItemId = item.getVariantId();
        if (debounceMap.containsKey(cartItemId)) {
            debounceHandler.removeCallbacks(debounceMap.get(cartItemId));
        }

        Runnable task = () -> incrementCartItem(item);
        debounceMap.put(cartItemId, task);
        debounceHandler.postDelayed(task, 500); // 500ms debounce
    }

    private void incrementCartItem(CartItemModel item) {
        int currentQuantity = item.getCartDetailQuantity();
        int newQuantity = currentQuantity + 1;

        UpdateCartRequest request = new UpdateCartRequest(
                item.getVariantId(),
                newQuantity
        );

        List<CartItemModel> currentCartItems = cartItemsLiveData.getValue();
        if (currentCartItems == null) return;

        // Cập nhật số lượng hiển thị
        item.setCartDetailQuantity(newQuantity);
        cartItemsLiveData.postValue(new ArrayList<>(currentCartItems));
        calculateTotalPrice(currentCartItems);

        // Gọi API cập nhật
        new Thread(() -> {
            try {
                cartUseCase.updateCartItem(request);
            } catch (Exception e) {
                errorLiveData.postValue("Không thể tăng số lượng: " + e.getMessage());
            }
        }).start();
    }

    public void deleteCartItem(CartItemModel item) {
        String cartItemId = item.getVariantId(); // hoặc item.getCartDetailId() nếu API yêu cầu

        List<CartItemModel> currentCartItems = cartItemsLiveData.getValue();
        if (currentCartItems == null) return;

        // Xóa item khỏi danh sách
        currentCartItems.remove(item);

        // Cập nhật giao diện
        cartItemsLiveData.postValue(new ArrayList<>(currentCartItems));
        calculateTotalPrice(currentCartItems);

        // Gọi API xoá khỏi server
        new Thread(() -> {
            try {
                cartUseCase.deleteCartItem(cartItemId); // giả định method này tồn tại trong use case
            } catch (Exception e) {
                errorLiveData.postValue("Không thể xoá mục khỏi giỏ hàng: " + e.getMessage());
            }
        }).start();
    }


}
