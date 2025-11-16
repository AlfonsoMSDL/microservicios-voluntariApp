document.addEventListener('DOMContentLoaded', () => {
  const cont = document.getElementById('detalle-proyecto');
  const idLocal = localStorage.getItem('idProyectoDetalle');
  const params = new URLSearchParams(location.search);
  const id = idLocal || params.get('id');

  if (!id) {
    cont.innerHTML = `<p style="color:red;">No se encontró el proyecto.</p>`;
    return;
  }

  fetch(`/proyectos-service/proyectos?action=getById&id=${id}`)
    .then(r => {
      if (!r.ok) throw new Error('Error');
      return r.json();
    })
    .then(p => {
      renderProyecto(cont, p);
    })
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
        organizacion: { id: '1', nombre: 'Organ', nombreUsuario: 'organizacion papa', correo: 'organ@gmail.com', telefono: '123232', descripcion: 'Sin descripción' }
      };
      renderProyecto(cont, mock);
    });
});

function renderProyecto(container, p) {
  const nombre = p?.nombre || '';
  const descripcion = p?.descripcion || '';
  const ubicacion = p?.ubicacion || '';
  const requisitos = p?.requisitos || '';
  const fechaInicio = p?.fecha_inicio || '';
  const fechaFin = p?.fecha_fin || '';
  const voluntarios = p?.voluntarios_requeridos || '';
  const categoria = p?.categoria?.nombre || '';
  const orgNombre = p?.organizacion?.nombre || '';
  const orgCorreo = p?.organizacion?.correo || '';
  const orgTelefono = p?.organizacion?.telefono || '';
  const orgDescripcion = p?.organizacion?.descripcion || '';

  container.innerHTML = `
    <div class="project-item" style="cursor:default;">
      <div class="project-link">${nombre}</div>
      <div style="color:#2e7d32; font-weight:600; margin:8px 0;">${categoria}</div>
      <div style="margin:8px 0;">${descripcion}</div>
      <div style="margin:8px 0;">Ubicación: ${ubicacion}</div>
      <div style="margin:8px 0;">Requisitos: ${requisitos}</div>
      <div style="margin:8px 0;">Fechas: ${fechaInicio} a ${fechaFin}</div>
      <div style="margin:8px 0;">Voluntarios requeridos: ${voluntarios}</div>
      <hr style="margin:16px 0;" />
      <div style="font-weight:600;">Organización</div>
      <div>Nombre: ${orgNombre}</div>
      <div>Correo: ${orgCorreo}</div>
      <div>Teléfono: ${orgTelefono}</div>
      <div>Descripción: ${orgDescripcion}</div>
    </div>
  `;
}