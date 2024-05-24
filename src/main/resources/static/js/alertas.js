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
    const urlEliminar = element.getAttribute('data-url-eliminar');
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
    .then((OK) => {
        if (OK.isConfirmed) {
            $.ajax({
                url: urlEliminar + codigo,
                success: function() {
                    
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
                    
                    setTimeout(function() {
                       location.href = urlListar;
                    }, 5000);
                },
                error: function(err) {
                    console.error(err);
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
                }
            });
        }
    });
}