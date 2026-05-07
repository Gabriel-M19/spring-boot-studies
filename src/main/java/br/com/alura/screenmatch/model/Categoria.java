package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action"),
    AVENTURA("Adventure"),
    ANIMACAO("Animation"),
    COMEDIA("Comedy"),
    COMEDIA_ROMANTICA("Romantic Comedy"),
    CRIME("Crime"),
    DOCUMENTARIO("Documentary"),
    DRAMA("Drama"),
    ESPORTE("Sport"),
    FANTASIA("Fantasy"),
    FAROESTE("Western"),
    FICCAO_CIENTIFICA("Sci-Fi"),
    GUERRA("War"),
    HISTORICO("History"),
    MISTERIO("Mystery"),
    MUSICAL("Musical"),
    POLICIAL("Crime"),
    ROMANCE("Romance"),
    SUSPENSE("Thriller"),
    TERROR("Horror"),
    THRILLER("Thriller"),
    BIOGRAFIA("Biography"),
    FAMILIA("Family"),
    INFANTIL("Children"),
    NOIR("Film-Noir"),
    REALITY_SHOW("Reality-TV");


    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
