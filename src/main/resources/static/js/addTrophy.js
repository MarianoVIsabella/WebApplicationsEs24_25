document.getElementById("addTrophyButton").addEventListener("click", function() {
    document.getElementById("trophyForm").style.display = "block";
    document.getElementById("removeTrophyForm").style.display = "none";
});

document.getElementById("newTrophyForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const competition = document.getElementById("competition").value;
    const year = document.getElementById("year").value;
    const tableBody = document.getElementById("trophyTable").getElementsByTagName('tbody')[0];
    addNewRow(tableBody ,competition , year); //aggiunta della nuova riga
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
