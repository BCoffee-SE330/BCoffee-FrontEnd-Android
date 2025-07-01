package com.example.coffeeshopmanagementandroid.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            out.value(value != null ? value.format(formatter) : null);
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String str = in.nextString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return str != null ? LocalDateTime.parse(str, formatter) : null;
        }
        throw new IOException("LocalDateTime parsing requires API level 26 or higher");
    }
}