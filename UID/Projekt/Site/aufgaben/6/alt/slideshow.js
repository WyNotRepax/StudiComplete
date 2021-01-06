class Slideshow {
  constructor(slideshowContainer) {
    this.pictures = [];
    for (var i = slideshowContainer.children.length - 1; i >= 0; i--) {
      this.pictures.push(slideshowContainer.children[i]);
    }
    this.pictures.unshift(this.pictures.pop());
    this.pictures[0].classList.add("picIn");
    this.pictures[1].classList.add("picOut");
  }

  iterate() {
    this.pictures[0].classList.remove("picIn");
    this.pictures[1].classList.remove("picOut");
    this.pictures.unshift(this.pictures.pop());
    this.pictures[0].classList.add("picIn");
    this.pictures[1].classList.add("picOut");
  }
}

slideshowContainers = document.getElementsByClassName("slideshow");
slideshows = [];
for (var i = 0; i < slideshowContainers.length; i++) {
  slideshows.push(new Slideshow(slideshowContainers[i]));
}

setInterval(() => {
  for (var i = 0; i < slideshows.length; i++) {
    slideshows[i].iterate();
  }
}, 100);
