/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $("#checkUsername").hide();
    $("#checkPassword").hide();
    enableRegisterButton();

    $("#username").keyup(function () {
        var usr = $("#username").val();
        if (usr !== "") {
            $.ajax({
                url: "register.html",
                data: {
                    cmd: "checkUsername",
                    username: usr
                },
                dataType: 'json',
                success: function (data, state) {
                    changeUsernameMessageState(data);
                },
                error: function (data, state) {

                }

            });
        }

    });

    $("#password1").keyup(function () {
        enableRegisterButton();
    });
    $("#password2").keyup(function () {
        enableRegisterButton();
    });

    function changeUsernameMessageState(check) {
        if (check.ok) {
            $("#checkUsername")
                    .addClass("ok")
                    .removeClass("error")
                    .text("Nome utente disponibile")
                    .show();
            enableRegisterButton();
        } else {
            $("#checkUsername")
                    .addClass("error")
                    .removeClass("ok")
                    .text("Nome utente non disponibile")
                    .show();
            enableRegisterButton();
        }
    }

    function enableRegisterButton() {
        var enable =
                checkPasswords() &&
                $("#checkUsername").hasClass("ok") &&
                $("#username").val() !== "";
        if (enable) {
            $("#register").prop("disabled", false);
        } else {
            $("#register").prop("disabled", true);
        }
    }

    function checkPasswords() {
        var firstPassword = $("#password1").val();
        var secondPassword = $("#password2").val();

        if (firstPassword === "" || secondPassword === "") {
            $("#checkPassword")
                    .removeClass("ok")
                    .addClass("error")
                    .text("Inserire una password");
            return false;
        }

        if (firstPassword !== secondPassword) {
            $("#checkPassword")
                    .removeClass("ok")
                    .addClass("error")
                    .text("Le due password non coincidono")
                    .show();
            return false;
        }

        $("#checkPassword")
                .removeClass("error")
                .addClass("ok")
                .text("Le due password coincidono")
                .show();
        return true;




    }
});
