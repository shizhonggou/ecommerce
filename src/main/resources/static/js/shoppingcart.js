//var is_dcfh = false;//是否多次发货
function qk_gwc(){
    var ids = "";
    $('input[name^=gwc_id]').each(function(index,element){
        if(ids == ""){
            ids = $(this).val();
        }else{
            ids = ids + "," + $(this).val();
        }
    });
    //console.log(ids);
    $.ajax({
        type: "get",
        url: "/purchase_car/clean_mycart",
        dataType: "json",
        //async:false,
        data: {
            gwc_id:ids
        },
        success: function (data) {
            if(data.code == 0){
                $(location).attr('href', '/purchase_car/mycart');
            }
            //console.log(data);
        }
    });
}

function scdd(){
    var gwc_ids = "";//购物车id
    var weights = "";//购买量
    var weights2 = "";//购买重量
    var bargains = "";//议价单价
    var dcfh_vaules = "";//多次发货
    //var is_split_lading = 0;//是否拆分提单
    var resources_ids = "";//资源id
    var vids = "";//资源id
    var status = "";
    var is_weight = true;
    var is_weight2 = true;
    var is_bargain = true;
    var is_th_num = true;
    var is_dcfh_vaule = true;
    var is_bargain2 = true;
    var ticket = $("input[name='ticket']:checked").val();
    var dz_code = $("#dz_code").val();
    
    $('#loading').css("display","block");
    if(ticket == 1 && dz_code == ""){
        $('#loading').css("display","none");
        alert("请选择到站！");
        return false;
    }
    for(var i = 0 ; i < $('input[name^=gwc_id]').length ; i++){
        if( $('input[name^=gwc_id]')[i].checked == true ){
            console.log("weight2="+($('input[name^=gwc_weight]')[i].value));
            if(gwc_ids == ""){
                gwc_ids = $('input[name^=gwc_id]')[i].value;
            }else{
                gwc_ids = gwc_ids + "," + $('input[name^=gwc_id]')[i].value;
            }

            if(resources_ids == ""){
                resources_ids = $('input[name^=resources_id]')[i].value;
            }else{
                resources_ids = resources_ids + "," + $('input[name^=resources_id]')[i].value;
            }

            if(vids == ""){
                vids = $('input[name^=vid]')[i].value;
            }else{
                vids = vids + "," + $('input[name^=vid]')[i].value;
            }

            if($('input[name^=gwc_weight]')[i].value == "" || parseFloat($('input[name^=gwc_weight]')[i].value) == 0){
                is_weight = false;
            }
            
            var zsl = $('input[name^=gwc_weight]')[i].value;
            if(weights == ""){
                weights =$('input[name^=gwc_weight]')[i].value;
            }else{
                weights = weights + "," + $('input[name^=gwc_weight]')[i].value;
            }

            if($('input[name^=vid]')[i].value == "1"){
                zsl = $('input[name^=gwc_jianshu]')[i].value;
                if($('input[name^=gwc_jianshu]')[i].value == "" || parseFloat($('input[name^=gwc_jianshu]')[i].value) == 0){
                    is_weight2 = false;
                }
            }
            if(weights2 == ""){
                weights2 = $('input[name^=gwc_jianshu]')[i].value;
            }else{
                weights2 = weights2 + "," + $('input[name^=gwc_jianshu]')[i].value;
            }

            if($('input[name^=bargain]')[i].value == "" || parseFloat($('input[name^=bargain]')[i].value) == 0){
                is_bargain = false;
            }
            if(bargains == ""){
                bargains = $('input[name^=bargain]')[i].value;
            }else{
                bargains = bargains + "," + $('input[name^=bargain]')[i].value;
            }
            var yhfd = parseFloat($('input[name^=price]')[i].value) - parseFloat($('input[name^=bargain]')[i].value);
            if(yhfd > parseFloat(preferential_amount)){
                //$('input[name^=bargain]')[i].value = "";
                //is_bargain2 = false;
                if(status == ""){
                    status = 1;
                }else{
                    status = status + "," + 1;
                }
            }else{
                if(status == ""){
                    status = 2;
                }else{
                    status = status + "," + 2;
                }
            }
            

            var dcfh_v = $('input[name^=dcfh_vaule]')[i].value;
            //if(is_dcfh){
                var first_str = dcfh_v.substr(0, 1);
                var strs= new Array();
                var th_num = 0;
                strs = dcfh_v.split(",");
                for (var k=0;k<strs.length;k++ ){
                    if(isNaN(strs[k])){
                        is_dcfh_vaule = false;
                        break;
                    }else{
                        th_num =parseFloat(th_num) + parseFloat(strs[k]);
                    }
                }

                if(parseFloat(th_num) > parseFloat(zsl)){
                    is_th_num = false;
                    $('input[name^=dcfh_vaule]')[i].value = "";
                }
                if(first_str == ","){
                    is_dcfh_vaule = false;
                }
                // if(dcfh_vaules == ""){
                //     dcfh_vaules = dcfh_v;
                // }else{
                //     dcfh_vaules = dcfh_vaules + "|" + dcfh_v;
                // }
                if(i == 0){
                    dcfh_vaules = dcfh_v;
                }else{
                    dcfh_vaules = dcfh_vaules + "|" + dcfh_v;
                }
            //}
        }
    }
    
    if(gwc_ids == ""){
        $('#loading').css("display","none");
        alert("请选择下单资源！");
        return false;
    }
    if(!is_weight){
        $('#loading').css("display","none");
        alert("请填写购买量！");
        return false;
    }
    if(!is_weight2){
        $('#loading').css("display","none");
        alert("请填写重量！");
        return false;
    }
    if(!is_bargain){
        $('#loading').css("display","none");
        alert("请输入议价单价！");
        return false;
    }
    if(!is_bargain2){
        // $('#loading').css("display","none");
        // alert("议价幅度超过限制，请重新输入！");
        // return false;
        //status = 1;
    }
    //if(is_dcfh){
        if(!is_dcfh_vaule){
            $('#loading').css("display","none");
            alert("请填写正确的提货数量用逗号分割！");
            return false;
        }
        if(!is_th_num){
            $('#loading').css("display","none");
            alert("螺纹多次提货数量总件数不能大于购买件数（件），高线及盘螺多次提货数量总数量不能大于购买数量（吨），请修改分次提货数量！");
            return false;
        }
        //is_split_lading = $('#is_split_lading').val();
    //}

    //执行生成订单
    $.ajax({
        url: "/purchase_car/create_order",
        type: "get",
        dataType: "json",
        //async:false,
        data: {
            gwc_ids:gwc_ids,
            weights:weights,
            weights2:weights2,
            bargains:bargains,
            dcfh_vaules:dcfh_vaules,
            resources_ids:resources_ids,
            vids:vids,
            ticket:ticket,
            dz_code:dz_code,
            status:status
        },
        success: function (data) {
            if(data.success == 1){
                $(location).attr('href', '/scorderlist/index');
            }else{
                $('#loading').css("display","none");
                alert(data.msg);
            }
            console.log(data);
        }
    });
}

//全选
function gwc_check_all(obj){
    if(obj.checked == true){
        $('input[name^=gwc_id]').each(function(index,element){
            this.checked = "checked";
        });
    }else{
        $('input[name^=gwc_id]').each(function(index,element){
            this.checked = "";
        });
    }
}

function is_dc_fahuo(obj){
    // if(obj.checked == true){
    //     obj.value = 1;
    //     is_dcfh = true;
    //     $('input[name^=dcfh_vaule]').each(function(index,element){
    //         this.disabled = "";
    //     });
    // }else{
    //     obj.value = 0;
    //     is_dcfh = false;
    //     $('input[name^=dcfh_vaule]').each(function(index,element){
    //         this.disabled = "disabled";
    //     });
    // }
}

function checkvalue(obj,k){
    obj.value = obj.value.replace(/\，/g,",");
    if($('#gwc_vid'+k).val() == "1"){
        obj.value = obj.value.replace(/\./g,"");
    }
    
    // if(obj.value.substr(0,1) == ","){
    //     alert("请输入正确的提货数量！");
    //     obj.value = "";
    //     return false;
    // }
    // var regPos = /^\d+(\.\d+)?$/;
    // var strs = obj.value.split(",");
    // for(var i = 0; i< strs.length; i++){
    //     if(!regPos.test(strs[i])) {
    //         alert("请输入正确的提货数量！");
    //         obj.value = "";
    //         break;
    //     }
    //     if(i > 4){
    //         alert("最多只能分五次提货，请重新输入分次提货数量！");
    //         obj.value = "";
    //         break;
    //     }
    // }
    
}

function del_purchase_car(obj,id){
    //var id = obj.value;
    var tr = obj.parentNode.parentNode;
    var tbody = tr.parentNode;
    tbody.removeChild(tr);
    var car_resource_num = $('#car_resource_num').html() ;
    car_resource_num = car_resource_num-1;
    $('#car_resource_num').html(car_resource_num) ;

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

//检查重量
function check_num2(obj,id){
    obj.value=obj.value.replace(/^\D*(\d*(?:\.\d{0,3})?).*$/g, '$1');
    var num = $("#num"+id).val();
    var d_weight = $("#d_weight"+id).val();
    var write_num = obj.value;
    if ( write_num < 0 ) {
        alert("请输入正确的购买量！");
        obj.value = "";
        obj.focus();
    }else if( parseFloat(write_num) > parseFloat(num) ){
        alert("购买数量不能大于总量！");
        obj.value = "";
        obj.focus();
    }else{
        if(d_weight != "" && write_num !="" && $('#gwc_vid'+id).val() == "1"){
            var jianshu = parseInt((parseFloat(write_num)/parseFloat(d_weight)));
            $('#gwc_weight2'+id).val(jianshu);
        }else{
            $('#gwc_weight2'+id).val("0.0");
        }
    }
}

//检查螺纹钢单件数量
function check_lw_num(obj, id){
    if($('#gwc_vid'+id).val() == "1"){
        var d_weight = $("#d_weight"+id).val();
        var write_num = obj.value;
        if(d_weight != "" && write_num !="" && $('#gwc_vid'+id).val() == "1"){
            var jianshu = parseInt((parseFloat(write_num)/parseFloat(d_weight)));
            var gmsl = (parseFloat(jianshu)*parseFloat(d_weight)).toFixed(3);
            $('#gwc_weight'+id).val(gmsl);
        }else{
            $('#gwc_weight'+id).val("0.0");
        }
        if(parseFloat(obj.value) < parseFloat(d_weight)){
            obj.value = 0.0;
            alert("购买量不能少于"+d_weight+"吨！");
            obj.blur();
        }
    }
}

//检查件数
function check_piece2(obj, id){
    if($('#gwc_vid'+id).val() != "1"){
        obj.value = 0;
    }else{
        obj.value=obj.value.replace(/\D|^0/g,'');
        var piece = $("#z_weight"+id).val();
        var d_weight = $("#d_weight"+id).val();
        var write_piece = obj.value;
        if (write_piece < 0) {
            alert("请输入正确的件数！");
            obj.value = "";
            obj.focus();
        }else if ( parseFloat(write_piece) > parseFloat(piece) ) {
            alert("购买件数不能大于总件数！");
            obj.value = "";
            obj.focus();
        }else{
            if(d_weight != "" && write_piece != "" && write_piece != ""){
                var gmsl = (parseFloat(write_piece)*parseFloat(d_weight)).toFixed(3);
                $('#gwc_weight'+id).val(gmsl);
            }else{
                $('#gwc_weight'+id).val(0.0);
            }
        }
    }
}

function check_ticket(type){
    if(type == 1){
        $('#dz_code').removeAttr("disabled");
    }else if(type == 2){
        $("#dz_code").val('');
        $('#dz_code').attr("disabled","disabled");
    }
}
