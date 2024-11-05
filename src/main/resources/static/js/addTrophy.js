document.getElementById("addTrophyButton").addEventListener("click", function() {
    document.getElementById("trophyForm").style.display = "block";
    document.getElementById("removeTrophyForm").style.display = "none";
});

document.getElementById("newTrophyForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const competition = document.getElementById("competition").value;
    const year = document.getElementById("year").value;
    const array_years=year.split(",").map(s => s.trim());
    //divido la striga per carattere ",", e in ognuna rimuovo eventuali spazi indesiderati su ogni elemento s dell'array
    const tableBody = document.getElementById("trophyTable").getElementsByTagName('tbody')[0];
    //addNewRow(tableBody ,competition , year); //aggiunta della nuova riga
    addNewRowArray(tableBody,competition,array_years);
    //nascondo nuovamente il form e ne azzero i campi
    document.getElementById("trophyForm").style.display = "none";
    document.getElementById("newTrophyForm").reset();
});

function addNewRow(tableBody , name, year){
    const newRow = tableBody.insertRow();

    const nameCell = newRow.insertCell(0);
    const yearCell = newRow.insertCell(1);
    const timesCell= newRow.insertCell(2);

    nameCell.textContent = name;
    yearCell.innerHTML = `<span class="badge bg-primary">${year}</span>`;
    timesCell.textContent="1";
    // console.log('Adding new Row')
}
function addNewRowArray(tableBody, name, years){
    const newRow = tableBody.insertRow();
    const nameCell = newRow.insertCell(0);
    const yearCell = newRow.insertCell(1);
    const timesCell= newRow.insertCell(2);

    nameCell.textContent = name;
    yearCell.innerHTML= years.map( year => `<span class="badge bg-primary">${year}</span> `).join("")
    //questa funzione crea un array contenente tanti span class con il rispettivo valore di year, e poi le unisce in un'unica
    //stringa via join per passarla a innerHTML
    //yearCell.innerHTML = `<span class="badge bg-primary">${years[0]}</span>`;
    volteVittoria=years.length
    timesCell.textContent=volteVittoria;
}
