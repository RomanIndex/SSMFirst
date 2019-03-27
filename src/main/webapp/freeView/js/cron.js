var cron = {};

$(function () {
    cron.init()
});

/* 初始化方法  */
cron.init = function () {
    cron.secondsHtml("seconds");
    cron.secondsHtml("minutes");
    cron.hoursHtml();
    cron.dayofMouthHtml();
    cron.mouthHtml();
    cron.chkClick();
    cron.chksClick();
    cron.radioClick();
    cron.cronBlur();
    cron.cycle();
}

$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    //e.target // newly activated tab
    //e.relatedTarget // previous active tab
    var that = $(this).attr("href")
})

cron.secondsHtml = function (type) {
    var html = "";
    var c = "";
    for (var i = 0; i < 60; i++) {
        c = '<input type="checkbox" name="chk_' + type + '_value" value="' + i + '">';
        if (i < 10) {
            c += '<span>0' + i + '</span>';
        } else {
            c += '<span>' + i + '</span>';
        }

        if (i == 0) {
            html += '<div class="imp secondList">' + c;
        } else if ((i + 1) % 10 == 0) {
            html += c + '</div><div class="imp secondList">';
        } else if (i == 59) {
            html += c + '</div>';
        } else {
            html += c;
        }
    }
    $("#div_" + type + " .linet").after(html);
}

cron.hoursHtml = function () {
    var html = "";
    var c = "";
    for (var i = 0; i < 24; i++) {
        c = '<input type="checkbox" name="chk_hours_value" value="' + i + '">';
        if (i < 10) {
            c += '<span>0' + i + '</span>';
        } else {
            c += '<span>' + i + '</span>';
        }
        if (i == 0) {
            html += '<div class="imp hourList">AM: ' + c;
        } else if (i == 12) {
            html += '</div><div class="imp hourList">PM: ' + c;
        } else if (i == 23) {
            html += c + '</div>';
        } else {
            html += c;
        }
    }
    $("#div_hours .linet").after(html);
}

cron.dayofMouthHtml = function () {
    var html = "";
    var c = "";
    for (var i = 1; i < 32; i++) {
        c = '<input type="checkbox" name="chk_dayofMouth_value" value="' + i + '">';
        c += '<span>' + i + '</span>';
        if (i == 1) {
            html += '<div class="imp dayList">' + c;
        } else if (i == 16 || i == 29) {
            html += '</div><div class="imp dayList">' + c;
        } else if (i == 31) {
            html += c + '</div>';
        } else {
            html += c;
        }
    }
    $("#div_dayofMouth .linet:eq(2)").after(html);
}

cron.mouthHtml = function () {
    var html = "";
    var c = "";
    for (var i = 1; i < 13; i++) {
        c = '<input type="checkbox" name="chk_mouth_value" value="' + i + '">';
        c += '<span>' + i + '</span>';
        if (i == 1) {
            html += '<div class="imp mouthList">' + c;
        } else if (i == 12) {
            html += c + '</div>';
        } else {
            html += c;
        }
    }
    $("#div_mouth .linet").after(html);
}

/**
 * 用户选择：
 * 1秒 2秒 1天 星期一......时触发
 */
cron.chkClick = function () {
    $("input[type='checkbox'][name*='_value']").click(function () {
        var name = $(this).attr("name").split("_")[1];
        var v = "";
        var flag = $(this).parents("div.tab-pane").find(".linet:eq(0) input").is(":checked");
        if (!flag)
            return;
        if (name == "dayofMouth") {
            var vr = $("input[type='radio'][name='radio_dayofMouth']:checked").val();
            if (vr == "L") {
                return;
            }
        }
        $("input[type='checkbox'][name='" + $(this).attr('name') + "']:checked").each(function () {
            v += $(this).val() + ",";
        });

        cron.setInputValue(v, name);
    });
}

cron.radioClick = function () {
    $("input[type='radio'][name='radio_dayofMouth']").click(function () {
        var flag = $(this).parents("div.tab-pane").find(".linet:eq(0) input").is(":checked");
        if (!flag)
            return;
        var vr = $(this).val();
        var v = "";
        if (vr == "L") {
            var iv = $("#input_dayofMouth");
            iv.val("L");
            cron.expressValue();
        } else {
            $("input[type='checkbox'][name='chk_dayofMouth_value']:checked").each(function () {
                v += $(this).val() + ",";
            });
            cron.setInputValue(v, "dayofMouth");
        }
    });
}

/**
 * 用户选择：
 * 使用秒、使用时，使用天，使用周时触发
 */
cron.chksClick = function () {
    $("input[type='checkbox'][name*='chks']").click(function () {
        var name = $(this).attr("name").replace("chks_", "");
        if ($(this).is(":checked")) {
            var v = "";
            if (name == "dayofMouth") {
                var vr = $("input[type='radio'][name='radio_dayofMouth']:checked").val();
                if (vr == "L") {
                    var iv = $("#input_dayofMouth");
                    iv.val("L");
                    cron.expressValue();
                    return;
                }
            }
            $("input[type='checkbox'][name='chk_" + name + "_value']:checked").each(function () {
                v += $(this).val() + ",";
            });
            cron.setInputValue(v, name);
        } else {
            var iv = $("#input_" + name);
            switch (name) {
                case "seconds":
                    iv.val("*");
                    break;
                case "minutes":
                    iv.val("*");
                    break;
                case "hours":
                    iv.val("*");
                    break;
                case "dayofMouth":
                    $("#input_dayofMouth").val() == "?" ? iv.val("*") : iv.val("?");
                    break;
                case "mouth":
                    iv.val("*");
                    break;
                case "dayofWeek":
                    iv.val("?");
                    $("#input_dayofMouth").val() == "?" ? $("#input_dayofMouth").val("*") : "";
                    break;
            }
            cron.expressValue();
        }
    });
}

cron.setInputValue = function (v, name) {
    if (v.length > 0) {
        v = v.substring(0, v.length - 1);
    }
    var iv = $("#input_" + name);
    switch (name) {
        case "seconds":
            everyCron();
            break;
        case "minutes":
            everyCron();
            $("#input_seconds").val() == "*" ? $("#input_seconds").val("0") : "";
            replaceCron("seconds");
            break;
        case "hours":
            iv.val(v);
            $("#input_seconds").val() == "*" ? $("#input_seconds").val("0") : "";
            $("#input_minutes").val() == "*" ? $("#input_minutes").val("0") : "";
            replaceCron("minutes");
            break;
        case "dayofMouth":
            iv.val(v);
            break;
        case "mouth":
            iv.val(v);
            break;
        case "dayofWeek":
            $("#input_seconds").val() == "*" ? $("#input_seconds").val("0") : "";
            $("#input_minutes").val() == "*" ? $("#input_minutes").val("0") : "";
            $("#input_hours").val() == "*" ? $("#input_hours").val("0") : "";
            $("#input_dayofMouth").val() == "*" ? $("#input_dayofMouth").val("?") : "";
            iv.val(v);
            break;
    }

    if (name == "dayofWeek") {
        iv.val() == "" ? iv.val("?") : "";
    } else {
        iv.val() == "" ? iv.val("*") : "";
    }

    //每秒  每分钟处理
    //如果选择了 5秒，值替换成 */5 （表示每5秒执行一次）
    function everyCron() {
        var flag = true;
        iv.nextAll().each(function () {
            var nv = $(this).val();
            if (nv != "*" && nv != "?" && nv != "L") {
                flag = false;
                return;
            }
        });
        if (flag) {
            if (v == "") {
                iv.val(v);
            } else {
                v.indexOf(",") > 0 ? iv.val(v) : iv.val("*/" + v);
            }

        } else {
            iv.val(v);
        }
    }

    //如果选择了5秒 和 1分钟  把（*/5）替换成（5）,表示每分钟1秒执行
    function replaceCron(name) {
        var imv = $("#input_" + name).val();
        if (imv.length > 1) {
            $("#input_" + name).val(imv.replace("*/", ""))
        }
    }
    cron.expressValue();
}

cron.expressValue = function () {
    var v = ["seconds", "minutes", "hours", "dayofMouth", "mouth", "dayofWeek"];
    var ev = "";
    for (var i = 0; i < v.length; i++) {
        ev += $("#input_" + v[i]).val() + " "
    }
    $("#express").val(ev.replace(/\s*$/g, ''));
}

cron.cronBlur = function () {
    $("#express").blur(function () {
        var v = $(this).val().split(" ");
        var l = $("#cron_input input").length;
        for (var i = 0; i < l; i++) {
            $("#cron_input input:eq(" + i + ")").val(v[i] == undefined ? "" : v[i]);
        }
    })
}

cron.cycle = function () {
    $("input[type='radio'][name='planExecute.isCycle']").click(function () {
        var v = $(this).val();
        if (v == "Y") {
            $("#div_cron_set").show();
            $("#express").removeAttr("ignore");
        } else {
            $("#div_cron_set").hide();
            $("#express").attr("ignore", "ignore");
            $("#cron_input").contents().find("input").val("");
            $("#express").val("");
        }
    });
}