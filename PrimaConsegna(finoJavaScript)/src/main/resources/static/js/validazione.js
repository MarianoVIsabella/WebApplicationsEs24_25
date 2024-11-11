function validate(event){
    event.preventDefault();
    firstName = document.getElementById("fname");
    lastName= document.getElementById("lname");
    nome = firstName.value;
    cognome= lastName.value;
    //la mail è già controllata perché il tipo di input field è "mail", eventuali controlli superflui
    const pattern=/^[A-Za-z]+(\s[A-Za-z]+)*$/
    if (! pattern.test(nome) || ! pattern.test(cognome)){
        alert("Nome e/o cognome non validi, riprovare");
    }
}
window.addEventListener("load", function(){
    form = document.getElementById("contactForm");
    form.addEventListener("submit", validate);
});