var table = document.getElementById("phoneTable").getElementsByTagName('tbody')[0];
var submit = document.getElementById('addPhoneBtn');
var deleteButton = document.getElementById('deleteButton');

function submitModal() {
    var countryCode = document.getElementById('country_code').value;
    var operatorCode = document.getElementById('operator_code').value;
    var number = document.getElementById('number').value;
    var phoneType = document.getElementById('phone_type').value;
    var comment = document.getElementById('p_comment').value;

    var row = table.insertRow(0);

    var checkbox = row.insertCell(0);
    var cccell = row.insertCell(1);
    var occell = row.insertCell(2);
    var ncell = row.insertCell(3);
    var ptcell = row.insertCell(4);
    var ccell = row.insertCell(5);

    checkbox.innerHTML = '<input type="checkbox" name="p_checkBox" />';
    cccell.innerHTML = countryCode;
    cccell.outerHTML = cccell.outerHTML + '<input type="hidden" name ="country_code_val" value = "' + countryCode + '"/>';
    occell.innerHTML = operatorCode;
    occell.outerHTML = occell.outerHTML + '<input type="hidden" name ="operator_code_val" value = "' + operatorCode + '"/>';
    ncell.innerHTML = number;
    ncell.outerHTML = ncell.outerHTML + '<input type="hidden" name ="number_val" value = "' + number + '"/>';
    ptcell.innerHTML = phoneType;
    ptcell.outerHTML = ptcell.outerHTML + '<input type="hidden" name ="phone_type_val" value = "' + phoneType + '"/>';
    ccell.innerHTML = comment;
    ccell.outerHTML = ccell.outerHTML + '<input type="hidden" name ="comment_val" value = "' + comment + '"/>';
    var modal = document.getElementById('myModal')
    modal.style.display = "none";
}

function deletePhones(){
    var inputs = document.getElementsByName("p_checkBox");
    var i = inputs.length;
    while (i--) {
        var input = inputs[i];
        if (input.checked == true) {
            var tr = input.parentNode.parentNode;
            table.deleteRow(tr.rowIndex - 1);
        }
    }
}

var fileTable = document.getElementById("fileTable").getElementsByTagName('tbody')[0];

function submitFileAdd(){
    var modal = document.getElementById('fileModal');
    var fileName = document.getElementById("fname").value;
    var fileRealName = document.getElementById("attachedFileName").innerHTML;
    var fileComment = document.getElementById("f_comment").value;
    var file = document.getElementById("file_upload_hidden");
    var row = fileTable.insertRow();

    var checkbox = row.insertCell(0);
    var fncell = row.insertCell(1);
    var fccell = row.insertCell(2);

    checkbox.innerHTML = '<input type="checkbox" name="f_checkBox" />';
    fncell.innerHTML = fileName;
    fccell.innerHTML = fileComment;

    var newNode = document.createElement("input");
    newNode.setAttribute("type", "hidden");
    newNode.setAttribute("name", "t_fname");
    newNode.setAttribute("value", fileName);
    checkbox.parentNode.appendChild(newNode);
    newNode = document.createElement("input");
    newNode.setAttribute("type", "hidden");
    newNode.setAttribute("name", "t_frealname");
    newNode.setAttribute("value", fileRealName);
    checkbox.parentNode.appendChild(newNode);
    newNode = document.createElement("input");
    newNode.setAttribute("type", "hidden");
    newNode.setAttribute("name", "t_fcomment");
    newNode.setAttribute("value", fileComment);
    checkbox.parentNode.appendChild(newNode);
    newNode = document.createElement("input");
    newNode.setAttribute("type", "file");
    newNode.setAttribute("name", "t_file_hidden");
    newNode.setAttribute("id", "file_upload_hidden");
    newNode.setAttribute("accept", "image/jpeg, text/*");
    newNode.setAttribute("style", "position: absolute; display: none; overflow: hidden; twidth: 0; height: 0; border: 0; padding: 0;");
    newNode.setAttribute("value", file.value);
    checkbox.parentNode.appendChild(newNode);

    modal.style.display = "none";
}

function deleteFiles(){
    var inputs = document.getElementsByName("f_checkBox");
    var i = inputs.length;
    while (i--) {
        var input = inputs[i];
        if (input.checked == true) {
            var tr = input.parentNode.parentNode;
            fileTable.deleteRow(tr.rowIndex - 1);
        }
    }
}