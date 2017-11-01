window.onload = function () {
    var page = Number(getParameterByName("page"));
    var prevButton = document.getElementById("prevButton");
    var nextButton = document.getElementById("nextButton");
    var maxpage = Number(document.getElementById("maxpage").value);
    var hasCriteria = document.getElementById("filters").value;
    var criteriaForm = document.getElementById("criteriaForm");

    if(hasCriteria == "yes"){
        criteriaForm.style.display = "block";
    }
    else criteriaForm.style.display = "none";

    if(page <= 1){
        prevButton.disabled = true;
    }
    else {
        prevButton.disabled = false;
    }
    if(page >= maxpage){
        nextButton.disabled = true;
    } else{
        nextButton.disabled = false;
    }

}

function submitSend(){
    var checkboxes = document.getElementsByName("selected[]")
    var submitInput = document.getElementById("submitSendInput");
    var sendButton = document.getElementById("mailButton");
    var ids = document.getElementsByName("id");
    var form = document.getElementById("mailForm");

    var i = checkboxes.length;
    while(i--){
        var checkbox = checkboxes[i];
        if(checkbox.checked == true){
            var newNode = document.createElement("input");
            newNode.setAttribute("type", "hidden");
            newNode.setAttribute("name", "emails");
            newNode.setAttribute("value", ids[i].value);
            form.appendChild(newNode);
        }
    }
    sendButton.disabled = true;
    submitInput.click();
}

function submitDelete() {
    var checkboxes = document.getElementsByName("selected[]")
    var submitInput = document.getElementById("submitDeleteHidden");
    var deleteButton = document.getElementById("deleteButton");
    var ids = document.getElementsByName("id");
    var form = document.getElementById("form");

    var i = checkboxes.length;
    while(i--){
        var checkbox = checkboxes[i];
        if(checkbox.checked == true){
            var newNode = document.createElement("input");
            newNode.setAttribute("type", "hidden");
            newNode.setAttribute("name", "toDelete");
            newNode.setAttribute("value", ids[i].value);
            form.appendChild(newNode);
        }
    }
    deleteButton.disabled = true;
    submitInput.click();
}

function goBack(){
    var page = Number(getParameterByName("page"));
    var submit = document.getElementById("backInput");
    var pageInput = document.getElementById("backPage");

    page--;

    pageInput.setAttribute("value", page.toString(10));
    submit.click();
}

function goForward(){
    var page = Number(getParameterByName("page"));
    var submit = document.getElementById("forwardInput");
    var pageInput = document.getElementById("forwardPage");

    page++;

    pageInput.setAttribute("value", page.toString(10));
    submit.click();
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}