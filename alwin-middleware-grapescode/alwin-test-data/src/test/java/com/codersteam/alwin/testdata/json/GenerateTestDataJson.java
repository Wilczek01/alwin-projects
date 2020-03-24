package com.codersteam.alwin.testdata.json;

import com.codersteam.alwin.testdata.MessageTemplateTestData;
import com.google.gson.Gson;

public class GenerateTestDataJson {

    public static void main(String[] args) {
        final Gson gson = new Gson();
        final String json = gson.toJson(MessageTemplateTestData.all());
        System.out.println(json);
    }
}
