package azizi.mahsa.yarabatman.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import azizi.mahsa.yarabatman.R;
import azizi.mahsa.yarabatman.model.data.JMovie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private OnMovieClickListener mClickListener = null;
    private ArrayList<JMovie> mItems = new ArrayList<>();

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_movie, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(List<JMovie> results) {
        int count = getItemCount();
        mItems.addAll(results);
        notifyItemRangeInserted(count, results.size());
    }

    public void setOnMovieClickListener(OnMovieClickListener clickListener) {
        mClickListener = clickListener;
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private ImageView movieImgView;
        private TextView movieTextView;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieTextView = itemView.findViewById(R.id.movie_text);
            movieImgView = itemView.findViewById(R.id.movie_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) {
                        mClickListener.onMovieClick(mItems.get(getAdapterPosition()).getImdbID());
                    }
                }
            });
        }

        public void bind(JMovie data) {
            String title = String.format(Locale.US, "%s (%s)", data.getTitle(), data.getYear());
            movieTextView.setText(title);
            movieImgView.setImageResource(0);
            Glide.with(itemView.getContext()).load(data.getPoster()).into(movieImgView);
        }
    }

    public interface OnMovieClickListener {
        public void onMovieClick(String imdbId);
    }
}
