package com.november.first.common;

import com.november.first.common.utils.PropertiesUtil;

public class Global {


public static void main(String[] args) {
System.out.println(getAdminPath());
System.out.println(PropertiesUtil.getValue(""));
System.out.println(PropertiesUtil.getValue("oracle.jdbc.driver"));
}

public static String getAdminPath(){
return PropertiesUtil.getValue("adminPath");
}

}
