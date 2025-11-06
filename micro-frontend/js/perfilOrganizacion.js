document.getElementById('profileForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    // Obtener los valores del formulario
    const idOrganizacion = document.getElementById('idOrganizacion').value;
    const nombreOrganizacion = document.getElementById('nombreOrganizacion').value;
    const nombreUsuario = document.getElementById('nombreUsuario').value;
    const correo = document.getElementById('correo').value;
    const telefono = document.getElementById('telefono').value;
    const tipo = document.getElementById('tipoOrganizacion').value;
    const descripcion = document.getElementById('descripcion').value;
    const clave = document.getElementById('clave').value;

    // Validaciones básicas
    if (!nombreOrganizacion || !nombreUsuario || !correo || !telefono || !tipo) {
        alert("Por favor completa todos los campos obligatorios.");
        return;
    }

    // Crear los parámetros del formulario
    const params = new URLSearchParams();
    params.append("idOrganizacion",idOrganizacion);
    params.append("action", "update");
    params.append("nombreOrganizacion", nombreOrganizacion);
    params.append("nombreUsuario", nombreUsuario);
    params.append("correo", correo);
    params.append("telefono", telefono);
    params.append("tipo", tipo);
    params.append("descripcion", descripcion);

    if (clave) {
        params.append("clave", clave);
    }

    try {
        // Enviar los datos al backend
        const response = await fetch("http://localhost:8181/voluntariApp/organizaciones", {
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
        console.log("Respuesta del servidor:", data);
        alert("Perfil actualizado exitosamente.");

        // Puedes redirigir o recargar
        // window.location.href = "dashboardOrganizacion.jsp";
    } catch (error) {
        console.error("Error al actualizar la organización:", error);
        alert("Error al actualizar la organización. Por favor intenta nuevamente.");
    }
});

function cancelar() {
    if (confirm('¿Estás seguro de que deseas cancelar los cambios?')) {
        window.history.back();
    }
}


document.addEventListener("DOMContentLoaded",() => {
    //Mostrar los tipos de organizacion al iniciar la pagina
    fetch('http://localhost:8181/voluntariApp/organizaciones?action=getTipos')
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del servidor:', data);

        const select = document.getElementById('tipoOrganizacion');
        data.forEach(tipo => {
            const option = document.createElement('option');
            option.value = tipo.id;
            option.textContent = tipo.nombre;
            select.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al conectar con el servidor: ' + error.message);
    })

    //Mostrar los datos actuales del perfil
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    fetch('http://localhost:8181/voluntariApp/organizaciones?action=getById&idOrganizacion=' + usuarioLogin.id)
    .then(response => {
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del servidor:', data);

        const idOrganizacion = document.getElementById('idOrganizacion');
        const nombreOrganizacion = document.getElementById('nombreOrganizacion');
        const nombreUsuario = document.getElementById('nombreUsuario');
        const correo = document.getElementById('correo');
        const telefono = document.getElementById('telefono');
        const tipo = document.getElementById('tipoOrganizacion');
        const descripcion = document.getElementById('descripcion');
        const clave = document.getElementById('clave');

        idOrganizacion.value = data.id;
        nombreOrganizacion.value = data.nombre;
        nombreUsuario.value = data.nombreUsuario;
        correo.value = data.correo;
        telefono.value = data.telefono;
        tipo.value = data.tipoOrganizacion.id;
        descripcion.value = data.descripcion;
        clave.value = data.clave;
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al conectar con el servidor: ' + error.message);
    })

})
