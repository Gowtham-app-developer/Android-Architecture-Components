package com.gowtham.moviesearchapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gowtham.moviesearchapplication.R;
import com.gowtham.moviesearchapplication.constants.URLConstants;
import com.gowtham.moviesearchapplication.model.MovieDetailsModel;
import com.gowtham.moviesearchapplication.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<MovieDetailsModel> dataList;
    private Context context;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private IHandleRowClick rowClick;

    public RecyclerViewAdapter(Context context, List<MovieDetailsModel> dataList, IHandleRowClick rowClick){
        this.context = context;
        this.dataList = dataList;
        this.rowClick = rowClick;
    }

    public void setUpdatedData( List<MovieDetailsModel> dataList) {
        this.dataList = dataList;
       // notifyDataSetChanged();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtReleaseDate;
        private ImageView imageView;

        CustomViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.title);
            txtReleaseDate = itemView.findViewById(R.id.releaseDate);
            imageView = itemView.findViewById(R.id.imageview);
            itemView.setTag(this);
            itemView.setOnClickListener(RecyclerViewAdapter.this);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.row_list, parent, false);
            return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder  = (CustomViewHolder)holder;
            customViewHolder.txtTitle.setText(dataList.get(position).getTitle());
            customViewHolder.txtReleaseDate.setText("Release on "+dataList.get(position).getRelease_date());
            Picasso.with(context).load(URLConstants.THUMBNAIL_URL + dataList.get(position).getPoster_path()).placeholder(context.getResources().getDrawable(R.drawable.no_image)).resize(100, 100).into(customViewHolder.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(dataList != null) {
            return dataList.size();
        }else{
            return 0;
        }
    }

    @Override
    public void onClick(View v) {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
        int position = viewHolder.getAdapterPosition();
        this.rowClick.handleRowClick(dataList.get(position));
    }
}
