package id.ac.umn.iot;

import com.google.gson.annotations.SerializedName;

public class GetTokenDevice {
    @SerializedName("generated_device")
    String generated_device;

    public String getGenerated_token() {
        return generated_device;
    }
}
