package id.ac.umn.iot;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BTDao {
    @Insert(onConflict = REPLACE)
    void insert(BTData btData);

    //delete
    @Delete
    void delete(BTData btData);

    //delete all
    @Delete
    void reset(List<BTData> btData);

    //update
    @Query("UPDATE bluetooth_project SET command =:sCommand, buttonName =:sBtnName WHERE ID = :sID")
    void update(String sCommand, String sBtnName, int sID);

    //get all data
    @Query("SELECT * FROM bluetooth_project")
    List<BTData> getAll();

    @Query("SELECT * FROM bluetooth_project WHERE parentID = :ID")
    List<BTData> getByParentID(int ID);

    @Query("DELETE FROM bluetooth_project WHERE ID=:sID")
    void deleteByID(int sID);
}
