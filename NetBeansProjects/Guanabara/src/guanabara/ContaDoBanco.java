package guanabara;

/**
 *
 * @author User
 */
public class ContaDoBanco{

public  int numConta;
protected String tipo;
private String Dono;
private float saldo;
private boolean Status;

public int getNumConta(){
    return numConta;
}

public void setNumConta(int numero){
    numConta = numero;
}

public String getTipo(){
    return tipo;
}

public void setTipo(String t){
    tipo = t;
}

public String getDono(){
    return Dono;
}

public void setDono(String proprietario){
    Dono = proprietario;
}

public float getSaldo(){
    return saldo;
}

public void setSaldo(float balanca){
    saldo = balanca;
}

public boolean getStatus(){
    return Status;
}

public void setStatus(boolean Estado){
    Status = Estado;
}

public ContaDoBanco(int num, String type, String owner, float balance, boolean estado){
    this.numConta = num;
    this.tipo = type;
    this.Dono = owner;
    this.saldo = balance;
    this.Status = estado;
}

public String abrirConta(String type){
    setTipo(type);
    setStatus(true);
    
    return type;
}

C   1 = new ContaDoBanco(100, "CC", "Pedro", 1000, false);

}
