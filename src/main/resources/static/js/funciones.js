// Function to toggle password visibility
function togglePassword() {
    var passwordField = document.getElementById("password");
    if (passwordField.type === "password") {
        passwordField.type = "text";
    } else {
        passwordField.type = "password";
    }
}

// Function to set max date for date of birth field
function setMaxDate() {
    var today = new Date();
    today.setDate(today.getDate() - 1); // Set to yesterday

    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    var yyyy = today.getFullYear();

    var maxDate = yyyy + '-' + mm + '-' + dd;
    document.getElementById('fechaNacimiento').setAttribute('max', maxDate);
}

function validateForm() {
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

    if (!namePattern.test(nombre) || !namePattern.test(apellido) || !namePattern.test(nacionalidad)) {
        alert("Nombre, apellido y nacionalidad solo pueden contener letras.");
        return false;
    }

    if (!cedulaPattern.test(cedula)) {
        alert("Cédula debe tener exactamente 9 números.");
        return false;
    }

    if (!telefonoPattern.test(telefono)) {
        alert("Teléfono debe tener exactamente 8 números.");
        return false;
    }

    if (!emailPattern.test(correo)) {
        alert("Por favor, introduce un correo electrónico válido.");
        return false;
    }

    if (fechaNacimiento > maxDate) {
        alert("La fecha de nacimiento no puede ser hoy o una fecha futura.");
        return false;
    }

    return true;
}
