document.addEventListener('DOMContentLoaded', async function() {
    
    // Obtener toda la información de provincias, cantones y distritos de una vez
    const response = await fetch('https://territorios.fetchcr.com/todos');
    const data = await response.json();
    
    const lugarDataDiv = document.getElementById('datosLugar'); //si esto se carga, es porque es el de actualizar
    const ubicacionDataDiv = document.getElementById('datosUbicacion');
    
    const provinciaSelect = document.getElementById('provincia');
    const cantonSelect = document.getElementById('canton');
    const distritoSelect = document.getElementById('distrito');
    
    // Función para seleccionar la opción en un select según el label
    const seleccionarOptionPorLabel = (select, label) => {
      Array.from(select.options).forEach(option => {
         if (option.text === label) {
            option.selected = true;
         }
      });
   };
    
   cargarProvincias();
    
    if (lugarDataDiv) {
        const lugar = JSON.parse(lugarDataDiv.getAttribute('dataLugar'));
        console.log(lugar.dias_horario);
        var diasArray = lugar.dias_horario.split(", ");
        console.log(diasArray);
        
        diasArray.forEach(function(dia) {
            var checkbox = document.getElementById(dia.toLowerCase());
            if (checkbox) {
                checkbox.checked = true;
            }
        });
        const ubicacion = lugar.ubicacion;
        seleccionarOptionPorLabel(provinciaSelect, ubicacion.nombreProvincia);
        cargarCantones();
        seleccionarOptionPorLabel(cantonSelect, ubicacion.canton);
        cargarDistritos();
        seleccionarOptionPorLabel(distritoSelect, ubicacion.distrito);

    }else{
        cargarCantones();
        cargarDistritos();
    }
    
    if(ubicacionDataDiv){
       const ubicacion = JSON.parse(ubicacionDataDiv.getAttribute('dataUbicacion'));
       
       seleccionarOptionPorLabel(provinciaSelect, ubicacion.nombreProvincia);
       cargarCantones();
       seleccionarOptionPorLabel(cantonSelect, ubicacion.canton);
       cargarDistritos();
       seleccionarOptionPorLabel(distritoSelect, ubicacion.distrito);
   }
   
    // Manejar cambio de provincia
    provinciaSelect.addEventListener('change', function() {
        cargarCantones();
        cargarDistritos();
    });

    // Manejar cambio de canton 
    cantonSelect.addEventListener('change', cargarDistritos);
    
    function cargarProvincias(){
        data.provincias.forEach(provincia => {
            const option = document.createElement('option');
            option.value = provincia.value;
            option.text = provincia.label;
            provinciaSelect.appendChild(option);
        });
    };
    
    function cargarCantones(){
        cantonSelect.innerHTML = ''; // Limpiar opciones anteriores
        data.cantones[provinciaSelect.value].forEach(canton => {
            const option = document.createElement('option');
            option.value = canton.value;
            option.text = canton.label;
            cantonSelect.appendChild(option);
        });
    };
    
    function cargarDistritos(){
        const indiceDistrito = provinciaSelect.value + cantonSelect.value;
        const distritos = data.distritos[indiceDistrito];
        
        distritoSelect.innerHTML = ''; // Limpiar opciones anteriores
        distritos.forEach(distrito => {
            const option = document.createElement('option');
            option.value = distrito.value;
            option.text = distrito.label;
            distritoSelect.appendChild(option);
        });
    };
   
    // Obtener el formulario
    const formulario = document.getElementById('formRegistrar');

    // Agregar un event listener para el envío del formulario
        formulario.addEventListener('submit', function(event) {
            event.preventDefault(); // Evitar el envío del formulario por defecto

            if (provinciaSelect && cantonSelect && distritoSelect) {
                
                // Asignar los textos de las opciones seleccionadas a los campos del formulario
                provinciaSelect.options[provinciaSelect.selectedIndex].value = provinciaSelect.options[provinciaSelect.selectedIndex].textContent;
                cantonSelect.options[cantonSelect.selectedIndex].value = cantonSelect.options[cantonSelect.selectedIndex].textContent;
                distritoSelect.options[distritoSelect.selectedIndex].value = distritoSelect.options[distritoSelect.selectedIndex].textContent;
                
            } else {
                console.error('Los elementos no están disponibles en el DOM.');
            }

            formulario.submit();
        });

    
});