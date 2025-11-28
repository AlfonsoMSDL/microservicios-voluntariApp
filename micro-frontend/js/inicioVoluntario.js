document.addEventListener('DOMContentLoaded', () => {
  const usuarioLoginJson = localStorage.getItem('usuarioLogin');
  let usuarioLogin = {};
  try { usuarioLogin = JSON.parse(usuarioLoginJson || '{}'); } catch {}

  if (!usuarioLogin || !usuarioLogin.id) {
    usuarioLogin = { id: 1, nombreUsuario: 'voluntario_demo', correo: 'vol-demo@example.com', clave: 'vol1234' };
    localStorage.setItem('usuarioLogin', JSON.stringify(usuarioLogin));
    const nombreEl = document.getElementById('nombreUsuario');
    if (nombreEl) nombreEl.textContent = `Bienvenid@ ${usuarioLogin.nombreUsuario}`;
  } else {
    const nombreEl = document.getElementById('nombreUsuario');
    if (nombreEl) nombreEl.textContent = `Bienvenid@ ${usuarioLogin.nombreUsuario}`;
  }

  const lista = document.getElementById('voluntarioProjects');

  fetch(`/proyectos-service/proyectos?action=getProyectos`)
    .then(resp => {
      if (!resp.ok) throw new Error('Error cargando inscripciones');
      return resp.json();
    })
    .then(async data => {

      const proyectosConCategoria = await Promise.all(
        data.map(async pro => {
          return {
            proyectoId: pro.id,
            nombre: pro.nombre || 'Proyecto sin nombre',
            categoria: pro.categoria.nombre || 'Sin categoría'
          };
        })
      );

      lista.innerHTML = proyectosConCategoria.map(p => `
        <div class="vol-card">
          <div class="vol-card-content">
            <div class="vol-card-title">${p.nombre}</div>
            <div class="vol-card-category">${p.categoria}</div>
            <button class="vol-btn" onclick="openModalProyecto('${p.proyectoId}')">ver</button>
          </div>
        </div>
      `).join('');
    })
    .catch(err => {
      console.error(err);
    });
});

function verDetalleInscripcion(idInscripcion) {
  localStorage.setItem('idInscripcionTemp', idInscripcion);
  window.location.href = '../pages/detalleInscripcion.html';
}

function openModalProyecto(idProyecto) {
  const overlay = document.getElementById('modalProyecto');
  const titleEl = document.getElementById('modalProyectoTitulo');
  const bodyEl = document.getElementById('modalProyectoBody');

  overlay.style.display = 'flex';
  overlay.addEventListener('click', (e) => { if (e.target === overlay) closeModalProyecto(); });
  document.addEventListener('keydown', (e) => { if (e.key === 'Escape') closeModalProyecto(); }, { once: true });

  fetch(`/proyectos-service/proyectos?action=getById&id=${idProyecto}`)
    .then(r => { if (!r.ok) throw new Error('err'); return r.json(); })
    .then(p => { renderModalProyecto(p, titleEl, bodyEl); })
    .catch(() => {
      console.log('Error cargando proyecto');
      alert('Error cargando proyecto.');
    });
}

function renderModalProyecto(p, titleEl, bodyEl) {
  titleEl.textContent = p?.nombre || '';
  const categoria = p?.categoria?.nombre || '';
  const descripcion = p?.descripcion || '';
  const ubicacion = p?.ubicacion || '';
  const requisitos = p?.requisitos || '';
  const fechaInicio = p?.fecha_inicio || '';
  const fechaFin = p?.fecha_fin || '';
  const voluntarios = p?.voluntarios_requeridos || '';
  const orgNombre = p?.organizacion?.nombre || '';
  const orgCorreo = p?.organizacion?.correo || '';
  const orgTelefono = p?.organizacion?.telefono || '';
  const orgDescripcion = p?.organizacion?.descripcion || '';

  bodyEl.innerHTML = `
    <div style="display:grid; gap:10px;">
      <div class="project-card-category">${categoria}</div>
      <div>${descripcion}</div>
      <div>Ubicación: ${ubicacion}</div>
      <div>Requisitos: ${requisitos}</div>
      <div>Fechas: ${fechaInicio} a ${fechaFin}</div>
      <div>Voluntarios requeridos: ${voluntarios}</div>
      <hr />
      <div style="font-weight:600;">Organización</div>
      <div>Nombre: ${orgNombre}</div>
      <div>Correo: ${orgCorreo}</div>
      <div>Teléfono: ${orgTelefono}</div>
      <div>Descripción: ${orgDescripcion}</div>

      <div id="zonaInscripcion" class="modal-actions" style="margin-top:12px;">
        <button class="vol-btn" onclick="mostrarTextAreaMotivacion('${p?.id || ''}')">Inscribirme</button>
      </div>
    </div>
  `;
}

function mostrarTextAreaMotivacion(idProyecto) {
  const zona = document.getElementById('zonaInscripcion');

  zona.innerHTML = `
    <textarea id="motivacionInput" placeholder="Escribe aquí tu motivación..." 
      style="
        width:100%; 
        height:100px; 
        padding:8px; 
        border-radius:8px; 
        border:1px solid #ccc;
        resize: vertical;
        margin-top:10px;
      ">
    </textarea>

    <button class="vol-btn" style="margin-top:10px;" 
      onclick="enviarSolicitudInscripcion('${idProyecto}')">
      Enviar solicitud
    </button>
  `;
}

function enviarSolicitudInscripcion(idProyecto) {
  const usuarioLoginJson = localStorage.getItem('usuarioLogin');
  let usuarioLogin = {};
  try { usuarioLogin = JSON.parse(usuarioLoginJson || '{}'); } catch {}

  const idVoluntario = usuarioLogin?.id;
  const motivacion = document.getElementById('motivacionInput')?.value || '';

  // Datos finales registrados
  const voluntarioIdFinal = idVoluntario;
  const proyectoIdFinal = idProyecto;
  const motivacionFinal = motivacion;

  const data = new URLSearchParams();
  data.append('idVoluntario', voluntarioIdFinal);
  data.append('idProyecto', proyectoIdFinal);
  data.append('motivacion', motivacionFinal);

  console.log("data: ", data);

  // Enviar los datos al backend
  fetch('/inscripciones-service/inscripciones?action=save', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: data.toString()
  })
    .then(r => { if (!r.ok) throw new Error('err'); return r.json(); })
    .then(r => {
      const tipo = r.status;
      const message = r.status == 'success' ? 'Solicitud de inscripción enviada exitosamente' : 'Error al enviar la solicitud de inscripción';

      Swal.fire({
      position: "center",
      icon: tipo,
      title: message,
      showConfirmButton: false,
      timer: 1500
      });

     })
    .catch(() => {
      console.log('❌ Error al enviar la solicitud de inscripción');
      alert('❌ Error al enviar la solicitud de inscripción. Por favor intenta nuevamente.');
    });

  console.log("Datos obtenidos para enviar solicitud:");
  console.log({ voluntarioIdFinal, proyectoIdFinal, motivacionFinal });

  // 👉 Aquí tú completarás la lógica para enviar estos datos al backend
}

function closeModalProyecto() {
  const overlay = document.getElementById('modalProyecto');
  overlay.style.display = 'none';
}
