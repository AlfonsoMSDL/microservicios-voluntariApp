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

  // Cargar participantes reales desde el backend
  cargarParticipantes(idProyecto);

  // Mantener los datos locales de inscritos (si luego tienes API, lo integramos)
  renderLista(inscritos, "lista-inscritos", "inscrito");

  // Event listeners para tabs
  document.getElementById("btn-inscripciones").addEventListener("click", () => {
    mostrarVista("inscripciones");
  });

  document.getElementById("btn-participantes").addEventListener("click", () => {
    mostrarVista("participantes");
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



// ====================
//  RESTO DE TU CÓDIGO
// ====================

// Datos locales de ejemplo (inscritos)
const inscritos = [
  { id: 1, nombre: "Ana Torres", email: "ana.torres@example.com", telefono: "3001234561", ciudad: "Santa Marta" },
  { id: 2, nombre: "Luis Pérez", email: "luis.perez@example.com", telefono: "3001234562", ciudad: "Bogotá" },
  { id: 3, nombre: "María López", email: "maria.lopez@example.com", telefono: "3001234563", ciudad: "Medellín" }
];

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
  if (confirm(`¿Estás seguro de cancelar la inscripción del voluntario ${id}?`)) {
    const index = inscritos.findIndex(v => v.id === id);
    if (index !== -1) {
      inscritos.splice(index, 1);
      renderLista(inscritos, "lista-inscritos", "inscrito");
      alert("Inscripción cancelada exitosamente");
    }
  }
}

function aceptarVoluntario(id) {
  if (confirm(`¿Aceptar al voluntario ${id} en el proyecto?`)) {
    const index = inscritos.findIndex(v => v.id === id);
    if (index !== -1) {
      const voluntario = inscritos.splice(index, 1)[0];
      participantes.push(voluntario);
      
      renderLista(inscritos, "lista-inscritos", "inscrito");
      renderLista(participantes, "lista-participantes", "participante");
      
      alert(`¡Voluntario ${voluntario.nombre} aceptado exitosamente!`);
    }
  }
}


// Tab switch
function mostrarVista(tipo) {
  document.querySelectorAll('.vista').forEach(v => v.classList.remove('visible'));
  document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));

  document.getElementById(`vista-${tipo}`).classList.add('visible');
  document.getElementById(`btn-${tipo}`).classList.add('active');
}
