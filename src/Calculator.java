import java.awt.*;
import java.awt.event.*; //importar todas as classes e interfaces necessárias para tratar eventos. Dá acesso aos "ouvintes de eventos"
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ActionMapUIResource;

public class Calculator {
    int boardWidht = 360;
    int boardHeight = 540;

    // cores da calculadora
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    String[] rightSymbols = { "÷", "x", "-", "+", "=" };
    String[] topSymbols = { "AC", "+/-", "%" };

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel(); // exibir textos ou imagens na tela
    JPanel displayPanel = new JPanel(); // contêiner usado pra organizar e estilizar a área do display
    JPanel buttonsPanel = new JPanel(); // contêiner usado para organizar e estilizar a área dos botões

    String A = "0";
    String operator = null;
    String B = null;

    // Construtor
    Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidht, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4)); // faz uma tabela 5x4 (grid = grade)
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i< buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack)); //borda entre os botões
            if (Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }else if (Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }else{
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }


            buttonsPanel.add(button);

            button.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton button =  (JButton) e.getSource(); // Recupera o componente que disparou o evento e trata como JButton
                    String buttonValue = button.getText(); //armazenando o texto do botão clicado
                    if (Arrays.asList(rightSymbols).contains(buttonValue)){
                        if (buttonValue == "="){
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                }
                                else if (operator == "x") {
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();
                            }
                        }else if ("+-x÷".contains(buttonValue)){
                            if (operator == null){
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }
                    }else if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonValue == "AC"){
                            clearAll();
                            displayLabel.setText("0");
                        }else if (buttonValue == "+/-"){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));

                        }else if (buttonValue == "%"){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }else{
                        if (buttonValue.equals(".")){
                            // Trata o ponto: só permite adicionar um se ainda não houver no display
                            if (!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        // Se for número: substitui o zero inicial ou concatena ao valor atual do display
                        else if ("0123456789".contains(buttonValue)){ //verifica se a string "0123456789" contém o caractere que veio do botão
                            if (displayLabel.getText().equals("0")){
                                displayLabel.setText(buttonValue);
                            }else{
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }

                }
            });
        }
    }

    void clearAll(){
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay){
        if (numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}