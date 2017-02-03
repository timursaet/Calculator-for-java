import javax.swing.*;
import java.awt.*;
import java.awt.event.*;.
class CalculatorFrame extends JFrame {
    CalculatorFrame(){
        int w=270, h=240;
        setTitle("Калькулятор");
        setBounds(100,100,w,h);
        CPanel panel = new CPanel(w,h);
        add(panel);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class CPanel extends JPanel {
    public JTextField TF;
    private BtnAction BtnPressed;
    CPanel(int W,int H) {
        int w=W/5, h=H/8,sx=w/5,sy=h/3;
        setLayout(null);
        setBounds(0,0,W,H);
        JTextField TF = new JTextField();
        TF.setHorizontalAlignment(JTextField.RIGHT);
        TF.setBounds(sx,sy,2*sx+3*w,h);
        TF.setEditable(false);
        add(TF);
        BtnPressed = new BtnAction(TF);
        String[] BtnTxt = {"1","2","3","+","4","5","6","-","7","8","9","/",".","=","*"};
        for(int i=0;i<BtnTxt.length;i++) {
            addBtn(sx+(w+sx)*(i%4),(2*sy+h)+(sy+h)*(i/4),w,h,BtnTxt[i],BtnPressed);
        }
        JButton BtnC = new JButton("C");
        BtnC.setBounds(4*sx+3*w,sy,w,h);
        BtnC.addActionListener(BtnPressed);
        BtnC.setFocusPainted(false);
        BtnC.setForeground(Color.RED);
        add(BtnC);
    }
    void addBtn(int i, int j, int w, int h, String txt, ActionListener AcList) {
        JButton b = new JButton(txt);
        b.setBounds(i,j,w,h);
        b.setFocusPainted(false);
        b.addActionListener(AcList);
        add(b);
    }
}
class BtnAction implements ActionListener{
    public JTextField TF;
    private boolean start;
    private boolean point;
    private String cmnd;
    private double result;
    private void onStart() {
        start=true;
        point=true;
        cmnd="C";
        result=0;
        TF.setText("0.0");
    }
    private void calc() {
        double x;
        x=Double.parseDouble(TF.getText());
        if(cmnd.equals("*")) result*=x;
        else if(cmnd.equals("/")) result/=x;
        else if(cmnd.equals("-")) result-=x;
        else if(cmnd.equals("+")) result+=x;
        else result=x;
        TF.setText(Double.toString(result));
    }
    BtnAction(JTextField TF){
        this.TF=TF;
        onStart();
    }
    public void actionPerformed(ActionEvent ae){
        String str = ae.getActionCommand();
        if(str.equals("C")) {
            onStart();
            return;
        }
        if(str.equals("+")|str.equals("-")|str.equals("*")|str.equals("*")|str.equals("=")){
            calc();
            cmnd=str;
            start=true;
            point=true;
            return;
        }
        if(start){
            if(str.equals(".")) {
                TF.setText("0.");
                point=false;
                start=false;
                return;
            }
            else {
                TF.setText(str);
                start=false;
                return;
            }
        }
        else {
            if(str.equals(".")) {
                str=point?str:"";
                point=false;
            }
            if(TF.getText().equals("0")&!str.equals(".")) TF.setText(str);
            else TF.setText(TF.getText()+str);
        }
    }
}
class MyCalculator{
    public static void main(String[] args) {
        new CalculatorFrame();
    }
}
