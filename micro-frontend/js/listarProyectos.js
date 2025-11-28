// Función para cargar proyectos
function cargarProyectos() {
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    fetch('/proyectos-service/proyectos?action=getProyectosByOrganizacion&id=' + usuarioLogin.id)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(data => {
            console.log('Respuesta del servidor:', data);
            const lista = document.getElementById('projectsList');

            if (data.length === 0) {
                lista.innerHTML = `
                    <div class="empty-state">
                        <div class="empty-state-icon">📋</div>
                        <p>No tienes proyectos aún. ¡Crea tu primer proyecto!</p>
                    </div>
                `;
                return;
            }

            lista.innerHTML = data.map(proyecto => `
                <div class="project-item" onclick="irAProyecto('${proyecto.id}')">
                    <a href="${proyecto.url}" class="project-link" onclick="event.preventDefault()">
                        ${proyecto.nombre}
                    </a>
                </div>
            `).join('');
        })
        .catch(error => {
            console.error('Error:', error);
            Swal.fire("Error al conectar con el servidor", error.message, "error");
        })
}

// Al cargar la página
document.addEventListener('DOMContentLoaded', () => {
    // Cargar proyectos directamente sin necesidad de bandera
    cargarProyectos();
});

// Detectar cuando vuelves a esta página (importante para navegación del navegador)
window.addEventListener('pageshow', (event) => {
    if (event.persisted) {
        // La página viene del cache del navegador (back button)
        cargarProyectos();
    }
});

function irAProyecto(idProyecto) {
    window.location.href = "../pages/gestionProyectosOrga.html";
    localStorage.setItem('idProyectoTemp', idProyecto);
}