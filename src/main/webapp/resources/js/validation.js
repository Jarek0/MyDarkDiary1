/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validate() {
    if (document.loginForm.username.value == "" && document.loginForm.password.value == "") {
        alert("${noUser} and ${noPass}");
    document.f.username.focus();
    return false;
    }
    if (document.loginForm.username.value == "") {
    alert("${noUser}");
    document.f.username.focus();
    return false;
     }
     if (document.loginForm.password.value == "") {
    alert("${noPass}");
    document.f.password.focus();
    return false;
     }
}

