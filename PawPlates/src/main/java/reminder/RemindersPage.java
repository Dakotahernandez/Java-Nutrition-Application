package reminder;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import frame.TemplateFrame;
import frame.Theme;
import org.json.JSONArray;
import org.json.JSONObject;
import user.User;
import frame.LoginPage;
import user.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RemindersPage extends TemplateFrame {

    private final JCheckBox weightCheckbox;
    private final JCheckBox mealsCheckbox;
    private final JCheckBox waterCheckbox;
    private final JCheckBox workoutCheckbox;
    private final JCheckBox motivationCheckbox;
    private final JCheckBox meanMotivationCheckBox;
    private final JButton confirmRemindersButton;
    private final JCheckBox useAltEmailCheckbox;
    private final JTextField altEmailField;
    private final JLabel altEmailLabel;

    public RemindersPage() {
        super();
        setTitle("Daily Reminders");
        addMenuBarPanel();

        // Instruction Label
        JLabel presetLabel = new JLabel("Select daily reminders to receive:");
        presetLabel.setForeground(Theme.FG_LIGHT);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        centerPanel.add(presetLabel, c);

        // Reminder options
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBackground(Theme.BG_DARK);

        weightCheckbox = new JCheckBox("Track your weight");
        mealsCheckbox = new JCheckBox("Log your meals");
        waterCheckbox = new JCheckBox("Stay hydrated");
        workoutCheckbox = new JCheckBox("Go work out");
        motivationCheckbox = new JCheckBox("Stay motivated");
        meanMotivationCheckBox = new JCheckBox("Get motivated by HATE");

        JCheckBox[] boxes = {
                weightCheckbox, mealsCheckbox, waterCheckbox,
                workoutCheckbox, motivationCheckbox, meanMotivationCheckBox
        };
        for (JCheckBox box : boxes) {
            box.setBackground(Theme.BG_DARKER);
            box.setForeground(Theme.FG_LIGHT);
            checkboxPanel.add(box);
        }

        c.gridx = 1;
        centerPanel.add(checkboxPanel, c);

        // Optional email toggle
        useAltEmailCheckbox = new JCheckBox("Send to different email?");
        useAltEmailCheckbox.setBackground(Theme.BG_DARK);
        useAltEmailCheckbox.setForeground(Theme.FG_LIGHT);
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        centerPanel.add(useAltEmailCheckbox, c);

        // Alternate email label
        altEmailLabel = new JLabel("Alternate Email:");
        altEmailLabel.setForeground(Theme.FG_LIGHT);
        altEmailLabel.setVisible(false);
        c.gridx = 0;
        c.gridy = 2;
        centerPanel.add(altEmailLabel, c);

        // Alternate email field
        altEmailField = new JTextField(20);
        altEmailField.setBackground(Theme.BG_DARKER);
        altEmailField.setForeground(Theme.FG_LIGHT);
        altEmailField.setCaretColor(Theme.FG_LIGHT);
        altEmailField.setVisible(false);
        c.gridx = 1;
        c.gridy = 2;
        centerPanel.add(altEmailField, c);

        // Show/hide alt email field
        useAltEmailCheckbox.addActionListener(e -> {
            boolean show = useAltEmailCheckbox.isSelected();
            altEmailLabel.setVisible(show);
            altEmailField.setVisible(show);
        });

        // Confirm button
        confirmRemindersButton = new JButton("Confirm My Reminders");
        confirmRemindersButton.addActionListener(e -> sendReminderConfirmation());
        addButton(confirmRemindersButton, 3);

        setVisible(true);
    }

    private void sendReminderConfirmation() {
        try {
            Properties config = loadConfigProperties();
            MailjetClient client = new MailjetClient(
                    config.getProperty("MAILJET_API_KEY", ""),
                    config.getProperty("MAILJET_SECRET_KEY", "")
            );

            // Determine recipient
            UserDatabase db = new UserDatabase();
            User currentUser = db.getUserById(LoginPage.CURRENT_USER.getId());

            String toEmail = useAltEmailCheckbox.isSelected()
                    ? altEmailField.getText().trim()
                    : currentUser.getEmail();

            if (toEmail == null || toEmail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Recipient email is required.");
                return;
            }
            //email REGEX
            if (!toEmail.matches("^[\\w.-]+@[\\w.-]+\\.[\\w]+$")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
                return;
            }

            // Collect selected reminders
            List<String> selected = new ArrayList<>();
            if (weightCheckbox.isSelected()) selected.add("✔ Track your weight");
            if (mealsCheckbox.isSelected()) selected.add("✔ Log your meals");
            if (waterCheckbox.isSelected()) selected.add("✔ Stay hydrated");
            if (workoutCheckbox.isSelected()) selected.add("✔ Go work out");
            if (motivationCheckbox.isSelected()) selected.add("✔ Stay motivated");
            if (meanMotivationCheckBox.isSelected()) selected.add("✔ Stay motivated with HATE");

            if (selected.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select at least one reminder.");
                return;
            }

            String htmlSummary = String.join("<br>", selected);
            String plainText = String.join("\n", selected);

            // Send confirmation email
            MailjetRequest confirmRequest = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", config.getProperty("MAILJET_FROM_EMAIL", ""))
                                            .put("Name", config.getProperty("MAILJET_FROM_NAME", "Reminder Bot")))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", toEmail)
                                                    .put("Name", currentUser.getUsername())))
                                    .put(Emailv31.Message.SUBJECT, "You’re Subscribed to Daily Reminders")
                                    .put(Emailv31.Message.TEXTPART, "You're signed up for:\n" + plainText)
                                    .put(Emailv31.Message.HTMLPART,
                                            "<h3>You’ve subscribed to the following reminders:</h3><p>" + htmlSummary + "</p>")
                            ));
            client.post(confirmRequest);

            // Send all delected reminder emails using template IDs from config
            if (weightCheckbox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_WEIGHT");
            }
            if (mealsCheckbox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_MEALS");
            }
            if (waterCheckbox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_WATER");
            }
            if (workoutCheckbox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_WORKOUT");
            }
            if (motivationCheckbox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_MOTIVATION");
            }
            if (meanMotivationCheckBox.isSelected()) {
                sendTemplateEmail(client, config, toEmail, currentUser.getUsername(), "TEMPLATE_ID_MEAN");
            }

            JOptionPane.showMessageDialog(this,
                    "Confirmation + reminders sent to: " + toEmail);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to send confirmation or reminders: " + ex.getMessage());
        }
    }

    private void sendTemplateEmail(MailjetClient client, Properties config, String toEmail, String name, String templateKey) {
        try {
            int templateId = Integer.parseInt(config.getProperty(templateKey, "0"));
            if (templateId == 0) return;

            MailjetRequest request = new MailjetRequest(Emailv31.resource)
                    .property(Emailv31.MESSAGES, new JSONArray()
                            .put(new JSONObject()
                                    .put(Emailv31.Message.FROM, new JSONObject()
                                            .put("Email", config.getProperty("MAILJET_FROM_EMAIL", ""))
                                            .put("Name", config.getProperty("MAILJET_FROM_NAME", "Reminder Bot")))
                                    .put(Emailv31.Message.TO, new JSONArray()
                                            .put(new JSONObject()
                                                    .put("Email", toEmail)
                                                    .put("Name", name)))
                                    .put(Emailv31.Message.TEMPLATEID, templateId)
                                    .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                    .put(Emailv31.Message.SUBJECT, "Your Daily Reminder")
                            ));
            client.post(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Properties loadConfigProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
            return new Properties();
        }
    }
}
