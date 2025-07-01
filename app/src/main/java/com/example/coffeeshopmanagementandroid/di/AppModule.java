package com.example.coffeeshopmanagementandroid.di;

import android.content.Context;

import com.example.coffeeshopmanagementandroid.data.api.AddressService;
import com.example.coffeeshopmanagementandroid.data.api.AuthService;
import com.example.coffeeshopmanagementandroid.data.api.BranchService;
import com.example.coffeeshopmanagementandroid.data.api.CartService;
import com.example.coffeeshopmanagementandroid.data.api.CategoryService;
import com.example.coffeeshopmanagementandroid.data.api.DiscountService;
import com.example.coffeeshopmanagementandroid.data.api.NotificationService;
import com.example.coffeeshopmanagementandroid.data.api.OrderService;
import com.example.coffeeshopmanagementandroid.data.api.PaymentService;
import com.example.coffeeshopmanagementandroid.data.api.ProductService;
import com.example.coffeeshopmanagementandroid.data.api.UserService;
import com.example.coffeeshopmanagementandroid.data.repository.AddressRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.AuthRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.BranchRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.CartRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.CategoryRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.DiscountRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.NotificationRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.OrderRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.PaymentRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.ProductRepositoryImpl;
import com.example.coffeeshopmanagementandroid.data.repository.UserRepositoryImpl;
import com.example.coffeeshopmanagementandroid.domain.repository.AddressRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.AuthRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.BranchRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.CartRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.CategoryRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.DiscountRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.NotificationRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.OrderRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.PaymentRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.ProductRepository;
import com.example.coffeeshopmanagementandroid.domain.repository.UserRepository;
import com.example.coffeeshopmanagementandroid.domain.usecase.AddressUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.BranchUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.CartUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.CategoryUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.DiscountUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.LoginUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.LogoutUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.NotificationUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.OrderUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.PaymentUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.ProductUseCase;
import com.example.coffeeshopmanagementandroid.domain.usecase.UserUseCase;
import com.example.coffeeshopmanagementandroid.utils.RetrofitInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    private static Context context;

    public AppModule(@ApplicationContext Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public static RetrofitInstance provideRetrofitInstance(@ApplicationContext Context context) {
        return new RetrofitInstance(context);
    }

    @Provides
    @Singleton
    public static AuthService provideAuthService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(AuthService.class);
    }

    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(AuthService authService, @ApplicationContext Context context) {
        return new AuthRepositoryImpl(authService, context);
    }

    @Provides
    public static LoginUseCase provideLoginUseCase(AuthRepository authRepository) {
        return new LoginUseCase(authRepository);
    }

    @Provides
    public static LogoutUseCase provideLogoutUseCase(AuthRepository authRepository) {
        return new LogoutUseCase(authRepository);
    }

    @Provides
    @Singleton
    public static ProductService provideProductService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(ProductService.class);
    }

    @Provides
    @Singleton
    public static ProductRepository provideProductRepository(ProductService productService) {
        return new ProductRepositoryImpl(productService);
    }

    @Provides
    public static ProductUseCase provideProductUseCase(ProductRepository productRepository) {
        return new ProductUseCase(productRepository);
    }
    @Provides
    @Singleton
    public static CartService provideCartService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(CartService.class);
    }

    @Provides
    @Singleton
    public static CartRepository provideCartRepository(CartService cartService) {
        return new CartRepositoryImpl(cartService);
    }
    @Provides
    public static CartUseCase provideCartUseCase(CartRepository cartRepository) {
        return new CartUseCase(cartRepository);
    }

    @Provides
    @Singleton
    public static PaymentService providePaymentService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(PaymentService.class);
    }

    @Provides
    @Singleton
    public static PaymentRepository providePaymentRepository(PaymentService paymentService) {
        return new PaymentRepositoryImpl(paymentService);
    }
    @Provides
    public static PaymentUseCase providePaymentUseCase(PaymentRepository paymentRepository) {
        return new PaymentUseCase(paymentRepository);
    }

    @Provides
    @Singleton
    public static AddressService provideAddressService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(AddressService.class);
    }

    @Provides
    @Singleton
    public static AddressRepository provideAddressRepository(AddressService addressService) {
        return new AddressRepositoryImpl(addressService);
    }
    @Provides
    public static AddressUseCase provideAddressUseCase(AddressRepository addressRepository) {
        return new AddressUseCase(addressRepository);
    }

    @Provides
    @Singleton
    public static CategoryService provideCategoryService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(CategoryService.class);
    }
    @Provides
    @Singleton
    public static CategoryRepository provideCategoryRepository(CategoryService categoryService) {
        return new CategoryRepositoryImpl(categoryService);
    }
    @Provides
    public static CategoryUseCase provideCategoryUseCase(CategoryRepository categoryRepository) {
        return new CategoryUseCase(categoryRepository);
    }
    @Provides
    @Singleton
    public static OrderService provideOrderService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(OrderService.class);
    }
    @Provides
    @Singleton
    public static OrderRepository provideOrderRepository(OrderService orderService) {
        return new OrderRepositoryImpl(orderService);
    }
    @Provides
    public static OrderUseCase provideOrderUseCase(OrderRepository orderRepository) {
        return new OrderUseCase(orderRepository);
    }
    @Provides
    @Singleton
    public static UserService provideUserService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(UserService.class);
    }
    @Provides
    @Singleton
    public static UserRepository provideUserRepository(UserService userService) {
        return new UserRepositoryImpl(userService);
    }
    @Provides
    public static UserUseCase provideUserUseCase(UserRepository userRepository) {
        return new UserUseCase(userRepository);
    }

    @Provides
    @Singleton
    public static NotificationService provideNotificationService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(NotificationService.class);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(NotificationService notificationService) {
        return new NotificationRepositoryImpl(notificationService);
    }

    @Provides
    public static NotificationUseCase provideNotificationUseCase(NotificationRepository notificationRepository) {
        return new NotificationUseCase(notificationRepository);
    }

    @Provides
    @Singleton
    public static BranchService provideBranchService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(BranchService.class);
    }

    @Provides
    @Singleton
    public static BranchRepository provideBranchRepository(BranchService branchService) {
        return new BranchRepositoryImpl(branchService);
    }

    @Provides
    public static BranchUseCase provideBranchUseCase(BranchRepository branchRepository) {
        return new BranchUseCase(branchRepository);
    }

    @Provides
    @Singleton
    public static DiscountService provideDiscountService(RetrofitInstance retrofitInstance) {
        return retrofitInstance.createService(DiscountService.class);
    }

    @Provides
    @Singleton
    public static DiscountRepository provideDiscountRepository(DiscountService discountService) {
        return new DiscountRepositoryImpl(discountService);
    }

    @Provides
    public static DiscountUseCase provideDiscountUseCase(DiscountRepository discountRepository) {
        return new DiscountUseCase(discountRepository);
    }
}
