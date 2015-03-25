package minesapper;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/** Minesweeper
 * 
 * @author AO
 *@throws No exception
 *@param Arguments
 *@see Button
 */
public class Minesapper extends JFrame implements ActionListener, MouseListener {
    Container cnt;
    int timer = 0;
    static int x;
    static int y;
    static int n;
    static int minesClick;
    static int[] mine;
    Button[] buts;
    static int currentVal;
    String right = "/right_mine.gif";
    String wrong = "/wrong_mine.gif";
    String ok = "/ok.gif";
    String minePic = "/mine.gif";
    boolean endGame = false;
    File file = new File("mineData.bin");
    JLabel min = new JLabel("Mines:");
    JLabel wid = new JLabel("Width:");
    JLabel hght = new JLabel("Height:");
    JTextField mines = new JTextField("" + n);
    JTextField width = new JTextField("" + x);
    JTextField height = new JTextField("" + y);
    JButton save = new JButton("Save");
    JButton start = new JButton("New");
    JButton load = new JButton("Load");
    JLabel minLeft;
    Timer tm;
    JLabel time=new JLabel("Time: "+timer);

    public static void main(String[] args) {
	new Minesapper();
    }

    public Minesapper() { //конструктор для первого запуска
	x = 10;
	y = 10;
	n = 10;
	minesClick = n;
	init(100, 100);
    }

    public Minesapper(int x1, int y1, int n1, int xCord, int yCord) { //конструктор для новой игры с новыми параметрами
	x = x1;
	y = y1;
	n = n1;
	minesClick = n;
	init(xCord, yCord);
    }

    public Minesapper(int x1, int y1, int n1, int minesClick1, 
	    int[] m, Button[] b, int xCord, int yCord) { //конструктор для восстановления сохраненнной игры
	x = x1;
	y = y1;
	n = n1;
	minesClick = minesClick1;
	init(m, b, xCord, yCord);
    }

    public void win(int xCord, int yCord) { //создание и прорисовка окна
	setDefaultCloseOperation(EXIT_ON_CLOSE);//при нажатии на х программа закрывается и удаляется из памяти
	if (x * 20 <= 475) { //ширина окна в зависимости от колва столбцов
	    setBounds(xCord, yCord, 475, y * 20 + 105);
	}
	else {
	    setBounds(xCord, yCord, x * 20 + 60, y * 20 + 105);
	}
	setVisible(true);//видимость окна
	setTitle("Minesweeper"); //заголовок окна
	cnt = getContentPane();//создание контейнера окна для получения доступа к свойствам
	cnt.setLayout(null);
	min.setBounds(0, 0, 40, 20);
	cnt.add(min);
	mines.setBounds(40, 0, 30, 20);
	cnt.add(mines);
	wid.setBounds(75, 0, 40, 20);
	cnt.add(wid);
	width.setBounds(120, 0, 30, 20);
	cnt.add(width);
	hght.setBounds(155, 0, 45, 20);
	cnt.add(hght);
	height.setBounds(205, 0, 30, 20);
	cnt.add(height);
	start.setBounds(240, 0, 65, 20);
	cnt.add(start);
	save.setBounds(310, 0, 70, 20);
	cnt.add(save);
	load.setBounds(385, 0, 70, 20);
	if (!file.exists()) {
	    load.setEnabled(false);
	}
	cnt.add(load);
	start.addActionListener(this);
	save.addActionListener(this);
	load.addActionListener(this);
	mines.setText("" + n);
	width.setText("" + x);
	height.setText("" + y);
	minLeft = new JLabel("Mines left: " + minesClick);
	minLeft.setBounds(0, 25, 90, 20);
	cnt.add(minLeft);
	tm = new Timer(1000, this);
	tm.start();
	time.setBounds(110, 25, 70, 20);
	cnt.add(time);
    }

    public void init(int[] m, Button[] b, int xCord, int yCord) { //Метод для восстановления игры
	buts=b;
	mine=m;
	win(xCord, yCord);
	int i = 0;
	for (int j = 45; j <= y * 20 + 25; j = j + 20) {
	    for (int k = 20;k <= x*20; k = k + 20) {
		buts[i].but = new JButton();
		buts[i].lab = new JLabel();
		buts[i].but.setBounds(k, j, 20, 20);
		buts[i].lab.setBounds(k, j, 20, 20);
		buts[i].lab.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		buts[i].lab.setHorizontalAlignment(SwingConstants.CENTER);
		buts[i].lab.setVerticalAlignment(SwingConstants.CENTER);
		if (buts[i].status == true) {
		    buts[i].but.setIcon(new ImageIcon(getClass().getResource(ok)));
		}
		if(buts[i].val != 0 && buts[i].val != 10) {
		    switch(buts[i].val) {  //в зависимости от кол-ва соседних мин ставим цифру и красим ее 
		    case 1:
			buts[i].lab.setForeground(new Color (66, 62, 220));
			break;
		    case 2:
			buts[i].lab.setForeground(new Color(85, 146, 48));
			break;
		    case 3:
			buts[i].lab.setForeground(new Color(192, 28, 63));
			break;
		    case 4:
			buts[i].lab.setForeground(new Color(10, 18, 76));
			break;
		    case 5:
			buts[i].lab.setForeground(new Color(33, 158, 174));
			break;
		    case 6:
			buts[i].lab.setForeground(new Color(181, 201, 11));
			break;
		    case 7:
			buts[i].lab.setForeground(new Color(100, 100, 50));
			break;
		    case 8:
			buts[i].lab.setForeground(new Color (200, 100, 100));
			break;
		    }
		    buts[i].lab.setText("" + buts[i].val);
		    Font f = new Font("" + buts[i].val, Font.BOLD, 16);
		    buts[i].lab.setFont(f);
		}
		if (buts[i].val==10) {
		    buts[i].lab.setIcon(new ImageIcon(getClass().getResource(minePic)));
		}
		cnt.add(buts[i].but);
		cnt.add(buts[i].lab);
		if (buts[i].open == true) {
		    buts[i].but.setVisible(false);
		}
		repaint();
		buts[i].but.addActionListener(this);
		buts[i].but.addMouseListener(this);
		buts[i].lab.addMouseListener(this);
		i++;
	    }
	}
    }

    public void init(int xCord, int yCord) { //Запуск новой, расстановка мин и просчет цифр для клеток 
	buts = new Button[x * y];
	mine = new int[n];
	win(xCord, yCord);
	int i = 0;
	a: while (i < mine.length) { //генерация расположения мин
	    double tmp = Math.random();
	    int z = (int)(tmp * x * y);
	    for (int j:mine) {
		if(z==j) {
		    continue a;
		}
	    }
	    mine[i] = z;
	    i++;
	}
	i=0;
	for (int j = 45;j <= y * 20 + 25; j = j + 20) { //расстановка кнопок
	    for (int k = 20;k <= x * 20; k = k + 20) {
		buts[i] = new Button(k, j, 20, 20);
		cnt.add(buts[i].but);
		cnt.add(buts[i].lab);
		buts[i].index = i;
		repaint();
		for (int m = 0; m < mine.length; m++) {
		    if (i == mine[m]) {
			buts[i].val = 10;
			buts[i].lab.setIcon(new ImageIcon(getClass().getResource(minePic)));
		    }
		}
		buts[i].but.addActionListener(this);
		buts[i].but.addMouseListener(this);
		buts[i].lab.addMouseListener(this);
		i++;
	    }
	}
	for (int z=0; z<buts.length; z++) {//подсчет цифр для клеток соседствующих с минами
	    int num = 0;
	    if (buts[z].val != 10) {
		Button []tmp = cellEnv(z);
		for (Button b : tmp) {
		    if (b != null) {
			if (b.val == 10) { 
			    num++;
			}
		    }
		}
		if (num != 0)
		{
		    buts[z].val = num;
		    switch (num)  //в зависимости от кол-ва соседних мин ставим цифру и красим ее 
		    {
		    case 1:
			buts[z].lab.setForeground(new Color (66, 62, 220));
			break;
		    case 2:
			buts[z].lab.setForeground(new Color(85, 146, 48));
			break;
		    case 3:
			buts[z].lab.setForeground(new Color(192, 28, 63));
			break;
		    case 4:
			buts[z].lab.setForeground(new Color(10, 18, 76));
			break;
		    case 5:
			buts[z].lab.setForeground(new Color(33, 158, 174));
			break;
		    case 6:
			buts[z].lab.setForeground(new Color(181, 201, 11));
			break;
		    case 7:
			buts[z].lab.setForeground(new Color(100, 100, 50));
			break;
		    case 8:
			buts[z].lab.setForeground(new Color (200, 100, 100));
			break;
		    }
		    buts[z].lab.setText("" + num);
		    Font f = new Font("" + num, Font.BOLD, 16);
		    buts[z].lab.setFont(f);
		}
	    }
	}
    }

    public void open(int i) { //рекурсивный метод для открытия поля
	buts[i].but.setVisible(false);
	buts[i].open = true;
	buts[i].rec = true;
	Button[] tmp = cellEnv(i);
	for (Button b : tmp) {
	    if (b != null && b.val != 10) {
		if (b.rec != true && b.status != true) {
		    if (b.val != 0) {
			b.but.setVisible(false);
			b.open = true;
		    }
		    else
			open(b.index);
		}
	    }
	}
    }

    public Button[] cellEnv(int buttonIndex) {  //Метод, который возвращает массив ячеек, которые окружают ячейку с переданным индексом  
	int[] center = new int[] {-x-1, -x, -x+1, -1, 1, x-1, x, x+1};
	int[] left = new int[] {-x, -x+1, 1, x, x+1};
	int[] right = new int[] {-x-1, -x, -1, x-1, x};
	int j = 0;
	Button[] returnMas = new Button[8];
	if (buttonIndex % x == 0) {
	    for (int i : left) {
		if ((buttonIndex + i) < 0 || (buttonIndex + i) >= buts.length) {
		    returnMas[j] = null;
		    j++;
		}
		else {
		    returnMas[j] = buts[buttonIndex + i];
		    j++;
		}
	    }
	}
	else if ((buttonIndex + 1) % x == 0) {
	    for (int i : right)
		if ((buttonIndex + i) <0 || (buttonIndex + i) >= buts.length){
		    returnMas[j] = null;
		    j++;
		}
		else {
		    returnMas[j] = buts[buttonIndex + i];
		    j++;
		}
	}
	else {
	    for (int i : center)
		if((buttonIndex + i) < 0 || (buttonIndex + i) >= buts.length){
		    returnMas[j] = null;
		    j++;
		}
		else {
		    returnMas[j] = buts[buttonIndex + i];
		    j++;
		}
	}
	return returnMas;	
    }

    public void checkWin() { //Проверка победил ли пользователь
	int counter = 0;
	for (int j = 0; j < buts.length; j++) {
	    if(buts[j].val != 10 && !buts[j].but.isVisible()) {
		counter++;
	    }
	}
	if (counter == x * y - n) {
	    endGame = true;
	    dialogBox("You win.");
	}
    }

    public void loose() { //В случае открытия мины открывается все поле
	for (int i = 0; i < buts.length; i++) {
	    buts[i].but.setVisible(false);
	    if (buts[i].status==true) {
		if (buts[i].val == 10)
		    buts[i].lab.setIcon(new ImageIcon(getClass().getResource(right)));
		else {
		    buts[i].lab.setText("");
		    buts[i].lab.setIcon(new ImageIcon(getClass().getResource(wrong)));
		}
	    }
	}
	endGame = true;
	dialogBox("You loose.");	
    }

    public void dialogBox(String s) {  //Диалоговое окно для новой игры 
	int pane = JOptionPane.showConfirmDialog(null, s + " New game?");
	if (pane == 0) {
	    int xCord = this.getX();
	    int yCord = this.getY();
	    new Minesapper(Integer.parseInt(width.getText()), 
		    Integer.parseInt(height.getText()), Integer.parseInt(mines.getText()),
		    xCord, yCord);
	    this.dispose();
	}
	else if (pane == 1) {
	    this.dispose();
	}
    }

    public void actionPerformed(ActionEvent ae) {
	Object src = ae.getSource();
	if (src == start) { //новая игра
	    int xCord = this.getX();
	    int yCord = this.getY();
	    new Minesapper(Integer.parseInt(width.getText()),
		    Integer.parseInt(height.getText()),Integer.parseInt(mines.getText()),
		    xCord,yCord);
	    this.dispose();
	}
	if (src == save) //сохранение игры
	{
	    try {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeInt(x);
		oos.writeInt(y);
		oos.writeInt(n);
		oos.writeInt(minesClick);
		oos.writeObject(buts);
		oos.writeObject(mine);
		oos.close();
		load.setEnabled(true);
	    }
	    catch (Exception e) {
		System.out.println(e);
	    }
	}
	if (src == load) { //загрузка игры
	    int[] mine1 = null;
	    Button []buts1 = null;
	    try {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		x = ois.readInt();
		y = ois.readInt();
		n = ois.readInt();
		minesClick = ois.readInt();
		buts1 = (Button[]) ois.readObject();
		mine1 = (int[])ois.readObject();
	    }
	    catch (Exception e) {
		System.out.println(e);
	    }
	    int xCord = this.getX();
	    int yCord = this.getY();
	    new Minesapper(x, y, n, minesClick, mine1, buts1, xCord, yCord);
	    this.dispose();
	}
	if (src == tm) {
	    timer++;
	    if(!endGame) {
		time.setText("Time: " + timer);
	    }
	}
    }

    @Override
    public void mousePressed (MouseEvent me) {
	if (me.getButton() == 3) {// реакция на нажатие правой клавишей (для П+Л)
	    Object src = me.getSource();
	    for (int i = 0; i < buts.length; i++) {
		if (src == buts[i].lab) {
		    if (buts[i].clickL==true){
			Button b[] = cellEnv(i);
			for (Button tmp : b)
			    if (tmp != null && tmp.open == false && tmp.status != true) {
				if (tmp.val != 0) {
				    tmp.lab.setText("");
				}
				tmp.lab.setIcon(new ImageIcon());
				tmp.but.setVisible(false);
			    }
		    }
		    buts[i].clickR = true;
		}
	    }
	}

	if (me.getButton() == 1) { //реакция на клик правой клавишей + левая, для показа неоткрытых соседних клеток
	    Object src = me.getSource();
	    for (int i = 0; i < buts.length; i++) {
		if (src == buts[i].lab) {
		    if (buts[i].clickR == true) {
			Button b[] = cellEnv(i);
			for (Button tmp : b) {
			    if (tmp != null && tmp.open == false && tmp.status != true) {
				if (tmp.val != 0)
				    tmp.lab.setText("");
				tmp.lab.setIcon(new ImageIcon());
				tmp.but.setVisible(false);
			    }
			}
		    }
		    buts[i].clickL = true;
		}
	    }
	}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	if(e.getButton() == 3){ //разминирование
	    Object src = e.getSource();
	    for (int i = 0; i < buts.length; i++) {
		if (src == buts[i].but) {
		    if (buts[i].status == true) {
			buts[i].status = false;
			buts[i].but.setIcon(new ImageIcon());
			minesClick++;
		    }
		    else {
			buts[i].but.setIcon(new ImageIcon(getClass().getResource(ok)));
			minesClick--;
			buts[i].status = true;
		    }
		    minLeft.setText("Mines left: " + minesClick);
		}
	    }
	}
	if (e.getButton() == 1) { //открытие ячеек
	    Object src = e.getSource();
	    for (int i = 0; i < buts.length; i++) {
		if (src == buts[i].but) {
		    if (buts[i].status != true) {
			if (buts[i].val != 0) {
			    buts[i].but.setVisible(false);
			    buts[i].open = true;
			}
			else {
			    open(i);
			}
			if (buts[i].val == 10)
			    loose();
			else checkWin();
		    }
		}
	    }
	}
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
	if (e.getButton() == 3 || e.getButton() == 1){//реакция на отпускание левой клавиши после нажатия ЛВ+ПР
	    Object src = e.getSource();
	    for (int i = 0; i < buts.length; i++) {
		if (src == buts[i].lab) {
		    if (buts[i].clickR == true && buts[i].clickL == true) {
			Button b[] = cellEnv(i);
			for (Button tmp : b) {
			    if (tmp != null && tmp.open == false) {
				tmp.but.setVisible(true);
				if (tmp.val < 10 && tmp.val > 0) {
				    tmp.lab.setText("" + tmp.val);
				}
				else if(tmp.val == 10) { 
				    tmp.lab.setIcon(new ImageIcon(getClass().getResource(minePic)));
				}
			    }
			}
			int j = 0;
			for (Button tmp : b) {
			    if(tmp != null)
				if (tmp.status == true)
				    j++;
			}
			if (j == buts[i].val) {
			    for (Button tmp:b) {
				if (tmp != null) {
				    if (tmp.val == 10 && tmp.status != true) {
					loose();
				    }
				    if (tmp != null && tmp.open == false && tmp.status != true) {
					tmp.but.setVisible(false);
					tmp.open = true;
					if (tmp.val == 0) {
					    open(tmp.index);
					}
				    }
				}
			    }
			}

		    }
		    buts[i].clickR = false;
		    buts[i].clickL = false;
		}
	    }
	    checkWin();
	}
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}