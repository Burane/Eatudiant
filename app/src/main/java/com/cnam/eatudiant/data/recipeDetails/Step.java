package com.cnam.eatudiant.data.recipeDetails;

import android.os.Parcel;
import android.os.Parcelable;
import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Step  implements Parcelable {
    private int id;

    @Json(name = "id_recipe")
    private int recipeId;

    @Json(name = "step_number")
    private int stepNumber;

    @Json(name = "text")
    private String instructions;

    protected Step(Parcel in) {
        id = in.readInt();
        recipeId = in.readInt();
        stepNumber = in.readInt();
        instructions = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(recipeId);
        parcel.writeInt(stepNumber);
        parcel.writeString(instructions);
    }


}
