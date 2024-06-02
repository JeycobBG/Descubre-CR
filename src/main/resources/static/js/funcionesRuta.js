function cantidadLugares(){

        // Función para agregar campos para la cantidad de lugares especificada
        document.getElementById('cantidadLugares').addEventListener('change', function() {
            var cantidad = parseInt(this.value);
            var lugaresContainer = document.getElementById('lugaresContainer');
            lugaresContainer.innerHTML = ''; // Limpiar el contenedor

            for (var i = 0; i < cantidad; i++) {
                var label = document.createElement('label');
                label.textContent = 'Lugar ' + (i + 1) + ':';
                lugaresContainer.appendChild(label);

                var input = document.createElement('input');
                input.type = 'text';
                input.name = 'lugar' + (i + 1);
                lugaresContainer.appendChild(input);

                lugaresContainer.appendChild(document.createElement('br'));
            }
        });
}