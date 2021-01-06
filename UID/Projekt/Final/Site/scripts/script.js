// Macht den entsprechenden div-Container sichtbar, sobald auf ein Bild geklickt wird.
$(".bs-projekt-image").on("click", (click) => {
    click.target.parentNode.parentNode.children[2].style.display = "block";
})

// Macht den entsprechenden div-Container sichtbar, sobald auf einen Titel geklickt wird.
$(".bs-projekt-title").on("click", (click) => {
    click.target.parentNode.parentNode.parentNode.parentNode.parentNode.children[2].style.display = "block";
    return false;
})

// Schließt all göffneten div-Container wenn außerhalb geklickt wird.
$(".bs-preview-container").on("click", (click) => {
    if (click.target.classList.contains("bs-preview-container")) {
        $(".bs-preview-container").css("display", "none")
    }
})

// Funktionalität für die Stilauswahl in Aufgabe 4
$("input[name='styleselector']").on("click", (click) => {
    for (var iframe of $("iframe")) {
        if (iframe.id.match("(duester|romantisch|eigen)\-iframe")) {
            if (iframe.id == click.target.id + "-iframe") {
                iframe.style.display = "block";
            } else {
                iframe.style.display = "none";
            }
        }
    }
})

// Funktionalität des "zur Seite" Buttons
$(".bs-button").on("click", (click) => {
    for (var elem of click.target.nextElementSibling.children) {
        if (elem.style.display != "none") {
            window.open(elem.src)
        }
    }
})

// Funktionalität des "x" Buttons
$(".bs-button-close").on("click", (click) => {
    $(".bs-preview-container").css("display", "none")
})