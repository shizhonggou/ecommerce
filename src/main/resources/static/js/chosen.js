
$(document).click(function(e){
    var divTop = $('#searchtd');   // 设置目标区域
    if(!divTop.is(e.target) && divTop.has(e.target).length === 0){
        searchDivOff();
  
    }
  })
//隐藏提示框
function searchDivOff() {
    $('.searchDiv').css('display', 'none');
}
var dataArr = [];
var noresult='<li style="color:#8f8f8f;pointer-events: none;" >未搜索到相关内容</li>';

//调用ajax返回名字模糊查询集合
function getCameraNames(){
    //替换输入内容中所有的空字符
    var keyVal = $("#cameName").val().replace(/(^\s*)|(\s*$)/g, "");
    var parameterName=document.getElementById("parameterName").value;
    var token=document.getElementById("token").value;
   
    //如果搜索框输入为空
    if(keyVal == null || keyVal == ""){
    	searchDivOff();
    }else if(keyVal=="公司" || keyVal=="有限公司" || keyVal=="集团"){
        alert("请输入有效的关键词");
        return false;
    }else{
       //显示搜索提示框
    	$('.searchDiv').css('display','block');
    	//清空搜索提示框中的内容
    	$('.searchDiv').html("");
        js_search(keyVal);
        console.log($('.searchDiv').children().length);
        if(dataArr.length < 2000 && $('.searchDiv').children().length==0){//全部搜索未搜索到内容
            $('.searchDiv').append(noresult);
            //未搜索到内容时讲原先的选中信息清空
            $('#companyid').val($(this).attr(""));
        }else if(dataArr.length >= 2000 && $('.searchDiv').children().length==0){
            var url = '/invoiceManagement/getCompay?'+parameterName+'='+token;
            $.ajax({
                type : "post",
                async : false, //同步执行
                url : url,
                data :{
                    "name":keyVal
                },
                
                dataType : "json", //返回数据形式为json
                success : function(result) {
                    //判断返回的list是否为空，若不为空
                    if (result!=null && result!="") {
                        for(var i in result){
                            console.log(result[i]);
                            $('.searchDiv').append('<li id="'+i+'">'+result[i]+'</li>');
                        }
                        
                    }else{
                        $('.searchDiv').append(noresult);
                         //未搜索到内容时讲原先的选中信息清空
                        $('#companyid').val($(this).attr(""));
                    }
                },
                // error : function(errorMsg) {
                // 	alert("不好意思,设备搜索提示请求失败啦!");
                // }
            })
        }
    	
        
    }
}

//点击提示框自动填充到搜索框
$(document).on("click", ".searchDiv>li", function () {
    $('#cameName').val($(this).text());
    $('#companyid').val($(this).attr("id"));
    searchDivOff();
})

function onload() { //初始化dataArr的数据
    var dataList = document.getElementById('dataList');
    var childs = dataList.children; //在IE下注释也算节点,不能用于
    for (var i = 0; i < childs.length; i++) {
        var tmp={};
        tmp['id']=childs[i].id;
        tmp['name']=childs[i].innerText;
        dataArr.push(tmp);
    }
 //  console.log(dataArr);
}
function js_search(str){
    dataArr.forEach(function (item) {
        if (item['name'].indexOf(str) != -1) {
            $('.searchDiv').append('<li id="'+item['id']+'">'+item['name']+'</li>');
        }
    })

}

