package com.proyect.conversordemonedas.service;

import com.proyect.conversordemonedas.modelo.TasaDeConversion;
import com.proyect.conversordemonedas.modelo.TasaDeConversionApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConvierteDatos {

    public TasaDeConversionApi convierteDatos(String json){

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();

        TasaDeConversionApi tasaDeConversionApi = gson.fromJson(json, TasaDeConversionApi.class);
        //System.out.println("Tasa de Conversion API: " + tasaDeConversionApi);

        TasaDeConversion tasaDeConversion = new TasaDeConversion(tasaDeConversionApi);
        //System.out.println("Tasa de Conversion objeto: " + tasaDeConversion);

        return tasaDeConversionApi;

    }

}
