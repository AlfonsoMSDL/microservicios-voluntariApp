document.addEventListener('DOMContentLoaded', () => {
  const usuarioLoginJson = localStorage.getItem('usuarioLogin');
  const usuarioLogin = JSON.parse(usuarioLoginJson || '{}');

  const lista = document.getElementById('voluntarioProjects');
  if (!usuarioLogin || !usuarioLogin.id) {
    lista.innerHTML = `
      <div class="empty-state">
        <div class="empty-state-icon">⚠️</div>
        <p>No se encontró información del usuario. Inicia sesión nuevamente.</p>
      </div>
    `;
    return;
  }

  fetch(`/inscripciones-service/inscripciones?action=getInscripcionesByVoluntario&idVoluntario=${usuarioLogin.id}`)
    .then(resp => {
      if (!resp.ok) throw new Error('Error cargando inscripciones');
      return resp.json();
    })
    .then(data => {
      if (!Array.isArray(data) || data.length === 0) {
        lista.innerHTML = `
          <div class="empty-state">
            <div class="empty-state-icon">📋</div>
            <p>Aún no tienes proyectos inscritos.</p>
          </div>
        `;
        return;
      }

      lista.innerHTML = data.map(ins => {
        const nombre = ins.proyecto?.nombre || 'Proyecto sin nombre';
        const ubicacion = ins.proyecto?.ubicacion ? ` • ${ins.proyecto.ubicacion}` : '';
        const estado = ins.estadoInscripcion?.nombre ? ` (${ins.estadoInscripcion.nombre})` : '';

        return `
          <div class="project-item" onclick="verDetalleInscripcion('${ins.id}')">
            <a href="#" class="project-link" onclick="event.preventDefault()">
              ${nombre}${ubicacion}${estado}
            </a>
          </div>
        `;
      }).join('');
    })
    .catch(err => {
      console.error(err);
      const fallback = [
        { nombre: 'Proyecto de apoyo comunitario', ubicacion: 'Bogotá', estado: 'En revisión' },
        { nombre: 'Reforestación Urbana', ubicacion: 'Medellín', estado: 'Aprobada' }
      ];
      lista.innerHTML = fallback.map(p => `
        <div class="project-item">
          <a href="#" class="project-link" onclick="event.preventDefault()">${p.nombre} • ${p.ubicacion} (${p.estado})</a>
        </div>
      `).join('');
    });
});

function verDetalleInscripcion(idInscripcion) {
  localStorage.setItem('idInscripcionTemp', idInscripcion);
  window.location.href = '../pages/detalleInscripcion.html';
}