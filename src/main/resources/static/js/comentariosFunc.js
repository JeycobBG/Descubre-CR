document.addEventListener("DOMContentLoaded", function() {
    
    document.getElementById("mostrarFormulario").addEventListener("click", function() {
        var formulario = document.getElementById("formRegistrar");
        if (formulario.style.display === "none") {
            formulario.style.display = "block";
        } else {
            formulario.style.display = "none";
        }
    });
    
    
});

function manejarLikes() {
    var likesSpan = document.querySelector(".likesCount");
    var likes = parseInt(likesSpan.innerText);
    if (likesSpan.classList.contains("liked")) {
        likes--;
        likesSpan.classList.remove("liked");
    } else {
        likes++;
        likesSpan.classList.add("liked");
    }
    likesSpan.innerText = likes;
}

function manejarDislikes() {
    var dislikesSpan = document.querySelector(".dislikesCount");
    var dislikes = parseInt(dislikesSpan.innerText);
    if (dislikesSpan.classList.contains("disliked")) {
        dislikes--;
        dislikesSpan.classList.remove("disliked");
    } else {
        dislikes++;
        dislikesSpan.classList.add("disliked");
    }
    dislikesSpan.innerText = dislikes;
}


