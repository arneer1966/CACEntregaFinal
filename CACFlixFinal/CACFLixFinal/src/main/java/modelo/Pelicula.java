package modelo;

public class Pelicula {

    private String poster_path;
    private String email;

    public Pelicula() {
    }

    public Pelicula(String poster_path, String email) {
        this.poster_path = poster_path;
        this.email = email;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getEmail() {
        return email;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
