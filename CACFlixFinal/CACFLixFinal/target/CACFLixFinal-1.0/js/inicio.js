let estado = 'L';

const formulario = document.getElementById('validaus');
formulario.addEventListener('submit', function (event) {
// event.preventDefault();

    const username = document.getElementById('nombrel').value;
    const password = document.getElementById('passl').value;

//    console.log(username + '   ' + password);
    if (username !== 'correo@electronico.com' && password !== 'password') {
        sessionStorage.setItem('usuario', username);
        window.location.href = "/CACFLixFinal/loginServlet.java";
    } else {
        alert('Credenciales incorrectas, intentelo nuevamente.');
    }
});
//const formulario = document.getElementById('validaus');
formulario.addEventListener('reset', function (event) {
    event.preventDefault();
    sessionStorage.removeItem('usuario');
    alert('¡Cancelo la operación, se perderan todos los datos de login!');
    window.location.href = "/CACFLixFinal/index.html";
});

function cambiomodo() {
    if (estado === 'L') {
        // alert(document.getElementsByClassName("login")[0]);
        document.getElementsByClassName("login")[0].style.display = "none";
        document.getElementsByClassName("registro")[0].style.display = "block";
        estado = 'R';
    } else {
        document.getElementsByClassName("login")[0].style.display = "block";
        document.getElementsByClassName("registro")[0].style.display = "none";
        estado = 'L';
    }
}

window.onload = function () {

    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('exito') === 'true') {
        document.getElementById('mensajeExito').style.display = 'block';
    } else if (urlParams.get('error') === 'true') {
        document.getElementById('mensajeError').style.display = 'block';
    }
};