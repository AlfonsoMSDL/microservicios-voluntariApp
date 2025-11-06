function procesarLogin(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);


    // Deshabilitar el botón mientras se envía
    const btnSubmit = form.querySelector('button[type="submit"]');
    const originalText = btnSubmit.textContent;
    btnSubmit.disabled = true;

    // Convertir FormData a URLSearchParams para application/x-www-form-urlencoded
    const urlEncodedData = new URLSearchParams(formData).toString();


    // Enviar datos al backend como application/x-www-form-urlencoded
    fetch('http://localhost:8181/voluntariApp/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: urlEncodedData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(data => {
            console.log('Respuesta del servidor:', data);

            // Verificar si se publicó correctamente
            if (!data.error) {
                const usuarioJson = JSON.stringify(data);
                localStorage.setItem('usuarioLogin', usuarioJson);
                console.log(data);
                // Limpiar formulario
                form.reset();

                alert("Bienvenido");

                if(data.rol.nombre == "Voluntario"){
                    window.location.href = '../pages/inicioVoluntario.html';
                }else if(data.rol.nombre == "Organizacion"){
                    window.location.href = '../pages/inicioOrganizacion.html';
                }else{
                    window.location.href = '../pages/inicioAdministrador.html';
                }
            } else {
                // Error reportado por el servidor
                alert(data.error );
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al conectar con el servidor: ' + error.message);
        })
        .finally(() => {
            // Rehabilitar el botón
            btnSubmit.disabled = false;
            btnSubmit.textContent = originalText;
        });


}