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
	public int val; //���-�� �������� ���
	public boolean status; //���������� �� ������
	public boolean rec; //��������� �� ��������
	public boolean clickL; //������ �� ����� ������� ����
	public boolean clickR; //������ �� ������ ������� ����
	public int index; //������ � ������� ������
	public boolean open;  //������� �� ������
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
