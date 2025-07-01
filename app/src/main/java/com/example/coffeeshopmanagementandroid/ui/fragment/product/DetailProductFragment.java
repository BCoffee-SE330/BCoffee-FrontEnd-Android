package com.example.coffeeshopmanagementandroid.ui.fragment.product;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductModel;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;
import com.example.coffeeshopmanagementandroid.ui.MainActivity;
import com.example.coffeeshopmanagementandroid.ui.adapter.VariantProductAdapter;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.CategoryViewModel;
import com.example.coffeeshopmanagementandroid.ui.viewmodel.DetailProductViewModel;
import com.example.coffeeshopmanagementandroid.utils.NavigationUtils;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.math.BigDecimal;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailProductFragment extends Fragment {

    private TextView descriptionTextView;
    private ImageButton backButton;
    private ImageButton favoriteButton;
    private TextView productNameTextView;
    private TextView categoryTextView;
    private ImageView productImage;
    private NavController navController;
    private TextView priceTextView;
    private ImageButton addToCardButton;
    private TextView tvRating;

    private Button buyButton;
    private ProductModel currentProduct;

    private boolean isExpanded = false;
    private static final String READ_MORE = "Đọc thêm";
    private static final String COLLAPSE = "Ẩn hiện";
    private static final String ELLIPSIS = "... ";
    private static final int MAX_LINES = 3;
    private final int expandCollapseColor = Color.parseColor("#4CAF50");

    private VariantProductAdapter variantProductAdapter;

    private DetailProductViewModel detailProductViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();

        navController = NavHostFragment.findNavController(this);
        detailProductViewModel = new ViewModelProvider(this).get(DetailProductViewModel.class);

        // Get productId from arguments
        String productId = requireArguments().getString("productId");

        Log.d("DetailProductFragment", "Received productId: " + productId);
        if (productId != null) {
            detailProductViewModel.fetchProductDetailAndVariants(productId);
        } else {
            Toast.makeText(requireContext(), "Missing product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Observe live data
        detailProductViewModel.getProductLiveData().observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                currentProduct = product;
                productNameTextView.setText(product.getProductName());
                categoryTextView.setText(product.getProductCategoryId());
                priceTextView.setText(CurrencyFormat.formatVND(product.getProductPrice()));
                loadProductImage(product.getProductThumb());
                setupExpandableText(product.getProductDescription());

                // Set rating from API
                TextView ratingTextView = requireView().findViewById(R.id.ratingTextView);
                if (product.getProductRatingsAverage() != null) {
                    ratingTextView.setText(product.getProductRatingsAverage().toString());
                } else {
                    ratingTextView.setText("0.0");
                }

                if (product.isFavorite()) {
                    favoriteButton.setImageResource(R.drawable.favorites_icon);
                } else {
                    favoriteButton.setImageResource(R.drawable.unfavorites_icon);
                }
            }
        });

        detailProductViewModel.getVariantListLiveData().observe(getViewLifecycleOwner(), this::setupVariants);

        detailProductViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        descriptionTextView = requireView().findViewById(R.id.descriptionTextView);
        if (descriptionTextView != null) {
            descriptionTextView.setMovementMethod(LinkMovementMethod.getInstance());
            descriptionTextView.setHighlightColor(Color.TRANSPARENT);
        }

        backButton = requireView().findViewById(R.id.backButtonDetailProduct);
        if (backButton != null) {
            backButton.setOnClickListener(v -> {
                if (navController != null) {
                    navController.popBackStack();
                }
            });
        }

        favoriteButton = requireView().findViewById(R.id.favoriteButtonDetailProduct);
        if (favoriteButton != null) {
            favoriteButton.setOnClickListener(v -> {
                if (detailProductViewModel.getProductLiveData().getValue() != null) {
                    ProductModel product = detailProductViewModel.getProductLiveData().getValue();
                    boolean isFavorite = !product.isFavorite();
                    product.setFavorite(isFavorite);
                    detailProductViewModel.addProductToFavorite(product.getProductId());
                    favoriteButton.setImageResource(isFavorite ? R.drawable.favorites_icon : R.drawable.unfavorites_icon);
                } else {
                    Toast.makeText(requireContext(), "Product not found", Toast.LENGTH_SHORT).show();
                }
            });
        }

        addToCardButton = requireView().findViewById(R.id.addToCardButton);
        if (addToCardButton != null) {
            addToCardButton.setOnClickListener(v -> onAddToCart());
        }

        buyButton = requireView().findViewById(R.id.buyButton);
        if (buyButton != null) {
            buyButton.setOnClickListener(v -> onBuyButton());
        }

        productNameTextView = requireView().findViewById(R.id.productNameTextView);
        categoryTextView = requireView().findViewById(R.id.categoryTextView);
        priceTextView = requireView().findViewById(R.id.priceTextView);
        productImage = requireView().findViewById(R.id.productImage);
    }

    private void loadProductImage(String imageUrl) {
        if (productImage != null && imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(productImage);
        } else {
            productImage.setImageResource(R.drawable.placeholder_image);
        }
    }

    private void setupVariants(List<ProductVariantModel> variants) {
        variantProductAdapter = new VariantProductAdapter(variants);
        variantProductAdapter.setOnVariantSelectedListener(position -> {
            if (currentProduct == null) return;
            BigDecimal price = currentProduct.getProductPrice();
            if (position == 0) {
                priceTextView.setText(CurrencyFormat.formatVND(price.multiply(BigDecimal.valueOf(0.8))));
            } else if (position == 1) {
                priceTextView.setText(CurrencyFormat.formatVND(price));
            } else {
                priceTextView.setText(CurrencyFormat.formatVND(price.multiply(BigDecimal.valueOf(1.2))));
            }
        });
        RecyclerView variantProductRecyclerView = requireView().findViewById(R.id.variantProductRecyclerView);
        if (variantProductRecyclerView != null) {
            FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(requireContext());
            flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
            flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_BETWEEN);
            flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
            variantProductRecyclerView.setLayoutManager(flexboxLayoutManager);
            int verticalSpacing = getResources().getDimensionPixelSize(R.dimen.vertical_spacing);
            variantProductRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(verticalSpacing));
            variantProductRecyclerView.setAdapter(variantProductAdapter);
        }
    }

    private void setupExpandableText(String text) {
        if (text == null || text.isEmpty()) {
            return;
        }

        descriptionTextView.setMaxLines(isExpanded ? Integer.MAX_VALUE : MAX_LINES);

        if (isExpanded) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            builder.append("\n");

            SpannableString collapseSpannable = new SpannableString(COLLAPSE);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    isExpanded = false;
                    setupExpandableText(text);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(expandCollapseColor);
                    ds.setUnderlineText(false);
                }
            };

            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

            collapseSpannable.setSpan(clickableSpan, 0, COLLAPSE.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            collapseSpannable.setSpan(boldSpan, 0, COLLAPSE.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            builder.append(collapseSpannable);
            descriptionTextView.setText(builder);

            Log.d("ExpandableText", "Text expanded, showing collapse option");
        } else {
            descriptionTextView.setText(text);

            descriptionTextView.post(() -> {
                if (descriptionTextView.getLineCount() > MAX_LINES) {
                    Log.d("ExpandableText", "Text needs expanding, line count: " + descriptionTextView.getLineCount());

                    Layout layout = descriptionTextView.getLayout();
                    if (layout == null) {
                        Log.e("ExpandableText", "Layout is null");
                        return;
                    }

                    int lineEndIndex;
                    try {
                        lineEndIndex = layout.getLineEnd(MAX_LINES - 1);
                        int reserve = ELLIPSIS.length() + READ_MORE.length() + 10;
                        if (lineEndIndex - reserve < 0) {
                            lineEndIndex = text.length();
                        } else {
                            lineEndIndex = lineEndIndex - reserve;
                        }
                    } catch (Exception e) {
                        Log.e("ExpandableText", "Error getting line end: " + e.getMessage());
                        return;
                    }

                    String truncatedText = text.substring(0, lineEndIndex);

                    Log.d("ExpandableText", "Truncated at index: " + lineEndIndex);
                    Log.d("ExpandableText", "Truncated text: " + truncatedText);

                    SpannableStringBuilder builder = new SpannableStringBuilder(truncatedText);
                    builder.append(ELLIPSIS);

                    SpannableString readMoreSpannable = new SpannableString(READ_MORE);

                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Log.d("ExpandableText", "Read more clicked");
                            isExpanded = true;
                            setupExpandableText(text);
                        }

                        @Override
                        public void updateDrawState(@NonNull TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(expandCollapseColor);
                            ds.setUnderlineText(false);
                        }
                    };

                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

                    readMoreSpannable.setSpan(clickableSpan, 0, READ_MORE.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    readMoreSpannable.setSpan(boldSpan, 0, READ_MORE.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    builder.append(readMoreSpannable);
                    descriptionTextView.setText(builder);
                } else {
                    Log.d("ExpandableText", "Text doesn't need expanding");
                    descriptionTextView.setText(text);
                }
            });
        }
    }

    private void onAddToCart() {
        if (variantProductAdapter == null || variantProductAdapter.getSelectedVariant() == null) {
            Toast.makeText(requireContext(), "Please select a variant", Toast.LENGTH_SHORT).show();
            return;
        }
        String variantId = variantProductAdapter.getSelectedVariant();
        int quantity = 1; // Default quantity, can be modified to get from user input
        detailProductViewModel.addToCart(variantId, quantity, new AddToCartCallback() {
            @Override
            public void onSuccess(String message) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void onBuyButton() {
        if (variantProductAdapter == null || variantProductAdapter.getSelectedVariant() == null) {
            Toast.makeText(requireContext(), "Please select a variant", Toast.LENGTH_SHORT).show();
            return;
        }
        String variantId = variantProductAdapter.getSelectedVariant();
        int quantity = 1; // Default quantity, can be modified to get from user input
        detailProductViewModel.addToCart(variantId, quantity, new AddToCartCallback() {
            @Override
            public void onSuccess(String message) {
                requireActivity().runOnUiThread(() ->
                        {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            NavigationUtils.safeNavigate(navController,
                                    R.id.detailProductFragment,
                                    R.id.action_detailProductFragment_to_cartFragment,
                                    "CartFragment",
                                    "DetailProductFragment");
                        }
                );
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideBottomNavigation();
        }
    }

    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
                outRect.bottom = verticalSpaceHeight;
            }
        }
    }
    public interface AddToCartCallback {
        void onSuccess(String message);
        void onError(String error);
    }
}