package vigiaquinze.Model;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo; // "cliente" ou "administrador"

    // Construtores
    public Cliente(int id, String nome, String email, String telefone, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    // Novo construtor que aceita apenas ID e Nome
    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.email = ""; // ou algum valor padrão
        this.telefone = ""; // ou algum valor padrão
        this.tipo = ""; // ou algum valor padrão
    }

    public Cliente() {
        super();
    }

    // getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nome; // Retorna o nome do cliente
    }
}
