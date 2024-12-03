package com.aluracursos.screenmatch.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double evaluacion;
    private String poster;
    @Enumerated(EnumType.STRING) //Se guarda el nombre del enum en la base de datos, mejor que guardar la posición del enum, ya que en un futuro si se cambia el orden de los enums no afecta a la base de datos
    private Categoria genero;
    private String actores;
    private String sinopsis ;

    //@Transient//existe una lista de episodios en la clase serie, Con transient se evita que se guarde en la base de datos
    @OneToMany(mappedBy = "serie", cascade =CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    public Serie() {}
    public Serie(DatosSerie datosSeries) {
        this.titulo = (datosSeries.titulo() != null) ? datosSeries.titulo().trim() : "";
        this.totalTemporadas = datosSeries.totalTemporadas();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSeries.evaluacion())).orElse(0.0);
        this.poster = datosSeries.poster();
        this.genero = Categoria.fromString(datosSeries.genero().split(",")[0]);//Solo se toma el primer genero, separado por coma el resultado de la API
        this.actores = datosSeries.actores();
        this.sinopsis = datosSeries.sinopsis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }
    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }
    public String getSinopsis() {
        return sinopsis;
    }
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    public Double getEvaluacion() {
        return evaluacion;
    }
    public void setEvaluacion(Double evaluacion) {
        this.evaluacion = evaluacion;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public Categoria getGenero() {
        return genero;
    }
    public void setGenero(Categoria genero) {
        this.genero = genero;
    }
    public String getActores() {
        return actores;
    }
    public void setActores(String actores) {
        this.actores = actores;
    }

    @Override
    public String toString() {
        return     "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", evaluacion=" + evaluacion +
                ", poster='" + poster + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\''+
                ", episodios=" + episodios + '\'';
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    private double parseDoubleOrDefault(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}