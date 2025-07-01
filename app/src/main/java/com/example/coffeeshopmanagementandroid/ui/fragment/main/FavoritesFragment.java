package com.example.coffeeshopmanagementandroid.ui.fragment.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.CategoryAdapter;
import com.example.coffeeshopmanagementandroid.ui.adapter.ProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.FavoriteViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.SharedPreferencesUtils;
import com.example.coffeeshopmanagementandroid.utils.SpaceItemDecoration;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.CategorySortBy;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.FavoriteProductSortBy;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoritesFragment extends Fragment {

    private ProductAdapter favoriteProductAdapter;
    private RecyclerView favoriteProductRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView categoryRecyclerView;
    private FavoriteViewModel favoriteViewModel;
    private String customerId;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        favoriteProductRecyclerView = view.findViewById(R.id.productRecyclerView);

        customerId = SharedPreferencesUtils.getUserId(requireContext());

        setupRecyclerView();
        fetchAndObserveProducts();
        fetchAndObserveCategories();

        // Thiết lập listener cho category
        categoryAdapter.setOnCategorySelectedListener(categoryId -> filterProductsByCategory(categoryId));
    }

    private void setupRecyclerView() {
        // Category
        categoryAdapter = new CategoryAdapter(new ArrayList<>());
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Boolean.FALSE.equals(favoriteViewModel.getIsLoading().getValue()) && dx > 0 && Boolean.FALSE.equals(favoriteViewModel.getIsAllDataLoaded().getValue())) {
                    LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert lm != null;
                    int totalItemCount = lm.getItemCount();
                    int lastVisibleItem = lm.findLastVisibleItemPosition();
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 5) {
                        favoriteViewModel.fetchMoreCategories(SortType.DESC, CategorySortBy.CREATED_AT);
                    }
                }
            }
        });

        // Product
        List<ProductModel> coffees = new ArrayList<>();

        favoriteProductAdapter = new ProductAdapter(coffees,
                product -> {
                    Bundle args = new Bundle();
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController,
                            R.id.favoriteFragment,
                            R.id.action_favoritesFragment_to_detailProductFragment,
                            "DetailProductFragment",
                            "FavoritesFragment",
                            args);
                },
                product -> {
                    Bundle args = new Bundle();
                    args.putString("productId", product.getProductId());
                    NavigationUtils.safeNavigate(navController,
                            R.id.favoriteFragment,
                            R.id.action_favoritesFragment_to_detailProductFragment,
                            "DetailProductFragment",
                            "FavoritesFragment",
                            args);
                }
        );
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        favoriteProductRecyclerView.setLayoutManager(gridLayoutManager);
        favoriteProductRecyclerView.setAdapter(favoriteProductAdapter);


        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing);
        favoriteProductRecyclerView.addItemDecoration(new SpaceItemDecoration().grid(2, spacingInPixels));
    }

    private void fetchAndObserveProducts() {
        if (customerId != null) {
            favoriteViewModel.fetchAllFavoriteProducts(customerId, 1, 15, SortType.DESC, FavoriteProductSortBy.CREATED_AT);
        } else {
            Toast.makeText(getContext(), "Can not fetch FAVORITE PRODUCTS", Toast.LENGTH_SHORT).show();
            return;
        }

        favoriteViewModel.getFavoriteProductsLiveData().observe(getViewLifecycleOwner(), favoriteProductModels -> {

        });

        favoriteViewModel.getDetailFavoriteProductsLiveData().observe(getViewLifecycleOwner(), productModels -> {
            if (productModels != null) {
                favoriteProductAdapter.setProducts(productModels);
            } else {
                favoriteProductAdapter.setProducts(new ArrayList<>());
            }
        });

        favoriteViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {

        });

        favoriteViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        favoriteViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("FavoritesFragment", "All favorite products loaded, no more fetching");
            }
        });
    }

    private void fetchAndObserveCategories() {
        favoriteViewModel.fetchAllCategories(1, 10, SortType.DESC, CategorySortBy.CREATED_AT);
        favoriteViewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            if (categories != null) {
                categoryAdapter.setCategories(categories);
            } else {
                categoryAdapter.setCategories(new ArrayList<>());
            }
        });

        favoriteViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {

        });

        favoriteViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        favoriteViewModel.getIsAllDataLoaded().observe(getViewLifecycleOwner(), isAllLoaded -> {
            if (isAllLoaded) {
                Log.d("FavoriteFragment", "All categories loaded, no more fetching");
            }
        });
    }

    private void filterProductsByCategory(String categoryId) {
        if (categoryId == null || "all".equals(categoryId)) {
            // Reset về danh sách đầy đủ
            if (favoriteViewModel.getDetailFavoriteProductsLiveData().getValue() != null) {
                favoriteProductAdapter.setProducts(favoriteViewModel.getDetailFavoriteProductsLiveData().getValue());
            } else {
                favoriteProductAdapter.setProducts(new ArrayList<>());
            }
        } else {
            // Lọc danh sách sản phẩm theo categoryId
            favoriteViewModel.getDetailFavoriteProductsLiveData().observe(getViewLifecycleOwner(), products -> {
                if (products != null) {
                    List<ProductModel> filteredProducts = new ArrayList<>();
                    for (ProductModel product : products) {
                        if (product.getProductCategoryId().equals(categoryId)) {
                            filteredProducts.add(product);
                        }
                    }
                    favoriteProductAdapter.setProducts(filteredProducts);
                }
            });
        }
    }
}