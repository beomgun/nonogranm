package nonogram;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import nonogram.gameManager;
public class panel_Game extends JPanel implements ActionListener,MouseListener{
	private static final long serialVersionUID = 1L;
	gameManager gm;
	
	
	private int xSize = 10;  // x�����
	private int ySize = 10;  // y�� ����
	private int board[][] = new int[xSize][ySize];
	
	private JButton btn_play_pause= new JButton("PAUSE");
	private JPanel pPause = new JPanel();
	
	private JButton btn_pause_back= new JButton("BACK");
	private JButton btn_pause_main=new JButton("MAIN");
	
	private boolean game_Pause = false;
	private int board_start_x,board_start_y;
	private int xboxSize,yboxSize;
	
	private JLabel lab_time = new JLabel("00:00");

	int size_clientWidth=685;
	int size_clientHeight=660;

	int PauseBoxWidth = (int)(size_clientWidth/1.2);
	int PauseBoxHeight = (int)(size_clientHeight/1.1);


	Image offScr;
	Graphics offG;  
	
	
	
	public panel_Game(gameManager gm) {
		this.gm = gm;

		size_clientWidth = gm.getContentPane().getSize().width;
		size_clientHeight = gm.getContentPane().getSize().height;
		setBounds(0, 0, size_clientWidth, size_clientHeight);
		
		setLayout(null);
		createPausePanel();
		makeButtonAndEventHandle();
	}
	

	public void paintComponent (Graphics g) { //g�� ���� �ִ� ��ü
		super.paintComponent(g);
		
		offScr = createImage(size_clientWidth, size_clientHeight);
		offG = offScr.getGraphics();
		
		
		xboxSize = (int)(size_clientWidth/1.7)/xSize;
		yboxSize = (int)(size_clientHeight/1.7)/ySize;
		
		
		
		
		// �簢�� ������ �������� x��ǥ (start_x) : size_clientWidth/3
		// �簢�� ������ �������� y��ǥ (start_y) : (int)(size_clientHeight/2.5)
		board_start_x = size_clientWidth/3-30;
		board_start_y = (int)(size_clientHeight/2.5)-30;
		
		// ���� ������ ���� line ���
		for(int j=0;j<ySize+1;j++) {
			offG.drawLine( board_start_x -120, board_start_y + j*yboxSize, board_start_x +xSize*xboxSize,  board_start_y+j*yboxSize);
			if (j % 5 ==0)
				offG.drawLine( board_start_x -120, board_start_y + j*yboxSize-1, board_start_x +xSize*xboxSize,  board_start_y+j*yboxSize-1);
		}
		// �׳� �簢�� ����������� �������θ� �ص� �ȵǳ�?
		
		// ���� line ���
		for (int i=0; i<xSize+1; i++) {
			offG.drawLine( board_start_x +i*xboxSize,  board_start_y-120, board_start_x +i*xboxSize,  board_start_y + ySize*yboxSize);
			if (i % 5 ==0)
				offG.drawLine( board_start_x +i*xboxSize-1,  board_start_y-120, board_start_x +i*xboxSize-1,  board_start_y + ySize*yboxSize);
		}
		// ������� ��
		
		g.drawImage(offScr, 0, 0, this);
	}
	
	
	public void createPausePanel() {
		pPause.setLayout(null);
		pPause.setSize(PauseBoxWidth,PauseBoxHeight);
		pPause.setLocation((size_clientWidth-PauseBoxWidth)/2,(size_clientHeight-PauseBoxHeight)/2);
		pPause.setBackground(Color.YELLOW);
		add(pPause);       //��ư�� �����־�� ��ư�� ����������
		pPause.setVisible(false);
		
	}
	
	
	public void makeButtonAndEventHandle() {
		lab_time.setSize(size_clientWidth/5, size_clientHeight/10);
		lab_time.setLocation(size_clientWidth/17, size_clientHeight/10);
		lab_time.setFont(new Font("Serif",Font.PLAIN,50));
		add(lab_time);
		
		btn_play_pause.setSize(size_clientWidth/5,size_clientHeight/10 );
		btn_play_pause.setLocation(size_clientWidth/17,(int)(size_clientHeight/4.7));
		add(btn_play_pause);
		
		
		
		// PAUSE//
		btn_pause_back.setSize(PauseBoxWidth/2,PauseBoxHeight/5);
		btn_pause_back.setLocation((PauseBoxWidth-PauseBoxWidth/2)/2, PauseBoxHeight/5);
		pPause.add(btn_pause_back);
		
		btn_pause_main.setSize(PauseBoxWidth/2,PauseBoxHeight/5);
		btn_pause_main.setLocation((PauseBoxWidth-PauseBoxWidth/2)/2,PauseBoxHeight*3/5);
		pPause.add(btn_pause_main);
		///////////////////
		
		
		// Listener //
		btn_play_pause.addActionListener(this);
		btn_pause_back.addActionListener(this);
		btn_pause_main.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String strCmd = e.getActionCommand(); // Ŭ���� ��ư�� �̸��� ����
		
		if (strCmd.equals("PAUSE")) {
			go_game_pause();
		}
		else if(strCmd.equals("BACK")) {
			go_game_play();
		}
		else if (strCmd.equals("MAIN")) {
			///////////////////////////	
			btn_play_pause.setVisible(true);
			lab_time.setVisible(true);	
			pPause.setVisible(false);
			gameManager.manager.setPanel_main();
			///////////////////////////////////
		}
	}
	
	
	
	public void go_game_pause() {
		btn_play_pause.setVisible(false);
		lab_time.setVisible(false);
		pPause.setVisible(true);
	}
	
	
	public void go_game_play() {
		btn_play_pause.setVisible(true);
		lab_time.setVisible(true);		
		pPause.setVisible(false);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		if (x >= board_start_x && x <= board_start_x +xSize*xboxSize) {
			if (y>= board_start_y && y <= board_start_y + ySize*yboxSize) {
				lab_time.setText(x+" "+y);
				return;
			}
		}	}


	@Override
	public void mouseEntered(MouseEvent e) {	}


	@Override
	public void mouseExited(MouseEvent e) {	}


	@Override
	public void mousePressed(MouseEvent e) {	}


	@Override
	public void mouseReleased(MouseEvent e) {	}
	



	
}