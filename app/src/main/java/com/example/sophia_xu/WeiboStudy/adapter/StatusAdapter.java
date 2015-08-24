package com.example.sophia_xu.WeiboStudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sophia_xu.WeiboStudy.entity.PicUrls;
import com.example.sophia_xu.WeiboStudy.entity.Status;
import com.example.sophia_xu.WeiboStudy.entity.User;
import com.example.sophia_xu.oneapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sophia_Xu on 2015/8/17.
 */
public class StatusAdapter extends BaseAdapter{
    private Context mContext;
    private List<Status> datas;
    private ImageLoader imageLoader;

    public StatusAdapter(Context mContext,List<Status> datas){
        this.mContext = mContext;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();

    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Status getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_status,null);
            viewHolder.ll_card_content = (LinearLayout) convertView.findViewById(R.id.id_ll_card_content);
            viewHolder.iv_avater = (ImageView) convertView.findViewById(R.id.id_im_photo);
            viewHolder.rl_content = (RelativeLayout) convertView.findViewById(R.id.id_rl_content);
            viewHolder.tv_subhead = (TextView) convertView.findViewById(R.id.id_tv_subhead);
            viewHolder.tv_caption = (TextView) convertView.findViewById(R.id.id_tv_caption);

            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.id_tv_content);
            viewHolder.include_status_image = (FrameLayout) convertView.findViewById(R.id.id_include_status_image);
            viewHolder.gv_images = (GridView) convertView.findViewById(R.id.id_gv_images);
            viewHolder.iv_image = (ImageView) convertView.findViewById(R.id.id_iv_image);

            viewHolder.ll_retweeted_status = (LinearLayout) convertView.findViewById(R.id.include_retweeted_status);
            viewHolder.tv_retweeted_content = (TextView) convertView.findViewById(R.id.id_tv_retweeted_content);
            viewHolder.include_retweeted_status_image = (FrameLayout) viewHolder.ll_retweeted_status.findViewById(R.id.id_include_status_image);
            viewHolder.gv_retweeted_images = (GridView) viewHolder.ll_retweeted_status.findViewById(R.id.id_gv_images);
            viewHolder.iv_retweeted_image = (ImageView) viewHolder.ll_retweeted_status.findViewById(R.id.id_iv_image);

            viewHolder.tv_share = (TextView) convertView.findViewById(R.id.id_tv_share_bottom);
            viewHolder.tv_comment = (TextView) convertView.findViewById(R.id.id_tv_comment_bottom);
            viewHolder.tv_like = (TextView) convertView.findViewById(R.id.id_tv_like_bottom);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Status status =  getItem(position);
        User user = status.getUser();
        imageLoader.displayImage(user.getProfile_image_url(),viewHolder.iv_avater);
        viewHolder.tv_subhead.setText(user.getName());
        viewHolder.tv_caption.setText(status.getCreated_at()+" 来自 " + status.getSource());
        viewHolder.tv_content.setText(status.getText());

        setImages(status,viewHolder.include_status_image,viewHolder.gv_images,viewHolder.iv_image);

        Status retweeted_status = status.getRetweeted_status();
        if(retweeted_status != null){
            User retUser = retweeted_status.getUser();

            viewHolder.ll_retweeted_status.setVisibility(View.VISIBLE);
            viewHolder.tv_retweeted_content.setText("@" + retUser.getName()+":"+retweeted_status.getText());

            setImages(retweeted_status,viewHolder.include_retweeted_status_image,viewHolder.gv_retweeted_images,viewHolder.iv_retweeted_image);

        }else{
            viewHolder.ll_retweeted_status.setVisibility(View.GONE);
        }


        viewHolder.tv_share.setText(status.getReposts_count() == 0? "转发":status.getReposts_count()+"");
        viewHolder.tv_comment.setText(status.getReposts_count() == 0? "评论":status.getComments_count()+"");
        viewHolder.tv_like.setText(status.getReposts_count() == 0 ? "赞" : status.getAttitudes_count() + "");


        return convertView;
    }

    private void setImages(Status status, FrameLayout imgContainer, GridView gv_images, ImageView iv_image) {

        ArrayList<PicUrls> pic_urls = status.getPic_urls();
        String thumbnail_pic = status.getThumbnail_pic();

        if(pic_urls != null && pic_urls.size() > 1){
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);

            StatusGridImgsAdapter gvAdapter = new StatusGridImgsAdapter(mContext,pic_urls);
            gv_images.setAdapter(gvAdapter);

        }else if(thumbnail_pic != null){
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);

            imageLoader.displayImage(thumbnail_pic,iv_image);
        }else
            imgContainer.setVisibility(View.GONE);

    }


    private static class ViewHolder{

        public LinearLayout ll_card_content;
        public ImageView iv_avater;
        public RelativeLayout rl_content;//
        public TextView tv_subhead;
        public TextView tv_caption;

        public TextView tv_content;
        public FrameLayout include_status_image;
        public GridView gv_images;
        public ImageView iv_image;

        public LinearLayout ll_retweeted_status;
        public TextView tv_retweeted_content;
        public FrameLayout include_retweeted_status_image;
        public GridView gv_retweeted_images;
        public ImageView iv_retweeted_image;

        public TextView tv_share;
        public TextView tv_comment;
        public TextView tv_like;


    }
}
