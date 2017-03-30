package com.gopettingcodingchallenge.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.model.Guide;
import com.gopettingcodingchallenge.util.Constants;
import com.squareup.picasso.Picasso;

/**
 * Created by jaypoojara on 31-03-2017.
 */

public class GuideListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Guide guide;
    private Context mContext;
    private RecyclerView recyclerView;

    public GuideListAdapter(Guide guide, Context mContext, RecyclerView recyclerView) {
        this.guide = guide;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuideItemHolder(LayoutInflater.from(mContext).inflate(R.layout.view_guide_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GuideItemHolder itemHolder = (GuideItemHolder) holder;
        Picasso.with(mContext).load(guide.getData().get(position).getIcon()).fit().centerCrop().into(itemHolder.ivBookIcon);
        itemHolder.tvBookTitle.setText(guide.getData().get(position).getName());
        itemHolder.tvBookType.setText(guide.getData().get(position).getEndDate());
        if (Constants.cartReference.contains(guide.getData().get(position).getUrl())){
            itemHolder.ivCart.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return guide.getData().size();
    }

    private class GuideItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivBookIcon, ivCart;
        TextView tvBookTitle, tvBookType;
        CardView main_container;

        public GuideItemHolder(View itemView) {
            super(itemView);
            ivBookIcon = (ImageView) itemView.findViewById(R.id.ivBookIcon);
            tvBookTitle = (TextView) itemView.findViewById(R.id.tvBookTitle);
            tvBookType = (TextView) itemView.findViewById(R.id.tvBookType);
            ivBookIcon.setLayoutParams(new LinearLayout.LayoutParams(recyclerView.getWidth() / 2, (recyclerView.getWidth() / 2)));
            main_container = (CardView) itemView.findViewById(R.id.main_container);
            ivCart = (ImageView) itemView.findViewById(R.id.ivCart);
            ivCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == ivCart) {
                if (ivCart.isSelected()) {
                    Constants.cart.remove(getAdapterPosition());
                    Constants.cartReference.remove(getAdapterPosition());
                } else {
                    Constants.cart.add(guide.getData().get(getAdapterPosition()));
                    Constants.cartReference.add(guide.getData().get(getAdapterPosition()).getUrl());
                }
                ivCart.setSelected(!ivCart.isSelected());

            }
        }
    }

}
