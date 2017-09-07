package com.mlexam.oscarodon.mlexam.parsers;

import com.mlexam.oscarodon.mlexam.model.Installment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oscarodon on 4/9/17.
 */

public class InstallmentParser {

    public Installment parseFromJSONObject(JSONObject object) throws JSONException {

        Installment installment = new Installment();

        CardIssuerParser cardIssuerParser = new CardIssuerParser();

        installment.setInstallments(object.getInt("installments"));
        installment.setRecommendedMessage(object.getString("recommended_message"));

        return installment;

    }

}
