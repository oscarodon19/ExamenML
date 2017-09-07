package com.mlexam.oscarodon.mlexam.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlexam.oscarodon.mlexam.R;
import com.mlexam.oscarodon.mlexam.model.Installment;

import java.util.List;

/**
 * Created by oscarodon on 4/9/17.
 */

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.ViewHolder> {

    private Activity mActivity;
    private List<Installment> mData;
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
        }

    }

    public InstallmentAdapter(Activity activity, List<Installment> data, View.OnClickListener listener) {
        mActivity = activity;
        mData = data;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v, mListener,parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Installment installment = mData.get(position);

        holder.mTextView.setText(installment.getRecommendedMessage());

        holder.itemView.setTag(installment);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
