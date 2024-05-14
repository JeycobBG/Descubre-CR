

function eliminar(id) {
    Swal.fire({
        title: "Esta seguro de eliminar?",
        text: "No se podrá revertir!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí, quiero eliminarlo!",
        cancelButtonText: "Cancelar"
    })
    .then((OK) => {
        if (OK.isConfirmed) {
            $.ajax({
                url:"/ubicacion/eliminar/"+id,
                success: function(res){
                    console.log(res);
                }
            });
            Swal.fire({
                title: "Eliminado!",
                text: "La ubicación ha sido eliminada.",
                icon: "success"
            }).then((ok)=>{
                if(ok.isConfirmed){
                    location.href="/ubicacion/listarAdmin";
                }
            });
        } else {
            Swal.fire("Ubicacion guardada!","","info");
        }
    });
}

function changePageSize(currentPage){
    const pageSize = document.getElementById("pageSize").value;
    window.location.href= "?page=" + currentPage + "&size=" + pageSize;
}

function verUbicacion(id){
    location.href="/ubicacion/"+id;
}

function editar(id) {
    location.href="/ubicacion/editar/"+id;
}

function formularioUbicacion(){
    window.location='/ubicacion/formularioUbicacion';
}

function regresarInicio(){
    window.location='/usuarios/login';
}
function regresarListar(){
    window.location='/ubicacion/listarAdmin';
}