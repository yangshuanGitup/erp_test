package com.singsong.erp.framework.init;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSessionFactory;

import com.singsong.erp.framework.util.SpringContextHolder;

public class InitContext {

	private static Map<String,Map<String,String>> mappingMap;
	private static String contextPath;
	
	public static Map<String, Map<String, String>> getMappingMap() {
		return mappingMap;
	}
	
	public static String getContextPath() {
		return contextPath;
	}
	public static void init(){
		contextPath=SpringContextHolder.getApplicationContext().getApplicationName();
		initResultMap();
	}	
	private static void initResultMap(){
		mappingMap=new HashMap<String,Map<String,String>>();
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
		Collection<ResultMap> maps=sqlSessionFactory.getConfiguration().getResultMaps();
		Iterator<ResultMap> it=maps.iterator();
		while(it.hasNext()){
			 Map<String,String> propertyColMap=new HashMap<String,String>();
			 Object obj=it.next();
			 //这里需要做判断,因为会出现org.apache.ibatis.session.Configuration$StrictMap$Ambiguity这种对象,目前原因没找到
			 if(obj instanceof ResultMap){
				 ResultMap resultMap=(ResultMap)obj;
				 List<ResultMapping> list=resultMap.getResultMappings();
				 for(ResultMapping mapping:list){
					 String column=mapping.getColumn();
					 String property=mapping.getProperty();
					 propertyColMap.put(property, column);
				 }
				 Class<?> classzz=resultMap.getType();
				 mappingMap.put(classzz.getCanonicalName(), propertyColMap);
			 }
		}
	}
}
