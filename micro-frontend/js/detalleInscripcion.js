document.addEventListener('DOMContentLoaded', () => {
  const idInscripcion = localStorage.getItem('idInscripcionTemp');
  const cont = document.getElementById('detalle-inscripcion');

  if (!idInscripcion) {
    cont.innerHTML = `<p style="color:red;">No se encontró la inscripción.</p>`;
    return;
  }

  let inscripcionData = null;
  let voluntarioData = null;

  fetch(`/inscripciones-service/inscripciones?action=getById&idInscripcion=${idInscripcion}`)
    .then(r => r.json())
    .then(data => {
      inscripcionData = data;
      const idVol = data.voluntario?.id;
      if (!idVol) return null;
      return fetch(`/usuarios-service/voluntarios?action=getById&id=${idVol}`);
    })
    .then(r => r ? r.json() : null)
    .then(vol => {
      voluntarioData = vol;
      renderDetalle(cont, inscripcionData, voluntarioData);
      wireActions(inscripcionData, voluntarioData);
    })
    .catch(err => {
      console.error(err);
      cont.innerHTML = `<p style="color:red;">Error al cargar los detalles.</p>`;
    });
});

function renderDetalle(container, ins, vol) {
  const nombreCompleto = `${ins?.voluntario?.nombre || ''} ${ins?.voluntario?.apellido || ''}`.trim();
  const fecha = ins?.fecha_inscripcion || '';
  const motivacion = ins?.motivacion || '';
  const habilidades = vol?.habilidades || 'No registradas';
  const experiencia = vol?.experiencia || 'No registrada';

  container.innerHTML = `
    <p><strong>👤 Nombre completo:</strong> ${nombreCompleto || 'Desconocido'}</p>
    <p><strong>📅 Fecha de inscripción:</strong> ${fecha}</p>
    <p><strong>💬 Motivación:</strong> ${motivacion}</p>
    <p><strong>🧩 Habilidades:</strong> ${habilidades}</p>
    <p><strong>🛠️ Experiencia:</strong> ${experiencia}</p>
  `;
}

function wireActions(ins, vol) {
  const btnAceptar = document.getElementById('btn-aceptar');
  const btnRechazar = document.getElementById('btn-rechazar');

  btnAceptar.addEventListener('click', () => actualizarEstado(ins, vol, 'Aprobada'));
  btnRechazar.addEventListener('click', () => actualizarEstado(ins, vol, 'Rechazada'));
}

function actualizarEstado(ins, vol, estadoNombre) {
  if (!confirm(`¿Confirmar ${estadoNombre.toLowerCase()} de esta inscripción?`)) return;

  fetch('/inscripciones-service/inscripciones?action=getEstadosInscripcion')
    .then(r => r.json())
    .then(estados => {
      const estado = estados.find(e => e.nombre === estadoNombre);
      if (!estado) throw new Error('Estado no encontrado');

      const params = new URLSearchParams();
      params.append('action', 'update');
      params.append('idInscripcion', String(ins.id));
      params.append('motivacion', ins.motivacion || '');
      params.append('fechaInscripcion', (ins.fecha_inscripcion || new Date().toISOString().slice(0,10)));
      params.append('idEstadoInscripcion', String(estado.id));

      return fetch('/inscripciones-service/inscripciones', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params.toString()
      });
    })
    .then(resp => {
      if (!resp.ok) throw new Error('Error actualizando inscripción');

      if (estadoNombre === 'Aprobada') {
        const params = new URLSearchParams();
        params.append('action', 'save');
        params.append('idVoluntario', String(vol?.id || ins.voluntario?.id || ''));
        params.append('idProyecto', String(ins?.proyecto?.id || ''));

        return fetch('/participaciones-service/participaciones', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: params.toString()
        });
      }
    })
    .then(() => {
      alert(`Inscripción ${estadoNombre.toLowerCase()} correctamente`);
      window.history.back();
    })
    .catch(err => {
      console.error(err);
      alert('Ocurrió un error al procesar la solicitud');
    });
}