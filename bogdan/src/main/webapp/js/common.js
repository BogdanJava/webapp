// Get the modal
var pmodal = document.getElementById('myModal');
var fmodal = document.getElementById('fileModal');

fmodal.onclose = function () {
    lastUpload.parentNode.removeChild(lastUpload);
}

// Get the button that opens the modal
var pbtn = document.getElementById("myBtn");
var fbtn = document.getElementById("addFileBtn");
// Get the <span> element that closes the modal
var pspan = document.getElementsByClassName("close")[1];
var fspan = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
pbtn.onclick = function() {
    pmodal.style.display = "block";
}

var lastUpload;

fbtn.onclick = function() {
    lastUpload = createUploadButton();
    fmodal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
pspan.onclick = function() {
    pmodal.style.display = "none";
}

fspan.onclick = function() {
    lastUpload.parentNode.removeChild(lastUpload);
    fmodal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == pmodal) {
        pmodal.style.display = "none";
    }
    if(event.target == fmodal){
        if(validateFiles(fmodal, validFileExtensions) == false) return;
        lastUpload.parentNode.removeChild(lastUpload);
        fmodal.style.display = "none";
    }
}

function clickSubmit(){

    if(validateFiles(form, validForPhoto) == false) return;

    var fileDiv = document.getElementById("file-name");
    var oldPhones = document.getElementsByName("id_val");
    var oldFiles = document.getElementsByName("t_fid");
    for(var i = 0; i<oldPhones.length; i++){
        if(oldPhones[i].value == '-1') continue;
        var tr1 = oldPhones[i].parentNode;
        tr1.parentNode.removeChild(tr1);
    }
    for(var i = 0; i<oldFiles.length; i++){
        if(oldFiles[i].value == '-1') continue;
        var tr2 = oldFiles[i].parentNode;
        tr2.parentNode.removeChild(tr2);
    }

    if(fileDiv.innerHTML === "Файл не выбран"){
        var photoDiv = document.getElementById("photoDiv");
        var notUpdate = document.getElementsByName("notUpdate")[0];
        if(notUpdate == null || notUpdate == undefined){
            notUpdate = document.createElement("input");
            notUpdate.setAttribute("type", "hidden");
            notUpdate.setAttribute("value", "1");
            notUpdate.setAttribute("name", "notUpdate");
            photoDiv.appendChild(notUpdate);
            document.createElement("input")
        }
    }
    document.getElementById("submitInput").click();
}

var table = document.getElementById("phoneTable").getElementsByTagName('tbody')[0];
var form = document.getElementById("form");

function submitModal() {
    var codeRegex = new RegExp("^[0-9]{1,3}$");
    var numberRegex = new RegExp("^[0-9]{7}$");

    var countryCode = document.getElementById('country_code').value;
    var operatorCode = document.getElementById('operator_code').value;
    var number = document.getElementById('number').value;
    var phoneType = document.getElementById('phone_type').value;
    var comment = document.getElementById('p_comment').value;

    if(!codeRegex.test(countryCode) || !codeRegex.test(operatorCode) || !numberRegex.test(number)){
        var countryCodeInput = document.getElementById('country_code');
        var operatorCodeInput = document.getElementById('operator_code');
        var numberInput = document.getElementById('number');

        if(!codeRegex.test(countryCode)){
            countryCodeInput.setAttribute("style", "margin-right: 2%;width:15%;color:red;");
        }
        if(!codeRegex.test(operatorCode)){
            operatorCodeInput.setAttribute("color", "margin-right: 2%;width:15%;color:red;")
        }
        if(!numberRegex.test(number)){
            numberInput.setAttribute("color", "margin-right: 2%;width:15%;color:red;")
        }
        return;
    }

    var row = table.insertRow(0);

    var checkbox = row.insertCell(0);
    var cccell = row.insertCell(1);
    var occell = row.insertCell(2);
    var ncell = row.insertCell(3);
    var ptcell = row.insertCell(4);
    var ccell = row.insertCell(5);

    checkbox.innerHTML = '<input type="checkbox" name="p_checkBox" />';
    cccell.innerHTML = countryCode;
    cccell.outerHTML = cccell.outerHTML + '<input type="hidden" name ="country_code_val" value = "' + countryCode + '"/>' +
        '<input type="hidden" name="id_val" value="-1"/>';
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

var fileTable = document.getElementById("fileTable").getElementsByTagName('tbody')[0];

function createUploadButton(){
    var fileContainer = document.getElementById("fileContainer");

    var uploadDivs = document.getElementsByName("uploadDiv");
    for(var i=0; i<uploadDivs.length; i++){
        var udiv = uploadDivs[i];
        udiv.style.display = "none";
    }

    var buttonContainer = document.getElementById("buttonContainer");
    var div = document.createElement("div");
    var btn = document.createElement("button");
    var div1 = document.createElement("div");
    var inputHidden = document.createElement("input");
    var inputVisible = document.createElement("input");
    var label = document.createElement("label");

    div.setAttribute("name", "uploadDiv");
    div.setAttribute("class", "form-group gend file_upload");
    fileContainer.insertBefore(div, buttonContainer);

    var attachedFileBtn = makeid();
    var hiddenid = makeid();
    var visibleid = makeid();
    var attachedFileName = makeid();

    btn.setAttribute("id", attachedFileBtn);
    btn.setAttribute("type", "button");
    btn.setAttribute("onclick", "document.getElementById(\'"+hiddenid+"\').click();");
    btn.setAttribute("class", "button");
    btn.setAttribute("style", "Margin: 0;");
    btn.innerHTML = '<span>Обзор</span>';
    div.appendChild(btn);

    div1.setAttribute("id", attachedFileName);
    div1.innerHTML="Файл не выбран";
    div.appendChild(div1);

    inputHidden.setAttribute("accept", "image/jpeg,image/gif,text/*");
    inputHidden.setAttribute("name", "file");
    inputHidden.setAttribute("style", "position: absolute; display: block; overflow: hidden; width: 0; height: 0; border: 0; padding: 0;");
    inputHidden.setAttribute("id", hiddenid);
    inputHidden.setAttribute("onchange", 'document.getElementById(\'' + visibleid + '\').value = this.value;document.getElementById(\''+attachedFileName+'\').innerHTML = this.value.slice(12);');
    inputHidden.setAttribute("type", "file");
    div.appendChild(inputHidden);

    inputVisible.setAttribute("id", visibleid);
    inputVisible.setAttribute("onclick", "document.getElementById(\'"+hiddenid+"\').click();");
    inputVisible.setAttribute("style", "height: 0;");
    inputVisible.setAttribute("type", "text");
    div.appendChild(inputVisible);

    label.setAttribute("class", "control-label");
    label.setAttribute("for", attachedFileBtn);
    label.innerHTML = "Файл";
    div.appendChild(label);

    var i = document.createElement("i");
    i.setAttribute("class", "bar");
    div.appendChild(i);
    attachedFile = attachedFileName;
    return div;
}

var attachedFile;

function submitFileAdd(){

    if(validateFiles(document.getElementById("form"), validFileExtensions) == false) return;
    var modal = document.getElementById('fileModal');
    var fileName = document.getElementById("fname").value;
    var fileRealName = document.getElementById(attachedFile).innerHTML;
    var fileComment = document.getElementById("f_comment").value;

    if(!new RegExp("^[a-zA-Z0-9._-]{1,30}$").test(fileName)) {
        var fileNameInput = document.getElementById("fname");
        fileNameInput.setAttribute("style", "margin-right: 2%;width:15%;color:red;");
        return;
    }

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
    newNode.setAttribute("type", "hidden");
    newNode.setAttribute("name", "t_fid");
    newNode.setAttribute("value", "-1");
    checkbox.parentNode.appendChild(newNode);
    newNode = document.createElement("input");
    newNode.setAttribute("type", "hidden");
    newNode.setAttribute("name", "t_fdate");
    newNode.setAttribute("value", "");
    checkbox.parentNode.appendChild(newNode);

    modal.style.display = "none";
}

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 5; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

document.getElementById('upload_hidden').addEventListener('change', handleFileSelect, false);
function handleFileSelect(evt) {
    var files = evt.target.files;
    var f = files[0];
    var reader = new FileReader();

    reader.onload = (function(theFile) {
        return function(e) {
            document.getElementById('list').innerHTML =
                ['<img src="', e.target.result,'" title="', theFile.name, '" height="150" width="150" />'].join('');
        };
    })(f);

    reader.readAsDataURL(f);
}

var validFileExtensions = [".jpg", ".jpeg", ".bmp", ".gif", ".png", ".txt", ".docx"];
var validForPhoto = [".jpg", ".jpeg", ".gif", "png"];

function validateFiles(oForm, validFiles) {
    var arrInputs = oForm.getElementsByTagName("input");
    for (var i = 0; i < arrInputs.length; i++) {
        var oInput = arrInputs[i];
        if (oInput.type == "file") {
            var files = oInput.files;
            if(files.length != Number(0))
            if(files[0].size > Number(9437184)) {
                    alert("file " + files[0].name + " has size more than 10 MB. Try to upload smaller file.");
                    return false;
            }
            var sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < validFiles.length; j++) {
                    var sCurExtension = validFiles[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }

                if (!blnValid) {
                    console.log(sFileName);
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + validFiles.join(", "));
                    return false;
                }
            }
        }
    }

    return true;
}