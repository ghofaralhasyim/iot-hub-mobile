package id.ac.umn.iot;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDataApi {
    @SerializedName("data")
    DataApi data;

    public DataApi getData() {
        return data;
    }

    public void setData(DataApi data) {
        this.data = data;
    }
}
