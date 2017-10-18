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