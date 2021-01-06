slideshows = document.getElementsByClassName("slideshow");
for (var i = 0; i < slideshows.length; i++) {
  var curr = slideshows[i];
  var slider = curr.children[0];
  slider.appendChild(slider.children[0].cloneNode());
}
