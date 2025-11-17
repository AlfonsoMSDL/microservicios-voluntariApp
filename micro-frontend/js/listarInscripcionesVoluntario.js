// =====================================================
//   OBTENER ID DEL VOLUNTARIO DESDE LOCALSTORAGE
// =====================================================
function getVoluntarioId() {
    const usuario = JSON.parse(localStorage.getItem("usuarioLogin"));
    return usuario?.id || null;
}

// =====================================================
//   CLASES PARA EL ESTADO
// =====================================================
function getEstadoClass(estado) {
    switch (estado) {
        case "En revisión":
            return "estado-tramite";

        case "Rechazada":
            return "estado-rechazado";

        case "Aprobada":
            return "estado-aceptada";

        default:
            return "estado-tramite";
    }
}

// =====================================================
//   FORMATEAR FECHA
// =====================================================
function formatearFecha(fecha) {
    const date = new Date(fecha);
    return date.toLocaleDateString("es-ES", { year: "numeric", month: "long", day: "numeric" });
}

// =====================================================
//   CANCELAR INSCRIPCIÓN (PETICIÓN AL BACK)
// =====================================================
async function cancelarInscripcion(idInscripcion) {
    const confirmar = confirm("¿Estás seguro de cancelar esta inscripción?");
    if (!confirmar) return;

    try {
        const data = new URLSearchParams();
        data.append("id", idInscripcion);

        fetch("/inscripciones-service/inscripciones?action=delete", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: data
        }).then(respuesta => {
            if (!respuesta.ok) throw new Error("Error en la cancelación");
            return respuesta.json();
        }).then(data => {
            if(data.status == "success"){
                alert("Inscripción cancelada exitosamente");
                obtenerInscripciones(); // refrescar lista
            }else{
                alert("Error cancelando inscripción");
            }
        })

    } catch (e) {
        console.error("❌ Error cancelando:", e);
        alert("Error cancelando inscripción");
    }
}

// =====================================================
//   RENDERIZAR INSCRIPCIONES
// =====================================================
function renderInscripciones(lista) {
    const container = document.getElementById("inscripcionesContainer");

    if (!lista || lista.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <div class="empty-state-icon">📋</div>
                <h3>No tienes inscripciones</h3>
                <p>Cuando te inscribas a un proyecto, aparecerá aquí.</p>
            </div>
        `;
        return;
    }

    container.innerHTML = lista.map(ins => `
        <div class="inscripcion-card">
            <div class="inscripcion-header">
                <div>
                    <div class="inscripcion-title">${ins.proyecto.nombre}</div>
                    <div class="inscripcion-org">${ins.proyecto.descripcion}</div>
                </div>

                <span class="estado ${getEstadoClass(ins.estadoInscripcion.nombre)}">
                    ${ins.estadoInscripcion.nombre}
                </span>
            </div>

            <div class="inscripcion-info">
                <div class="info-item">
                    <span class="info-label">Fecha de inscripción:</span>
                    <span>${formatearFecha(ins.fechaInscripcion)}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">Motivación:</span>
                    <span>${ins.motivacion}</span>
                </div>
            </div>

            ${ins.estadoInscripcion.nombre.toLowerCase() === "en revisión" ?
                `<button class="btn-cancelar" onclick="cancelarInscripcion(${ins.id})">Cancelar</button>` 
                : ""
            }
        </div>
    `).join("");
}

// =====================================================
//   OBTENER INSCRIPCIONES DEL BACKEND
// =====================================================
async function obtenerInscripciones() {
    const voluntarioId = getVoluntarioId();

    if (!voluntarioId) {
        alert("No se encontró el voluntario. Inicia sesión nuevamente.");
        return;
    }

    try {
        const url = `/inscripciones-service/inscripciones?action=getInscripcionesByVoluntario&idVoluntario=${voluntarioId}`;

        const respuesta = await fetch(url);
        if (!respuesta.ok) throw new Error("Error obteniendo inscripciones");

        const lista = await respuesta.json();
        renderInscripciones(lista);

    } catch (e) {
        console.error("❌ Error cargando inscripciones:", e);
        alert("Error cargando las inscripciones");
    }
}

// =====================================================
//   VOLVER ATRÁS
// =====================================================
function volverAtras() {
    window.history.back();
}

// =====================================================
//   INICIALIZAR
// =====================================================
document.addEventListener("DOMContentLoaded", obtenerInscripciones);
