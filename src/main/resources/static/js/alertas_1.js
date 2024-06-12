document.addEventListener("DOMContentLoaded", function() {
    var alertMessagesDiv = document.getElementById('alert-messages');

    var exito = alertMessagesDiv.getAttribute('data-exito');
    var error = alertMessagesDiv.getAttribute('data-error');

    if (exito) {
        Swal.fire({
            title: "¡HECHO!",
            text: exito,
            icon: 'success',
            timer: 5000,
            timerProgressBar: true,
            backdrop: false,
            toast: true,
            position: 'top-end',
            allowOutsideClick: false,
            allowEscapeKey: false,
            allowEnterKey: false,
            stopKeydownPropagation: false
        });
    }

    if (error) {
        Swal.fire({
            title: "¡ERROR!",
            text: error,
            icon: 'error',
            timer: 5000,
            timerProgressBar: true,
            backdrop: false,
            toast: true,
            position: 'top-end',
            allowOutsideClick: false,
            allowEscapeKey: false,
            allowEnterKey: false,
            stopKeydownPropagation: false
        });
    }
});

function eliminar(element) {
    const codigo = element.getAttribute('data-codigo');
    const urlEliminar = element.getAttribute('data-url-eliminar') + codigo;
    const urlListar = element.getAttribute('data-url-listar');
    
    Swal.fire({
        title: "¡CUIDADO!",
        text: "¿Confirma que desea eliminar el elemento?",
        icon: "warning",
        backdrop: false,
        toast: true,
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        stopKeydownPropagation: false,
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "¡Sí, quiero eliminarlo!",
        cancelButtonText: "Cancelar"
    })
    .then((result) => {
        if (result.isConfirmed) {
            fetch(urlEliminar, {
                method: 'GET' // O 'POST' si tu método de eliminación es POST
            })
            .then(response => {
                if (response.ok) {
                    Swal.fire({
                        title: "¡Eliminado!",
                        text: "El elemento ha sido eliminado.",
                        icon: "success",
                        timer: 5000,
                        timerProgressBar: true,
                        backdrop: false,
                        toast: true,
                        position: 'top-end',
                        allowOutsideClick: false,
                        allowEscapeKey: false,
                        allowEnterKey: false,
                        stopKeydownPropagation: false
                    });

                    setTimeout(() => {
                        location.href = urlListar;
                    }, 5000);
                } else {
                    throw new Error('Error en la eliminación');
                }
            })
            .catch(error => {
                console.error(error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al eliminar el elemento.",
                    icon: "error",
                    backdrop: false,
                    toast: true,
                    position: 'top-end',
                    allowOutsideClick: false,
                    allowEscapeKey: false,
                    allowEnterKey: false,
                    stopKeydownPropagation: false
                });
            });
        }
    });

    // Prevenir la acción predeterminada del enlace
    return false;
}
