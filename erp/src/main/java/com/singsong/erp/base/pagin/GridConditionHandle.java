package com.singsong.erp.base.pagin;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.singsong.erp.base.exception.ErpException;
import com.singsong.erp.base.util.CommonUtils;
import com.singsong.erp.base.util.DateUtil;
import com.singsong.erp.framework.init.InitContext;


public class GridConditionHandle {
	public static String handle(String className,String conditions) throws Exception{
		StringBuffer sql = new StringBuffer("");
		if (StringUtils.isNotEmpty(conditions)) {
			try {
				List<GridCondition> list = CommonUtils.fromJson(conditions, new TypeToken<List<GridCondition>>() {});
				Map<String, String> propertyColMap = InitContext.getMappingMap().get(className);
				for (GridCondition rule : list) {
					String fieldName = rule.getField();
					String column = propertyColMap.get(fieldName);
					String value = rule.getData();
					String operator = rule.getOp();
					if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(value)	&& StringUtils.isNotEmpty(operator)) {
						// 校验过滤字段的值
						boolean flagColumn = CommonUtils.validSqlExtract(column);
						boolean flagValue = CommonUtils.validSqlExtract(value);
						boolean flagOp = CommonUtils.validSqlExtract(operator);
						if (flagColumn == false || flagValue == false || flagOp == false) {
							throw new ErpException("过滤条件数据格式不正确,参数=" + rule.toString());
						}
						String tempSQL = getFilterCondition(className, fieldName, column, value, operator, "and");
						sql.append(tempSQL);
					} else {
						throw new ErpException("过滤条件数据格式不正确,参数=" + rule.toString());
					}
				}

			} catch (JsonSyntaxException e) {
				String errMs = className + " filters 转换成json格式出错==>" + conditions;
				throw new ErpException(errMs, e);
			}
		}
		return sql.toString();
	}
	public static String getFilterCondition(String className,String fieldName,String column,String value, String operator,String groupOp) throws Exception {
		Object transformValue = getDataBaseValue(className,fieldName, value);
		if(transformValue==null){
			return "";
		}
		String str = null;
		switch (operator) {
		case "eq":
			str = groupOp +" ( " + column + " ="+transformValue+") ";
			break;
		case "ne":
			str = groupOp +" ( " + column + " !="+transformValue+") ";
			break;
		case "bw":
			str = groupOp +" ( " + column + " like '"+transformValue.toString().replace("'", "")+"%') ";
			break;
		case "bn":
			str = groupOp +" ( " + column + " not like '"+transformValue.toString().replace("'", "")+"%') ";
			break;
		case "ew":
			str = groupOp +" ( " + column + " like '%"+transformValue.toString().replace("'", "")+"') ";
			break;
		case "en":
			str = groupOp +" ( " + column + " not like '%"+transformValue.toString().replace("'", "")+"') ";
			break;
		case "cn":
			str = groupOp +" ( " + column + " like '%"+transformValue.toString().replace("'", "")+"%') ";
			break;
		case "nc":
			str = groupOp +" ( " + column + " not like '%"+transformValue.toString().replace("'", "")+"%') ";
			break;
		case "nu":
			str="";//这种包含暂时不支持
			break;
		case "nn":
			str="";//这种包含暂时不支持
			break;
		case "in":
			str = groupOp +" ( " + column + " in ("+transformValue+") ) ";
			break;
		case "ni":
			str = groupOp +" ( " + column + " not in ("+transformValue+") ) ";
			break;
			
		case "lt":
			str = groupOp +" ( " + column + "  < "+transformValue+" ) ";
			break;
		case "le":
			str = groupOp +" ( " + column + "  <= "+transformValue+" ) ";
			break;
		case "gt":
			str = groupOp +" ( " + column + "  > "+transformValue+" ) ";
			break;
		case "ge":
			str = groupOp +" ( " + column + "  >= "+transformValue+" ) ";
			break;
		default:
			String errMs="不支持这种操作符==>"+operator+",提示信息:"+className+",column="+column;
			throw new ErpException(errMs);
		}
		return str;
	}
	private static Object getDataBaseValue(String className, String filedName, String value) throws Exception {
		Object object = null;
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		Class<?> c = Class.forName(className);
		Field f[] = c.getDeclaredFields();
		String fieldType = null;
		for (int i = 0; i < f.length; i++) {
			String name = f[i].getName();
			if (StringUtils.equals(filedName, name)) {
				fieldType = f[i].getType().getCanonicalName();
				break;
			}
		}
		String errMs = null;
		Exception ee=null;
		//传到后端的字段在类中找不到
		if(fieldType==null){
			throw new ErpException("传入的过滤字段有错,字段名="+filedName);
		}
		switch (fieldType) {
		case "java.lang.Boolean":
			if (StringUtils.equals(value, "true")) {
				object = 1;
			} else {
				object = 0;
			}
			break;
		case "boolean":
			if (StringUtils.equals(value, "true")) {
				object = 1;
			} else {
				object = 0;
			}
			break;
		case "java.lang.String":
			object = "'" + value + "'";
			break;
		case "java.lang.Character":
			object = "'" + value + "'";
			break;
		case "char":
			object = "'" + value + "'";
			break;
		case "java.lang.Byte":
			try {
				object = Byte.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Byte";
				ee=e;
			}
			break;
		case "byte":
			try {
				object = Byte.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>byte";
				ee = e;
			}
			break;
		case "java.lang.Short":
			try {
				object = Short.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Short";
				ee = e;
			}
			break;
		case "short":
			try {
				object = Short.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>short";
				ee = e;
			}
			break;
		case "java.lang.Integer":
			try {
				object = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Integer";
				ee = e;
			}
			break;
		case "int":
			try {
				object = Integer.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>int";
				ee = e;
			}
			break;
		case "java.lang.Long":
			try {
				object = Long.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Long";
				ee = e;
			}
			break;
		case "long":
			try {
				object = Long.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>long";
				ee = e;
			}
			break;
		case "java.lang.Float":
			try {
				object = Float.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Float";
				ee = e;
			}
			break;
		case "float":
			try {
				object = Float.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>float";
				ee = e;
			}
			break;
		case "java.lang.Double":
			try {
				object = Double.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Double";
				ee = e;
			}
			break;
		case "double":
			try {
				object = Double.valueOf(value);
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>double";
				ee = e;
			}
			break;
		case "java.util.Date":
			try {
				//先校验是不是日期格式
				DateTime.parse(value, DateUtil.PATTERN_DAY_DEFAULT).toDate();
				object="CONVERT(datetime,"+value+",120)";
			} catch (NumberFormatException e) {
				errMs = value + "数据类型转换出错 ==>Double";
				ee = e;
			}
			break;			
		default:
			errMs = className + "类属性的类型不符合==>" + fieldType;
			break;
		}
		if (StringUtils.isNotEmpty(errMs)) {
			throw new ErpException(errMs,ee);
		}
		return object;
	}
}
