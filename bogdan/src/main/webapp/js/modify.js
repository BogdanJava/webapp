function setBodyData(){

    var fileCount = document.getElementById("fileTable").getElementsByTagName('tbody')[0].childElementCount;
    for(var i = 0; i<fileCount; i++) {
        var div = document.createElement("div");
        div.setAttribute("name", "uploadDiv");
        div.setAttribute("style", "display:none;");
        document.getElementById("fileContainer").appendChild(div);
        var hiddenInput = document.createElement("input");
        hiddenInput.setAttribute("type", "file");
        hiddenInput.setAttribute("style", "display:none;");
        div.appendChild(hiddenInput);
    }
}
window.onloadend = setBodyData();

function deleteFiles(){
    var t = document.getElementById("fileTable").getElementsByTagName('tbody')[0];
    var inputs = document.getElementsByName("f_checkBox");
    var uploads = document.getElementsByName("uploadDiv");

    var i = inputs.length;
    console.log(inputs.length);
    while (i--) {
        var input = inputs[i];
        var upload = uploads[i];
        if (input != undefined && input.type != undefined && input.checked == true) {
            var tr = input.parentNode.parentNode;
            var id;
            var c = tr.getElementsByTagName("input");
            for(var j=0; j<c.length; j++){
                console.log(c[j].name);
                if(c[j].name === 't_fid'){
                    id = c[j].value;
                    break;
                }
            }

            t.deleteRow(tr.rowIndex - 1);
            upload.parentNode.removeChild(upload);

            if(id !== '-1') {
                var deletedIdInput = document.createElement("input");
                deletedIdInput.setAttribute("name", "deletedFile");
                deletedIdInput.setAttribute("type", "hidden");
                deletedIdInput.setAttribute("value", id);
                t.appendChild(deletedIdInput);
            }
        }
    }
}

function deletePhones(){
    var t = document.getElementById("phoneTable").getElementsByTagName('tbody')[0];
    var inputs = document.getElementsByName("p_checkBox");
    var i = inputs.length;
    console.log(inputs.length);
    while (i--) {
        var input = inputs[i];
        if (input != undefined && input.checked == true) {
            var tr = input.parentNode.parentNode;
            var id = '-1';
            var c = tr.getElementsByTagName("input");
            for(var i=0; i<c.length; i++){
                console.log(c[i].name);
                if(c[i].name === 'id_val'){
                    id = c[i].value;
                    break;
                }
            }

            if(id !== '-1') {
                var deletedIdInput = document.createElement("input");
                deletedIdInput.setAttribute("name", "deletedPhone");
                deletedIdInput.setAttribute("type", "hidden");
                deletedIdInput.setAttribute("value", id);
                t.appendChild(deletedIdInput);
            }

            t.deleteRow(tr.rowIndex - 1);
        }
    }
}