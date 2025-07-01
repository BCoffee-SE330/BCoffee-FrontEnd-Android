package com.example.coffeeshopmanagementandroid.ui.fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.CategoryAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.DiscountHomeAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.ProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.CategoryViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DiscountViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.ProductViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CategorySortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.DiscountSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.ProductSortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private DiscountHomeAdapter discountHomeAdapter;
    private ProductAdapter recentlyProductAdapter;
    private RecyclerView productRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView discountRecyclerView;
    private RecyclerView recentlyProductRecyclerView;
    private NavController navController;
    private ImageButton notificationButton;
    private ImageButton cartButton;
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    private DiscountViewModel discountViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        discountViewModel = new ViewModelProvider(this).get(DiscountViewModel.class);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        recentlyProductRecyclerView = view.findViewById(R.id.recentlyProductRecyclerView);
        discountRecyclerView = view.findViewById(R.id.superSaleRecyclerView);

        navController = NavHostFragment.findNavController(this);
        NavigationUtils.checkAndFixNavState(navController, R.id.homeFragment, "HomeFragment");
        cartButton = view.findViewById(R.id.cartButton);
        notificationButton = view.findViewById(R.id.notificationButton);

        setUpRecyclerView();
        setUpListener();

        fetchAndObserveProducts();
        fetchAndObserveCategories();
        fetchAndObserveRecentlyProducts();
        try {
            fetchAndObserveDiscounts();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        categoryAdapter.setOnCategorySelectedListener(categoryId -> filterProductsByCategory(categoryId));
    }

    private void setUpRecyclerView() {
        List<ProductModel> coffees = new ArrayList<>();

        productAdapter = new ProductAdapter(coffees,
                product -> {
                    Bundle args = new Bundle();
                    Log.d("HomeFragment", "Selected product: " + product.getProductId());
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController, R.id.homeFragment, R.id.action_homeFragment_to_detailProductFragment, "DetailProductFragment", "HomeFragment", args);
                },
                product -> {
                    Bundle args = new Bundle();
                    Log.d("HomeFragment", "Selected product: " + product.getProductId());
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController, R.id.homeFragment, R.id.action_homeFragment_to_detailProductFragment, "DetailProductFragment", "HomeFragment", args);
                }
        );
        productRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(productViewModel.getIsLoading().getValue()) && dx > 0 && Boolean.FALSE.equals(productViewModel.getIsAllDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert lm != null;
                    int totalItemCount = lm.getItemCount();
                    int lastVisibleItem = lm.findLastVisibleItemPosition();
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                        productViewModel.fetchMoreProducts(SortType.DESC, ProductSortBy.CREATED_AT);
                    }
                }
            }
        });

        categoryAdapter = new CategoryAdapter(new ArrayList<>());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(categoryViewModel.getIsLoading().getValue()) && dx > 0 && Boolean.FALSE.equals(categoryViewModel.getIsAllDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert lm != null;
                    int totalItemCount = lm.getItemCount();
                    int lastVisibleItem = lm.findLastVisibleItemPosition();
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                        categoryViewModel.fetchMoreCategories(SortType.DESC, CategorySortBy.CREATED_AT);
                    }
                }
            }
        });

        recentlyProductAdapter = new ProductAdapter(new ArrayList<>(),
                product -> {
                    Bundle args = new Bundle();
                    Log.d("HomeFragment", "Selected product: " + product.getProductId());
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController, R.id.homeFragment, R.id.action_homeFragment_to_detailProductFragment, "DetailProductFragment", "HomeFragment", args);
                },
                product -> {
                    Bundle args = new Bundle();
                    Log.d("HomeFragment", "Selected product: " + product.getProductId());
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController, R.id.homeFragment, R.id.action_homeFragment_to_detailProductFragment, "DetailProductFragment", "HomeFragment", args);
                });
        recentlyProductRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recentlyProductRecyclerView.setAdapter(recentlyProductAdapter);
        recentlyProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(productViewModel.getIsLoading().getValue()) && dx > 0 && Boolean.FALSE.equals(productViewModel.getIsAllDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert lm != null;
                    int totalItemCount = lm.getItemCount();
                    int lastVisibleItem = lm.findLastVisibleItemPosition();
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                        productViewModel.fetchMoreProducts(SortType.DESC, ProductSortBy.CREATED_AT);
                    }
                }
            }
        });

        discountHomeAdapter = new DiscountHomeAdapter(
                new ArrayList<>(),
                discount -> {
                    Bundle args = new Bundle();
                    args.putString("discountId", discount.getDiscountId());
                    NavigationUtils.safeNavigate(
                            navController,
                            R.id.homeFragment,
                            R.id.action_homeFragment_to_discountDetailFragment,
                            "DiscountDetailFragment",
                            "HomeFragment",
                            args
                    );
                }
        );
        discountRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(discountViewModel.getIsDiscountLoading().getValue())
                        && dx > 0
                        && Boolean.FALSE.equals(discountViewModel.getIsAllDiscountDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (lm != null) {
                        int totalItemCount = lm.getItemCount();
                        int lastVisibleItem = lm.findLastVisibleItemPosition();
                        if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                            discountViewModel.fetchMoreDiscounts(SortType.DESC, DiscountSortBy.CREATED_AT);
                        }
                    }
                }
            }
        });
        discountRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        discountRecyclerView.setAdapter(discountHomeAdapter);

        int marginHorizontal = getResources().getDimensionPixelOffset(R.dimen.vertical_spacing);
        productRecyclerView.addItemDecoration(new SpaceItemDecoration().horizontal(marginHorizontal));
        recentlyProductRecyclerView.addItemDecoration(new SpaceItemDecoration().horizontal(marginHorizontal));
        discountRecyclerView.addItemDecoration(new SpaceItemDecoration().horizontal(marginHorizontal));
    }

    private void setUpListener() {
        cartButton.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.homeFragment,
                        R.id.action_homeFragment_to_cartFragment,
                        "CartFragment",
                        "HomeFragment"));
        notificationButton.setOnClickListener(v ->
                NavigationUtils.safeNavigate(navController,
                        R.id.homeFragment,
                        R.id.action_homeFragment_to_noticationFragment,
                        "NotificationFragment",
                        "HomeFragment"));
    }

    private void fetchAndObserveProducts() {
        productViewModel.fetchAllProducts(1, 10, SortType.DESC, ProductSortBy.CREATED_AT);

        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                productAdapter.setProducts(products);
            } else {
                productAdapter.setProducts(new ArrayList<>());
            }
        });

        productViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {});

        productViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        productViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("HomeFragment", "All data loaded, no more fetching");
            }
        });
    }

    private void fetchAndObserveRecentlyProducts() {
        productViewModel.fetchRecentlyProducts(1, 10, SortType.DESC, ProductSortBy.CREATED_AT);
        productViewModel.getRecentlyProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            if (products != null) {
                recentlyProductAdapter.setProducts(products);
            } else {
                recentlyProductAdapter.setProducts(new ArrayList<>());
            }
        });

        productViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {});

        productViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        productViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("HomeFragment", "All data loaded, no more fetching");
            }
        });
    }

    private void fetchAndObserveCategories() {
        categoryViewModel.fetchAllCategories(1, 10, SortType.DESC, CategorySortBy.CREATED_AT);
        categoryViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryAdapter.setCategories(categories);
            } else {
                categoryAdapter.setCategories(new ArrayList<>());
            }
        });

        categoryViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {});

        categoryViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        categoryViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("HomeFragment", "All data loaded, no more fetching");
            }
        });
    }

    private void fetchAndObserveDiscounts() {
        try {
            discountViewModel.FetchAllDiscounts(1, 10, SortType.DESC, DiscountSortBy.CREATED_AT);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error fetching discounts: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        discountViewModel.getDiscountsLiveData().observe(getViewLifecycleOwner(), discounts -> {
            if (discounts != null) {
                discountHomeAdapter.setDiscounts(discounts);
            } else {
                discountHomeAdapter.setDiscounts(new ArrayList<>());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBottomNavigation();
        }
    }

    private void filterProductsByCategory(String categoryId) {
        if (categoryId == null) {
            productAdapter.setProducts(productViewModel.getProductsLiveData().getValue());
        } else {
            productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
                if (products != null) {
                    List<ProductModel> filteredProducts = new ArrayList<>();
                    for (ProductModel product : products) {
                        if (product.getProductCategoryId().equals(categoryId)) {
                            filteredProducts.add(product);
                        }
                    }
                    productAdapter.setProducts(filteredProducts);
                }
            });
        }
    }
}