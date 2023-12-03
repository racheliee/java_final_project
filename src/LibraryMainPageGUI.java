

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.DefaultComboBoxModel;

public class LibraryMainPageGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JPanel topPanel;
	private final JLabel appTitle = new JLabel("SKKU Library");
	private JLabel appBookIcon;
	private JPanel leftPanel;
	private JPanel midPanel;

	private List<String> announcement_list = new ArrayList<String>();
	private JLabel libraryHourTitle;
	private JList libraryHourList;
	private JButton searchButton;
	private JTextField searchTextField;
	private JComboBox<String> searchByGenreComboBox;
	private JLabel searchByGenreTitle;
	private JPanel rightPanel;
	private JLabel announceTitle;
	private JLabel newBooksLabel;
	private JList announceList;
	private JList newArrivalsList;
	public JButton logInButton;
	private JPanel changingPanel;
	private CardLayout cardLayout;

	MainPagePanel mainPanel;
	ProfilePanel profilePanel;
	BookListPanel bookListPanel;

	private List<User> userList;
	boolean loggedIn = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryMainPageGUI window = new LibraryMainPageGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibraryMainPageGUI() {
		initialize();
	}

	// test line

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// scan the user lists from file and save to list
		readUserFile();

		// create GUI
		frame = new JFrame();
		frame.setBounds(100, 100, 850, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		appTitle.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// doesn't work
				cardLayout.first(mainPanel);
			}
		});
		appTitle.setIcon(new ImageIcon(LibraryMainPageGUI.class.getResource("/images/book2_r.png")));
		appTitle.setFont(new Font("Monaco", Font.PLAIN, 18));
		topPanel.add(appTitle);

		appBookIcon = new JLabel("");
		appBookIcon.setIcon(new ImageIcon(LibraryMainPageGUI.class.getResource("/images/book2_r.png")));
		topPanel.add(appBookIcon);

		announcement_list.add(null);

		rightPanel = new JPanel();
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		GridBagLayout gbl_rightPanel = new GridBagLayout();
		gbl_rightPanel.columnWidths = new int[] { 180, 0 };
		gbl_rightPanel.rowHeights = new int[] { 0, 16, 0, 0, 0, 0 };
		gbl_rightPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightPanel.rowWeights = new double[] { 0.0, 1.0, 6.0, 1.0, 6.0, Double.MIN_VALUE };
		rightPanel.setLayout(gbl_rightPanel);

		logInButton = new JButton("     Log In     ");
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loggedIn == false) {
					LogInDialog logInDialog = new LogInDialog(LibraryMainPageGUI.this, userList);
					logInDialog.setVisible(true);

					if (logInDialog.willSignUp) {
						SignUpDialog signUpDialog = new SignUpDialog(LibraryMainPageGUI.this, userList);
						signUpDialog.setVisible(true);
						if (signUpDialog.isSignUpSuccessful()) {
							User newUser = new User(signUpDialog.getUserName(), signUpDialog.getPassword());
							userList.add(newUser);
							writeUserFile(newUser);
						}
					}

					if (logInDialog.isLogInSuccessful()) {
						logInButton.setText(logInDialog.getUser().getUserName());
						loggedIn = true;
					}
					logInDialog.dispose();
				} else {
					// doesn't work
					cardLayout.first(profilePanel);
				}
			}
		});
		GridBagConstraints gbc_logInButton = new GridBagConstraints();
		gbc_logInButton.insets = new Insets(0, 0, 5, 0);
		gbc_logInButton.gridx = 0;
		gbc_logInButton.gridy = 0;
		rightPanel.add(logInButton, gbc_logInButton);

		announceTitle = new JLabel("Announcement");
		announceTitle.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_announceTitle = new GridBagConstraints();
		gbc_announceTitle.insets = new Insets(0, 0, 5, 0);
		gbc_announceTitle.gridx = 0;
		gbc_announceTitle.gridy = 1;
		rightPanel.add(announceTitle, gbc_announceTitle);

		announceList = new JList();
		GridBagConstraints gbc_announceList = new GridBagConstraints();
		gbc_announceList.insets = new Insets(0, 0, 5, 0);
		gbc_announceList.fill = GridBagConstraints.BOTH;
		gbc_announceList.gridx = 0;
		gbc_announceList.gridy = 2;
		rightPanel.add(announceList, gbc_announceList);

		newBooksLabel = new JLabel("New Arrivals");
		GridBagConstraints gbc_newBooksLabel = new GridBagConstraints();
		gbc_newBooksLabel.insets = new Insets(0, 0, 5, 0);
		gbc_newBooksLabel.gridx = 0;
		gbc_newBooksLabel.gridy = 3;
		rightPanel.add(newBooksLabel, gbc_newBooksLabel);

		newArrivalsList = new JList();
		GridBagConstraints gbc_newArrivalsList = new GridBagConstraints();
		gbc_newArrivalsList.fill = GridBagConstraints.BOTH;
		gbc_newArrivalsList.gridx = 0;
		gbc_newArrivalsList.gridy = 4;
		rightPanel.add(newArrivalsList, gbc_newArrivalsList);

		midPanel = new JPanel();
		frame.getContentPane().add(midPanel, BorderLayout.CENTER);
		GridBagLayout gbl_midPanel = new GridBagLayout();
		gbl_midPanel.columnWidths = new int[] { 0, 30, 0, 0, 30, 30, 0, 30, 30 };
		gbl_midPanel.rowHeights = new int[] { 0, 46, 0, 0, 0 };
		gbl_midPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0 };
		gbl_midPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		midPanel.setLayout(gbl_midPanel);

		searchTextField = new JTextField();
		GridBagConstraints gbc_searchTextField = new GridBagConstraints();
		gbc_searchTextField.insets = new Insets(0, 0, 5, 5);
		gbc_searchTextField.gridwidth = 8;
		gbc_searchTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchTextField.gridx = 1;
		gbc_searchTextField.gridy = 1;
		midPanel.add(searchTextField, gbc_searchTextField);
		searchTextField.setColumns(10);

		searchButton = new JButton("Search");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO : search by title
				cardLayout.show(changingPanel, "BookListPanel");
			}
		});
		GridBagConstraints gbc_searchButton = new GridBagConstraints();
		gbc_searchButton.insets = new Insets(0, 0, 5, 0);
		gbc_searchButton.gridx = 9;
		gbc_searchButton.gridy = 1;
		midPanel.add(searchButton, gbc_searchButton);

		searchByGenreTitle = new JLabel("Search by Genre:");
		GridBagConstraints gbc_searchByGenreTitle = new GridBagConstraints();
		gbc_searchByGenreTitle.gridwidth = 2;
		gbc_searchByGenreTitle.insets = new Insets(0, 0, 5, 5);
		gbc_searchByGenreTitle.gridx = 1;
		gbc_searchByGenreTitle.gridy = 2;
		midPanel.add(searchByGenreTitle, gbc_searchByGenreTitle);

		searchByGenreComboBox = new JComboBox<String>();
		searchByGenreComboBox.setModel(new DefaultComboBoxModel(
				new String[] { "Genre", "Fiction", "Non-Fiction", "Mystery", "Romance", "Science Fiction" }));
		GridBagConstraints gbc_searchByGenreComboBox = new GridBagConstraints();
		gbc_searchByGenreComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_searchByGenreComboBox.gridwidth = 6;
		gbc_searchByGenreComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_searchByGenreComboBox.gridx = 3;
		gbc_searchByGenreComboBox.gridy = 2;
		midPanel.add(searchByGenreComboBox, gbc_searchByGenreComboBox);

		changingPanel = new JPanel();
		GridBagConstraints gbc_changingPanel = new GridBagConstraints();
		gbc_changingPanel.gridwidth = 10;
		gbc_changingPanel.insets = new Insets(0, 0, 0, 5);
		gbc_changingPanel.fill = GridBagConstraints.BOTH;
		gbc_changingPanel.gridx = 0;
		gbc_changingPanel.gridy = 3;
		midPanel.add(changingPanel, gbc_changingPanel);

		cardLayout = new CardLayout(0, 0);
		changingPanel.setLayout(cardLayout);

		// add panels to card layout
		mainPanel = new MainPagePanel();
		changingPanel.add(mainPanel, "MainPagePanel");

		profilePanel = new ProfilePanel();
		changingPanel.add(profilePanel, "ProfilePanel");

		bookListPanel = new BookListPanel();
		changingPanel.add(bookListPanel, "BookListPanel");

	}

	public void readUserFile() {
		userList = new ArrayList<User>();

		FileInputStream userFile = null;
		try {
			userFile = new FileInputStream("users.txt");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found", "Error", JOptionPane.ERROR_MESSAGE);
		}

		try (Scanner scanner = new Scanner(userFile)) {
			int isUsername = 0;

			while (scanner.hasNext()) {
				// current line is username
				if (isUsername % 2 == 0) {
					User newUser = new User();
					newUser.setUserName(scanner.nextLine());
					userList.add(newUser);
					isUsername++;
				}
				// current line is password
				else {
					userList.get(userList.size() - 1).setPassword(scanner.nextLine());
					isUsername--;
				}
			}
		}
	}

	public void writeUserFile(User newUser) {
		FileOutputStream userFile = null;
		try {
			userFile = new FileOutputStream("users.txt");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "User.txt not found", "Error", JOptionPane.ERROR_MESSAGE);
		}

		try (PrintWriter writer = new PrintWriter(userFile)) {
			for (int i = 0; i < userList.size(); i++) {
				writer.println(userList.get(i).getUserName());
				writer.println(userList.get(i).getPassword());
			}
		}

	}

}