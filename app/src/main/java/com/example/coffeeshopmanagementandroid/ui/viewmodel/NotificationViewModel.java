package com.example.coffeeshopmanagementandroid.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.coffeeshopmanagementandroid.data.dto.BasePagingResponse;
import com.example.coffeeshopmanagementandroid.data.dto.notification.request.GetAllReceivedNotificationRequests;
import com.example.coffeeshopmanagementandroid.data.dto.notification.response.NotificationResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.NotificationMapper;
import com.example.coffeeshopmanagementandroid.domain.model.notification.NotificationModel;
import com.example.coffeeshopmanagementandroid.domain.usecase.NotificationUseCase;
import com.example.coffeeshopmanagementandroid.utils.enums.SortType;
import com.example.coffeeshopmanagementandroid.utils.enums.sortBy.OrderSortBy;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private final NotificationUseCase notificationUseCase;
    private final MutableLiveData<List<NotificationModel>> notificationModelsLiveData = new MutableLiveData<>();
    private final MutableLiveData<NotificationModel> notificationModelMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    @Inject
    public NotificationViewModel(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }

    public MutableLiveData<List<NotificationModel>> getNotificationModelsLiveData() {
        return notificationModelsLiveData;
    }

    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchNotifications(int page, int limit, SortType sortType, OrderSortBy sortBy) {
        new Thread(() -> {
            try {
                GetAllReceivedNotificationRequests request = new GetAllReceivedNotificationRequests(page, limit, sortType, sortBy);
                BasePagingResponse<List<NotificationResponse>> response = notificationUseCase.getReceivedNotifications(request);
                List<NotificationModel> models = NotificationMapper.mapToNotificationModelList(response.getData());
                notificationModelsLiveData.postValue(models);
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
            }
        }).start();
    }

    public void markNotificationAsRead(String notificationId) {
        new Thread(() -> {
            try {
                notificationUseCase.markNotificationAsRead(notificationId);
                List<NotificationModel> currentList = notificationModelsLiveData.getValue();
                if (currentList != null) {
                    for (NotificationModel model : currentList) {
                        if (model.getId().equals(notificationId)) {
                            model.setRead(true);
                            break;
                        }
                    }
                    notificationModelsLiveData.postValue(currentList);
                }
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
            }
        }).start();
    }

    public MutableLiveData<NotificationModel> getNotificationModelMutableLiveData() {
        return notificationModelMutableLiveData;
    }

    public void fetchNotificationById(String id) {
        new Thread(() -> {
            try {
                NotificationResponse response = notificationUseCase.getNotificationById(id).getData();
                NotificationModel model = NotificationMapper.mapToNotificationModel(response);
                notificationModelMutableLiveData.postValue(model);
            } catch (Exception e) {
                errorLiveData.postValue(e.getMessage());
            }
        }).start();
    }
}