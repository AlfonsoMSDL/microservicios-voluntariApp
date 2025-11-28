// Establecer fecha mínima como hoy
const hoy = new Date().toISOString().split('T')[0];
document.getElementById('fechaInicio').min = hoy;
document.getElementById('fechaFin').min = hoy;

// Validar que fecha fin sea después de fecha inicio
document.getElementById('fechaInicio').addEventListener('change', function() {
    document.getElementById('fechaFin').min = this.value;
});

function volver() {

    Swal.fire({
        title: "¿Estás seguro/a?",
        text: "Los cambios no guardados se perderán.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, salir"
    }).then((result) => {
        if (result.isConfirmed) {
            window.history.back();
        }
    });
}

function publicarProyecto(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);

    // Validación de descripción mínima
    const descripcion = formData.get('descripcion');
    if (descripcion.length < 50) {
        Swal.fire("Error", "La descripción debe tener al menos 50 caracteres", "error");
        return;
        
        
    }

    // Validación de fechas
    const fechaInicio = new Date(formData.get('fechaInicio'));
    const fechaFin = new Date(formData.get('fechaFin'));

    if (fechaFin <= fechaInicio) {
        Swal.fire("Error", "La fecha de fin debe ser posterior a la fecha de inicio", "error");

        return;

    }



    // Deshabilitar el botón mientras se envía
    const btnSubmit = form.querySelector('button[type="submit"]');
    const originalText = btnSubmit.textContent;
    btnSubmit.disabled = true;
    btnSubmit.textContent = 'Publicando...';

    // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    formData.append('idOrganizacion', usuarioLogin.id);

    const urlEncodedData = new URLSearchParams(formData).toString();
    console.log('Datos del formulario:', urlEncodedData);

    // Enviar datos al backend como application/x-www-form-urlencoded
    fetch('/proyectos-service/proyectos', { // Cambia la URL a tu endpoint
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: urlEncodedData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(data => {
            console.log('Respuesta del servidor:', data);

            // Verificar si se publicó correctamente
            if (data.status == "success" ) {
                // Mostrar mensaje de éxito
                const successMsg = document.getElementById('successMessage');
                successMsg.textContent = data.mensaje || '✓ Proyecto publicado exitosamente';
                successMsg.classList.add('show');

                // Limpiar formulario
                form.reset();

                // Ocultar mensaje después de 3 segundos y redirigir
                setTimeout(() => {
                    successMsg.classList.remove('show');
                    // Opcional: redirigir a la lista de proyectos
                    // window.location.href = '/proyectos';
                }, 3000);
            } else {
                // Error reportado por el servidor
                Swal.fire("Error", data.mensaje || "Error al publicar el proyecto", "error");
                
            }
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire("Error", "Error al publicar el proyecto. Por favor intenta nuevamente.", "error");
            
        })
        .finally(() => {
            // Rehabilitar el botón
            btnSubmit.disabled = false;
            btnSubmit.textContent = originalText;
        });


}

// Contador de caracteres para descripción
document.getElementById('descripcion').addEventListener('input', function() {
    const length = this.value.length;
    const helperText = this.nextElementSibling;

    if (length < 50) {
        helperText.textContent = `Faltan ${50 - length} caracteres`;
        helperText.style.color = '#e53e3e';
    } else {
        helperText.textContent = `${length} caracteres`;
        helperText.style.color = '#38a169';
    }
});


document.addEventListener("DOMContentLoaded",() => {
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    //Mostrar los tipos de organizacion al iniciar la pagina
    fetch('/proyectos-service/proyectos?action=getCategorias')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del servidor:', data);

        const select = document.getElementById('categoria');
        data.forEach(categoria => {
            const option = document.createElement('option');
            option.value = categoria.id;
            option.textContent = categoria.nombre;
            select.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error:', error);
        Swal.fire("Error", "Error al conectar con el servidor. Por favor intenta nuevamente.", "error");
        
    })


});