var select = document.getElementById("select");
var input = document.getElementById("text");
var previewButton = document.getElementById("modalButton");

window.onload = function () {
    if(select.options[select.selectedIndex].getAttribute("value") != null) {
        input.disabled = true;
    }
    else {
        previewButton.disabled = true;
    }
}

function clickSend() {
    var submit = document.getElementById("submitInput");
    submit.click();
}

function addMailField() {
    var parrent = document.getElementById("emails");

    var inputs = parrent.getElementsByTagName("input");
    var lastInput = inputs[inputs.length - 1];

    if(lastInput == undefined || validateEmail(lastInput.value)) {
        var newId = makeid();

        var div = document.createElement("div");
        div.setAttribute("class", "form-group");
        var input = document.createElement("input");
        input.setAttribute("name", "emailadresses");
        input.setAttribute("type", "email");
        input.setAttribute("id", newId);
        var label = document.createElement("label");
        label.setAttribute("for", newId);
        label.setAttribute("class", "control-label");
        label.innerHTML = "Email";
        var bar = document.createElement("i");
        bar.setAttribute("class", "bar");

        div.appendChild(input);
        div.appendChild(label);
        div.appendChild(bar);

        parrent.appendChild(div);
    }
}

function onChangeSelect() {
    if(select.options[select.selectedIndex].getAttribute("value")  == null) {
        previewButton.disabled = true;
        input.disabled = false;
        input.value = "";
    }
    else {
        input.disabled = true;
        previewButton.disabled = false;
    }
}

var modal = document.getElementById("previewModal");
var span = document.getElementById("close");

span.onclick = function () {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function closeModal() {
    modal.style.display = "none";
}

function showModalPreview(){
    var templateName = select.options[select.selectedIndex].getAttribute('value');
    console.log(templateName);
    var text = document.getElementById(templateName).value;
    var p = document.getElementById("templateText");
    p.innerHTML = text;
    modal.style.display = "block";
}

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 5; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}