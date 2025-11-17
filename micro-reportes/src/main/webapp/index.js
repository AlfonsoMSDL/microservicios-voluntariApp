async function testEndpoint(url, responseId) {
      const responseElement = document.getElementById(responseId);
      const button = event.target;

      // Deshabilitar botón durante la petición
      button.disabled = true;
      button.textContent = 'Cargando...';

      responseElement.style.display = 'block';
      responseElement.className = 'response loading';
      responseElement.textContent = 'Realizando petición...';

      try {
          const startTime = Date.now();
          const response = await fetch(url);
          const endTime = Date.now();
          const duration = endTime - startTime;

          const contentType = response.headers.get("content-type");
          let data;

          if (contentType && contentType.includes("application/json")) {
              data = await response.json();
          } else {
              data = await response.text();
          }

          if (response.ok) {
              responseElement.className = 'response';
              responseElement.textContent =
                  `Status: ${response.status} OK (${duration}ms)\n\n` +
                  JSON.stringify(data, null, 2);
          } else {
              responseElement.className = 'response error';
              responseElement.textContent =
                  `Status: ${response.status} ${response.statusText} (${duration}ms)\n\n` +
                  JSON.stringify(data, null, 2);
          }
      } catch (error) {
          responseElement.className = 'response error';
          responseElement.textContent =
              `Error de Red o Conexión\n\n` +
              `Mensaje: ${error.message}\n\n` +
              `Posibles causas:\n` +
              `- El microservicio de reportes no está corriendo\n` +
              `- Uno o más microservicios dependientes están caídos\n` +
              `- Problema de CORS o red\n\n` +
              `Verifica que todos los servicios estén activos:\n` +
              `- http://localhost:8081 (usuarios)\n` +
              `- http://localhost:8082 (proyectos)\n` +
              `- http://localhost:8083 (inscripciones)\n` +
              `- http://localhost:8085 (reportes)`;
      } finally {
          // Rehabilitar botón
          button.disabled = false;
          button.textContent = 'Probar Endpoint';
      }
  }