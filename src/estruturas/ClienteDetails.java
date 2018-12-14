package estruturas;

public class ClienteDetails {

    private String IP;
    private int porto;

    public ClienteDetails(String IP, int porto){
        this.IP = IP;
        this.porto = porto;
    }

    public String getIP() {
        return IP;
    }

    public int getPorto() {
        return porto;
    }

    @Override
    public String toString() {
        return "ClienteDetails{" +
                "IP='" + IP + '\'' +
                ", porto=" + porto +
                '}';
    }
}
