    <!-- 基价设置 start -->
    $("#basepricereview").click(function() {
        save_base_price(true);
    });
    $("#basepricesave").click(function() {
        save_base_price(false);
    });

    //保存、审核基价
    function save_base_price(power){
        var lwprice = $("#basepricelwg").val();
        var gxprice = $("#basepricegx").val();
        var plprice = $("#basepricepl").val();
        var tpwd = $("#basepricepwd").val();
        if( lwprice > 1.0 && gxprice > 1.0 && plprice > 1.0 && tpwd != '' ) {
            $.ajax({
                type: "get",
                url: "/baseprice/saveprice",
                dataType: "json",
                //async:false,
                data: {
                    lwprice:lwprice, gxprice:gxprice, plprice:plprice, tpwd:tpwd, power:power
                },
                success: function (data) {
                    if( data == 0 ){
                        if( power == false ){
                            alert("保存成功！");
                        }else{
                            $('#basepricestatus').html('<p>基价维护(已审核)</p>');
                            alert("审核成功！");
                            location.reload();
                        }
                    }else if( data == -1 ){
                        alert("交易密码错误");
                    }else{
                        alert("操作失败");
                    }
                }
            });
        }else{
            if(lwprice <= 1.0){
                alert("请录入正确的螺纹价格");
            }
            if(gxprice <= 1.0){
                alert("请录入正确的高线价格");
            }
            if(plprice <= 1.0){
                alert("请录入正确的盘螺价格");
            }
            if(tpwd =='' ){
                alert("请填写交易密码!");
            }
        }
    }

    // 翻页
    function jumpbasepricepage( url ) {
      
        var pageNum = document.getElementById("jump_num").value;
        if(pageNum>0){
            queryBasePriceRecords(pageNum, url );
        }
    }

    function queryBasePriceRecords(pageNum,url) {
        location.href =url+pageNum;
       // location.href = "/baseprice/settings?page="+pageNum;
    }

    <!-- 基价设置 end -->

    <!-- 极差设置 start -->
    //切换menu
    function range_menu(kind) {
        window.location.href = "/settings/range?kind="+kind;
    }

    //删除一行
    function delete_this_tr( obj ) {
        $(obj).parent().parent().parent().remove();
    }
    //执行审核
    $("#settingsreview").click(function() {
        save_range_jiajianjia( "review" );
    });
    //执行保存
    $("#save_jiajianjia").click(function() {
        save_range_jiajianjia( "save" );
    });
    //更新今日资源价格
    $("#update_today_resource_price").click(function() {
        update_today_resource_price();
    });
    function save_range_jiajianjia( actiontype ){
        var kind = $('#kind').val();
        var savedate = $('#savedate').val();
        var settingspwd = $('#settingspwd').val();
        var varietyName = [];
        if ( 3 == kind ) {
            $("input[name='varietyName']").each(function(){
                varietyName.push( $(this).val() ) ;
            });
             varietyName.push(  $("select[name='varietyName']").val() );
        }
        var description = [];
        $("input[name='description']").each(function(){
            description.push( $(this).val() ) ;
        });
        $("select[name='description']").each(function(){
            description.push( $(this).val() ) ;
        });
        var jiajianjia = [];
        $("input[name='jiajianjia']").each(function(){
            jiajianjia.push( $(this).val() ) ;
        });
        var varietyNameStr = varietyName.join(",");
        $.ajax({
            type: "get",
            url: "/settings/saverange",
            dataType: "json",
            //async:false,
            data: {
                varietyName:varietyName.join(","), description:description.join(","), jiajianjia:jiajianjia.join(","), kind:kind, savedate:savedate, actiontype:actiontype, settingspwd:settingspwd
            },
            success: function (data) {
                if( data == 0 ){
                    if( actiontype == "save" ) {
                        alert("保存成功！");
                    }else{
                        alert("审核成功！");
                    }
                    location.reload();
                }else if( data == 1 ){
                    alert("有重复项，请检查后修改提交！");
                }else if( data == 2 ){
                    alert("交易密码错误,请重试！");
                }else{
                    alert("操作失败!");
                }
            }
        });
    }

    function update_today_resource_price(){
        $.ajax({
            type: "get",
            url: "/baseprice/updateprice",
            dataType: "json",
            //async:false,
            data: {
            },
            success: function (data) {
                if( data == 1 ){
                    alert("更新成功！");
                }else{
                    alert("操作失败!");
                }
            }
        });
    }
    <!-- 极差设置 end -->