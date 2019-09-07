package azizi.mahsa.yarabatman.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import azizi.mahsa.yarabatman.model.data.JMovie;

@Database(entities = {JMovie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public static MovieDatabase createInstance(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                MovieDatabase.class,
                "data")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public abstract MovieDao getMovieDao();
}
