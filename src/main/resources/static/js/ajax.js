function Ajax( url, requestmethod, params ){
	var http_request = send_request();
	this.send = function (){
	    http_request.onreadystatechange = ajax_back;  
        // 确定发送请求的方式和URL以及是否同步执行下段代码   
		url = encodeURI( url );
        url = encodeURI( url ); 
	    params = encodeURI( params );
        params = encodeURI( params ); 
        http_request.open("POST", url, true);
		http_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        http_request.send( params );
		//http_request.setRequestHeader("Connection", "close");
	}

	function ajax_back(){
        if (http_request.readyState == 4) { // 判断对象状态   
            if (http_request.status == 200) {
				var returnStr = http_request.responseText;
			    requestmethod( returnStr );
			}
	    } 
    }
	this.send();
}

function send_request() {//初始化、指定处理函数、发送请求的函数   
    var http_request = false;   
        //开始初始化XMLHttpRequest对象   
    if(window.XMLHttpRequest) { //Mozilla 浏览器   
        http_request = new XMLHttpRequest();   
        if (http_request.overrideMimeType) {//设置MiME类别   
            //http_request.overrideMimeType('text/xml');   
        }   
    }   
    else if (window.ActiveXObject) { // IE浏览器   
        try {   
            http_request = new ActiveXObject("Msxml2.XMLHTTP");   
        } catch (e) {   
            try {   
                http_request = new ActiveXObject("Microsoft.XMLHTTP");   
            } catch (e) {}   
        }   
    }   
    if (!http_request) { // 异常，创建对象<SPAN class=hilite2>实例</SPAN>失败   
        window.alert("不能创建XMLHttpRequest对象<SPAN class=hilite2>实例</SPAN>.");   
        return false;   
    }   
    return http_request; 
}   
