package minesapper;
import java.awt.Color;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Button extends JFrame implements  Serializable{
	public int val; //кол-во соседних мин
	public boolean status; //установлен ли флажек
	public boolean rec; //проходила ли рекурсия
	public boolean clickL; //зажата ли левая клавиша мыши
	public boolean clickR; //зажата ли правая клавиша мыши
	public int index; //индекс в массиве клеток
	public boolean open;  //открыта ли ячейка
	public transient JButton but=new JButton();
	public transient JLabel lab=new JLabel ();
	public String labelText;

	public Button(int a, int b, int c, int d){
		but.setBounds(a,b,c,d);
		lab.setBounds(a,b,c,d);
		lab.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setVerticalAlignment(SwingConstants.CENTER);

	}


}
