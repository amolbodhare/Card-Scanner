package com.example.ocrappthird.commen;

import com.adoisstudio.helper.Api;

import java.util.HashMap;
import java.util.Map;

public class C
{
    public static Api.OnHeaderRequestListener getHeaders() {

        return new Api.OnHeaderRequestListener() {
            @Override
            public Map<String, String> getHeaders() {

                Map<String, String> headers = new HashMap<>();

                headers.put("Authorization","Basic YWRtaW46MTIzNEBhZG1pbg==");
                headers.put("x-api-key","123456");
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

    }
}
