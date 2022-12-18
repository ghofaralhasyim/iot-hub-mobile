package id.ac.umn.iot;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "bluetooth_project")
public class BTData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "parentID")
    private int parentID;

    @ColumnInfo(name = "buttonName")
    private String buttonName;

    @ColumnInfo(name = "command")
    private String command;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public int getParentID() {
        return parentID;
    }
    public void setParentID(int ID) {
        this.parentID = ID;
    }

    public String getButtonName() {
        return buttonName;
    }
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getCommand() {
        return command;
    }
    public void setCommand(String projectName) {
        this.command = projectName;
    }
}
