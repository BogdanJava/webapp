function deleteFiles(){
    var table = document.getElementById("fileTable").getElementsByTagName('tbody')[0];
    var inputs = document.getElementsByName("f_checkBox");
    var uploads = document.getElementsByName("uploadDiv");
    var i = inputs.length;
    while (i--) {
        var input = inputs[i];
        var upload = uploads[i];
        if (input.checked == true) {
            var tr = input.parentNode.parentNode;
            table.deleteRow(tr.rowIndex - 1);
            upload.parentNode.removeChild(upload);
        }
    }
}

function deletePhones(){
    var t = document.getElementById("phoneTable").getElementsByTagName('tbody')[0];
    var inputs = document.getElementsByName("p_checkBox");
    var i = inputs.length;
    while (i--) {
        var input = inputs[i];
        if (input.checked == true) {
            var tr = input.parentNode.parentNode;
            t.deleteRow(tr.rowIndex - 1);
        }
    }
}