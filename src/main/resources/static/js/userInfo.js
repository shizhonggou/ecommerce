<!-- sreach 下拉 start -->
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
			},function(){}
			);
			$('ul.son_ul li').click(function(){
			$(this).parents('li').find('span').html($(this).html());
			$(this).parents('li').find('ul').slideUp();
			});
			}
);
<!-- sreach 下拉 end -->

    <!-- 校验   start -->
function checkForm() {
    //校验空格
    var regP = new RegExp(/\s+/g);
    //获取用户的用户名信息
	var uname=document.getElementById("real_name").value.replace(/\s*/g,"");
	//var nick_name=document.getElementById("nick_name").value.replace(/\s*/g,"");
	// 校验用户名 只能是 数字字母 下划线
	var regUN = /^[0-9a-zA-Z_]{1,}$/;
	var user_name=document.getElementById("user_name").value.replace(/\s*/g,"");
	//获取校验规则  密码的
    //var regPwd = /^[a-z]\w{5,7}$/;
    var regPwd = /^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z_][A-Za-z_0-9]{5,16}$/;
	var password=document.getElementById("pwd_input").value.replace(/\s*/g,"");
	var pay_password=document.getElementById("pay_password").value.replace(/\s*/g,"");
	var pay_password_old=document.getElementById("pay_password_old").value;
	var password_old=document.getElementById("password_old").value;
	// 校验手机号
	var regMobile = /^1[3,4,5,7,8]\d{9}$/;
	var mobile=document.getElementById("mobile").value.replace(/\s*/g,"");
	// 校验身份证号
	var regIdcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	var idcard=document.getElementById("idcard").value.replace(/\s*/g,"");
	// 校验邮箱
	var regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
	var email=document.getElementById("email").value.replace(/\s*/g,"");
	var job=document.getElementById("job").value.replace(/\s*/g,"").replace(/\s*/g,"");
	//var address_user=document.getElementById("address_user").value.replace(/\s*/g,"");

	if (uname == '' || uname ==null){
	    alert("请输入姓名");
        return false;
	}
	/*if (nick_name == '' || nick_name ==null) {
        alert("请输入昵称");
        return false;
	}*/
	if (user_name == '' || user_name ==null) {
        alert("请输入用户名");
        return false;
    } else if(!regUN.test(user_name)) {
        alert("用户名只能包含数字、字母、下划线");
        return false;
    }
    if (password == '' || password ==null) {
        alert("请输入密码（6~16位字母数字下划线，不能以数字开头）");
        return false;
    } else if (password != password_old) {
        // 不相等 说明修改了   要校验
        if(!regPwd.test(password)){
             alert("请输入正确的密码（6~16位字母数字下划线，不能以数字开头）");
             return false;
        }
    }
    if (pay_password == '' || pay_password ==null) {
        alert("请输入交易密码（6~16位字母数字下划线，不能以数字开头）");
        return false;
    } else if (pay_password != pay_password_old) {
        // 不相等 说明修改了   要校验
        if(!regPwd.test(pay_password)){
            alert("请输入正确的交易密码（6~16位字母数字下划线，不能以数字开头）");
            return false;
        }
    }

    if (mobile == '' || mobile ==null) {
        alert("请输入手机号码");
        return false;
    } else if(!regMobile.test(mobile)){
        alert("请输入正确的手机号码");
        return false;
    }
    /*if (idcard == '' || idcard ==null) {
        alert("请输入身份证号码");
        return false;
    } else if (!regIdcard.test(idcard)){
        alert("请输入正确的身份证号码");
        return false;
    }*/
    if (email != "" && email !=null) {
        if(!regEmail.test(email)) {
                alert("请输入正确的邮箱地址");
                return false;
            }
    }

    /*if (job == '' || job ==null) {
        alert("请输入职位");
        return false;
    }
    if (address_user == '' || address_user ==null) {
        alert("请输入地址");
        return false;
    }*/
    return true;
}
function checkCompanyInfoForm () {
    // 校验只能输入中文
//    var regZW = /^[\u4E00-\u9FA5]+$/;
    var com_name = document.getElementById("com_name").value.replace(/\s*/g,"");
    var com_name_short=document.getElementById("com_name_short").value.replace(/\s*/g,"");
    //var com_desc=document.getElementById("com_desc").value.replace(/\s*/g,"");
    var address_state=document.getElementById("address_state").value.replace(/\s*/g,"");
    var address_city=document.getElementById("address_city").value.replace(/\s*/g,"");
    var address=document.getElementById("address").value.replace(/\s*/g,"");
    //var file=document.getElementById("file").value.replace(/\s*/g,"");

    if(com_name == '' || com_name == null) {
        alert("请输入公司名称");
        return false;
    }
    if(com_name_short == '' || com_name_short == null) {
            alert("请输入公司简称");
            return false;
        }
    /*if(com_desc == '' || com_desc == null) {
            alert("请输入公司描述");
            return false;
        }*/
    if(address_state == '' || address_state == null) {
            alert("请输入公司省份");
            return false;
        }
    if(address_city == '' || address_city == null) {
            alert("请输入公司所在的城市");
            return false;
        }
    if(address == '' || address == null) {
            alert("请输入公司的详细地址");
            return false;
        }
    /*if(file == '' || file == null) {
            alert("请选择三证附件");
            return false;
    }*/
    return true;
}


    <!-- 校验   end -->
<!-- 权限   start -->
    function setchakan(){
    	var chakan = document.getElementById('chakan').checked;
    	if(chakan == true){
    		document.getElementById('jiaoyi').checked = false;
    		document.getElementById('ziliaoxiugai').checked = false;
    	}
    }

    function unsetchakan(val){
    	if(val == true){
    		document.getElementById('chakan').checked = false;
    	}
    }
    <!-- 权限   end -->
    <!--  出入账方式 承兑 银行 显示隐藏 -->
    function billTypeChange() {
        var radio = document.getElementsByName("bill_type");
        var yhcdts = document.getElementById("yhcdts");
        if (radio[0].checked) {
            //第一个radio被选中后触发的事件
            yhcdts.style.display = 'none';
        } else {
            //第二个radio被选中后触发的事件
            yhcdts.style.display = '';
        }
    }
    function checkFinancialForm() {
        //校验空格
        //var regP = new RegExp(/\s+/g);
        //获取用户的用户名信息
    	var company_name=document.getElementById("company_name").value.replace(/\s*/g,"");
    	var regMoney = /^\d+(\.\d{1,2})?$/;
    	var money=document.getElementById("money").value.replace(/\s*/g,"");
    	// 出入账方式 = 2 承兑用
    	var crzfs=document.getElementById("crzfs").value.replace(/\s*/g,"");
    	var accept_bank=document.getElementById("accept_bank").value.replace(/\s*/g,"");
    	var accept_date=document.getElementById("accept_date").value.replace(/\s*/g,"");
    	if (company_name == '' || company_name ==null){
    	    alert("请输入公司全称");
            return false;
    	}
    	if (money == '' || money ==null) {
            alert("请输入金额");
            return false;
    	} else if (!regMoney.test(money)) {
            alert("请检查输入的金额是否正确");
            return false;
    	}
    	if (crzfs == '2') {
    	    if (accept_bank == '' || accept_bank ==null){
                alert("请输入银行名称");
                return false;
            }
            if (accept_date == '' || accept_date ==null){
                alert("请输入承兑天数");
                return false;
            } else if (!(/(^[1-9]\d*$)/.test(accept_date))){
                alert("承兑天数应为正整数");
                return false;
            }

    	}

    }
    // 录入财务信息 校验
    function checkFinancialForm() {
            //校验空格
            //var regP = new RegExp(/\s+/g);
            //获取用户的用户名信息
        	var company_name=document.getElementById("cameName").value.replace(/\s*/g,"");
        	var regMoney = /^\d+(\.\d{1,2})?$/;
        	var money=document.getElementById("money").value.replace(/\s*/g,"");
        	// 出入账方式 = 2 承兑用
        	var crzfs=document.getElementById("crzfs");
        	var accept_bank=document.getElementById("accept_bank").value.replace(/\s*/g,"");
        	var accept_date=document.getElementById("accept_date").value.replace(/\s*/g,"");
        	if (company_name == '' || company_name ==null){
        	    alert("请输入公司名称");
                return false;
        	}
        	if (money == '' || money ==null) {
                alert("请输入金额");
                return false;
        	} else if (!regMoney.test(money)) {
                alert("请检查输入的金额是否正确");
                return false;
        	}
        	if (crzfs.checked == true) {
        	    if (accept_bank == '' || accept_bank ==null){
                    alert("请输入银行名称");
                    return false;
                }
                if (accept_date == '' || accept_date ==null){
                    alert("请输入承兑天数");
                    return false;
                } else if (!(/(^[1-9]\d*$)/.test(accept_date))){
                    alert("承兑天数应为正整数");
                    return false;
                }

        	}

        }
        // 修改余额 校验
        function checkBalanceForm() {
                    //校验空格
                    //var regP = new RegExp(/\s+/g);
                    //获取用户的用户名信息
                	var company_name=document.getElementById("company_name").value.replace(/\s*/g,"");
                	var regMoney = /^\d+(\.\d{1,2})?$/;
                	var balance=document.getElementById("balance").value.replace(/\s*/g,"");
                	if (company_name == '' || company_name ==null){
                	    alert("请输入公司全称");
                        return false;
                	}
                	if (balance == '' || balance ==null) {
                        alert("请输入金额");
                        return false;
                	} else if (!regMoney.test(balance)) {
                        alert("请检查输入的金额是否正确");
                        return false;
                	}


                }


