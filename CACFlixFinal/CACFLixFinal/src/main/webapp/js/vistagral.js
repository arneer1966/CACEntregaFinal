paginaE = 1;
paginaP = 1;
paginaS = 1;
cargar = true;

const btnSiguiente = document.getElementById('sig');
btnSiguiente.addEventListener('click', () => {
    if (paginaE < 1000) {
        paginaE++;
        cargarPelis('estrenos');
    }
    else {
        alert('Estas en la última página, no se puede avanzar.');
    }
});

const btnAnterior = document.getElementById('ant');
btnAnterior.addEventListener('click', () => {
    if (paginaE > 1) {
        paginaE--;
        cargarPelis('estrenos');
    }
    else {
        alert('Estas en la pagina 1, no se puede retroceder.');
    }
});

function cargarPelis(modo) {
    // console.log(modo);
    switch (modo) {
        case 'estrenos':
            leerPelis(`https://api.themoviedb.org/3/movie/upcoming?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${paginaE}`, modo);
            break;
        case 'peliculas':
            leerPelis(`https://api.themoviedb.org/3/movie/popular?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${paginaP}`, modo);
            break;
        case 'series':
            leerPelis(`https://api.themoviedb.org/3/tv/popular?api_key=afaf4c9ae3a0ac7f6c3ae156b91a73bc&language=es-AR&page=${paginaS}`, modo);
            break;
        default: {
            //            no procesar error
            break;
        }
    }
}

// completa para todas las pelis
function leerPelis(origen, modo) {
    // console.log(origen);
    const json = fetch(`${origen}`)
        .then(response => response.json())
        .then(data => procesaPelis(data));
    // console.log(json);
    function procesaPelis(fjson) {
        const filas = fjson.results.map(obj => fila(obj));
        filas.push(
            `
            <div class="griditem" data-aos="zoom-in-left" data-aos-delay="1000" >
               <div class="poster">
                  <div class="poster-detalle">
                    <img src="img/fondo.jpg">
                  </div>
                  <div class="poster-detalle">
                   <h4>hay mas películas, presione siguiente para continuar</h4>
                  </div>
               </div>
             </div>
           `
        );
        document.getElementById(modo).innerHTML = filas.join(' ');

        //     <li><strong>Actors: </strong>${data.Actors}</li>
        //     <li><strong>Director: </strong>${data.Director}</li>
        //     <li><strong>Writers: </strong>${data.Writer}</li>
        //     <li><strong>Genre: </strong>${data.}</li>
        //     <li><strong>Release Date: </strong>${data.DVD}</li>
        //     <li><strong>Box Office: </strong>${data.BoxOffice}</li>
        //     <li><strong>Movie Runtime: </strong>${data.Runtime}</li>
        function fila(obj) {
            console.log(obj);
            return `
        <div class="griditem" data-aos="zoom-in-left" data-aos-delay="300")>
        <div class="poster">
                <div class="poster-detalle">
                  <img src="https://image.tmdb.org/t/p/w500${obj.poster_path}">
                </div>
                <div class="poster-detalle">
                <h4>${obj.title}</h4>
                <h4>${obj.release_date}</h4>
                </div>
               </div>
            </div>
              `
        }
    };
}

if (cargar) {
    cargar = false;
    cargarPelis('estrenos');
}