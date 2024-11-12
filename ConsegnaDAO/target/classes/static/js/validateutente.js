function validate(event){
    event.preventDefault();
    casellaTesto = document.getElementById("user");
    content = casellaTesto.value;
    if (content.includes("@") ){
        let form = document.getElementById("formSubmit");
        form.submit();
    }else{
        alert("Manca la chiocciola");
    }
}

window.addEventListener("load", function(){
    var form = document.getElementById("formSubmit");
    form.addEventListener("submit", validate);
});



