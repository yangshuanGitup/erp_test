(function($) {
	// 备份jquery的ajax方法
	var _ajax = $.ajax;
	// 重写jquery的ajax方法
	$.ajax = function(opt) {
		// 备份opt中error和success方法
		var fn = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		}
		// 指可能由于错误的参数造成的请求异常,这种一般都在调用者的参数中定义了的
		if (opt.errorHandler) {
			fn.errorHandler = opt.errorHandler;
		}
		// 指网络通信错误
		if (opt.error) {
			fn.error = opt.error;
		}
		if (opt.success) {
			fn.success = opt.success;
		}
		// 扩展增强处理
		var _opt = $.extend(opt, {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 当请求出现错误的时候的处理(网络故障并非后台异常,一般只要不是网络通信错误，不会走这个分支)
				// 错误方法增强处理
				if (fn.error) {
					fn.error(XMLHttpRequest, textStatus, errorThrown);
				} else {
					ajaxRequestException(data);
				}
			},
			success : function(data, textStatus) {
				// 如果别人调用的时候是返回的string，则要将它转换成json格式下面才能判断
				if (typeof data === "string") {
					data = JSON.parse(data);
				}
				// 成功回调方法增强处理
				if (data.errorCode == 1002) {
					window.location = baseUrl + '/common/login';
				} else {
					if (opt.jqgridFlag == false) {
						if (data == null || data == undefined
								|| data.status != 1) {
							var errorHandler = fn.errorHandler;
							if (errorHandler != undefined
									&& errorHandler != null
									&& $.isFunction(errorHandler)) {
								errorHandler(data);
							} else {
								bootboxError(data.errorMsg);
							}
						} else {
							var successHandler = fn.success;
							if (successHandler != undefined
									&& successHandler != null
									&& $.isFunction(successHandler)) {
								successHandler(data, textStatus);
							} else {
								bootboxSuccess("操作成功");
							}
						}
					} else {
						var successHandler = fn.success;
						if (successHandler != undefined
								&& successHandler != null
								&& $.isFunction(successHandler)) {
							fn.success(data, textStatus);
						}
					}
				}
			}
		});
		_ajax(_opt);
	};
})(jQuery);
function strToJson(res) {
	return JSON.parse(res);
}
function jsonToStr(object) {
	return JSON.stringify(object);
}
function ajax(url, data, success, errorHandler) {
	jQuery.ajax({
		url : url,
		data : data,
		timeout : 30000,
		method : 'post',
		dataType : 'json',
		success : success,
		jqgridFlag : false,
		errorHandler : errorHandler
	});
}
// 任何的ajax请求都要做判断ajax请求是否出现异常
function ajaxRequestException(data) {
	bootboxError("页面请求出错");
}
function bootboxError(message, callback) {
	bootbox.dialog({
		message : message,
		closeButton : false,
		buttons : {
			"danger" : {
				"label" : "<i class='ace-icon fa fa-check'></i> 关闭",
				"className" : "btn-sm btn-danger",
				"callback" : function() {
					if (callback != undefined && callback != null
							&& $.isFunction(callback)) {
						callback();
						;
					}
				}
			}
		}
	});
}
function bootboxSuccess(message, callback) {
	bootbox.dialog({
		message : message,
		closeButton : false,
		buttons : {
			"success" : {
				"label" : "<i class='ace-icon fa fa-check'></i> 关闭",
				"className" : "btn-sm btn-success",
				"callback" : function() {
					if (callback != undefined && callback != null
							&& $.isFunction(callback)) {
						callback();
					}
				}
			}
		}
	});
}
function getFormJson(frm) {
	var o = {};
	var a = $(frm).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
}

String.prototype.startWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substr(0, s.length) == s)
		return true;
	else
		return false;
	return true;
}