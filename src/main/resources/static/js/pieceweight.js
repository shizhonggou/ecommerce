
function checkpieceweightForm() {
    var newUrl = "/pieceweight/save";
    $("#pieceweightform").attr('action', newUrl);
    $('#spec').val();
    $('#weight').val();
    if ( $('#spec').val() == '' ){
        $('#spec').focus();
        alert("请录入规格信息");
        return false;
    }else if( $('#weight').val() == '' ){
        $('#spec').focus();
        alert("请录入规格信息");
        return false;
    }else{
      $('#saveSubmitBtn').attr("disabled", true);
      return true;
    }

}