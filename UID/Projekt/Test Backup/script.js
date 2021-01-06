itemHeight = 340;
navHeight = 56;

function bsOpen(obj) {
    obj.parentNode.parentNode.children[2].style.display = "block";
}

function bsClose(obj) {
    console.log(obj);
    obj.style.display = "none";
}

$(".bs-scrolltrigger").on("click", function (evt) {
    var num = 0;
    for(var i = 0; i < evt.target.parentNode.children.length; i++){
        if(evt.target.parentNode.children[i].innerText == evt.target.innerText){
            num = i;
            break;
        }
    }
    console.log(num)
    var y = (num * itemHeight) ;
    var options = {
        top: y,
        left: 0,
        behavior: "smooth"
    }
    console.log(options);
    window.scrollTo(options);
})