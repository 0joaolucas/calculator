public class App {
    public static void main(String[] args) throws Exception {
        // Garante que a interface Swing seja criada na EDT (evita bugs e falhas na renderização)
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Calculator();
        });
    }
}
