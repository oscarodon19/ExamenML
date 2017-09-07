package com.mlexam.oscarodon.mlexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mercadopago.decorations.DividerItemDecoration;
import com.mlexam.oscarodon.mlexam.adapters.PaymentMethodAdapter;
import com.mlexam.oscarodon.mlexam.handler.HttpHandler;
import com.mlexam.oscarodon.mlexam.model.PaymentMethod;
import com.mlexam.oscarodon.mlexam.parsers.PaymentMethodParser;


import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;
import java.util.List;

import static com.mlexam.oscarodon.mlexam.BuildConfig.BASE_URL;
import static com.mlexam.oscarodon.mlexam.BuildConfig.PUBLIC_KEY;

public class SelectPaymentActivity extends AppCompatActivity {

    private List<PaymentMethod> paymentMethodList;

    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private Integer amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        Intent mIntent = getIntent();
        amount = mIntent.getIntExtra("amount", 0);

        paymentMethodList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.payment_methods_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetPaymentMethodRequest(this).execute();

    }

    protected void setContentView(){

        setContentView(R.layout.activity_select_payment);
    }


    public class GetPaymentMethodRequest extends AsyncTask<Void, Void, Void> {

        private PaymentMethodParser paymentMethodParser = new PaymentMethodParser();
        private String TAG = GetPaymentMethodRequest.class.getSimpleName();
        private Context mContext;


        public GetPaymentMethodRequest(Context mContext) {

            this.mContext = mContext;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority(BASE_URL).appendPath("v1").appendPath("payment_methods").appendQueryParameter("public_key", PUBLIC_KEY);
            String url = builder.build().toString();
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);


            Log.i(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {

                    JSONArray paymentMethods = new JSONArray(jsonStr);

                    for (int i = 0; i < paymentMethods.length(); i++) {

                        PaymentMethod paymentMethod = paymentMethodParser.parseFromJSONObject(paymentMethods.getJSONObject(i));
                        if(paymentMethod.getPaymentTypeId().equals("credit_card")) {
                            paymentMethodList.add(paymentMethod);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            mRecyclerView.setAdapter(new PaymentMethodAdapter(mActivity, paymentMethodList, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, SelectCardIssuerActivity.class);
                    PaymentMethod selectedPaymentMethod = (PaymentMethod) view.getTag();
                    intent.putExtra("amount",amount);
                    intent.putExtra("paymentMethodId",selectedPaymentMethod.getId());
                    startActivity(intent);
                    finish();
                }
            }));
        }

    }

}

