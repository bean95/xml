package com.november.first.common.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

public static String getValue(String key){

Properties prop = new Properties();
try {
//InputStream in = PropertiesUtil.class.getResourceAsStream("/db.properties");
prop.load(PropertiesUtil.class.getResourceAsStream("/db.properties"));
} catch (IOException e) {
e.printStackTrace();
}
return prop.getProperty(key);
}

}
