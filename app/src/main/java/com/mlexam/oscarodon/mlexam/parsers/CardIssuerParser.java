package com.mlexam.oscarodon.mlexam.parsers;

import com.mlexam.oscarodon.mlexam.model.CardIssuer;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by oscarodon on 3/9/17.
 */

public class CardIssuerParser {

    public CardIssuer parseFromJSONObject(JSONObject object) throws JSONException {

        CardIssuer paymentMethod = new CardIssuer();

        paymentMethod.setId(object.getString("id"));
        paymentMethod.setName(object.getString("name"));
        paymentMethod.setThumbnail(object.getString("thumbnail"));

        return paymentMethod;

    }
}
