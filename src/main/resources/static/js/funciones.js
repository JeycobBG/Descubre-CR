// Function to toggle password visibility
function togglePassword() {
    var passwordField = document.getElementById("password");
    if (passwordField.type === "password") {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
}

function setMaxDate() {
    var today = new Date();
    today.setDate(today.getDate() - 1); 

    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); 
    var yyyy = today.getFullYear();

    var maxDate = yyyy + '-' + mm + '-' + dd;
    document.getElementById('fechaNacimiento').setAttribute('max', maxDate);
}

function validateForm(event) {
    var namePattern = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;
    var cedulaPattern = /^[0-9]{9}$/;
    var telefonoPattern = /^[0-9]{8}$/;
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    var nombre = document.forms["userForm"]["nombre"].value;
    var apellido = document.forms["userForm"]["apellido"].value;
    var nacionalidad = document.forms["userForm"]["nacionalidad"].value;
    var cedula = document.forms["userForm"]["cedula"].value;
    var telefono = document.forms["userForm"]["telefono"].value;
    var correo = document.forms["userForm"]["correo"].value;
    var fechaNacimiento = document.forms["userForm"]["fechaNacimiento"].value;
    var maxDate = document.getElementById('fechaNacimiento').getAttribute('max');
    var password = document.forms["userForm"]["password"].value;

    var errorMessages = document.getElementById('errorMessages');
    errorMessages.innerHTML = '';

    var valid = true;

    if (!namePattern.test(nombre) || !namePattern.test(apellido) || !namePattern.test(nacionalidad)) {
        errorMessages.innerHTML += "Nombre, apellido y nacionalidad solo pueden contener letras.<br>";
        valid = false;
    }

    if (!cedulaPattern.test(cedula)) {
        errorMessages.innerHTML += "Cédula debe tener exactamente 9 números.<br>";
        valid = false;
    }

    if (!telefonoPattern.test(telefono)) {
        errorMessages.innerHTML += "Teléfono debe tener exactamente 8 números.<br>";
        valid = false;
    }

    if (!emailPattern.test(correo)) {
        errorMessages.innerHTML += "Por favor, introduce un correo electrónico válido.(ejem:ejem@ejem.com)<br>";
        valid = false;
    }

    if (fechaNacimiento > maxDate) {
        errorMessages.innerHTML += "La fecha de nacimiento no puede ser hoy o una fecha futura.<br>";
        valid = false;
    }

    // Validación de contraseña
    if (password.length < 4) {
        errorMessages.innerHTML += "La contraseña debe tener al menos 4 caracteres.<br>";
        valid = false;
    }

    if (!valid) {
        event.preventDefault();
    }

    return valid;
}
