function logout(){
    $.ajax({
        url: "/logout",
        type: "GET",
        dataType:"json",
        success: function(data) {
            if(data.status==200){
                window.location.reload();
            }else {
                alert(data.msg);
            }
        },
        error: function (xhr,ajaxOptions, thrownError) {
            console.log(xhr.status + ": " + thrownError);
        }
    });
}