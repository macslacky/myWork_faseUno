package info.ribosoft.mywork.HttpConn;

public class RecyclerOrari {
    // string variables for our data
    String data, orario, ore, data_sel, orario_tipo;

    public String getData() {return data;}
    public String getOrario() {return orario;}
    public String getOre() {return ore;}
    public String getDataSel() {return data_sel;}
    public String getOrarioTipo() {return orario_tipo;}

    public void setData(String data) {this.data = data;}
    public void setOrario(String orario) {this.orario = orario;}
    public void setOre(String ore) {this.ore = ore;}
    public void setDatasel(String data_sel) {this.data_sel = data_sel;}
    public void setOrarioTipo(String orario_tipo) {this.orario_tipo = orario_tipo;}

    public RecyclerOrari(String data, String orario, String ore, String data_sel,
        String orario_tipo) {
        this.data = data;
        this.orario = orario;
        this.ore = ore;
        this.data_sel = data_sel;
        this.orario_tipo = orario_tipo;
    }
}
