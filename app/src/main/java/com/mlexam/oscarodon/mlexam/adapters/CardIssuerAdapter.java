package com.mlexam.oscarodon.mlexam.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadopago.util.TextUtils;
import com.mlexam.oscarodon.mlexam.R;
import com.mlexam.oscarodon.mlexam.model.CardIssuer;
import com.mlexam.oscarodon.mlexam.model.PaymentMethod;
import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by oscarodon on 3/9/17.
 */

public class CardIssuerAdapter extends RecyclerView.Adapter<CardIssuerAdapter.ViewHolder>{

    private Activity mActivity;
    private List<CardIssuer> mData;
    private View.OnClickListener mListener = null;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ImageView mImageView;
        public Context mContext;

        public ViewHolder(View v, View.OnClickListener listener, Context context) {

            super(v);
            mContext = context;
            mTextView = (TextView) v.findViewById(R.id.label);
            if (listener != null) {
                v.setOnClickListener(listener);
            }
            mImageView = (ImageView) v.findViewById(R.id.thumbnail);
        }

    }

    public CardIssuerAdapter(Activity activity, List<CardIssuer> data, View.OnClickListener listener) {
        mActivity = activity;
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_simple_list, parent, false);

        return new ViewHolder(v, mListener,parent.getContext());

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CardIssuer cardIssuer = mData.get(position);

        holder.mTextView.setText(cardIssuer.getName());

        if(!TextUtils.isEmpty(cardIssuer.getThumbnail())) {

            Picasso.with(holder.mContext).load(cardIssuer.getThumbnail()).error(R.drawable.mpsdk_review_product_placeholder).placeholder(R.drawable.mpsdk_review_product_placeholder).into(holder.mImageView);

        }

        holder.itemView.setTag(cardIssuer);

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

}
