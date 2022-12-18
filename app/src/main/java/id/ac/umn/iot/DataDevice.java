package id.ac.umn.iot;

import com.google.gson.annotations.SerializedName;

public class DataDevice {
    @SerializedName("data")
    String data;

    @SerializedName("timestamp")
    String timestamp;

    public DataDevice(String data, String timestamp) {
        this.data = data;
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }
}
