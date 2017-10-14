package com.singsong.erp.base.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class CommonUtils {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	// Gson生成json时候，会忽略掉值为null的key  
    private static final Gson            gson   = new GsonBuilder().serializeNulls().create();
    private static final DozerBeanMapper mapper = new DozerBeanMapper();
    private static final String STRING = "abcdefghijklmnopqrstuvwxyz"; 

    /**
     * json转字符串
     * @param jsonElement
     * @return
     */
    public static String toJson(Object jsonElement) {
        return gson.toJson(jsonElement);
    }
	/**
	 * json转字符串
	 * 带过滤字段的转换
	 * @param args
	 */
	public static String toJsonFilterProperty(Object jsonElement,List<String> propertyList){
        // 过滤掉声明为 protcted 的变量  
		Gson gson = new GsonBuilder().serializeNulls().setExclusionStrategies(new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				// 过滤掉字段
				if (propertyList != null && propertyList.contains(f.getName())) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				// 过滤掉 类名
				return false;
			}
		}).create();
		return gson.toJson(jsonElement);
	}

    /**
     * 一般的字符串转json
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T fromJson(String json,Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    /**
     * 支持泛型的字符串转对象
     * @param json
     * @param typeToken
     * @return
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return gson.fromJson(json, typeToken.getType());
    }

    /**
     * object转map
     * @param map
     * @param classOfT
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> classOfT) {
        return mapper.map(map, classOfT);
    }
    public static String createId() {
        return UUID.randomUUID().toString();
    }
    public static Date getNowDate(){
        return new Date();
    }
    public static boolean checkAjax(HttpServletRequest request){
        if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ 
            return true;
        }
        return false;
    }
    //这个方法主要用于get请求中包含有中文字符的处理
    public static String getGetParam(String str) {  
        String projectCode="utf-8";
        String result=null;
        try {  
             String encode = "ISO-8859-1";
            if (str.equals(new String(str.getBytes(encode), encode))){  
                result = new String(str.getBytes(encode),projectCode);  
            }
            else{
                result=str;
            }
        } catch (Exception exception) {  
        }  
        return result;  
    } 
	public static String getColumnValue(Map<String,Object> dataMap,String column){
		if(dataMap.get(column.toUpperCase())==null){
			return null;
		}
		return dataMap.get(column.toUpperCase()).toString();
	}
	public static String toJsonFormatDate(Object jsonElement) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		return gson.toJson(jsonElement);
	}

	public static String toJsonFormatDate(Object jsonElement, String format) {
		if (StringUtils.isEmpty(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Gson gson = new GsonBuilder().setDateFormat(format).serializeNulls().create();
		return gson.toJson(jsonElement);
	}
	/**
	 * 过滤sql中的一些特殊字符串
	 * 返回true:校验通过;false:校验拒绝
	 */
	public static boolean validSqlExtract(String str){
		if(StringUtils.isEmpty(str)){
			return false;
		}
		String injStr = "'|exec|insert|drop|alter|delete|update|*|%|chr|mid|master|truncate|char|declare|;|or|--|+|,";
		String injStra[] = injStr.split("\\|");
		for (int i = 0; i < injStra.length; i++) {
			String s=injStra[i];
			if(!injStra[i].equals("--")){
				s=s+" ";
			}
			if (str.indexOf(s) >= 0) {
				return false;
			}
		}
		return true;
	}
	
	private static int getRandom(int count) {
		return (int) Math.round(Math.random() * (count));
	}

	public static String getRandomString(int length) {
		StringBuffer sb = new StringBuffer();
		int len = STRING.length();
		for (int i = 0; i < length; i++) {
			sb.append(STRING.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println(validSqlExtract("truncate"));
	}
}
