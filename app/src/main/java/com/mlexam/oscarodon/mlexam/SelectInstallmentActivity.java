package com.mlexam.oscarodon.mlexam;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mercadopago.decorations.DividerItemDecoration;
import com.mercadopago.util.JsonUtil;
import com.mlexam.oscarodon.mlexam.adapters.CardIssuerAdapter;
import com.mlexam.oscarodon.mlexam.adapters.InstallmentAdapter;
import com.mlexam.oscarodon.mlexam.handler.HttpHandler;
import com.mlexam.oscarodon.mlexam.model.CardIssuer;
import com.mlexam.oscarodon.mlexam.model.Installment;
import com.mlexam.oscarodon.mlexam.model.PaymentMethod;
import com.mlexam.oscarodon.mlexam.parsers.CardIssuerParser;
import com.mlexam.oscarodon.mlexam.parsers.InstallmentParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mlexam.oscarodon.mlexam.BuildConfig.BASE_URL;
import static com.mlexam.oscarodon.mlexam.BuildConfig.PUBLIC_KEY;

public class SelectInstallmentActivity extends AppCompatActivity {

    private List<Installment> installmentList;

    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private Integer amount;
    private String selectedPaymentMethodId;
    private String selectedIssuerId;
    private String getSelectedIssuerName;
    private String selectedInstallment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        installmentList = new ArrayList<>();

        Intent mIntent = getIntent();
        amount = mIntent.getIntExtra("amount", 0);
        selectedPaymentMethodId = mIntent.getStringExtra("paymentMethodId");
        selectedIssuerId = mIntent.getStringExtra("selectedCardIssuerId");
        getSelectedIssuerName = mIntent.getStringExtra("selectedCardIssuerName");

        mRecyclerView = (RecyclerView) findViewById(R.id.installments_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new GetInstallmentsRequest(this,amount,selectedPaymentMethodId,selectedIssuerId).execute();

    }

    protected void setContentView(){

        setContentView(R.layout.activity_select_installments);
    }

    public class GetInstallmentsRequest extends AsyncTask<Void, Void, Void> {

        //falta modularizar la llamada, se está escribiendo la url a mano
        private InstallmentParser installmentParser = new InstallmentParser();
        private String TAG = SelectInstallmentActivity.GetInstallmentsRequest.class.getSimpleName();

        private Integer amount;
        private String selectedPaymentMethodId;
        private String selectedIssuerId;
        private Context mContext;


        public GetInstallmentsRequest(Context context,Integer amount,String selectedPaymentMethodId, String selectedIssuerId) {
            this.selectedPaymentMethodId = selectedPaymentMethodId;
            this.amount = amount;
            this.selectedIssuerId = selectedIssuerId;
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority(BASE_URL).appendPath("v1").appendPath("payment_methods").appendPath("installments").appendQueryParameter("public_key", PUBLIC_KEY).appendQueryParameter("amount", String.valueOf(amount)).appendQueryParameter("payment_method_id",selectedPaymentMethodId).appendQueryParameter("issuer.id",selectedIssuerId);
            String url = builder.build().toString();
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);


            Log.i(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {

                    JSONArray paymentMethods = new JSONArray(jsonStr);
                    JSONObject selectedPaymentMethod = paymentMethods.getJSONObject(0);
                    JSONArray installments = selectedPaymentMethod.getJSONArray("payer_costs");

                    for (int i = 0; i < installments.length(); i++) {

                        Installment installment = installmentParser.parseFromJSONObject(installments.getJSONObject(i));

                        installmentList.add(installment);

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

            mRecyclerView.setAdapter(new InstallmentAdapter(mActivity, installmentList, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Installment installment = (Installment) view.getTag();
                    selectedInstallment = installment.getRecommendedMessage();
                    displayAlertDialog(amount.toString(),selectedPaymentMethodId,getSelectedIssuerName,selectedInstallment);

                }
            }));
        }

        private void displayAlertDialog(String amount,String paymentMethod,String selectedIssuerId, String installment) {

            String title = "Ustéd va a realizar un pago de: ";
            String message = "$"+amount+System.getProperty("line.separator")+"con "+paymentMethod+" "+selectedIssuerId+System.getProperty("line.separator")+"En "+installment;
            AlertDialog.Builder ad = new AlertDialog.Builder(mContext);
            ad.setTitle(title);
            ad.setMessage(message);

            ad.setPositiveButton(
                    R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            //Dissmiss AlertDialog
                            goToParentActivity();
                        }
                    }
            );

            ad.show();
        }

        private void goToParentActivity() {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("amount", amount);
            returnIntent.putExtra("paymentMethod", selectedPaymentMethodId);
            returnIntent.putExtra("installment", selectedInstallment);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }
}
