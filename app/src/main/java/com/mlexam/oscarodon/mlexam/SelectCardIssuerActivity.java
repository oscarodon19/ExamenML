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
import com.mercadopago.util.JsonUtil;
import com.mlexam.oscarodon.mlexam.adapters.CardIssuerAdapter;
import com.mlexam.oscarodon.mlexam.handler.HttpHandler;
import com.mlexam.oscarodon.mlexam.model.CardIssuer;
import com.mlexam.oscarodon.mlexam.parsers.CardIssuerParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.mlexam.oscarodon.mlexam.BuildConfig.BASE_URL;
import static com.mlexam.oscarodon.mlexam.BuildConfig.PUBLIC_KEY;

public class SelectCardIssuerActivity extends AppCompatActivity {

    private List<CardIssuer> cardIssuerList;

    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private Integer amount;
    private String selectedPaymentMethodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();

        cardIssuerList = new ArrayList<>();

        Intent mIntent = getIntent();
        amount = mIntent.getIntExtra("amount", 0);
        selectedPaymentMethodId = mIntent.getStringExtra("paymentMethodId");


        mRecyclerView = (RecyclerView) findViewById(R.id.card_issuer_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetCardIssuerRequest(this,selectedPaymentMethodId).execute();

    }

    protected void setContentView(){

        setContentView(R.layout.activity_select_card_issuer);
    }

    public class GetCardIssuerRequest extends AsyncTask<Void, Void, Void> {

//        public static final String CARD_ISSUER_BASE_URL = "https://api.mercadopago.com/v1/payment_methods/card_issuers?public_key=444a9ef5-8a6b-429f-abdf-587639155d88&payment_method_id=";
        private CardIssuerParser cardIssuerParser = new CardIssuerParser();
        private String TAG = SelectCardIssuerActivity.GetCardIssuerRequest.class.getSimpleName();
        private Context mContext;

        private String selectedPaymentMethodId;

        public GetCardIssuerRequest(Context context,String selectedPaymentMethodId) {
            this.selectedPaymentMethodId = selectedPaymentMethodId;
            this.mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority(BASE_URL).appendPath("v1").appendPath("payment_methods").appendPath("card_issuers").appendQueryParameter("public_key", PUBLIC_KEY).appendQueryParameter("amount", String.valueOf(amount)).appendQueryParameter("payment_method_id",selectedPaymentMethodId);
            String url = builder.build().toString();
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);


            Log.i(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {

                    JSONArray cardIssuers = new JSONArray(jsonStr);

                    for (int i = 0; i < cardIssuers.length(); i++) {

                        CardIssuer cardIssuer = cardIssuerParser.parseFromJSONObject(cardIssuers.getJSONObject(i));

                        cardIssuerList.add(cardIssuer);

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

            mRecyclerView.setAdapter(new CardIssuerAdapter(mActivity, cardIssuerList, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mContext, SelectInstallmentActivity.class);
                    CardIssuer selectedCardIssuer = (CardIssuer) view.getTag();
                    intent.putExtra("amount",amount);
                    intent.putExtra("paymentMethodId",selectedPaymentMethodId);
                    intent.putExtra("selectedCardIssuerId",selectedCardIssuer.getId());
                    intent.putExtra("selectedCardIssuerName",selectedCardIssuer.getName());
                    startActivity(intent);
                    finish();
                }
            }));
        }

    }

}


