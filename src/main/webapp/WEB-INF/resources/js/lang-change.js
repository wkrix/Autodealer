$(document).ready(function () {
    $("#en_flag").on("click", function () {
        updateParam("lang", "en");
    });
    $("#hu_flag").on("click", function () {
        updateParam("lang", "hu");
    });
});

function updateParam(key, value) {
    var q = String(location.search);

    if (q.length > 0) {
        if (q.indexOf(key) > -1) {
            var regExpPattern = key + "=([a-zA-Z]+)?";
            var regExp = new RegExp(regExpPattern);

            q = q.replace(regExp, key + "=" + value);
        } else {
            q += "\u0026" + key + "=" + value;
        }
    } else {
        q += "?" + key + "=" + value;
    }

    location.search = q;
}
