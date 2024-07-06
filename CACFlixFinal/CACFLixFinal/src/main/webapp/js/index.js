let pagina = 1;
let cargar = true;
let ubiE = 0;
let ubiP = 0;
let ubiS = 0;
let ubiV = 0;
let ver = ['', '', '', ''];

function seleccionar(modo, genero, posi) {
    let existe = true;
    let p = ++posi;

    const seleccion = document.getElementById(genero).innerHTML.split('   ');
    let desde = seleccion[p].indexOf('https');
    let hasta = seleccion[p].indexOf('></div>') - 2;
    let cadena = seleccion[p].substring(desde, hasta);
    if (modo) {
        for (let i = 0; i < ver.length; i++) {
            if (ver[i].indexOf(cadena, 0) >= 0) {
                existe = false;
                break;
            }
        }
        if (existe) {
            seleccion[p] = seleccion[p].replace(genero, 'vista');
            seleccion[p] = seleccion[p].replace('true', 'false');
            ver.unshift(seleccion[p]);
            for (let i = ver.length; i < 4; i++) {
                //       ver.unshift("");  
                ver.push("");
            }
            tratarVista(cadena, "I");
            cargarPelis('vista', ubiV);
        } else {
            alert("Película ya existe en su espacio.");
        }
    } else {
        for (let i = 0; i < ver.length; i++) {
            if (ver[i].indexOf(cadena, 0) >= 0) {
                ver.splice(i, 1);
                tratarVista(cadena, "D");
                cargarPelis('vista', ubiV);
                alert("Película borrada de su espacio.");
                break;
            }
        }
    }
}

function tratarVista(cadena, modo) {

    const pelis = {
        poster_path: cadena,
        email: sessionStorage.getItem('usuario')
    };
    let mensaje = "Error al borrar pelicula en la BD";
    let tratar = 'DELETE';
    if (modo === "I") {
        let mensaje = "Error al insertar pelicula en la BD";
        tratar = 'PUT';
    }
    console.log(pelis);
    fetch('/CACFLixFinal/PelisUsuario', {
        method: tratar,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pelis)
    })
            .then(response => response.json())
            .then(data => {
                if (data.exito === false) {
                    alert(mensaje);
                }
            });
    //           .catch(error => console.error('Error:', error));
}

function controlCarrusel(modo, direccion) {
    switch (modo) {
        case 'estrenos':
            ubiE = procesarDir(direccion, ubiE, 16);
            cargarPelis(modo, ubiE);
            break;
        case 'peliculas':
            ubiP = procesarDir(direccion, ubiP, 16);
            cargarPelis(modo, ubiP);
            break;
        case 'series':
            ubiS = procesarDir(direccion, ubiS, 16);
            cargarPelis(modo, ubiS);
            break;
        case 'vista':
            ubiV = procesarDir(direccion, ubiV, ver.length - 4);
            cargarPelis(modo, ubiV);
            break;
    }

    function procesarDir(dir, ubi, tope) {
        if (dir === 'S') {
            if (ubi < tope) {
                ubi++;
            } else {
                ubi = 0;
            }
        } else if (dir === 'A') {
            if (ubi > 0) {
                ubi--;
            } else {
                ubi = tope;
            }
        }
        return ubi;
    }
}

function cargarPelis(modo, posi) {
    // console.log(modo);
    switch (modo) {
        case 'estrenos':
            leerPelis(`https://api.themoviedb.org/3/movie/upcoming?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${pagina}`, modo, posi);
            break;
        case 'peliculas':
            leerPelis(`https://api.themoviedb.org/3/movie/popular?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${pagina}`, modo, posi);
            break;
        case 'series':
            leerPelis(`https://api.themoviedb.org/3/tv/popular?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${pagina}`, modo, posi);
            break;
        case 'vista':
            leerVistas(posi);
            break;
        default:
        {
            //            no procesar error
            break;
        }
    }
}

function leerPelis(origen, modo, ubi) {
    var posi = 0;
    const json = fetch(`${origen}`)
            .then(response => response.json())
            .then(data => procesaPelis(data));

    function procesaPelis(fjson) {
        //     console.log(fjson);
        const filas = fjson.results.slice(ubi, ubi + 4);
        const fila4 = filas.map(obj => fila(obj));
        fila4.splice(0, 0, `<button class="boton" onclick="controlCarrusel('${modo}','S');">=></button>`);
        fila4.splice(5, 0, `<button class="boton" onclick="controlCarrusel('${modo}','A');"><=</button>`);
        //       console.log(fila4);
        document.getElementById(modo).innerHTML = fila4.join('   '); // /n

        function fila(fobj) {
            return `<div class="item-horiz" onclick="seleccionar(true,'${modo}',${posi++})"><img src="https://image.tmdb.org/t/p/w500${fobj.poster_path} "></div>`;
        }
        ;
    }
    ;
}
;

function leerVistas(ubi) {
    let posi = 0;
    let usuario = sessionStorage.getItem('usuario');

    if (usuario !== null) {
        if (cargar) {
            console.log(usuario);
            fetch(`/CACFLixFinal/PelisUsuario?email=${usuario}`)
                    .then(response => response.json())
                    .then(leidos => procesar(leidos));
        }
        for (let i = ver.length; i < 4; i++) {
            //       ver.unshift("");  
            ver.push("");
        }
    }
    console.log(ubi);
    console.log(ver);
    const vistas = ver.slice(ubi, ubi + 4);
    const mapeo = vistas.map(obj => vista(obj));
    mapeo.splice(0, 0, `<button class="boton" onclick="controlCarrusel('vista','S');">=></button>`);
    mapeo.splice(5, 0, `<button class="boton" onclick="controlCarrusel('vista','A');"><=</button>`);
    document.getElementById('vista').innerHTML = mapeo.join('   '); // /n

    function vista(fobj) {
        let desde = fobj.indexOf('https');
        let hasta = fobj.indexOf('></div>') - 1;
        let cadena = "";

        if (desde > 0) {
            cadena = fobj.substring(desde, hasta);
        } else {
            cadena = "img/fondo.jpg";
        }
        return `<div class="item-horiz" onclick="seleccionar(false,'vista',${posi++})"><img src="${cadena}"></div>`;
    }
    ;

    function procesar(leidos) {
        ver = [];
        let nuevos = leidos.map(obj => sumar(obj));
        nuevos.forEach(valor => ver.unshift(`'${valor} '></div>`));
        function sumar(peli) {
            return peli;
        }
    }
}
;

function validarUs(event, modo) {
    // console.log(event);
    let usuario = sessionStorage.getItem('usuario');
    if (modo && (usuario === "" || usuario === null)) {
        event.preventDefault();
        alert("Debe iniciar sesión.(Menu superior/Inicio) \nusuario: admin@correo.com o user@correo.com \nclave: pass \ntodo en minúsculas");
        removeEventListener(onmousedown, validarUs);
    } else {
        if (cargar) {
            cargarPelis('vista', ubiV);
            cargar = false;
        }
    }
}

if (cargar) {
    cargarPelis('estrenos', ubiE);
    cargarPelis('peliculas', ubiP);
    cargarPelis('series', ubiS);
    cargarPelis('vista', ubiV);
//    cargar = false;
}