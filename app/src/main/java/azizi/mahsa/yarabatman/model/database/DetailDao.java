package azizi.mahsa.yarabatman.model.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import azizi.mahsa.yarabatman.model.data.JMovieDetail;
@Dao
public interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(JMovieDetail movieDetail);

    @Query("SELECT *FROM detail WHERE _id=:id")
    JMovieDetail getDetail(String id);
}
