document.getElementById('profileForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    // Obtener los valores del formulario
    const idVoluntario = document.getElementById('idVoluntario').value;
    const nombre = document.getElementById('nombre').value;
    const apellido = document.getElementById('apellido').value;
    const nombreUsuario = document.getElementById('nombreUsuario').value;
    const correo = document.getElementById('correo').value;
    const telefono = document.getElementById('telefono').value;
    const habilidades = document.getElementById('habilidades').value;
    const experiencia = document.getElementById('experiencia').value;
    const disponibilidad = document.getElementById('disponibilidad').value;
    const areasInteres = document.getElementById('areas_interes').value;
    const clave = document.getElementById('clave').value;

    // Validación básica
    if (!nombre || !apellido || !nombreUsuario || !correo || !telefono) {
        alert("⚠️ Por favor completa todos los campos obligatorios.");
        return;
    }

    // Crear los parámetros a enviar
    const params = new URLSearchParams();
    params.append("action", "update");
    params.append("idVoluntario", idVoluntario);
    params.append("nombre", nombre);
    params.append("apellido", apellido);
    params.append("nombreUsuario", nombreUsuario);
    params.append("correo", correo);
    params.append("telefono", telefono);
    params.append("habilidades", habilidades);
    params.append("experiencia", experiencia);
    params.append("disponibilidad", disponibilidad);
    params.append("areas_interes", areasInteres);

    if (clave) {
        params.append("clave", clave);
    }

    try {
        // Enviar los datos al backend
        const response = await fetch("http://localhost:8181/voluntariApp/voluntarios", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params.toString()
        });

        if (!response.ok) {
            throw new Error("Error en la petición: " + response.status);
        }

        const data = await response.text();
        console.log("✅ Respuesta del servidor:", data);
        alert("✅ Perfil actualizado exitosamente.");

        // Opcional: redirigir o recargar
        // window.location.href = "dashboardVoluntario.jsp";
    } catch (error) {
        console.error("❌ Error al actualizar el perfil:", error);
        alert("Error al actualizar el perfil. Por favor intenta nuevamente.");
    }
});

function cancelar() {
    if (confirm('¿Estás seguro de que deseas cancelar los cambios?')) {
        window.history.back();
    }
}

document.addEventListener("DOMContentLoaded",() => {
    
    //Mostrar los datos actuales del perfil
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    fetch('http://localhost:8181/voluntariApp/voluntarios?action=getById&idVoluntario=' + usuarioLogin.id)
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del servidor:', data);

        const idVoluntario = document.getElementById('idVoluntario');
        const nombreVoluntario = document.getElementById('nombre');
        const apellidoVoluntario = document.getElementById('apellido');
        const nombreUsuario = document.getElementById('nombreUsuario');
        const correo = document.getElementById('correo');
        const telefono = document.getElementById('telefono');
        
        const clave = document.getElementById('clave');
        const habilidades= document.getElementById('habilidades');
        const experiencia = document.getElementById('experiencia');
        const disponibilidad = document.getElementById('disponibilidad');
        const areasInteres = document.getElementById('areas_interes');

        idVoluntario.value = data.id;
        nombreVoluntario.value = data.nombre;
        nombreUsuario.value = data.nombreUsuario;
        correo.value = data.correo;
        telefono.value = data.telefono;
        apellidoVoluntario.value = data.apellido;
        habilidades.value = data.habilidades;
        experiencia.value = data.experiencia;
        disponibilidad.value = data.disponibilidad;
        areasInteres.value = data.areas_interes;
        clave.value = data.clave;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al conectar con el servidor: ' + error.message);
    })

})