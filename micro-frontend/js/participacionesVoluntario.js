async function loadParticipaciones() {
    const container = document.getElementById('participacionesContainer');

    // Obtener voluntario del localStorage
    const usuario = JSON.parse(localStorage.getItem("usuarioLogin"));
    if (!usuario) {
        container.innerHTML = `<p>No se pudo obtener el voluntario.</p>`;
        return;
    }

    const idVoluntario = usuario.id;

    try {
        const response = await fetch(
            `/participaciones-service/participaciones?action=getParticipacionesByVoluntario&idVoluntario=${idVoluntario}`
        );

        if (!response.ok) throw new Error("Error en la solicitud");

        const participaciones = await response.json();

        if (participaciones.length === 0) {
            container.innerHTML = `
                <div class="empty-state">
                    <div class="empty-icon">📘</div>
                    <h3>No tienes participaciones registradas</h3>
                    <p>Cuando participes en un proyecto, aparecerá aquí.</p>
                </div>
            `;
            return;
        }

        container.innerHTML = participaciones.map(p => `
            <div class="card">
                <div class="card-title">${p.proyecto.nombre}</div>
                <div class="card-desc">${p.proyecto.descripcion}</div>

                <div class="info-row">
                    <span class="info-label">Fecha de inicio:</span>
                    <span>${new Date(p.fechaInicio).toLocaleDateString('es-ES')}</span>
                </div>

                <div class="info-row">
                    <span class="info-label">Horas realizadas:</span>
                    <span>${p.horasRealizadas} horas</span>
                </div>
            </div>
        `).join('');

    } catch (err) {
        console.error(err);
        container.innerHTML = `
            <div class="empty-state">
                <div class="empty-icon">⚠️</div>
                <h3>Error al cargar las participaciones</h3>
                <p>Intenta nuevamente más tarde.</p>
            </div>
        `;
    }
}

loadParticipaciones();
