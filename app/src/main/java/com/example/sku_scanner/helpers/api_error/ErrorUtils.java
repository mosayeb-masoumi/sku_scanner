package com.example.sku_scanner.helpers.api_error;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.network.ErrorProvider;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static  APIError parseError(Response<?> response){
        Converter<ResponseBody , APIError> converter=
                ErrorProvider
                        .retrofit.
                        responseBodyConverter(APIError.class,new Annotation[0]);
        APIError error;

        try{
            error=converter.convert(response.errorBody());

        }catch (IOException e){
            return  new APIError();
        }
          return error;
    }
}
