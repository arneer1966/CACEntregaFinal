
document.addEventListener('DOMContentLoaded', cargarUsuarios);
let modificarModal;
document.addEventListener('DOMContentLoaded', function () {
    modificarModal = new bootstrap.Modal(document.getElementById('modificarModal'));
    cargarUsuarios();
});

function cargarUsuarios() {
    const tbody = document.querySelector('#usuariosTable tbody');
    fetch('/CACFLixFinal/GestionUsuariosServlet')
            .then(response => response.json())
            .then(usuarios => {
 //               console.log(usuarios);
                tbody.innerHTML = '';
                usuarios.forEach(usuario => {
                    tbody.innerHTML += `
                    <tr>
                        <td>${usuario.documento}</td>
                        <td>${usuario.apellido}</td>
                        <td>${usuario.nombres}</td>
                        <td>${usuario.direccion}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.password}</td>
                        <td>${usuario.estado}</td>
                        <td>${usuario.tipo}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="mostrarModificarModal(${usuario.documento})">Modificar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarUsuario(${usuario.documento})">Eliminar</button>
                        </td>
                    </tr>
                `;
                });
                tbody.innerHTML += ` <button type="button" class="btn btn-danger btn-sm" onclick="terminar()">Menù principal</button>`;
            });
}

function mostrarModificarModal(documento) {
    fetch(`/CACFLixFinal/GestionUsuariosServlet?documento=${documento}`)
            .then(response => response.json())
            .then(usuario => {
                //console.log(usuario)
                document.getElementById('documento').value = usuario.documento;
                document.getElementById('apellido').value = usuario.apellido;
                document.getElementById('nombres').value = usuario.nombres;
                document.getElementById('direccion').value = usuario.direccion;
                document.getElementById('email').value = usuario.email;
                document.getElementById('password').value = usuario.password;
                document.getElementById('estado').value = usuario.estado;
                document.getElementById('tipo').value = usuario.tipo;
                modificarModal.show();
            })
            .catch(error => console.error('Error:', error));
}

function guardarModificacion() {
    const usuario = {
        documento: document.getElementById('documento').value,
        apellido: document.getElementById('apellido').value,
        nombres: document.getElementById('nombres').value,
        direccion: document.getElementById('direccion').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        estado: document.getElementById('estado').value,
        tipo: document.getElementById('tipo').value
    };
    if (document.getElementById('apellido').value !== "" &&
            document.getElementById('nombres').value !== "" &&
            document.getElementById('direccion').value !== "" &&
            document.getElementById('email').value !== "" &&
            document.getElementById('password').value !== "" &&
            document.getElementById('estado').value !== "" &&
            document.getElementById('tipo').value) {
        fetch('/CACFLixFinal/GestionUsuariosServlet', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(usuario)
        })
                .then(response => response.json())
                .then(data => {
//                console.log(data.exito);
                    if (data.exito) {
                        modificarModal.hide();
                        cargarUsuarios();
                    } else {
                        alert('Error al modificar el usuario');
                    }
                })
                .catch(error => console.error('Error:', error));
    } else {
        alert('Error al modificar el usuario, no puede ingresar contenidos vacios');
    }
}

function eliminarUsuario(documento) {
    if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
        fetch(`/CACFLixFinal/GestionUsuariosServlet?documento=${documento}`, {method: 'DELETE'})
                .then(response => response.json())
                .then(data => {
                    //                   console.log(data.exito);
                    if (data.exito) {
                        cargarUsuarios();
                    } else {
                        alert('Error al eliminar el usuario');
                    }
                })
                .catch(error => console.error('Error:', error));
    }
}

function terminar() {
    window.location.href = "/CACFLixFinal/index.html";
}
