package info.ribosoft.mywork.HttpConn;

public class RecyclerLogin {
    // string variables for our data
    String id_operatore, cod_Operatore, cognome, nome, ore;

    public String getId_Operatore() {return id_operatore;}
    public String getCod_Operatore() {return cod_Operatore;}
    public String getCognome() {return cognome;}
    public String getNome() {return nome;}
    public String getOre() {return ore;}

    public void setId_Operatore(String id_operatore) {this.id_operatore = id_operatore;}
    public void setCod_Operatore(String cod_Operatore) {this.cod_Operatore = cod_Operatore;}
    public void setCognome(String cognome) {this.cognome = cognome;}
    public void setNome(String nome) {this.nome = nome;}
    public void setOre(String ore) {this.ore = ore;}

    public RecyclerLogin(String id_operatore, String cod_Operatore, String cognome, String nome,
        String ore) {
        this.id_operatore = id_operatore;
        this.cod_Operatore = cod_Operatore;
        this.cognome = cognome;
        this.nome =nome;
        this.ore = ore;
    }
}
