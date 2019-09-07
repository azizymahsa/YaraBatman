package azizi.mahsa.yarabatman.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import azizi.mahsa.yarabatman.model.data.JMovie;
import azizi.mahsa.yarabatman.model.data.JMovieDetail;
@Dao
public interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(JMovieDetail movieDetail);

    @Query("DELETE FROM detail")
    void deleteAll();

    @Query("SELECT *FROM detail")
    JMovieDetail getAll();
}
