package id.ac.umn.iot;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "projects")
public class MainData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "projectName")
    private String projectName;

    @ColumnInfo(name = "deviceToken")
    private String deviceToken;

    @ColumnInfo(name = "projectType")
    private String projectType;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDeviceToken() {return deviceToken;}
    public void setDeviceToken(String deviceToken){this.deviceToken = deviceToken;}

    public String getProjectType() {return projectType;}
    public void setProjectType(String type){this.projectType = type;}
}

