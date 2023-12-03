import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton addAnnouncementButton;
	private JButton newArrivalButton;
	private JLabel bookInfoLabel;
	private JScrollPane infoScrollPane;
	private JTable bookInfotable;

	AnnouncementDialog announcementDialog;

	Object data[][];
	private JButton logOutButton;

	/**
	 * Create the panel.
	 */
	public AdminPanel(LibraryMainPageGUI mainGUI, List<Book> bookList) {
		GridBagLayout gridBagLayout_1 = new GridBagLayout();
		gridBagLayout_1.columnWidths = new int[] { 30, 0, 30 };
		gridBagLayout_1.rowHeights = new int[] { 30, 30, 30, 14, 30, 155, 30 };
		gridBagLayout_1.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gridBagLayout_1.rowWeights = new double[] { 0.0, 0.1, 0.1, 0.0, 0.1, 1.0 };
		setLayout(gridBagLayout_1);

		addAnnouncementButton = new JButton("Add Announcement");
		addAnnouncementButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				announcementDialog = new AnnouncementDialog(mainGUI.announcementList, mainGUI.announcementList.size(),
						mainGUI.frame,
						true,
						true);
				announcementDialog.setVisible(true);
				mainGUI.addAnnouncement(mainGUI.announcementList.get(mainGUI.announcementList.size() - 1).getTitle(),
						mainGUI.announcementList.get(mainGUI.announcementList.size() - 1).getContents(),
						mainGUI.announcementList.size() - 1);
				announcementDialog.dispose();

			}
		});
		GridBagConstraints gbc_addAnnouncementButton = new GridBagConstraints();
		gbc_addAnnouncementButton.insets = new Insets(0, 0, 5, 5);
		gbc_addAnnouncementButton.gridx = 1;
		gbc_addAnnouncementButton.gridy = 1;
		add(addAnnouncementButton, gbc_addAnnouncementButton);

		newArrivalButton = new JButton("Add New Book");
		newArrivalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainGUI.addNewArrival();
			}
		});
		GridBagConstraints gbc_newArrivalButton = new GridBagConstraints();
		gbc_newArrivalButton.insets = new Insets(0, 0, 5, 5);
		gbc_newArrivalButton.gridx = 1;
		gbc_newArrivalButton.gridy = 2;
		add(newArrivalButton, gbc_newArrivalButton);

		bookInfoLabel = new JLabel("Book Information:");
		bookInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_bookInfoLabel = new GridBagConstraints();
		gbc_bookInfoLabel.fill = GridBagConstraints.BOTH;
		gbc_bookInfoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_bookInfoLabel.gridx = 1;
		gbc_bookInfoLabel.gridy = 4;
		add(bookInfoLabel, gbc_bookInfoLabel);

		infoScrollPane = new JScrollPane();
		GridBagConstraints gbc_infoScrollPane = new GridBagConstraints();
		gbc_infoScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_infoScrollPane.fill = GridBagConstraints.BOTH;
		gbc_infoScrollPane.gridx = 1;
		gbc_infoScrollPane.gridy = 5;
		add(infoScrollPane, gbc_infoScrollPane);

		DefaultTableModel bookInfoModel = new DefaultTableModel(data,
				new Object[] { "Book Title", "Author", "Genre", "Total Copies", "Num Rented" });
		bookInfotable = new JTable(bookInfoModel);

		for (Book book : bookList) {
			bookInfoModel.addRow(new Object[] { book.getTitle(), book.getAuthor(), book.getGenre(),
					String.valueOf(book.getCopies().size()), String.valueOf(book.getAvailableCopies().size()) });
		}
		bookInfoModel.fireTableDataChanged();
		infoScrollPane.setViewportView(bookInfotable);

		logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainGUI.logOutPressed();
			}
		});
		GridBagConstraints gbc_logOutButton = new GridBagConstraints();
		gbc_logOutButton.insets = new Insets(0, 0, 0, 5);
		gbc_logOutButton.gridx = 1;
		gbc_logOutButton.gridy = 6;
		add(logOutButton, gbc_logOutButton);

	}
}