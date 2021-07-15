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
    function add_purchase_car(id){
        if( id > 0 ) {
            document.getElementById('loading').style.display="";
        }
        $.ajax({
            type: "get",
            url: "/purchase_car/add_purchase_car",
            dataType: "json",
            //async:false,
            data: {
                resids:id
            },
            success: function (data) {
                //console.log(data);
                if ( data != null && data != "" ) {
                    var purchase_car_html = "";
                    var readflag = "";
                    $('#car_resource_num').html( data.length ) ;
                    for( var i=0;i<data.length;i++ ) {
                        if ( data[i].vid == 1 ){
                            readflag = "readonly";
                        }else{
                            readflag = "";
                        }
                        purchase_car_html += '<tr><td class="checkbox"><input type="checkbox" id="'+data[i].id+'" name="pre_order_resource" checked="checked"></td><td>'+data[i].varietyname+'</td><td>'+data[i].material+'</td><td>'+data[i].spec+'</td><td>'+data[i].cangku+'</td><td>'+data[i].originCode+'</td><td><input type="text" value="'+data[i].writePiece+'" id="purchase_piece_'+data[i].id+'" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')" onBlur="check_piece(this,'+data[i].vid+',\''+data[i].spec+'\','+data[i].piece+','+data[i].id+')"><span> /'+data[i].piece+'</span></td><td><input type="text" value="'+data[i].writeNum+'" id="purchase_num_'+data[i].id+'" '+readflag+' onkeyup="this.value=this.value.replace(/[^0-9.]/g,\'\')" onBlur="check_num(this,'+data[i].vid+','+data[i].num+')"><span> /'+data[i].num+'</span></td><td><span class="red">¥ '+data[i].price+'</span></td><td class="caozuo"><a href="javascript:void(0);" onclick="del_purchase_car( this, '+data[i].id+')">删除</a></td></tr>';
                    }
                    $('#purchase_car').html( purchase_car_html ) ;
                }
                document.getElementById('loading').style.display="none";
            }
        });
        if( id > 0 ) {
            show_purchase_car();
        }
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
                resids:id
            },
            success: function (data) {
                console.log(data);
            }
        });
    }

    //清空购物车
    function clear_purchase_car( ){
        $('#purchase_car').html( "" ) ;
        alert("清空购物车已完成！");
        //后台session 异步执行
        $.ajax({
            type: "get",
            url: "/purchase_car/clear_purchase_car",
            dataType: "json",
            success: function (data) {
                console.log(data);
            }
        });
    }

    //检查件数
    function check_piece ( obj, vid, spec, piece, id ){
        var write_piece = obj.value;
        if ( write_piece < 0 || write_piece > piece ) {
            alert("请输入正确的件数");
            obj.value = "";
            obj.focus();
        }else if( write_piece > 0 ){
            if ( vid == 1 ) {
                $.ajax({
                    type: "get",
                    url: "/resourcemanage/getWeight",
                    dataType: "json",
                    //async:false,
                    data: {
                        spec:spec
                    },
                    success: function (data) {
                        var purchase_num = data * write_piece;
                        purchase_num = purchase_num.toFixed(3);
                        $('#purchase_num_'+id).val( purchase_num );
                        update_purchase_car_num( id, write_piece, purchase_num );
                    }
                });
            }else{
                update_purchase_car_num( id, write_piece, 0 );
            }
        }
    }

    //检查重量
    function check_num( obj, id, num ){
        var write_num = obj.value;
        if ( write_num < 0 || write_num > num ) {
            alert("请输入正确的重量");
            obj.value = "";
            obj.focus();
        }else{
            update_purchase_car_num( id, 0, write_num );
        }
    }

    //更新购物车数量
    function update_purchase_car_num( id, piece, num ) {
        $.ajax({
            type: "get",
            url: "/purchase_car/update_purchase_car_num",
            dataType: "json",
            //async:false,
            data: {
                id:id,
                piece:piece,
                num:num
            },
            success: function (data) {
                console.log( data );
            }
        });
    }

    function show_purchase_car(){
        show("SonContent1")
    }

    function show(tag){
        var SonContent=document.getElementById(tag);
        SonContent.style.display='block';
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
    function do_purchase_order(){
        document.getElementById('loading').style.display="";
        //选择下单的资源
        var purchase_resource_ids = "";
        var purchase_resource_piece = "";
        var purchase_resource_num = "";
        var num = 0;
        $('input:checkbox[name=pre_order_resource]:checked').each(function (i) {
            var resource_id = $(this).attr("id");
            if( $('#purchase_piece_'+resource_id).val() > 0 && $('#purchase_num_'+resource_id).val()>0 ) {
                if (0 == num) {
                    purchase_resource_ids = resource_id;
                    purchase_resource_piece = $('#purchase_piece_'+resource_id).val();
                    purchase_resource_num = $('#purchase_num_'+resource_id).val();
                } else {
                    purchase_resource_ids += ("," + resource_id);
                    purchase_resource_piece += ("," + $('#purchase_piece_'+resource_id).val() );
                    purchase_resource_num += ("," + $('#purchase_num_'+resource_id).val() );
                }
                num++;
            }else{
                $('#purchase_piece_'+resource_id).focus();
                alert("请填写正确的件数和数量");
                return false;
            }

        });
        if ( purchase_resource_ids != "" ) {
            $.ajax({
                type: "get",
                url: "/orderinformation/generate_order",
                dataType: "json",
                //async:false,
                data: {
                    purchase_resource_ids:purchase_resource_ids,
                    purchase_resource_piece:purchase_resource_piece,
                    purchase_resource_num:purchase_resource_num
                },
                success: function (data) {
                    console.log(data);
                    if ( data == "1" ) {
                        alert("下单成功");
                        location.href = "orderinformation/index?status=1";
                    }else if( data != null && data != "" ){
                        alert(data);
                    }
                },
                error: function(msg){
                    alert(msg.responseText);
                }
            });
        }else{
            alert("请选择资源下单！");
        }
        document.getElementById('loading').style.display="none";

    }

    <!-- 下订单 end -->