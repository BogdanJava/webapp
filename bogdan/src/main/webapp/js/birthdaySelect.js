window.onload = function () {
    var day = new Date,
    md = (new Date(day.getFullYear(), day.getMonth() + 1, 0, 0, 0, 0, 0)).getDate(),
    $month_name = "Январь Февраль Март Апрель Май Июнь Июль Август Сентябрь Октябрь Ноябрь Декабрь".split(" ");
    function set_select(a, c, d, e) {
        var el = document.getElementsByName(a)[0];
        for (var b = 0; b < c; b++) {
            el.options[b] = new Option(a == 'month' ? $month_name[b] : b + d, b + d);
        }
        el.options[e] && (el.options[e].selected = !0)
    }
    set_select("day", md, 1, day.getDate() - 1);
    set_select("month", 12, 1, day.getMonth());
    set_select("year", 100, day.getFullYear()-99, 10);
    var year = document.getElementById('year');
    var month = document.getElementById("month");
    function check_date() {
        var a = year.value | 0,
        c = month.value | 0;
        md = (new Date(a, c, 0, 0, 0, 0, 0)).getDate();
        a = document.getElementById("day").selectedIndex;
        set_select("day", md, 1, a)
    }
    if (document.addEventListener) {
        year.addEventListener('change', check_date, false);
        month.addEventListener('change', check_date, false);
    } else {
        year.detachEvent('onchange', check_date);
        month.detachEvent('onchange', check_date);
    }

    var _day = document.getElementById("day");
    var _month = document.getElementById("month");
    var _year = document.getElementById("year")

    if(document.getElementById("hidden_day") != null) {
        _day.value = document.getElementById("hidden_day").value;
        var m = parseInt(document.getElementById("hidden_month").value);
        _month.selectedIndex = m;
        var y = Number(1900) + Number(document.getElementById("hidden_year").value)
        _year.value = y;
    }
}