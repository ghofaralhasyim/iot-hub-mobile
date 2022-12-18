package id.ac.umn.iot;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //delete
    @Delete
    void delete(MainData mainData);

    //delete all
    @Delete
    void reset(List<MainData> mainData);

    @Query("UPDATE projects SET projectName =:sText WHERE ID = :sID")
    void update(int sID,String sText);

    @Query("UPDATE projects SET projectName =:sProjectName WHERE ID = :sID")
    void update(String sProjectName,int sID);

    @Query("SELECT * FROM projects WHERE ID = :sID")
    MainData findByUserId(int sID);

    @Query("SELECT * FROM projects WHERE deviceToken = :tokenDevice")
    MainData findByTokenDevice(String tokenDevice);

    @Query("SELECT * FROM projects WHERE projectType = :type")
    MainData selectProjectType(String type);

    @Query("SELECT * FROM projects")
    List<MainData> getAll();

    @Query("DELETE FROM projects WHERE ID = :ID")
    void deleteById(int ID);
}
