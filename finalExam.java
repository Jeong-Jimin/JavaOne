package finalExam;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

class SaveText implements Serializable{
}

class MyFrame extends JFrame {
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel Textinfo;
	private JPanel ChangePanel; //---문자열 바꾸기 할때 사용
	private JLabel text_Color;
	private JLabel text_Size;
	private JLabel Line_num;
	private JLabel Column_num;
	private JLabel print_Line;
	private JLabel print_Column;
	private JLabel change_before_label;
	private JLabel change_after_label;
	private JButton TextChange;
	private JButton Black;
	private JButton Red;
	private JButton Blue;
	private JButton Change_Text_Size;
	private JMenuBar menubar;
	private JMenu File;
	private JMenuItem Save;
	private JMenuItem Load;
	private JMenuItem Exit;
	private JTextField font_Size;
	private JTextField change_before;
	private JTextField change_after;
	private JTextArea InputText;
	private Color OriginalButton;
	private JButton CurrentButton;
	private Font font;
	final String EXTENSION 		= 		".txt"; 
	final String DIRECTORY 		= 		"C://";	//파일 저장 경로
	FileOutputStream fos 			= 		null;
	ObjectOutputStream oos 		=		null;
	FileInputStream fis 				= 		null;
	ObjectInputStream ois 			= 		null;
	char[] TextList = null;
	String SaveText;
		
		
	MyFrame() {
		this.setTitle("기말고사 - 텍스트 에디터");

		// --- 버튼, 라벨, 텍스트 영역 설정
		text_Color 				= 		new JLabel("글자 색 : ");
		Black 						= 		new JButton("Black");
		Red 							= 		new JButton("Red");
		Blue 							= 		new JButton("Blue");
		OriginalButton 			=		Black.getBackground();

		CurrentButton 			= 		new JButton();
		text_Size 					= 		new JLabel("글자 크기 : ");
		font_Size 					= 		new JTextField(2);
		change_before 			= 		new JTextField(2);
		change_after				= 		new JTextField(2);
		Change_Text_Size 	= 		new JButton("글자 크기 변경");
		topPanel 					= 		new JPanel();
		bottomPanel 			= 		new JPanel();
		InputText 					= 		new JTextArea();
		Textinfo						= 		new JPanel();
		ChangePanel				=		new JPanel();
		menubar 					= 		new JMenuBar();
		File 							= 		new JMenu("File");
		Save 						= 		new JMenuItem("Save");
		Load 						= 		new JMenuItem("Load");
		Exit 							= 		new JMenuItem("Exit");
		font 							= 		new Font("Serif",Font.PLAIN,15);
		Line_num					=		new JLabel("라인 수 : ");
		Column_num			=		new JLabel("열 수 : ");
		print_Line					=		new JLabel("0");
		print_Column			=		new JLabel("0");
		TextChange				=		new JButton("바꾸기!");
		change_before_label	=		new JLabel("바꿀 문자");
		change_after_label	=		new JLabel("바뀐 후 문자");
		
		// =============== 메뉴 바 붙이기 ========
		menubar.add(File);
		File.add(Save);
		File.addSeparator();
		File.add(Load);
		File.addSeparator();
		File.add(Exit);
		
		// 메뉴바 기능 설정
		// 저장
		Save.addActionListener(new ActionListener() {
			
			File 	file 				= 		null;

			public void actionPerformed(ActionEvent e) {
					JFileChooser chooser = new JFileChooser();
						chooser.setFileFilter(new FileNameExtensionFilter("txt","txt"));
						int choice = chooser.showSaveDialog(getParent());
						
						//--- 저장 버튼
						if(choice == chooser.APPROVE_OPTION)
							{
								file = chooser.getSelectedFile();	
								}

						//--- 취소 버튼
						else if(choice == chooser.CANCEL_OPTION)
						{

							}

				try {

					String t = InputText.getText();

					fos = new FileOutputStream(file);
					oos = new ObjectOutputStream(fos);

					oos.writeObject(t);

					oos.close();
					oos.flush();
					fos.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					if (fos != null)
						try {
							fos.close();
						} catch (IOException e1) {

						}
					if (oos != null)
						try {
							oos.close();
						} catch (IOException e1) {

						}
				}
			}
		});

		// 열기
		Load.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				File file 							= 		null;
				JFileChooser chooser 	= 		new JFileChooser();
				chooser.setFileFilter(new FileNameExtensionFilter("txt","txt"));
				chooser.showOpenDialog(getParent());
				
				int choice = chooser.showOpenDialog(getParent());
				
				//--- 열기 버튼 눌렀을 떄
				if(choice == chooser.APPROVE_OPTION){
					file = chooser.getSelectedFile();
				}
				
				//취소를 선택 했을 떄
				else if(choice == chooser.CANCEL_OPTION){
					
				}
				
				try {

					fis = new FileInputStream(file);
					ois = new ObjectInputStream(fis);

					String SaveText = (String) ois.readObject();

					ois.close();
					fis.close();
					InputText.setText("");
					InputText.setText(SaveText);
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {
					if (fis != null)
						try {
							fis.close();
						} catch (IOException e1) {

						}
					if (ois != null)
						try {
							ois.close();
						} catch (IOException e1) {
						}
				}

			}
		});

		// 나가기
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// ==================== 메뉴 바 ==============

		// Frame에 패널 붙이기
		Container contentPane 			=			getContentPane(); // --- 현재 패널
		contentPane.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.CENTER);
		
		this.add(ChangePanel,BorderLayout.AFTER_LINE_ENDS);
		this.add(Textinfo,BorderLayout.SOUTH);
		
		// 패널에 Component 붙이기
		topPanel.setLayout(new FlowLayout());
		topPanel.add(text_Color);
		topPanel.add(Black);
		topPanel.add(Red);
		topPanel.add(Blue);
		topPanel.add(text_Size);
		topPanel.add(font_Size);
		topPanel.add(Change_Text_Size);
		
		contentPane.add(InputText); 
		InputText.setFont(font); //--- 기본 설정
		
		//열, 라인 수 표시
		Textinfo.setLayout(new FlowLayout());
		Textinfo.add(Line_num);
		Textinfo.add(print_Line);
		Textinfo.add(Column_num);
		Textinfo.add(print_Column);

		ChangePanel.setLayout(new GridLayout(5,1));
		ChangePanel.add(change_before_label);
		ChangePanel.add(change_before);
		ChangePanel.add(change_after_label);
		ChangePanel.add(change_after);
		ChangePanel.add(TextChange);
		
		InputText.addKeyListener(new KeyAdapter(){
			
			public void keyPressed(KeyEvent e) {
		
				//라인 수 출력, 반응 속도 느림
				int LINE_NUM 			=		InputText.getLineCount();
				String LINE_NUM2 	= 		Integer.toString(LINE_NUM);
				print_Line.setText(LINE_NUM2);
			
				//열 번호 출력, 커서 위치, 반응속도 느림
				int COLUMN_NUM		= 		InputText.getCaretPosition();
				String COLUMN_NUM2 = Integer.toString(COLUMN_NUM);
				print_Column.setText(COLUMN_NUM2);
			}
		});
		
		//----버튼 클릭시 버튼의 색상 변경
		class SetColor {
			SetColor() {
				Black.setBackground(OriginalButton);
				Blue.setBackground(OriginalButton);
				Red.setBackground(OriginalButton);
				CurrentButton.setBackground(Color.ORANGE);
			}
		}

		//버튼 색 변경 및 글자 색 변경(검정)
		Black.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();
				
				InputText.setForeground(Color.BLACK);
			}
		});

		//버튼 색 변경 및 글자 색 변경(파랑)
		Blue.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();
				
				InputText.setForeground(Color.blue);
			}
		});

		//버튼 색 변경 및 글자 색 변경(빨강)
		Red.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();
				InputText.setForeground(Color.RED);
			}
		});

		//글자 크기 변경 버튼
		Change_Text_Size.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent e) {
				String Size = font_Size.getText();
				int fontSize = Integer.parseInt(Size);
				Font font = new Font("Serif",Font.PLAIN,fontSize);
				InputText.setFont(font);	
			}
		});
		
		//문자바꾸기 버튼
		TextChange.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				
			String before = 	change_before.getText();
			char before2 = before.charAt(0);
			String after = change_after.getText();
			char after2 = after.charAt(0);
			
			SaveText = InputText.getText();
			
			
			TextList = new char[SaveText.length()];
			
			//입력된 문자열을 char으로 바꾸기
			for(int i = 0 ; i<SaveText.length() ; i++){
				TextList[i] = SaveText.charAt(i);
			}
			
			for(int i = 0 ; i<SaveText.length() ; i++){
				if(TextList[i] == before2){
					TextList[i] = after2;		
				}
			}
			
			String changed_Text = 		new String();
			
			//--배열을 문자열로 바꿔야 함
			changed_Text 			= 		String.valueOf(TextList);
			InputText.setText(changed_Text);
		
			change_before.setText("");
			change_after.setText("");
			}
		});
		
		//---Frame 설정
		this.setJMenuBar(menubar);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(600, 100, 900, 800);
	}// ---MyFrame Creator
}// --- MyFrame class

public class finalExam {
	public static void main(String ags[]) {
		MyFrame MF = new MyFrame();
	}
}
