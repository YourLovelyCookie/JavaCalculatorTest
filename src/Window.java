import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Window {
    String title;
    int width, height;

    JFrame frame;
    GridLayout grid;
    JLabel result;

    public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        // Frame
        frame = new JFrame(title);

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(3*100, 4*100));

        result = new JLabel("");
        result.setFont(new Font("Monospaced", Font.PLAIN, Math.round((float) width / 20)));

        JPanel btnPanel = new JPanel();
        JButton[] nButtons = {
            new JButton("0"),
            new JButton("1"),
            new JButton("2"),
            new JButton("3"),
            new JButton("4"),
            new JButton("5"),
            new JButton("6"),
            new JButton("7"),
            new JButton("8"),
            new JButton("9"),
            new JButton("+"),
            new JButton("-")
        };
        {
            ActionListener tmpActLis = e -> {
                JButton tmpBtn = (JButton) e.getSource();
                result.setText(result.getText() + tmpBtn.getText());
            };
            for (JButton btn : nButtons) {
                btn.addActionListener(tmpActLis);
                btnPanel.add(btn);
            }
        }

        grid = new GridLayout(4,3, Math.round((float) width / 30), 20);
        btnPanel.setLayout(grid);

        JButton resultBtn = new JButton("=");
        JButton clearBtn = new JButton("C");
        btnPanel.add(resultBtn);
        btnPanel.add(new Component(){});
        btnPanel.add(new Component(){});
        btnPanel.add(clearBtn);

        resultBtn.addActionListener(e -> result.setText(String.valueOf(calculate(format(result.getText())))));
        clearBtn.addActionListener(e -> result.setText(""));

        frame.setLayout(new BorderLayout());
        frame.add(result, BorderLayout.NORTH);
        frame.add(btnPanel, BorderLayout.CENTER);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                redraw(nButtons, resultBtn, clearBtn);
            }
        });

        redraw(nButtons, resultBtn, clearBtn);
        frame.setVisible(true);
    }

    public void redraw(JButton[] nBtns, JButton resBtn, JButton clrBtn) {
        width = frame.getWidth();
        height = frame.getHeight();
        grid = new GridLayout(4,3, Math.round((float) width / 30), Math.round((float) width / 30));
        result.setFont(new Font("Monospaced", Font.PLAIN, Math.round((float) width / 20)));

        for(JButton btn : nBtns) {
            btn.setFont(new Font("Monospaced", Font.PLAIN, Math.round((float) width / 10)));
        }
        resBtn.setFont(new Font("Monospaced", Font.PLAIN, Math.round((float) width / 10)));
        clrBtn.setFont(new Font("Monospaced", Font.PLAIN, Math.round((float) width / 10)));
    }


    public String format(String txt) {
        if(txt.isEmpty()) return "";

        while (txt.charAt(txt.length()-1) == '+' || txt.charAt(txt.length()-1) == '-') {
            txt = txt.substring(0, txt.length()-1);
        }
        while (txt.charAt(0) == '+') {
            txt = txt.substring(1);
        }
        if(txt.length() >= 2)
            while ((String.valueOf(txt.charAt(0)) + String.valueOf(txt.charAt(1))).equals("--")) {
                txt = txt.substring(2);
            }
        for (int i = 0; i < txt.length()-1; i++) {
            if((String.valueOf(txt.charAt(i)) + String.valueOf(txt.charAt(i+1))).equals("++") || (String.valueOf(txt.charAt(i)) + String.valueOf(txt.charAt(i+1))).equals("--")) {
                txt = txt.substring(0, i) + "+" + txt.substring(i+2);
            }
            if((String.valueOf(txt.charAt(i)) + String.valueOf(txt.charAt(i+1))).equals("+-") || (String.valueOf(txt.charAt(i)) + String.valueOf(txt.charAt(i+1))).equals("-+")) {
                txt = txt.substring(0, i) + "-" + txt.substring(i+2);
            }
        }

        return txt;
    }

    public int calculate(String txt) {
        int finalResult = 0;
        for (int i = 1; i < txt.length(); i++)
        {
            if(txt.charAt(i) == '+' || txt.charAt(i) == '-') {
                finalResult += Integer.parseInt(txt.substring(0, i));
                txt = txt.substring(i);
                i = 1;
            }
        }
        finalResult += Integer.parseInt(txt);
        return finalResult;
    }
}
