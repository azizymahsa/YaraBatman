package azizi.mahsa.yarabatman.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import azizi.mahsa.yarabatman.model.data.JMovie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<JMovie> venue);

    @Query("DELETE FROM movie")
    void deleteAll();

    @Query("SELECT *FROM movie")
    List<JMovie> getAll();
}
