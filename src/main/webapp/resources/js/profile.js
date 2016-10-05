$(document).ready(function(){

$('.image-upload').hover(function(){$(".cameraImage").show();},
                   function(){$(".cameraImage").hide();});
                   
$('.discription').hover(function(){$(".writeImage").show();},
                   function(){$(".writeImage").hide();});
$('.writeImage').hover(function(){$(".writeImage").css("opacity", "1");},
                   function(){$(".writeImage").css("opacity", "0.5");});
$('.writeImage').click(function(){$("#discriptionForm").show();
    $(".discription").hide();});


});
function countChar(val) {
        var len = val.value.length;
        if (len > 1000) {
          val.value = val.value.substring(0, 1000);
        } else {
          $('#charNum').text(1000 - len);
        }
      };


