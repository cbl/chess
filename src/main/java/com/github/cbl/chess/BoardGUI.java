import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class BoardGUI extends JFrame implements ActionListener {
	
//	GameData data;
	
	JButton newGame;
	JButton resign;
	JButton back;
	JButton foward;
	JButton savepgm;
	JButton savealg;
	JButton loadpgm;
	JButton loadalg;
	JTextArea gamelog;
	JButton tile0;
	JButton tile1;
	JButton tile2;
	JButton tile3;
	JButton tile4;
	JButton tile5;
	JButton tile6;
	JButton tile7;
	JButton tile8;
	JButton tile9;
	JButton tile10;
	JButton tile11;
	JButton tile12;
	JButton tile13;
	JButton tile14;
	JButton tile15;
	JButton tile16;
	JButton tile17;
	JButton tile18;
	JButton tile19;
	JButton tile20;
	JButton tile21;
	JButton tile22;
	JButton tile23;
	JButton tile24;
	JButton tile25;
	JButton tile26;
	JButton tile27;
	JButton tile28;
	JButton tile29;
	JButton tile30;
	JButton tile31;
	JButton tile32;
	JButton tile33;
	JButton tile34;
	JButton tile35;
	JButton tile36;
	JButton tile37;
	JButton tile38;
	JButton tile39;
	JButton tile40;
	JButton tile41;
	JButton tile42;
	JButton tile43;
	JButton tile44;
	JButton tile45;
	JButton tile46;
	JButton tile47;
	JButton tile48;
	JButton tile49;
	JButton tile50;
	JButton tile51;
	JButton tile52;
	JButton tile53;
	JButton tile54;
	JButton tile55;
	JButton tile56;
	JButton tile57;
	JButton tile58;
	JButton tile59;
	JButton tile60;
	JButton tile61;
	JButton tile62;
	JButton tile63;
	
	public BoardGUI(/*GameData d*/)
	{
//		data = d;
		int tileSize = 75;

		//Buttons
		newGame = new JButton();
		newGame.setBounds(25, tileSize*10, 2*tileSize, tileSize);
		newGame.setText("New Game");
		newGame.setFocusable(false);
		newGame.setForeground(Color.LIGHT_GRAY);
		newGame.setBackground(Color.black);
//		newGame.addActionListener(e -> );
		
		resign = new JButton();
		resign.setBounds(2*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		resign.setText("Resign");
		resign.setFocusable(false);
		resign.setForeground(Color.LIGHT_GRAY);
		resign.setBackground(Color.black);
//		resign.addActionListener(e -> );
		
		back = new JButton();
		back.setBounds(4*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		back.setText("Move back");
		back.setFocusable(false);
		back.setForeground(Color.LIGHT_GRAY);
		back.setBackground(Color.black);
//		back.addActionListener(e -> );
		
		foward = new JButton();
		foward.setBounds(6*tileSize+25, tileSize*10, 2*tileSize, tileSize);
		foward.setText("Move Foward");
		foward.setFocusable(false);
		foward.setForeground(Color.LIGHT_GRAY);
		foward.setBackground(Color.black);
//		foward.addActionListener(e -> );
		
		savepgm = new JButton();
		savepgm.setBounds(25, tileSize*11, 2*tileSize, tileSize);
		savepgm.setText("Save as PGM");
		savepgm.setFocusable(false);
		savepgm.setForeground(Color.LIGHT_GRAY);
		savepgm.setBackground(Color.black);
//		savepgm.addActionListener(e -> );
		
		savealg = new JButton();
		savealg.setBounds(25+2*tileSize, tileSize*11, 2*tileSize, tileSize);
		savealg.setText("Save as ALG");
		savealg.setFocusable(false);
		savealg.setForeground(Color.LIGHT_GRAY);
		savealg.setBackground(Color.black);
//		savealg.addActionListener(e -> );
		
		loadpgm = new JButton();
		loadpgm.setBounds(25+4*tileSize, tileSize*11, 2*tileSize, tileSize);
		loadpgm.setText("Load PGM");
		loadpgm.setFocusable(false);
		loadpgm.setForeground(Color.LIGHT_GRAY);
		loadpgm.setBackground(Color.black);
//		loadpgm.addActionListener(e -> );
		
		loadalg = new JButton();
		loadalg.setBounds(25+6*tileSize, tileSize*11, 2*tileSize, tileSize);
		loadalg.setText("Load ALG");
		loadalg.setFocusable(false);
		loadalg.setForeground(Color.LIGHT_GRAY);
		loadalg.setBackground(Color.black);
//		loadalg.addActionListener(e -> );
		
		gamelog = new JTextArea();
		gamelog.setBounds(10*tileSize,2*tileSize,3*tileSize, 3*tileSize);

		//Tiles
		tile63 = new JButton();
		tile63.setBackground(new Color(81,73,54));
		tile63.setBounds((0*tileSize),(0*tileSize),tileSize,tileSize);
		tile63.addActionListener(e -> tile63.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile62 = new JButton();
		tile62.setBackground(new Color(39,26,12));
		tile62.setBounds((1*tileSize),(0*tileSize),tileSize,tileSize);
		tile62.addActionListener(e -> tile62.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile61 = new JButton();
		tile61.setBackground(new Color(81,73,54));
		tile61.setBounds((2*tileSize),(0*tileSize),tileSize,tileSize);
		tile61.addActionListener(e -> tile61.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile60 = new JButton();
		tile60.setBackground(new Color(39,26,12));
		tile60.setBounds((3*tileSize),(0*tileSize),tileSize,tileSize);
		tile60.addActionListener(e -> tile60.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile59 = new JButton();
		tile59.setBackground(new Color(81,73,54));
		tile59.setBounds((4*tileSize),(0*tileSize),tileSize,tileSize);
		tile59.addActionListener(e -> tile59.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile58 = new JButton();
		tile58.setBackground(new Color(39,26,12));
		tile58.setBounds((5*tileSize),(0*tileSize),tileSize,tileSize);
		tile58.addActionListener(e -> tile58.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile57 = new JButton();
		tile57.setBackground(new Color(81,73,54));
		tile57.setBounds((6*tileSize),(0*tileSize),tileSize,tileSize);
		tile57.addActionListener(e -> tile57.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile56 = new JButton();
		tile56.setBackground(new Color(39,26,12));
		tile56.setBounds((7*tileSize),(0*tileSize),tileSize,tileSize);
		tile56.addActionListener(e -> tile56.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile55 = new JButton();
		tile55.setBackground(new Color(39,26,12));
		tile55.setBounds((0*tileSize),(1*tileSize),tileSize,tileSize);
		tile55.addActionListener(e -> tile55.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile54 = new JButton();
		tile54.setBackground(new Color(81,73,54));
		tile54.setBounds((1*tileSize),(1*tileSize),tileSize,tileSize);
		tile54.addActionListener(e -> tile54.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile53 = new JButton();
		tile53.setBackground(new Color(39,26,12));
		tile53.setBounds((2*tileSize),(1*tileSize),tileSize,tileSize);
		tile53.addActionListener(e -> tile53.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile52 = new JButton();
		tile52.setBackground(new Color(81,73,54));
		tile52.setBounds((3*tileSize),(1*tileSize),tileSize,tileSize);
		tile52.addActionListener(e -> tile52.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile51 = new JButton();
		tile51.setBackground(new Color(39,26,12));
		tile51.setBounds((4*tileSize),(1*tileSize),tileSize,tileSize);
		tile51.addActionListener(e -> tile51.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile50 = new JButton();
		tile50.setBackground(new Color(81,73,54));
		tile50.setBounds((5*tileSize),(1*tileSize),tileSize,tileSize);
		tile50.addActionListener(e -> tile50.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile49 = new JButton();
		tile49.setBackground(new Color(39,26,12));
		tile49.setBounds((6*tileSize),(1*tileSize),tileSize,tileSize);
		tile49.addActionListener(e -> tile49.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile48 = new JButton();
		tile48.setBackground(new Color(81,73,54));
		tile48.setBounds((7*tileSize),(1*tileSize),tileSize,tileSize);
		tile48.addActionListener(e -> tile48.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile47 = new JButton();
		tile47.setBackground(new Color(81,73,54));
		tile47.setBounds((0*tileSize),(2*tileSize),tileSize,tileSize);
		tile47.addActionListener(e -> tile47.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile46 = new JButton();
		tile46.setBackground(new Color(39,26,12));
		tile46.setBounds((1*tileSize),(2*tileSize),tileSize,tileSize);
		tile46.addActionListener(e -> tile46.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile45 = new JButton();
		tile45.setBackground(new Color(81,73,54));
		tile45.setBounds((2*tileSize),(2*tileSize),tileSize,tileSize);
		tile45.addActionListener(e -> tile45.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile44 = new JButton();
		tile44.setBackground(new Color(39,26,12));
		tile44.setBounds((3*tileSize),(2*tileSize),tileSize,tileSize);
		tile44.addActionListener(e -> tile44.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile43 = new JButton();
		tile43.setBackground(new Color(81,73,54));
		tile43.setBounds((4*tileSize),(2*tileSize),tileSize,tileSize);
		tile43.addActionListener(e -> tile43.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile42 = new JButton();
		tile42.setBackground(new Color(39,26,12));
		tile42.setBounds((5*tileSize),(2*tileSize),tileSize,tileSize);
		tile42.addActionListener(e -> tile42.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile41 = new JButton();
		tile41.setBackground(new Color(81,73,54));
		tile41.setBounds((6*tileSize),(2*tileSize),tileSize,tileSize);
		tile41.addActionListener(e -> tile41.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile40 = new JButton();
		tile40.setBackground(new Color(39,26,12));
		tile40.setBounds((7*tileSize),(2*tileSize),tileSize,tileSize);
		tile40.addActionListener(e -> tile40.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile39 = new JButton();
		tile39.setBackground(new Color(39,26,12));
		tile39.setBounds((0*tileSize),(3*tileSize),tileSize,tileSize);
		tile39.addActionListener(e -> tile39.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile38 = new JButton();
		tile38.setBackground(new Color(81,73,54));
		tile38.setBounds((1*tileSize),(3*tileSize),tileSize,tileSize);
		tile38.addActionListener(e -> tile38.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile37 = new JButton();
		tile37.setBackground(new Color(39,26,12));
		tile37.setBounds((2*tileSize),(3*tileSize),tileSize,tileSize);
		tile37.addActionListener(e -> tile37.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile36 = new JButton();
		tile36.setBackground(new Color(81,73,54));
		tile36.setBounds((3*tileSize),(3*tileSize),tileSize,tileSize);
		tile36.addActionListener(e -> tile36.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile35 = new JButton();
		tile35.setBackground(new Color(39,26,12));
		tile35.setBounds((4*tileSize),(3*tileSize),tileSize,tileSize);
		tile35.addActionListener(e -> tile35.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile34 = new JButton();
		tile34.setBackground(new Color(81,73,54));
		tile34.setBounds((5*tileSize),(3*tileSize),tileSize,tileSize);
		tile34.addActionListener(e -> tile34.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile33 = new JButton();
		tile33.setBackground(new Color(39,26,12));
		tile33.setBounds((6*tileSize),(3*tileSize),tileSize,tileSize);
		tile33.addActionListener(e -> tile33.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile32 = new JButton();
		tile32.setBackground(new Color(81,73,54));
		tile32.setBounds((7*tileSize),(3*tileSize),tileSize,tileSize);
		tile32.addActionListener(e -> tile32.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile31 = new JButton();
		tile31.setBackground(new Color(81,73,54));
		tile31.setBounds((0*tileSize),(4*tileSize),tileSize,tileSize);
		tile31.addActionListener(e -> tile31.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile30 = new JButton();
		tile30.setBackground(new Color(39,26,12));
		tile30.setBounds((1*tileSize),(4*tileSize),tileSize,tileSize);
		tile30.addActionListener(e -> tile30.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile29 = new JButton();
		tile29.setBackground(new Color(81,73,54));
		tile29.setBounds((2*tileSize),(4*tileSize),tileSize,tileSize);
		tile29.addActionListener(e -> tile29.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile28 = new JButton();
		tile28.setBackground(new Color(39,26,12));
		tile28.setBounds((3*tileSize),(4*tileSize),tileSize,tileSize);
		tile28.addActionListener(e -> tile28.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile27 = new JButton();
		tile27.setBackground(new Color(81,73,54));
		tile27.setBounds((4*tileSize),(4*tileSize),tileSize,tileSize);
		tile27.addActionListener(e -> tile27.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile26 = new JButton();
		tile26.setBackground(new Color(39,26,12));
		tile26.setBounds((5*tileSize),(4*tileSize),tileSize,tileSize);
		tile26.addActionListener(e -> tile26.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile25 = new JButton();
		tile25.setBackground(new Color(81,73,54));
		tile25.setBounds((6*tileSize),(4*tileSize),tileSize,tileSize);
		tile25.addActionListener(e -> tile25.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile24 = new JButton();
		tile24.setBackground(new Color(39,26,12));
		tile24.setBounds((7*tileSize),(4*tileSize),tileSize,tileSize);
		tile24.addActionListener(e -> tile24.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile23 = new JButton();
		tile23.setBackground(new Color(39,26,12));
		tile23.setBounds((0*tileSize),(5*tileSize),tileSize,tileSize);
		tile23.addActionListener(e -> tile23.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile22 = new JButton();
		tile22.setBackground(new Color(81,73,54));
		tile22.setBounds((1*tileSize),(5*tileSize),tileSize,tileSize);
		tile22.addActionListener(e -> tile22.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile21 = new JButton();
		tile21.setBackground(new Color(39,26,12));
		tile21.setBounds((2*tileSize),(5*tileSize),tileSize,tileSize);
		tile21.addActionListener(e -> tile21.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile20 = new JButton();
		tile20.setBackground(new Color(81,73,54));
		tile20.setBounds((3*tileSize),(5*tileSize),tileSize,tileSize);
		tile20.addActionListener(e -> tile20.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile19 = new JButton();
		tile19.setBackground(new Color(39,26,12));
		tile19.setBounds((4*tileSize),(5*tileSize),tileSize,tileSize);
		tile19.addActionListener(e -> tile19.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile18 = new JButton();
		tile18.setBackground(new Color(81,73,54));
		tile18.setBounds((5*tileSize),(5*tileSize),tileSize,tileSize);
		tile18.addActionListener(e -> tile18.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile17 = new JButton();
		tile17.setBackground(new Color(39,26,12));
		tile17.setBounds((6*tileSize),(5*tileSize),tileSize,tileSize);
		tile17.addActionListener(e -> tile17.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile16 = new JButton();
		tile16.setBackground(new Color(81,73,54));
		tile16.setBounds((7*tileSize),(5*tileSize),tileSize,tileSize);
		tile16.addActionListener(e -> tile16.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile15 = new JButton();
		tile15.setBackground(new Color(81,73,54));
		tile15.setBounds((0*tileSize),(6*tileSize),tileSize,tileSize);
		tile15.addActionListener(e -> tile15.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile14 = new JButton();
		tile14.setBackground(new Color(39,26,12));
		tile14.setBounds((1*tileSize),(6*tileSize),tileSize,tileSize);
		tile14.addActionListener(e -> tile14.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile13 = new JButton();
		tile13.setBackground(new Color(81,73,54));
		tile13.setBounds((2*tileSize),(6*tileSize),tileSize,tileSize);
		tile13.addActionListener(e -> tile13.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile12 = new JButton();
		tile12.setBackground(new Color(39,26,12));
		tile12.setBounds((3*tileSize),(6*tileSize),tileSize,tileSize);
		tile12.addActionListener(e -> tile12.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile11 = new JButton();
		tile11.setBackground(new Color(81,73,54));
		tile11.setBounds((4*tileSize),(6*tileSize),tileSize,tileSize);
		tile11.addActionListener(e -> tile11.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile10 = new JButton();
		tile10.setBackground(new Color(39,26,12));
		tile10.setBounds((5*tileSize),(6*tileSize),tileSize,tileSize);
		tile10.addActionListener(e -> tile10.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile9 = new JButton();
		tile9.setBackground(new Color(81,73,54));
		tile9.setBounds((6*tileSize),(6*tileSize),tileSize,tileSize);
		tile9.addActionListener(e -> tile9.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile8 = new JButton();
		tile8.setBackground(new Color(39,26,12));
		tile8.setBounds((7*tileSize),(6*tileSize),tileSize,tileSize);
		tile8.addActionListener(e -> tile8.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile7 = new JButton();
		tile7.setBackground(new Color(39,26,12));
		tile7.setBounds((0*tileSize),(7*tileSize),tileSize,tileSize);
		tile7.addActionListener(e -> tile7.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile6 = new JButton();
		tile6.setBackground(new Color(81,73,54));
		tile6.setBounds((1*tileSize),(7*tileSize),tileSize,tileSize);
		tile6.addActionListener(e -> tile6.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile5 = new JButton();
		tile5.setBackground(new Color(39,26,12));
		tile5.setBounds((2*tileSize),(7*tileSize),tileSize,tileSize);
		tile5.addActionListener(e -> tile5.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile4 = new JButton();
		tile4.setBackground(new Color(81,73,54));
		tile4.setBounds((3*tileSize),(7*tileSize),tileSize,tileSize);
		tile4.addActionListener(e -> tile4.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile3 = new JButton();
		tile3.setBackground(new Color(39,26,12));
		tile3.setBounds((4*tileSize),(7*tileSize),tileSize,tileSize);
		tile3.addActionListener(e -> tile3.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile2 = new JButton();
		tile2.setBackground(new Color(81,73,54));
		tile2.setBounds((5*tileSize),(7*tileSize),tileSize,tileSize);
		tile2.addActionListener(e -> tile2.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile1 = new JButton();
		tile1.setBackground(new Color(39,26,12));
		tile1.setBounds((6*tileSize),(7*tileSize),tileSize,tileSize);
		tile1.addActionListener(e -> tile1.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));

		tile0 = new JButton();
		tile0.setBackground(new Color(81,73,54));
		tile0.setBounds((7*tileSize),(7*tileSize),tileSize,tileSize);
		tile0.addActionListener(e -> tile0.setBorder(BorderFactory.createLineBorder(new Color(255,255,0))));
		
		
		//Axis lable
		JPanel xCoordinatesPanel = new JPanel();
		xCoordinatesPanel.setBounds(0, 8*tileSize, 8*tileSize, 2*tileSize);
		xCoordinatesPanel.setBackground(new Color(230,248,220));
		JLabel xCoordinatesLabel = new JLabel();
		xCoordinatesLabel.setText("A B C D E F G H");
		xCoordinatesLabel.setFont(new Font("Mx Boli", Font.PLAIN,tileSize));
		xCoordinatesPanel.add(xCoordinatesLabel);

		JPanel yCoordinatesPanel1 = new JPanel();
		yCoordinatesPanel1.setBounds(8*tileSize,(7*tileSize),tileSize,tileSize);
		yCoordinatesPanel1.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel1 = new JLabel();
		yCoordinatesLabel1.setText("1");
		yCoordinatesLabel1.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel1.add(yCoordinatesLabel1);

		JPanel yCoordinatesPanel2 = new JPanel();
		yCoordinatesPanel2.setBounds(8*tileSize,(6*tileSize),tileSize,tileSize);
		yCoordinatesPanel2.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel2 = new JLabel();
		yCoordinatesLabel2.setText("2");
		yCoordinatesLabel2.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel2.add(yCoordinatesLabel2);

		JPanel yCoordinatesPanel3 = new JPanel();
		yCoordinatesPanel3.setBounds(8*tileSize,(5*tileSize),tileSize,tileSize);
		yCoordinatesPanel3.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel3 = new JLabel();
		yCoordinatesLabel3.setText("3");
		yCoordinatesLabel3.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel3.add(yCoordinatesLabel3);

		JPanel yCoordinatesPanel4 = new JPanel();
		yCoordinatesPanel4.setBounds(8*tileSize,(4*tileSize),tileSize,tileSize);
		yCoordinatesPanel4.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel4 = new JLabel();
		yCoordinatesLabel4.setText("4");
		yCoordinatesLabel4.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel4.add(yCoordinatesLabel4);

		JPanel yCoordinatesPanel5 = new JPanel();
		yCoordinatesPanel5.setBounds(8*tileSize,(3*tileSize),tileSize,tileSize);
		yCoordinatesPanel5.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel5 = new JLabel();
		yCoordinatesLabel5.setText("5");
		yCoordinatesLabel5.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel5.add(yCoordinatesLabel5);

		JPanel yCoordinatesPanel6 = new JPanel();
		yCoordinatesPanel6.setBounds(8*tileSize,(2*tileSize),tileSize,tileSize);
		yCoordinatesPanel6.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel6 = new JLabel();
		yCoordinatesLabel6.setText("6");
		yCoordinatesLabel6.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel6.add(yCoordinatesLabel6);

		JPanel yCoordinatesPanel7 = new JPanel();
		yCoordinatesPanel7.setBounds(8*tileSize,(1*tileSize),tileSize,tileSize);
		yCoordinatesPanel7.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel7 = new JLabel();
		yCoordinatesLabel7.setText("7");
		yCoordinatesLabel7.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel7.add(yCoordinatesLabel7);

		JPanel yCoordinatesPanel8 = new JPanel();
		yCoordinatesPanel8.setBounds(8*tileSize,(0*tileSize),tileSize,tileSize);
		yCoordinatesPanel8.setBackground(new Color(230,248,220));
		JLabel yCoordinatesLabel8 = new JLabel();
		yCoordinatesLabel8.setText("8");
		yCoordinatesLabel8.setFont(new Font("My Boli", Font.PLAIN,tileSize));
		yCoordinatesPanel8.add(yCoordinatesLabel8);

		
		//Frame
		JFrame frame = new JFrame();
		frame.add(tile0);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(1000,1000);
		frame.setVisible(true);
		frame.getContentPane().setBackground(new Color(230,248,220));
		frame.add(resign);
		frame.add(newGame);
		frame.add(back);
		frame.add(foward);
		frame.add(savepgm);
		frame.add(loadpgm);
		frame.add(savealg);
		frame.add(loadalg);
		frame.add(gamelog);
		frame.add(tile0);
		frame.add(tile1);
		frame.add(tile2);
		frame.add(tile3);
		frame.add(tile4);
		frame.add(tile5);
		frame.add(tile6);
		frame.add(tile7);
		frame.add(tile8);
		frame.add(tile9);
		frame.add(tile10);
		frame.add(tile11);
		frame.add(tile12);
		frame.add(tile13);
		frame.add(tile14);
		frame.add(tile15);
		frame.add(tile16);
		frame.add(tile17);
		frame.add(tile18);
		frame.add(tile19);
		frame.add(tile20);
		frame.add(tile21);
		frame.add(tile22);
		frame.add(tile23);
		frame.add(tile24);
		frame.add(tile25);
		frame.add(tile26);
		frame.add(tile27);
		frame.add(tile28);
		frame.add(tile29);
		frame.add(tile30);
		frame.add(tile31);
		frame.add(tile32);
		frame.add(tile33);
		frame.add(tile34);
		frame.add(tile35);
		frame.add(tile36);
		frame.add(tile37);
		frame.add(tile38);
		frame.add(tile39);
		frame.add(tile40);
		frame.add(tile41);
		frame.add(tile42);
		frame.add(tile43);
		frame.add(tile44);
		frame.add(tile45);
		frame.add(tile46);
		frame.add(tile47);
		frame.add(tile48);
		frame.add(tile49);
		frame.add(tile50);
		frame.add(tile51);
		frame.add(tile52);
		frame.add(tile53);
		frame.add(tile54);
		frame.add(tile55);
		frame.add(tile56);
		frame.add(tile57);
		frame.add(tile58);
		frame.add(tile59);
		frame.add(tile60);
		frame.add(tile61);
		frame.add(tile62);
		frame.add(tile63);
		frame.add(yCoordinatesPanel1);
		frame.add(yCoordinatesPanel2);
		frame.add(yCoordinatesPanel3);
		frame.add(yCoordinatesPanel4);
		frame.add(yCoordinatesPanel5);
		frame.add(yCoordinatesPanel6);
		frame.add(yCoordinatesPanel7);
		frame.add(yCoordinatesPanel8);
		frame.add(xCoordinatesPanel);
	
	}
}
