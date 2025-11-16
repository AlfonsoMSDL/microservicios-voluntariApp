// ======================
// CARGAR DATOS DEL BACK
// ======================

document.addEventListener("DOMContentLoaded", () => {
  console.log("Iniciando aplicación...");

  const idProyecto = localStorage.getItem("idProyectoTemp");
  localStorage.removeItem("idProyectoTemp");

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

function cargarInscritos(idProyecto){
  const url = `/inscripciones-service/inscripciones?action=getInscripcionesByProyecto&idProyecto=${idProyecto}`;
  
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
      id: item.id,
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

// Datos locales de ejemplo (inscritos)
const inscritos = [];

// Participantes ahora serán llenados desde la API
const participantes = [];

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

    let botones = `
      <button class="btn btn-ver" onclick="verVoluntario(${v.id}, '${tipo}')">👁️ Ver</button>
    `;

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


// Modal
function verVoluntario(id, tipo) {
  const lista = tipo === "inscrito" ? inscritos : participantes;
  const v = lista.find(p => p.id === id);
  
  if (!v) {
    alert("Voluntario no encontrado");
    return;
  }

  const modal = document.getElementById("modal-detalles");
  const detalle = document.getElementById("detalle-voluntario");

  detalle.innerHTML = `
    <p><strong>👤 Nombre:</strong> ${v.nombre}</p>
    <p><strong>📧 Correo:</strong> ${v.email}</p>
    <p><strong>📱 Teléfono:</strong> ${v.telefono}</p>
    <p><strong>📍 Ubicación:</strong> ${v.ciudad}</p>
  `;

  modal.style.display = "flex";
}

function cerrarModal() {
  document.getElementById("modal-detalles").style.display = "none";
}


// Acciones inscripciones
function cancelarInscripcion(id) {
  if (!confirm(`¿Estás seguro de rechazar la inscripción ${id}?`)) return;

  const inscripcion = inscritos.find(v => v.id === id);
  const idProyecto = localStorage.getItem('idProyectoTemp');
  if (!inscripcion) return;

  fetch('/inscripciones-service/inscripciones?action=getEstadosInscripcion')
    .then(r => r.json())
    .then(estados => {
      const rechazada = estados.find(e => e.nombre === 'Rechazada');
      if (!rechazada) throw new Error('Estado Rechazada no encontrado');

      const params = new URLSearchParams();
      params.append('action', 'update');
      params.append('idInscripcion', String(id));
      params.append('motivacion', '');
      params.append('fechaInscripcion', new Date().toISOString().slice(0,10));
      params.append('idEstadoInscripcion', String(rechazada.id));

      return fetch('/inscripciones-service/inscripciones', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
      });
    })
    .then(resp => {
      if (!resp.ok) throw new Error('Error actualizando inscripción');
      cargarInscritos(idProyecto);
      alert('Inscripción rechazada');
    })
    .catch(err => {
      console.error(err);
      alert('Error al rechazar la inscripción');
    });
}

function aceptarVoluntario(id) {
  if (!confirm(`¿Aceptar al voluntario ${id} en el proyecto?`)) return;

  const idProyecto = localStorage.getItem("idProyectoTemp");
  const inscripcion = inscritos.find(v => v.id === id);
  if (!idProyecto || !inscripcion) return;

  fetch('/inscripciones-service/inscripciones?action=getEstadosInscripcion')
    .then(r => r.json())
    .then(estados => {
      const aprobada = estados.find(e => e.nombre === 'Aprobada');
      if (!aprobada) throw new Error('Estado Aprobada no encontrado');

      const params = new URLSearchParams();
      params.append('action', 'update');
      params.append('idInscripcion', String(id));
      params.append('motivacion', '');
      params.append('fechaInscripcion', new Date().toISOString().slice(0,10));
      params.append('idEstadoInscripcion', String(aprobada.id));

      return fetch('/inscripciones-service/inscripciones', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
      });
    })
    .then(resp => {
      if (!resp.ok) throw new Error('Error actualizando inscripción');
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
      alert('¡Inscripción aceptada y participación creada!');
    })
    .catch(err => {
      console.error(err);
      alert('Error al aceptar la inscripción');
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

