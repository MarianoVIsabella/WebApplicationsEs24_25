document.getElementById("removeTrophyButton").addEventListener("click", function() {
    document.getElementById("removeTrophyForm").style.display = "block";
    document.getElementById("trophyForm").style.display = "none";
});
function removeTrophyByNumber(event) {
    event.preventDefault();
    const numberToEliminate = parseInt(document.getElementById("trophyNumber").value)+1;
    const rows =document.getElementById('trophyTable').querySelectorAll('tr');
    if (numberToEliminate > 0 && numberToEliminate <= rows.length) {
        rows[numberToEliminate-1].remove();
        document.getElementById("removeTrophyForm").style.display = "none"; // Nasconde il form
        document.getElementById("removeTrophyByNumberForm").reset(); // Resetta il campo del form
    } else {
        alert("Numero competizione non valido!"); // Avviso per numero non valido
    }
}
