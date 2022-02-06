package com.cnam.eatudiant.jsonAdapter;

import android.annotation.SuppressLint;
import android.util.Log;
import com.squareup.moshi.*;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateAdapter extends JsonAdapter<Date> {

    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    /**
     * Decodes a nullable instance of type {@link T} from the given {@code reader}.
     *
     * @param reader
     */
    @Nullable
    @Override
    @FromJson
    public Date fromJson(JsonReader reader) {
        String dateStr = null;
        try {
            dateStr = reader.nextString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Date d = simpleDateFormat.parse(dateStr);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encodes the given {@code value} with the given {@code writer}.
     *
     * @param writer
     * @param value
     */
    @Override
    @FromJson
    public void toJson(JsonWriter writer, @Nullable Date value) throws IOException {
        if (value != null) {
            writer.value(value.toString());
        }
    }
}
