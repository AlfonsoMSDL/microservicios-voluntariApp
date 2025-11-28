// ======================
// CARGAR DATOS DEL BACK
// ======================

document.addEventListener("DOMContentLoaded", () => {
  console.log("Iniciando aplicación...");

  const idProyecto = localStorage.getItem("idProyectoTemp");

  if (!idProyecto) {
    console.error("No se encontró idProyectoTemp en localStorage");
    return;
  }

  cargarDetalleProyecto(idProyecto);
  mostrarVista("detalle");


  // Cargar participantes reales desde el backend
  cargarParticipantes(idProyecto);

  // Cargar inscripciones reales desde el backend
  cargarInscritos(idProyecto);

  // Event listeners para tabs
  document.getElementById("btn-inscripciones").addEventListener("click", () => {
    mostrarVista("inscripciones");
  });

  document.getElementById("btn-participantes").addEventListener("click", () => {
    mostrarVista("participantes");
  });

  document.getElementById("btn-detalle").addEventListener("click", () => {
    mostrarVista("detalle");
  });


  // Modal
  document.getElementById("cerrarModal").onclick = cerrarModal;
  document.getElementById("cerrarBtn").onclick = cerrarModal;

  window.onclick = (e) => {
    if (e.target === document.getElementById("modal-detalles")) {
      cerrarModal();
    }
  };

  console.log("Aplicación iniciada correctamente");
});

// Datos locales de ejemplo (inscritos)
const inscritos = [];

// Participantes ahora serán llenados desde la API
const participantes = [];

// ================================================
// 🔵 FUNCIÓN PARA TRAER PARTICIPANTES DEL BACKEND
// ================================================
function cargarParticipantes(idProyecto) {
  const url = `/participaciones-service/participaciones?action=getParticipacionesByProyecto&idProyecto=${idProyecto}`;

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Error cargando participantes");
      }
      return response.json();
    })
    .then(data => {
      console.log("Participantes desde la API:", data);

      // Convertir JSON al formato que usa la UI
      participantes.length = 0;

      data.forEach(item => {
        participantes.push({
          id: item.id,
          nombre: `${item.voluntario.nombre} ${item.voluntario.apellido}`,
          email: item.voluntario.correo,
          telefono: item.voluntario.telefono,
          ciudad: item.proyecto.ubicacion
        });
      });

      renderLista(participantes, "lista-participantes", "participante");
    })
    .catch(err => {
      console.error(err);
      document.getElementById("lista-participantes").innerHTML = `
        <p style="color:red;">Error al cargar participantes</p>
      `;
    });
}


// ================================================
// 🔵 FUNCIÓN PARA TRAER INSCRITOS DEL BACKEND
// ================================================
function cargarInscritos(idProyecto) {
  const url = `/inscripciones-service/inscripciones?action=getInscripcionesByProyecto&idProyecto=${idProyecto}`;

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Error cargando inscripciones");
      }
      return response.json();
    })
    .then(data => {
      inscritos.length = 0;
      data.forEach(item => {
        inscritos.push({
          id: item.id, //id de la inscripcion
          idVoluntario: item.voluntario?.id,
          idProyecto: item.proyecto?.id,
          nombre: `${item.voluntario?.nombre || ''} ${item.voluntario?.apellido || ''}`.trim() || 'Voluntario',
          email: item.voluntario?.correo || '',
          telefono: item.voluntario?.telefono || '',
          ciudad: item.proyecto?.ubicacion || ''
        });
      });

      renderLista(inscritos, "lista-inscritos", "inscrito");
    })
    .catch(err => {
      console.error(err);
      document.getElementById("lista-inscritos").innerHTML = `
        <p style="color:red;">Error al cargar inscripciones</p>
      `;
    });
}


// ================================================
// 🔵 FUNCIÓN PARA TRAER EL PROYECTO DEL BACKEND
// ================================================
async function cargarDetalleProyecto(idProyecto) {

  if (!idProyecto) {
    console.error("No hay idProyectoTem en localStorage");
    return;
  }

  try {
    const response = await fetch(`/proyectos-service/proyectos?action=getById&id=${idProyecto}`);
    const proyecto = await response.json();

    const cont = document.getElementById("detalle-proyecto");

    cont.innerHTML = `
      <p><strong>📌 Nombre:</strong> ${proyecto.nombre}</p>
      <p><strong>📝 Descripción:</strong> ${proyecto.descripcion}</p>
      <p><strong>📍 Ubicación:</strong> ${proyecto.ubicacion}</p>
      <p><strong>📋 Requisitos:</strong> ${proyecto.requisitos}</p>
      <p><strong>📅 Fecha inicio:</strong> ${proyecto.fecha_inicio}</p>
      <p><strong>📅 Fecha fin:</strong> ${proyecto.fecha_fin}</p>
      <p><strong>👥 Voluntarios requeridos:</strong> ${proyecto.voluntarios_requeridos}</p>
      <p><strong>🏷️ Categoría:</strong> ${proyecto.categoria.nombre}</p>
    `;

  } catch (err) {
    console.error("Error cargando proyecto:", err);
  }
}


// ====================
//  RESTO DE TU CÓDIGO
// ====================



// ----- Renderizado -----
function renderLista(lista, contenedorId, tipo) {
  const contenedor = document.getElementById(contenedorId);

  if (!contenedor) {
    console.error(`Contenedor ${contenedorId} no encontrado`);
    return;
  }

  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = `
      <div class="empty-state">
        <p>📭 No hay ${tipo === "inscrito" ? "inscripciones" : "participantes"} registrados</p>
      </div>
    `;
    return;
  }

  lista.forEach(v => {
    const li = document.createElement("li");
    li.classList.add("item-voluntario");

    let botones = ``;

    if (tipo === "inscrito") {
      botones += `
        <button class="btn btn-cancelar" onclick="cancelarInscripcion(${v.id})">❌ Rechazar</button>
        <button class="btn btn-aceptar" onclick="aceptarVoluntario(${v.id})">✅ Aceptar</button>
        <button class="btn btn-ver" onclick="verDetalle(${v.id})">📄 Ver detalle</button>
      `;
    }

    li.innerHTML = `
      <span class="info-voluntario">👤 ${v.nombre}</span>
      <div class="botones-acciones">${botones}</div>
    `;

    contenedor.appendChild(li);
  });
}

// Acciones inscripciones
function cancelarInscripcion(id) {

  Swal.fire({
    title: "¿Estás seguro/a?",
    text: "¡No podrás revertir esto!",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Si, rechazar inscripción"
  }).then((result) => {
    if (result.isConfirmed) {
      const idProyecto = localStorage.getItem('idProyectoTemp');
      fetch('/inscripciones-service/inscripciones?action=getEstadosInscripcion')
        .then(r => r.json())
        .then(estados => {
          const rechazada = estados.find(e => e.nombre === 'Rechazada');
          if (!rechazada) throw new Error('Estado Rechazada no encontrado');

          const params = new URLSearchParams();
          params.append('action', 'updateEstado');
          params.append('idInscripcion', String(id));
          params.append('idEstado', String(rechazada.id));

          return fetch('/inscripciones-service/inscripciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params.toString()
          });
        })
        .then(resp => {
          if (!resp.ok) throw new Error('Error actualizando inscripción');
          cargarInscritos(idProyecto);
          Swal.fire('Inscripción rechazada', 'La inscripción ha sido rechazada', 'success');
          
        })
        .catch(err => {
          console.error(err);
          Swal.fire('Error', 'Error al rechazar la inscripción', 'error');

        });
    }
  })


}

function aceptarVoluntario(id) {

  Swal.fire({
    title: "¿Aceptar al voluntario en el proyecto?",
    text: "",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    cancelButtonColor: "#d33",
    confirmButtonText: "Si, salir"
  }).then((result) => {
    if (result.isConfirmed) {
      const idProyecto = localStorage.getItem("idProyectoTemp");
      const inscripcion = inscritos.find(v => v.id == id);
      console.log("Incripciones:", inscritos);
      console.log("Inscripción a aceptar:", inscripcion);
      console.log("ID Proyecto:", idProyecto);
      if (!idProyecto || !inscripcion) return;

      fetch('/inscripciones-service/inscripciones?action=getEstadosInscripcion')
        .then(r => r.json())
        .then(estados => {
          const aprobada = estados.find(e => e.nombre === 'Aprobada');
          if (!aprobada) throw new Error('Estado Aprobada no encontrado');

          const params = new URLSearchParams();
          params.append('action', 'updateEstado');
          params.append('idInscripcion', String(id));
          params.append('idEstado', String(aprobada.id));

          return fetch('/inscripciones-service/inscripciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params.toString()
          });
        })
        .then(resp => {
          if (!resp.ok) throw new Error('Error actualizando inscripción');
          //Cuando la inscripcion se acepta se crea la participacion
          const params = new URLSearchParams();
          params.append('action', 'save');
          params.append('idVoluntario', String(inscripcion.idVoluntario || ''));
          params.append('idProyecto', String(inscripcion.idProyecto || idProyecto));
          return fetch('/participaciones-service/participaciones', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params.toString()
          });
        })
        .then(() => {
          cargarParticipantes(idProyecto);
          cargarInscritos(idProyecto);
          Swal.fire('¡Inscripción aceptada!', 'La inscripción se ha aceptado correctamente.', 'success');

        })
        .catch(err => {
          console.error(err);
          Swal.fire('Error al aceptar la inscripción', 'Ha ocurrido un error al aceptar la inscripción.', 'error');

        });
    }
  });


}

function verDetalle(idInscripcion) {
  localStorage.setItem('idInscripcionTemp', String(idInscripcion));
  window.location.href = '../pages/detalleInscripcion.html';
}


// Tab switch
function mostrarVista(tipo) {
  document.querySelectorAll('.vista').forEach(v => v.classList.remove('visible'));
  document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));

  document.getElementById(`vista-${tipo}`).classList.add('visible');
  document.getElementById(`btn-${tipo}`).classList.add('active');
}

