package com.example.coffeeshopmanagementandroid.data.mapper;

import com.example.coffeeshopmanagementandroid.data.dto.user.response.UserResponse;
import com.example.coffeeshopmanagementandroid.domain.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserMapper {
    public static UserModel mapUserResponseToUserDomain(UserResponse userResponse) {
        UserModel user = new UserModel();
        user.setUserId(userResponse.getId());
        user.setUserName(userResponse.getName());
        user.setUserLastName(userResponse.getLastName());
        user.setUserGender(userResponse.getGender());
        user.setUserEmail(userResponse.getEmail());
        user.setUserPhone(userResponse.getPhoneNumber());
        user.setUserAvatar(userResponse.getAvatar());

        // Parse LocalDateTime string to Date
        String birthdayStr = userResponse.getBirthday();
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                // Example: "1971-06-30T00:14:18.895578"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date birthday = sdf.parse(birthdayStr.split("\\.")[0]); // Remove nanoseconds if present
                user.setUserBirthday(birthday);
            } catch (ParseException e) {
                user.setUserBirthday(null);
            }
        } else {
            user.setUserBirthday(null);
        }

        return user;
    }
}