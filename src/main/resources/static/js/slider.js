document.addEventListener('DOMContentLoaded', async function(){
    const btnNextLugar = document.querySelector(".btnNextLugar");
    const btnPrevLugar = document.querySelector(".btnPrevLugar");
    const catalogoLugares = document.querySelector(".catalogoLugares");
    const sliderLugares = document.querySelector(".sliderLugares");

    let indexLugar = 0;

    function cargarLugares() {
        
        catalogoLugares.style.opacity = 0;
        setTimeout(() => {
            catalogoLugares.innerHTML = ""; 

            const startIndex = indexLugar;
            const endIndex = Math.min(indexLugar + 5, lugares.length);

            // Iterar sobre los lugares en el rango especificado y agregarlos al contenedor
            for (let i = startIndex; i < endIndex; i++) {
                const lugar = lugares[i];
                const lugarElement = document.createElement("li");
                lugarElement.classList.add("lugar");
                lugarElement.innerHTML = `
                    <div class="img-container">
                        <img src="/imagenes/${lugar.imagen}" alt="${lugar.imagen}">
                    </div>
                    <div class="categoria-container">
                        <p><span>${lugar.categoria}</span><a href="/lugares/consulta_individual?codigo=${lugar.codigo}">Ver más info</a></p>
                    </div>
                    <div class="info-container"> 
                        <h4>${lugar.nombre}</h4>
                        <p>${lugar.descripcion}</p>    
                    </div>`;
                catalogoLugares.appendChild(lugarElement);
            }

            // Mostrar nuevamente el contenedor con los cambios aplicados
            catalogoLugares.style.opacity = 1;
        }, 150); 
    }

    btnNextLugar.addEventListener("click", function () {
        if (indexLugar + 5 < lugares.length) {
            indexLugar++;
            cargarLugares();
        }
    });

    btnPrevLugar.addEventListener("click", function () {
        if (indexLugar > 0) {
            indexLugar--;
            cargarLugares();
        }
    });

    cargarLugares();

});
