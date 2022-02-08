package com.cnam.eatudiant.data.recipeDetails;

import android.os.Parcel;
import android.os.Parcelable;
import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Requirement implements Parcelable {

    @Json(name = "nom")
    private String name;

    @Json(name = "qte")
    private double quantity;

    protected Requirement(Parcel in) {
        name = in.readString();
        quantity = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(quantity);
    }

    public static final Creator<Requirement> CREATOR = new Creator<Requirement>() {
        @Override
        public Requirement createFromParcel(Parcel in) {
            return new Requirement(in);
        }

        @Override
        public Requirement[] newArray(int size) {
            return new Requirement[size];
        }
    };


}
