/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addFriend(contextPath,userId){
    $.ajax({
        url : contextPath+'/profile/addFriend/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            if ($("#addFriend"+userId).length)
                $("#addFriend"+userId).attr('id',"deleteFriend"+userId);
            $("#deleteFriend"+userId).val("Delete friend");
            $("#deleteFriend"+userId).attr("onclick","deleteFriend('"+contextPath+"','"+userId+"');");
            $("#deleteFriend"+userId).attr('name',"Deletefriend");
            console.log("SUCCESS: ",data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
      }
    });

}

function deleteFriend(contextPath,userId){
    $.ajax({
        url : contextPath+'/profile/deleteFriend/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            if ($("#deleteFriend"+userId).length)
                $("#deleteFriend"+userId).attr('id',"addFriend"+userId);
            $("#addFriend"+userId).val("Add friend");
            $("#addFriend"+userId).attr("onclick","addFriend('"+contextPath+"','"+userId+"');");
            $("#addFriend"+userId).attr('name',"Addfriend");
            console.log("SUCCESS: ",data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
      }
    });

}

function banUser(contextPath,userId){
    $.ajax({
        url : contextPath+'/admin/console/ban/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            if ($("#Ban"+userId).length)
                $("#Ban"+userId).attr('id',"Unban"+userId);
            $("#Unban"+userId).val("Unban");
            $("#Unban"+userId).attr("onclick","unbanUser('"+contextPath+"','"+userId+"');");
            $("#Unban"+userId).attr('name',"Unban");
            console.log("SUCCESS: ",data);
            $("#result").html(data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
        $("#result").html(data.responseText);
      }
    });

}

function unbanUser(contextPath,userId){
    $.ajax({
        url : contextPath+'/admin/console/unban/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            if ($("#Unban"+userId).length)
                $("#Unban"+userId).attr('id',"Ban"+userId);
            $("#Ban"+userId).val("Ban");
            $("#Ban"+userId).attr("onclick","banUser('"+contextPath+"','"+userId+"');");
            $("#Ban"+userId).attr('name',"Ban");
            console.log("SUCCESS: ", data);
            $("#result").html(data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
        $("#result").html(data.responseText);}
    });

}

function upgradeUser(contextPath,userId){
    $.ajax({
        url : contextPath+'/admin/console/upgrade/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            $("#Role"+userId).text("Admin");
            console.log("SUCCESS: ",data);
            $("#result").html(data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
        $("#result").html(data.responseText);
      }
    });

}

function deleteUser(contextPath,userId){
    $.ajax({
        url : contextPath+'/admin/console/delete/'+userId,
        method : 'GET',
        async : false,
        success : function(data, textStatus, xhr) {
            $("#Row"+userId).remove();
            console.log("SUCCESS: ",data);
            $("#result").html(data);
        },
        error: function (data, textStatus, xhr) {
        console.log("FAIL: ", data.responseText);
        $("#result").html(data.responseText);
      }
    });

}