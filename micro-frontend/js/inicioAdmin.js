document.addEventListener("DOMContentLoaded", () => {

    const btnOrganizaciones = document.getElementById('btn-organizaciones');
    const btnVoluntarios = document.getElementById('btn-voluntarios');
    const tablaDatos = document.getElementById('tabla-datos');

    // ===== Eventos =====
    btnOrganizaciones.addEventListener('click', () => {
        cargarDatos("organizaciones", "/usuarios-service/organizaciones?action=getAllOrganizaciones");
    });

    btnVoluntarios.addEventListener('click', () => {
        cargarDatos("voluntarios", "/usuarios-service/voluntarios?action=getAllVoluntarios");
    });

    // ========================================================
    //                FUNCIÓN GENERAL fetch()
    // ========================================================
    function cargarDatos(tipo, url) {
        tablaDatos.innerHTML = `<p>Cargando ${tipo}...</p>`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error en la respuesta del servidor");
                }
                return response.json();
            })
            .then(data => {
                console.log(`Datos de ${tipo}:`, data);

                if (tipo === "organizaciones") {
                    renderOrganizaciones(data);
                } else {
                    renderVoluntarios(data);
                }
            })
            .catch(error => {
                console.error(error);
                tablaDatos.innerHTML = `
                    <p style="color:red;">Error al cargar ${tipo}: ${error.message}</p>
                `;
            });
    }

    // ========================================================
    //              TABLA: ORGANIZACIONES
    // ========================================================
    function renderOrganizaciones(lista) {
        if (!lista.length) {
            tablaDatos.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">🏢</div>
                    <p>No hay organizaciones registradas.</p>
                </div>
            `;
            return;
        }

        tablaDatos.innerHTML = `
            <table class="tabla-admin">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Tipo Organización</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    ${lista.map(org => `
                        <tr>
                            <td>${org.id}</td>
                            <td>${org.nombre}</td>
                            <td>${org.correo}</td>
                            <td>${org.telefono}</td>
                            <td>${org.tipoOrganizacion?.nombre || "No especificado"}</td>
                            <td>
                                
                                <button class="boton-accion" onclick="eliminarOrganizacion(${org.id})">Eliminar</button>
                            </td>
                        </tr>
                    `).join("")}
                </tbody>
            </table>
        `;
    }

    // ========================================================
    //                TABLA: VOLUNTARIOS
    // ========================================================
    function renderVoluntarios(lista) {
        if (!lista.length) {
            tablaDatos.innerHTML = `
                <div class="empty-state">
                    <div class="empty-state-icon">🙋</div>
                    <p>No hay voluntarios registrados.</p>
                </div>
            `;
            return;
        }

        tablaDatos.innerHTML = `
            <table class="tabla-admin">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre completo</th>
                        <th>Usuario</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Experiencia</th>
                        <th>Disponibilidad</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    ${lista.map(v => `
                        <tr>
                            <td>${v.id}</td>
                            <td>${v.nombre} ${v.apellido}</td>
                            <td>${v.nombreUsuario}</td>
                            <td>${v.correo}</td>
                            <td>${v.telefono}</td>
                            <td>${v.experiencia || "N/A"}</td>
                            <td>${v.disponibilidad || "N/A"}</td>
                            <td>
                                <button class="boton-accion" onclick="eliminarVoluntario(${v.id})">Eliminar</button>
                            </td>
                        </tr>
                    `).join("")}
                </tbody>
            </table>
        `;
    }

});

// ========================================================
//      Funciones globales para los botones de acción
// ========================================================
function eliminarOrganizacion(id) {
    if (!confirm("¿Seguro que deseas eliminar esta organización?")) {
        return;
    }

    fetch(`/usuarios-service/organizaciones`, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `action=delete&id=${id}`
    })
        .then(res => res.json())
        .then(data => {
            alert(data.mensaje);

            // Recargar tabla
            document.getElementById('btn-organizaciones').click();
        })
        .catch(err => {
            console.error(err);
            alert("Error eliminando la organización.");
        });
}

function eliminarVoluntario(id) {
    Swal.fire({
        title: "¿Estás seguro/a?",
        text: "¡Ojo, se eliminarán las inscripciones y participationes asociadas!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, eliminalo"
    }).then((result) => {
        if (result.isConfirmed) {
            // Si el usuario confirma, proceder con la eliminación
            fetch(`/usuarios-service/voluntarios`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `action=delete&id=${id}`
            })
                .then(res => res.json())
                .then(data => {
                    const tipo = data.status;
                    const mensaje = data.status == 'success' ? 'Voluntario eliminado exitosamente' : 'Error eliminando voluntario';

                    Swal.fire({
                        position: "center-center",
                        icon: tipo,
                        title: mensaje,
                        showConfirmButton: false,
                        timer: 1500
                    }).then(() => {
                        // Recargar tabla
                        document.getElementById('btn-voluntarios').click();
                    });


                })
                .catch(err => {
                    console.error(err);
                    Swal.fire({
                        position: "center-center",
                        icon: "error",
                        title: "Error eliminando el voluntario.",
                        showConfirmButton: false,
                        timer: 1500
                    });
                });
        }
    })


}

