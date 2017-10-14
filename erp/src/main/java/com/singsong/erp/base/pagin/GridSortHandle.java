package com.singsong.erp.base.pagin;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.singsong.erp.base.exception.ErpException;
import com.singsong.erp.base.util.CommonUtils;
import com.singsong.erp.framework.init.InitContext;

public class GridSortHandle {

	public static String handle(String className,String sorts) throws Exception{
		StringBuffer sql = new StringBuffer("");
		if (StringUtils.isNotEmpty(sorts)) {
			try {
				List<GridSort> list = CommonUtils.fromJson(sorts, new TypeToken<List<GridSort>>() {});
				Map<String, String> propertyColMap = InitContext.getMappingMap().get(className);
				for (GridSort obj : list) {
					String fieldName = obj.getField();
					String column = propertyColMap.get(fieldName);
					String order = obj.getOrder();
					if (StringUtils.isNotEmpty(column) && StringUtils.isNotEmpty(order)) {
						// 校验过滤字段的值
						boolean columnFlag = CommonUtils.validSqlExtract(column);
						boolean orderFlag = CommonUtils.validSqlExtract(order);
						if (columnFlag == false || orderFlag == false) {
							throw new ErpException("排序数据格式不正确,参数=" + obj.toString());
						}
						String tempSQL=null;
						if(sql.length()==0){
							tempSQL = " order by "+column+" "+order;
						}
						else{
							tempSQL = ", "+column+" "+order;
						}
						sql.append(tempSQL);
					} else {
						throw new ErpException("排序数据格式不正确,参数=" + obj.toString());
					}
				}

			} catch (JsonSyntaxException e) {
				String errMs = className + " sorts 转换成json格式出错==>" +sorts ;
				throw new ErpException(errMs, e);
			}
		}
		return sql.toString();
	}
}
