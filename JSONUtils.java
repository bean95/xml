package com.november.first.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

private final static ObjectMapper objectMapper = new ObjectMapper();

private JSONUtils(){

}

public static ObjectMapper getInstance(){
return objectMapper;
}

public static String POJOtoJSON(Object obj) throws JsonProcessingException{
return objectMapper.writeValueAsString(obj);
}

public static <T> T JSONtoPOJO(String jsonStr, Class<T> clazz) throws Exception{
return objectMapper.readValue(jsonStr, clazz);
}



}
