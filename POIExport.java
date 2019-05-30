package com.november.first.common.utils;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.november.first.common.annotation.Excel;

public class POIExportUtils<T>{

public void ResponseInit(HttpServletResponse response, String fileName){
response.reset();
//download not open
response.setHeader("Content-Disposition", "attachment;filename="+ fileName + new Date() + ".xls");
//tell browser it is a excel file
response.setContentType("application/vnd.ms-excel;charset=UTF-8");
response.setHeader("Prama", "no-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
}

public void POIOutPutStream(HttpServletResponse response, HSSFWorkbook wb){

try {
BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
wb.write(out);
out.flush();
out.close();
} catch (FileNotFoundException e) {
e.printStackTrace();
} catch (IOException e) {
e.printStackTrace();
}
}

@SuppressWarnings({ "unchecked", "rawtypes" })
public void export(Class<T> objClass, List<T> dataList, HttpServletResponse response, String fileName) throws Exception{

ResponseInit(response,fileName);

Class excelClass = Class.forName(objClass.toString().substring(6));
Method[] methods = excelClass.getMethods();

Map<Integer, String> mapCol = new TreeMap<>();
Map<Integer, String> mapMethod = new TreeMap<>();

for (Method method : methods) {
Excel excel = method.getAnnotation(Excel.class);
if (excel != null) {
mapCol.put(excel.order(), excel.colName());
mapMethod.put(excel.order(), method.getName());
}
}
HSSFWorkbook wb = new HSSFWorkbook();
POIBuildBody(POIBuildHead(wb,"sheet1",mapCol),excelClass,mapMethod,dataList);

POIOutPutStream(response,wb);
}

public HSSFSheet POIBuildHead(HSSFWorkbook wb, String sheetName, Map<Integer, String> mapCol){
HSSFSheet sheet01 = wb.createSheet(sheetName);
HSSFRow row = sheet01.createRow(0);
HSSFCell cell;
int i = 0;
for (Map.Entry<Integer, String> entry : mapCol.entrySet()) {
cell = row.createCell(i++);
cell.setCellValue(entry.getValue());
}
return sheet01;
}

public void POIBuildBody(HSSFSheet sheet01, Class<T> excelClass, Map<Integer, String> mapMethod, List<T> dataList) throws Exception{

HSSFRow r = null;
HSSFCell c = null;

if(dataList != null && dataList.size() > 0){
for(int i = 0;i<dataList.size();i++){
r= sheet01.createRow(i+1);
//r.setHeightInPoints(25);
int j = 0;
for (Map.Entry<Integer, String> entry : mapMethod.entrySet()) {
c = r.createCell(j++);
Object obj = excelClass.getDeclaredMethod(entry.getValue()).invoke(dataList.get(i));
c.setCellValue(obj==null?"":obj+"");
}
}
}
}

}
