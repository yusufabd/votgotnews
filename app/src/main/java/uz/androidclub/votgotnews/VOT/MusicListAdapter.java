package uz.androidclub.votgotnews.VOT;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import uz.androidclub.votgotnews.R;

/**
 * Created by yusuf.abdullaev on 8/26/2016.
 */
public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Music> musicArrayList;
    private LinearLayout.LayoutParams params;

    public MusicListAdapter(Context context, ArrayList<Music> musicArrayList) {
        this.context = context;
        this.musicArrayList = musicArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemViwe = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_item, parent, false);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return new ViewHolder(itemViwe);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setLayoutParams(params);
        final Music m = musicArrayList.get(position);
        final Handler handler = new Handler();
        holder.title.setText(m.getTitle());
        holder.author.setText(m.getAuthor());
        holder.seekBar.setMax(m.getDuration());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra("music_info", m);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musicArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, author;
        public ImageView state, share;
        public SeekBar seekBar;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.music_title);
            author = (TextView)itemView.findViewById(R.id.music_author);
            state = (ImageView)itemView.findViewById(R.id.play_pause);
            share = (ImageView)itemView.findViewById(R.id.share);
            seekBar = (SeekBar)itemView.findViewById(R.id.seek_bar);
        }
    }
}
