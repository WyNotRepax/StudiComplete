curr = "0";
bsSupercontainerInner = document.getElementById("bs-supercontainer-inner");

$(".bs-horscrolltrigger").on("click", (click) => {
    var from = curr;
    var to = click.target.parentNode.id;
    if (from != to) {
        for (var i = 0; i < bsSupercontainerInner.children.length; i++) {
            bsSupercontainerInner.children[i].classList.remove("bs-supercontainer-item-inactive")
            bsSupercontainerInner.children[i].classList.remove("bs-supercontainer-item-active")
            if (parseInt(to) != i) {
                bsSupercontainerInner.children[i].classList.add("bs-supercontainer-item-inactive")
            } else {
                bsSupercontainerInner.children[i].classList.add("bs-supercontainer-item-active")
            }
        }

        for (var i1 = 0; i1 < 3; i1++) {
            for (var i2 = 0; i2 < 3; i2++) {
                bsSupercontainerInner.classList.remove("bs-horizontalscroll" + i1.toString() + i2.toString())
            }
        }
        bsSupercontainerInner.classList.add("bs-horizontalscroll" + from + to)

        for (var node of click.target.parentNode.parentNode.children) {
            node.classList.remove("active");
        }
        click.target.parentNode.classList.add("active");
    }

    if (!(from == "0" && to == "0")) {
        window.scrollTo({
            top: 0,
            left: 0,
            behavior: "smooth"
        });
    }
    curr = to;
})

$(".bs-vertscrolltrigger").on("click", (click) => {
    var pos = $("#" + click.target.href.split("#")[1]).first().position()
    window.scrollTo({
        top: pos.top,
        left: pos.left,
        behavior: "smooth"
    })
    return false;
})