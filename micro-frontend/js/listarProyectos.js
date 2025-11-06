document.addEventListener('DOMContentLoaded',() =>{
    //Mostrar los proyectos
    const usuarioLoginJson = localStorage.getItem('usuarioLogin');
    const usuarioLogin = JSON.parse(usuarioLoginJson);

    fetch('http://localhost:8181/voluntariApp/proyectos?action=getProyectos&idOrganizacion=' + usuarioLogin.id)
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
            <div class="project-item" onclick="irAProyecto('${proyecto.url}')">
                <a href="${proyecto.url}" class="project-link" onclick="event.preventDefault()">
                    ${proyecto.nombre}
                </a>
            </div>
        `).join('');
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al conectar con el servidor: ' + error.message);
    })
})