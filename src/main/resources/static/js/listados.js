function cargarDetallesLugar(codigo) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "./consulta_verDetalles?codigo=" + codigo, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
               var response = JSON.parse(xhr.responseText);
               var detallesHtml = `
                    <h2>DETALLES DEL LUGAR</h2>
                    <h4>${response.nombre}</h4>
                    <img src="/imagenes/${response.imagen}" alt="${response.imagen}">
                    <p><strong>Código:</strong> <span>${response.codigo}</span></p>
                    <p><strong>Descripción:</strong> <span>${response.descripcion}</span></p>
                    <p><strong>Categoría:</strong> <span>${response.categoria}</span></p>
                    <p><strong>Días abiertos:</strong> <span>${response.dias_horario}</span></p>
                    <p><strong>Horario de apertura:</strong> <span>${response.hora_apertura}</span></p>
                    <p><strong>Horario de cierre:</strong> <span>${response.hora_cierre}</span></p>
                    <p><strong>Precio de la entrada:</strong> <span>${response.precio_entrada}</span></p>
                    <p><strong>Provincia:</strong> <span>${response.provincia}</span></p>
                    <p><strong>Cantón:</strong> <span>${response.canton}</span></p>
                    <p><strong>Distrito:</strong> <span>${response.distrito}</span></p>
                    <p><strong>Calidad de la recepción telefónica:</strong> <span>${response.calidad_recepcion_telefonica}</span></p>
                `;
                document.getElementById("verDetalles").innerHTML = detallesHtml;
            } else {
                console.error("Error al cargar los detalles del lugar. Estado de la solicitud:", xhr.status);
            }
        }
    };
    xhr.onerror = function() {
        console.error("Error de red al cargar los detalles del lugar.");
    };
    xhr.send();
}


function cargarDetallesArticulo(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET","./verDetallesArticulo?idArticulo=" + id, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
               var articulo = JSON.parse(xhr.responseText);
               var detallesHtml = `
                <h2>DETALLES DEL ARTICULO</h2>
                <p><strong>Identificador:</strong> <span>${articulo.identificador}</span></p>
                <p><strong>Título:</strong> <span>${articulo.titulo}</span></p>
                <p><strong>Tema:</strong> <span>${articulo.tema}</span></p>
                <p><strong>Descripción:</strong> <span>${articulo.descripcion}</span></p>
                <p><strong>Nombre del Autor:</strong> <span>${articulo.nombreAutor}</span></p>
                <p><strong>Fecha:</strong> <span>${articulo.fecha}</span></p>
                <p><strong>Acerca del Autor:</strong> <span>${articulo.acercaDelAutor}</span></p>
                <p><strong>Texto del Artículo:</strong> <span>${articulo.textoArticulo}</span></p>
                `;
                document.getElementById("verDetallesArticulo").innerHTML = detallesHtml;
            } else {
                console.error("Error al cargar los detalles del articulo. Estado de la solicitud:", xhr.status);
            }
        }
    };
    xhr.onerror = function() {
        console.error("Error de red al cargar los detalles del articulo.");
    };
    xhr.send();
}


