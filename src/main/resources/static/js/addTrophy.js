document.getElementById("addTrophyButton").addEventListener("click", function() {
    document.getElementById("trophyForm").style.display = "block";
    document.getElementById("removeTrophyForm").style.display = "none";
});

document.getElementById("newTrophyForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const competition = document.getElementById("competition").value;
    const year = document.getElementById("year").value;
    const tableBody = document.getElementById("trophyTable").getElementsByTagName('tbody')[0];
    const array_years=year.split(",").map(s => s.trim());
    //divido la striga per carattere ",", e in ognuna rimuovo eventuali spazi indesiderati su ogni elemento s dell'array
    //inizializzo una variabile per validare ogni elemento di array_years
    let stringaValida=true;
    array_years.forEach(function (valore){
        if (isNaN(valore)){
            //se uno dei valori non è numerico stringaValida è settato a false
            stringaValida=false;
        }
    })
    if (stringaValida){
        addNewRowArray(tableBody,competition,array_years); //aggiunta nuova riga
        document.getElementById("trophyForm").style.display = "none";
        document.getElementById("newTrophyForm").reset();
        //resetto solo se sono stati inseriti correttamente degli anni, in questo modo mantengo i dati del form
    } else {
        alert ("Non hai inserito degli anni corretti, riprova");
    }

});

function addNewRowArray(tableBody, name, years){
    const newRow = tableBody.insertRow();
    const nameCell = newRow.insertCell(0);
    const yearCell = newRow.insertCell(1);
    const timesCell= newRow.insertCell(2);

    nameCell.textContent = name;
    yearCell.innerHTML= years.map( year => `<span class="badge bg-primary">${year}</span> `).join("")
    //questa funzione crea un array contenente tanti span class con il rispettivo valore di year, e poi le unisce in un'unica
    //stringa via join per passarla a innerHTML
    volteVittoria=years.length
    timesCell.textContent=volteVittoria;
}
