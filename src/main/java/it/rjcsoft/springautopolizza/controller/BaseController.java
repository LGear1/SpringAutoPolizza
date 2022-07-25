package it.rjcsoft.springautopolizza.controller;

import it.rjcsoft.springautopolizza.dto.BaseResponse;
import it.rjcsoft.springautopolizza.dto.EnumStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLWarning;

public class BaseController {

    public static ResponseEntity<BaseResponse> buildBaseResponse(Exception e){
        if(e == null){
            BaseResponse response = new BaseResponse(EnumStatusResponse.OK.getStatus(), EnumStatusResponse.OK.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else if(e instanceof SQLWarning){
            BaseResponse response = new BaseResponse(EnumStatusResponse.CAR_NOT_FOUND.getStatus(), EnumStatusResponse.CAR_NOT_FOUND.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else{
            BaseResponse response = new BaseResponse(EnumStatusResponse.INTERNAL_SERVER_ERROR.getStatus(), EnumStatusResponse.INTERNAL_SERVER_ERROR.getMessage() + " - "+ e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
