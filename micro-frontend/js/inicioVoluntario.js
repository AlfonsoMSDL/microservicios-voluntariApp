document.addEventListener('DOMContentLoaded', () => {
  const usuarioLoginJson = localStorage.getItem('usuarioLogin');
  let usuarioLogin = {};
  try { usuarioLogin = JSON.parse(usuarioLoginJson || '{}'); } catch {}

  if (!usuarioLogin || !usuarioLogin.id) {
    usuarioLogin = { id: 1, nombreUsuario: 'voluntario_demo', correo: 'vol-demo@example.com', clave: 'vol1234' };
    localStorage.setItem('usuarioLogin', JSON.stringify(usuarioLogin));
    const nombreEl = document.getElementById('nombreUsuario');
    if (nombreEl) nombreEl.textContent = `Bienvenid@ ${usuarioLogin.nombreUsuario}`;
  }

  const lista = document.getElementById('voluntarioProjects');

  fetch(`/inscripciones-service/inscripciones?action=getInscripcionesByVoluntario&idVoluntario=${usuarioLogin.id}`)
    .then(resp => {
      if (!resp.ok) throw new Error('Error cargando inscripciones');
      return resp.json();
    })
    .then(async data => {
      if (!Array.isArray(data) || data.length === 0) {
        lista.innerHTML = `
          <div class="empty-state">
            <div class="empty-state-icon">📋</div>
            <p>Aún no tienes proyectos inscritos.</p>
          </div>
        `;
        return;
      }

      const proyectosConCategoria = await Promise.all(
        data.map(async ins => {
          const idProyecto = ins.proyecto?.id;
          let categoriaNombre = '';
          if (idProyecto) {
            try {
              const r = await fetch(`/proyectos-service/proyectos?action=getById&id=${idProyecto}`);
              if (r.ok) {
                const p = await r.json();
                categoriaNombre = p?.categoria?.nombre || '';
              }
            } catch {}
          }
          return {
            inscripcionId: ins.id,
            proyectoId: idProyecto,
            nombre: ins.proyecto?.nombre || 'Proyecto sin nombre',
            categoria: categoriaNombre || 'Sin categoría'
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
      const fallback = [
        { id: '1', nombre: 'Gatitos uwu 23', categoria: 'Salud Comunitaria' },
        { id: 'm2', nombre: 'Refuerzo educativo', categoria: 'Educativo' },
        { id: 'm3', nombre: 'Comedor comunitario', categoria: 'Social' },
        { id: 'm4', nombre: 'Reforestación urbana', categoria: 'Ambiental' }
      ];
      lista.innerHTML = fallback.map(p => `
        <div class="vol-card">
          <div class="vol-card-content">
            <div class="vol-card-title">${p.nombre}</div>
            <div class="vol-card-category">${p.categoria}</div>
            <button class="vol-btn" onclick="openModalProyecto('${p.id}')">ver</button>
          </div>
        </div>
      `).join('');
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
      const mock = {
        id: '1',
        nombre: 'Gatitos uwu 23',
        descripcion: 'Rescata gatitos x545',
        ubicacion: 'Cartagena',
        requisitos: 'Tener plata y ser perro',
        fecha_inicio: '2023-04-07',
        fecha_fin: '2024-04-09',
        voluntarios_requeridos: '3',
        categoria: { id: '3', nombre: 'Salud Comunitaria' },
        organizacion: { id: '1', nombre: 'Organ', correo: 'organ@gmail.com', telefono: '123232', descripcion: 'Sin descripción' }
      };
      renderModalProyecto(mock, titleEl, bodyEl);
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
      <div class="modal-actions" style="margin-top:12px;">
        <button class="vol-btn" onclick="inscribirEnProyecto('${p?.id || ''}')">Inscribirme</button>
      </div>
    </div>
  `;
}

function closeModalProyecto() {
  const overlay = document.getElementById('modalProyecto');
  overlay.style.display = 'none';
}

function inscribirEnProyecto(idProyecto) {
  const usuarioLoginJson = localStorage.getItem('usuarioLogin');
  let usuarioLogin = {};
  try { usuarioLogin = JSON.parse(usuarioLoginJson || '{}'); } catch {}
  const idVoluntario = usuarioLogin?.id;
  if (!idVoluntario || !idProyecto) {
    alert('No se encontró información para inscribir.');
    return;
  }

  const params = new URLSearchParams();
  params.append('action', 'save');
  params.append('idVoluntario', String(idVoluntario));
  params.append('idProyecto', String(idProyecto));

  fetch('/inscripciones-service/inscripciones', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: params.toString()
  })
    .then(r => {
      if (!r.ok) throw new Error('Error creando inscripción');
      alert('Inscripción enviada.');
      closeModalProyecto();
    })
    .catch(err => {
      console.error(err);
      alert('No se pudo inscribir. Verifica el backend.');
    });
}