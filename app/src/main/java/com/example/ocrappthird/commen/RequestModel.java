package com.example.ocrappthird.commen;

import com.adoisstudio.helper.Json;

import org.json.JSONObject;

public class RequestModel extends Json
{
    private int version = 1;

public RequestModel(String api)
{
    addString(P.api,api);
    addInt(P.version,version);
    addString(P.device_id,P.device_id);
    addJSON(P.data,new JSONObject());

}
public static RequestModel  newRequestModel(String api)
{
    return new RequestModel(api);
}
}
