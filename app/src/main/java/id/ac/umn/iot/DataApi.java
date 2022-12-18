package id.ac.umn.iot;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class DataApi {
    @SerializedName("data")
    List<DataDevice> dataDeviceList;

    @SerializedName("_id")
    String _id;

    @SerializedName("id_device")
    String id_device;

    public List<DataDevice> getDataDeviceList() {
        return dataDeviceList;
    }

    public void setDataDeviceList(List<DataDevice> dataDeviceList) {
        this.dataDeviceList = dataDeviceList;
    }
}
