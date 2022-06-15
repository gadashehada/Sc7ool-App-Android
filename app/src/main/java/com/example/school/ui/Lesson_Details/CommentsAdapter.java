package com.example.school.ui.Lesson_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.school.R;
import com.example.school.read_api.ApiComments;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<ViewHolder1> {

    List<ApiComments> comments;
    int itemLayoutId;

    public CommentsAdapter(List<ApiComments> comments ,int itemLayoutId){
        this.itemLayoutId = itemLayoutId;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId , parent , false);
        ViewHolder1 viewHolder = new ViewHolder1(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder1 holder, int position) {
        ApiComments apiComment = comments.get(position);
        holder.tv_comment_body.setText(apiComment.getComment_body());
        holder.tv_user_name.setText(apiComment.getUser_name());
        Picasso.get().load(apiComment.getUser_image()).into(holder.im_user);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}

class ViewHolder1 extends RecyclerView.ViewHolder {

    TextView tv_comment_body , tv_user_name ;
    ImageView im_user ;

    public ViewHolder1(@NonNull View itemView) {
        super(itemView);
        tv_comment_body = itemView.findViewById(R.id.tv_comment_item);
        tv_user_name = itemView.findViewById(R.id.tv_user_comment_name);
        im_user = itemView.findViewById(R.id.im_user_comment);
    }
}
