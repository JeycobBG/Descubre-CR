/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


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
                url:"/albums/eliminar/"+id,
                success: function(res){
                    console.log(res);
                }
            });
            Swal.fire({
                title: "Eliminado!",
                text: "El album ha sido eliminado.",
                icon: "success"
            }).then((ok)=>{
                if(ok.isConfirmed){
                    location.href="/albums/listarAdmin";
                }
            });
        } else {
            Swal.fire("Album guardado!","","info");
        }
    });
}

function eliminarImagen(id_imagen, id_album) {
    Swal.fire({
        title: "Esta seguro de eliminar?",
        text: "No se podrá revertir!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí, quiero eliminarla!",
        cancelButtonText: "Cancelar"
    })
    .then((OK) => {
        if (OK.isConfirmed) {
            $.ajax({
                url:"/albums/imagenes/eliminar/"+id_imagen+"/"+id_album,
                success: function(res){
                    console.log(res);
                }
            });
            Swal.fire({
                title: "Eliminada!",
                text: "La imagen ha sido eliminada.",
                icon: "success"
            }).then((ok)=>{
                if(ok.isConfirmed){
                    location.href="/albums/editar/"+id_album;
                }
            });
        } else {
            Swal.fire("Imagen guardada!","","info");
        }
    });
}

function changePageSize(currentPage){
    const pageSize = document.getElementById("pageSize").value;
    window.location.href= "?page=" + currentPage + "&size=" + pageSize;
}

function verAlbum(id){
    location.href="/albums/"+id;
}

function editar(id) {
    location.href="/albums/editar/"+id;
}

function formularioAlbum(){
    window.location='/albums/formularioAlbum';
}

function regresarInicio(){
    window.location='/usuarios/menuPrincipal';
}
function regresarListar(){
    window.location='/albums/listarAdmin';
}