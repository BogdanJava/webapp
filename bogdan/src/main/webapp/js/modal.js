// Get the modal
var pmodal = document.getElementById('myModal');
var fmodal = document.getElementById('fileModal');

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

fbtn.onclick = function() {
    fmodal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
pspan.onclick = function() {
    pmodal.style.display = "none";
}

fspan.onclick = function() {
    fmodal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == pmodal) {
        pmodal.style.display = "none";
    }
	if(event.target == fmodal){
		fmodal.style.display = "none";
	}
}

