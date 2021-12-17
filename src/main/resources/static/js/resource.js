    <!-- 翻页 start -->
    function queryDeviceRecords(pageNum,vid) {

        var url = "/index?page="+pageNum+"&vid="+vid ;

		window.location.href= url;

    }
    <!-- 翻页 end -->

    $(document).ready(function(){
        $('.son_ul').hide(); //初始ul隐藏
        $('.select_box span').hover(function(){ //鼠标移动函数
            $(this).parent().find('ul.son_ul').slideDown();  //找到ul.son_ul显示
            $(this).parent().find('li').hover(function(){$(this).addClass('hover')},function(){$(this).removeClass('hover')}); //li的hover效果
            $(this).parent().hover(function(){},
                 function(){
                     $(this).parent().find("ul.son_ul").slideUp();
                 }
             );
            },function(){
            }
        );
        $('ul.son_ul li').click(function(){
            $(this).parents('li').find('span').html($(this).html());
            $(this).parents('li').find('input').val($(this).html());
            $(this).parents('li').find('ul').slideUp();
        });
    });
    <!-- sreach 下拉 end -->
    <!-- 切换tab start -->
    $(document).ready(function() {
        jQuery.jqtab = function(tabtit,tab_conbox,shijian) {
            $(tab_conbox).find("li").hide();
            $(tabtit).find("li:first").addClass("thistab").show();
            $(tab_conbox).find("li:first").show();

            $(tabtit).find("li").bind(shijian,function(){
              $(this).addClass("thistab").siblings("li").removeClass("thistab");
                var activeindex = $(tabtit).find("li").index(this);
                $(tab_conbox).children().eq(activeindex).show().siblings().hide();
                //return false;
            });
        };
        /*调用方法如下：*/
        $.jqtab("#tabs","#tab_conbox","click"); // 点击切换

        //滑动切换
        $.jqtab("#tabs1","#tab_conbox1","mouseenter");
        $.jqtab("#tabs2","#tab_conbox2","mouseenter");
        $.jqtab("#tabs3","#tab_conbox3","mouseenter");
        add_purchase_car("");
    });
    <!-- 切换tab end -->


    <!-- 搜索 start -->
    function submitForm(){
        $("#searchdetail").submit();
    }
    <!-- 搜索 end -->


    <!-- 加入购物车 start -->

    //加入购物车
    function add_purchase_car(id,is_pljr){
        if( id > 0 ) {
            document.getElementById('loading').style.display="";
        }
        $.ajax({
            type: "get",
            url: "/purchase_car/add_purchase_car",
            dataType: "json",
            //async:false,
            data: {
                resids:id,
                is_pljr:is_pljr
            },
            success: function (retdata) {
                //console.log(data);
                if(retdata != undefined){
                    if(retdata.gwclist != undefined)
                        $('#car_resource_num').html( retdata.gwclist.length );
                    if(retdata.success == 1){
                        alert("成功加入采购车！");
                        // var data = retdata.gwclist;
                        // if ( data != null && data != "" ) {
                        //     var purchase_car_html = "";
                        //     var readflag = "";
                        //     $('#car_resource_num').html( data.length );
                        //     var gwc_ids = "";
                        //     for( var i=0;i<data.length;i++ ) {
                        //         var z_weight = "";
                        //         if ( data[i].v_id != 1 ){
                        //             readflag = "readonly";
                        //         }else{
                        //             readflag = "";
                        //             if (typeof(data[i].d_weight) != "undefined"){
                        //                 z_weight = parseInt((parseFloat(data[i].num)/parseFloat(data[i].d_weight)));
                        //             }
                        //         }
                        //         if(gwc_ids == ""){
                        //             gwc_ids = data[i].id;
                        //         }else{
                        //             gwc_ids = gwc_ids + "," + data[i].id;
                        //         }
                                
                        //         purchase_car_html += '<tr><td><input type="hidden" id="'+data[i].id+'" value="'+data[i].id+'" name="pre_order_resource[]">'+data[i].variety_name+'</td><td>'+data[i].material+'</td><td>'+data[i].specification+'</td><td>'+data[i].warehouse+'</td><td>'+data[i].factory+'</td><td><input type="text" value="'+data[i].weight+'" id="weight'+data[i].id+'" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')" onBlur="check_num(this,'+data[i].num+','+data[i].id+','+data[i].d_weight+')"><span> /'+data[i].num+'</span></td><td><input type="text" value="'+data[i].weight2+'" id="weight2'+data[i].id+'" '+readflag+' onkeyup="this.value=this.value.replace(/[^0-9.]/g,\'\')" onBlur="check_piece(this,'+z_weight+','+data[i].id+','+data[i].d_weight+')"><span> /'+z_weight+'</span></td><td><span class="red">¥ '+data[i].price+'</span></td><td class="caozuo"><a href="javascript:void(0);" onclick="del_purchase_car( this, '+data[i].id+')">删除</a></td></tr>';
                        //     }
                        //     $('#purchase_car').html( purchase_car_html ) ;
                        //     $('#gwc_ids').val( gwc_ids );
                        // }
                        // if( id > 0 ) {
                        //     show_purchase_car();
                        // }
                    }else{
                        if(retdata.msg != undefined && retdata.msg != "")
                            alert(retdata.msg);
                    }
                }
                document.getElementById('loading').style.display="none";
            }
        });
//        if( id > 0 ) {
//            show_purchase_car();
//        }
    }

    //从购物车删除
    function del_purchase_car( obj, id ){
        //通过this找到父级元素节点
        var tr = obj.parentNode.parentNode;
        //找到表格
        var tbody = tr.parentNode;
        //删除行
        tbody.removeChild(tr);
        //只剩行首时删除表格
        if (tbody.rows.length == 1) {
            //tbody.parentNode.removeChild(tbody);
        }
        var car_resource_num = $('#car_resource_num').html() ;
        car_resource_num = car_resource_num-1;
        $('#car_resource_num').html(car_resource_num) ;
        //后台session 异步执行
        $.ajax({
            type: "get",
            url: "/purchase_car/del_purchase_car",
            dataType: "json",
            //async:false,
            data: {
                gwc_id:id
            },
            success: function (data) {
                console.log(data);
            }
        });
    }

    //清空购物车
    function clear_purchase_car(){
        $('#purchase_car').html( "" ) ;
        var ids = $('#gwc_ids').val();
        $.ajax({
            type: "get",
            url: "/purchase_car/clear_purchase_car",
            dataType: "json",
            data: {
                gwc_id:ids
            },
            success: function (data) {
                if(data.code == 0)
                    alert("清空采购车已完成！");
                hide('SonContent1');
                $('#car_resource_num').html(0) ;
                console.log(data);
            }
        });
    }

    //检查重量
    function check_num ( obj,  num, id, d_weight){
        var write_num = obj.value;
        if ( write_num < 0 ) {
            alert("请输入正确的购买量！");
            obj.value = "";
            obj.focus();
        }else if( write_num > num ){
            alert("购买数量不能大于总量！");
            obj.value = "";
            obj.focus();
        }else{
            if(d_weight != ""){
                var jianshu = parseInt((parseFloat(write_num)/parseFloat(d_weight)));
                $('#weight2'+id).val(jianshu);
            }
        }
    }

    //检查件数
    function check_piece( obj, piece, id, d_weight){
        var write_piece = obj.value;
        if (write_piece < 0) {
            alert("请输入正确的件数！");
            obj.value = "";
            obj.focus();
        }else if ( write_piece > piece ) {
            alert("购买件数不能大于总件数！");
            obj.value = "";
            obj.focus();
        }else{
            if(d_weight != ""){
                $('#weight'+id).val((parseFloat(write_piece)*parseFloat(d_weight)).toFixed(3));
            }
        }
    }

    //更新购物车数量
    function update_purchase_car_num( ids, weight, weight2, istz) {
        $.ajax({
            type: "get",
            url: "/purchase_car/update_purchase_car_num",
            dataType: "json",
            //async:false,
            data: {
                ids:ids,
                weights:weight,
                weights2:weight2
            },
            success: function (data) {
                if (istz == 1) {
                    location.href = "/purchase_car/mycart";
                }else if(istz == 2){
                    hide('SonContent1');
                }
                console.log( data );
            }
        });
    }

    function show_purchase_car(){
        show("SonContent1")
    }

    function show(tag){
        $(location).attr('href', '/purchase_car/mycart');
        // var SonContent=document.getElementById(tag);
        // SonContent.style.display='block';
    }
    function hide(tag){
        var SonContent=document.getElementById(tag);
        SonContent.style.display='none';
    }

    $("#gwc_check_all").on('click',function() {
          $("input[name='pre_order_resource']").prop("checked", this.checked);
    });

    <!-- 加入购物车 end -->

    <!-- 下订单 start -->
        //下订单
    function do_purchase_order(istz){
        document.getElementById('loading').style.display="";
        var gwc_ids = $('#gwc_ids').val();
        var gwc_id_arr = gwc_ids.split(",");
        var weights = "";
        var weights2 = "";
        if ( gwc_ids != "" ) {
            for(var i = 0;i< gwc_id_arr.length; i++){
                if(weights == ""){
                    weights = $('#weight'+gwc_id_arr[i]).val();
                    weights2 = $('#weight2'+gwc_id_arr[i]).val();
                }else{
                    weights = weights + "," + $('#weight'+gwc_id_arr[i]).val();
                    weights2 = weights2 + "," + $('#weight2'+gwc_id_arr[i]).val();
                }
            }
            update_purchase_car_num(gwc_ids,weights,weights2,istz);
        }
        document.getElementById('loading').style.display="none";

    }

    <!-- 下订单 end -->

    // 全选
    function selall(){
        var checkarr = document.getElementsByName("check_num");
        var length = checkarr.length;
        obj = document.getElementById("selallbox");
        if(obj.checked){
            //全选
            for(i=0;i<length;i++){
                checkarr[i].checked=true;
            }
        }else{
            //全不选
            for(i=0;i<length;i++){
                checkarr[i].checked=false;
            }
        }
    }

    //获得选中项
    function getcheckednum() {
        var allSel = "";
        var checkarr = document.getElementsByName("check_num");
        for (i = 0; i < checkarr.length; i++) {
            if (checkarr[i].checked) {
                if (allSel == "")
                    allSel = checkarr[i].value;
                else
                    allSel = allSel + "," + checkarr[i].value;
            }
        }
        return allSel;
    }

    //批量加入购物车
    function save(){
        var resids = getcheckednum();
        add_purchase_car(resids,1);
    }