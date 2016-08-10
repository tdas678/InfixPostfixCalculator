import javax.swing.*;
import java.util.Stack;
import java.awt.*;
import java.awt.event.*;

public class newCalculator  {

	public JLabel t =new JLabel("0");
	public JFrame calFrame;
	public JPanel buttonPanel;
	public JPanel modePanel;
	public float result=0.0f,num=0;
	public String opr="=";
	public boolean entr=false;
	JButton b;
	JButton infix=new JButton("Infix");
	JButton postfix=new JButton("Postfix");
	JButton enter=new JButton("Enter");
	public String mode="infix";
	public boolean start=true;
	public boolean k=false;
	public Stack<Float> stack=new Stack();
	
	
	newCalculator(){
		prep();
	}
	
	public void addButtons(JPanel P,String buttons){
		int count=buttons.length();
		for(int i=0;i<count;i++){
			b=new JButton(buttons.substring(i,i+1));
			b.setActionCommand(buttons.substring(i, i+1));
		    b.addActionListener(new ButtonClick());
			P.add(b);
			
			
		}
	}
	
	public static void main(String args[]){
		newCalculator n=new newCalculator();
		
	}
	public void apply(String n){
		float sValue = new Float (n).floatValue();
        char c = opr.charAt (0);
        switch (c) {
            case '=':   result  = sValue;
                        start=false;
                        break;
            case '+':   result += sValue;
                        break;
            case '-':   result -= sValue;
                        break;
            case '*':   result *= sValue;
                        break;
            case '/':   result /= sValue;
                        break;
        }
        t.setText (String.valueOf(result));
    }
	
	
	public void applyPOs(String opr){
		char c = opr.charAt (0);
		float sValue;
    switch (c) {
        case '=':   result=stack.pop();
                    break;
        case '+':    sValue = stack.pop();
                     result=stack.pop();        	        
        	        result += sValue;
                    break;
        case '-':     sValue = stack.pop();
                     result=stack.pop();
                   result -= sValue;
                    break;
        case '*':   
        	 sValue = stack.pop();
             result=stack.pop();
             result *= sValue;
                    break;
        case '/':  sValue = stack.pop();
        result=stack.pop();  
        	
        	result /= sValue;
                    break;
    }
    stack.push(result);
    t.setText (String.valueOf(result));

	}
	public void prep(){
	calFrame=new JFrame("Java Calculator");
	calFrame.setSize(700, 700);
	calFrame.setLayout(new GridLayout(3,0));
	
	calFrame.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent w){
			System.exit(0);
		}
	});
	
	buttonPanel=new JPanel();
	buttonPanel.setLayout(new GridLayout(4,4));
	addButtons(buttonPanel,"789/");
	addButtons(buttonPanel,"456*");
	addButtons(buttonPanel,"123-");
	addButtons(buttonPanel,"0=+.");
	modePanel=new JPanel();
	modePanel.setLayout(new GridLayout(1,3));
	modePanel.add(infix);
	modePanel.add(postfix);
	modePanel.add(enter);
	infix.addActionListener(new ButtonClick());
	postfix.addActionListener(new ButtonClick());
	enter.addActionListener(new ButtonClick());
	
	calFrame.add(t);
	calFrame.add(modePanel);
	t.setForeground(Color.cyan);
	t.setBackground(Color.blue);
	calFrame.add(buttonPanel);
	calFrame.setVisible(true);
	
	
	}
	
	public class ButtonClick implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			String key=e.getActionCommand();
			if(key.equalsIgnoreCase("Postfix"))
				mode="postfix";
			else if(key.equalsIgnoreCase("Infix"))
				mode="infix";
			
			
			else if("0123456789.".indexOf(key)!= -1 && mode.equalsIgnoreCase("infix")){
				if(start){
				t.setText(key);
				start=false;
				}else{
					// num=Float.parseFloat(key);
					t.setText(t.getText()+key);
				}
				
			}else if(mode.equalsIgnoreCase("infix")){//is operator
				if(!start){
					
					apply(t.getText());
					start=true;
				}
				opr=key;
			}
			
			else if("0123456789.".indexOf(key)!=-1 && mode.equalsIgnoreCase("postfix")){
					if(start && ! entr){
						t.setText(key);
						start=false;
					}else if (!entr && !start){
						t.setText(t.getText()+key);
						
						
					}else if(k && entr){
						String p=t.getText();
						float result=new Float (p).floatValue();	
						stack.push(result);
						t.setText(key);
						k=false;
						}else if(!k && entr){
						t.setText(t.getText()+key);
						entr=false;
					}
				}else if(key.equalsIgnoreCase("enter") && mode.equalsIgnoreCase("postfix")){
					entr=true;
				 k=true;
					
				}
				else {
					if(entr && k){
						String p=t.getText();
					    float result=new Float (p).floatValue();
						stack.push(result);
						opr=key;
						applyPOs(key);
						start=true;
						k=false;
					}
					
				}
			}
			
		}
		
		
	}
	
