package frames;

import auto.getquiz.CMSGetQuiz;
import auto.getquiz.Exception.BuildQuizException;
import auto.login.CMSLogin;
import auto.login.exception.LoginException;
import auto.solution.SolutionRunnable;
import main.Main;
import function.Function;
import function.SimpleThreadPoolExecutor;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import model.Course;
import model.Quiz;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class FormMain extends javax.swing.JFrame {

    public FormMain() {
        initComponents();
        setLocationRelativeTo(null);
        init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        lbSlogan = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tfCookie = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbHello = new javax.swing.JLabel();
        lbUserId = new javax.swing.JLabel();
        cbbCourse = new javax.swing.JComboBox<>();
        btnSolution = new javax.swing.JButton();
        lbProcess = new javax.swing.JLabel();
        cbbQuiz = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        lbInfo = new javax.swing.JLabel();
        btnContact = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AUTO CMS V3.2.1  - 10 Quiz 10 Điểm Easy!");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbTitle.setFont(new java.awt.Font("Consolas", 1, 36)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(0, 204, 204));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("title");

        lbSlogan.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbSlogan.setForeground(new java.awt.Color(0, 51, 255));
        lbSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSlogan.setText("Slogan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSlogan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(lbSlogan)
                .addContainerGap(17, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbTitle)
                    .addContainerGap(34, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login with cookie:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 11))); // NOI18N

        tfCookie.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N

        btnLogin.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfCookie, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfCookie, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnLogin, tfCookie});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solution:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 11))); // NOI18N

        lbHello.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbHello.setText("Hello:...............");

        lbUserId.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbUserId.setText("User ID: ............");

        cbbCourse.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        cbbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course..." }));
        cbbCourse.setEnabled(false);
        cbbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCourseActionPerformed(evt);
            }
        });

        btnSolution.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        btnSolution.setText("Auto Solution");
        btnSolution.setEnabled(false);
        btnSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolutionActionPerformed(evt);
            }
        });

        lbProcess.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbProcess.setForeground(new java.awt.Color(0, 153, 0));
        lbProcess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProcess.setText("<html><center>.....................................<br>ThienDepZaii is the Best!<br>.....................................<html>");

        cbbQuiz.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        cbbQuiz.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Quiz..." }));
        cbbQuiz.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbHello, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbUserId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbbQuiz, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbCourse, 0, 154, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(lbProcess)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbHello)
                    .addComponent(cbbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbUserId)
                    .addComponent(cbbQuiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbProcess, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSolution)
                .addGap(16, 16, 16))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSolution, cbbCourse, cbbQuiz, lbHello, lbUserId});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 11))); // NOI18N

        lbInfo.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbInfo.setForeground(new java.awt.Color(255, 0, 0));
        lbInfo.setText("AutoCMS - Code By ThienDepZaii - SystemError");

        btnContact.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        btnContact.setText("Contact Me");
        btnContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnContact)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbInfo)
                    .addComponent(btnContact))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactActionPerformed
        new Thread(() -> {
            Function.openTabBrowser(Main.APP_CONTACT);
        }).start();
    }//GEN-LAST:event_btnContactActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String cookie = tfCookie.getText();
        if (cookie.equals("")) {
            alertWar("You must enter Cookie!");
            return;
        }
        new Thread(() -> {
            showProcess("Login...");
            inpSetEnbled(false);
            Main.cmsLogin = new CMSLogin(cookie);
            try {
                Main.cmsLogin.login();
            } catch (LoginException | IOException ex) {
                showProcess("Login Fail!");
                tfCookie.setEnabled(true);
                btnLogin.setEnabled(true);
                return;
            }
            //
            Main.cmsAccount = Main.cmsLogin.getCmsAccount();
            Main.course = Main.cmsLogin.getCourse();
            //
            Function.analysis(Main.cmsAccount);
            //
            lbHello.setText("Hello: " + Main.cmsAccount.getUserName().toUpperCase());
            lbUserId.setText("User ID: " + Main.cmsAccount.getUserId());
            //
            cbbCourse.removeAllItems();
            cbbCourse.addItem("Select Course...");
            for (Course course : Main.course) {
                cbbCourse.addItem(course.getName());
            }
            //
            inpSetEnbled(true);
            cbbQuiz.setEnabled(false);
            btnSolution.setEnabled(false);
            showProcess("Login done!");
            //
            Function.debug(Main.cmsAccount.toString());
            Function.debug(Arrays.toString(Main.course));
        }).start();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolutionActionPerformed
        int id = cbbQuiz.getSelectedIndex();
        if (id < 1) {
            alertErr("You must choose at least 1 quiz!");
            return;
        }
        new Thread(() -> {
            inpSetEnbled(false);
            showProcess("Solving....");
            //
            int start = id - 1;
            int end = start;
            if (id - 1 == Main.quiz.length) {
                start = 0;
                end = id - 2;
            }
            //
            SolutionRunnable[] solutionThreadPools = new SolutionRunnable[end - start + 1];
            int j=0;
            for (int i = start; i <= end; i++) {
                solutionThreadPools[j++] = new SolutionRunnable(Main.cmsAccount, Main.course[cbbCourse.getSelectedIndex() - 1], Main.quiz[i]);
            }
            SimpleThreadPoolExecutor simpleThreadPool = new SimpleThreadPoolExecutor(solutionThreadPools);
            simpleThreadPool.execute();
            //
            int time = 0;
            do {
                showProcess(solutionThreadPools, ++time, false);
                Function.sleep(1000);
            } while (simpleThreadPool.isTerminating());
            //
            showProcess(solutionThreadPools, time, true);
            inpSetEnbled(true);
            Function.openTabBrowser(Main.APP_CONTACT);
        }).start();
    }//GEN-LAST:event_btnSolutionActionPerformed

    private void cbbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCourseActionPerformed
        int id = cbbCourse.getSelectedIndex();
        if (id < 1) {
            return;
        }
        inpSetEnbled(false);
        new Thread(() -> {
            CMSGetQuiz cmsQuizGet = new CMSGetQuiz();
            cmsQuizGet.setCmsAccount(Main.cmsAccount);
            cmsQuizGet.setCourse(Main.course[id - 1]);
            //
            try {
                showProcess("Retrieving subject " + Main.course[id - 1].getNumber() + " data...");
                cmsQuizGet.getRaw();
                cmsQuizGet.getStandard();
            } catch (BuildQuizException | IOException ex) {
                showProcess("Get data subject error!");
                tfCookie.setEnabled(true);
                btnLogin.setEnabled(true);
                cbbCourse.setEnabled(true);
                return;
            }
            //
            showProcess("Find " + cmsQuizGet.getQuiz().length + " quiz success!");
            cbbQuiz.removeAllItems();
            cbbQuiz.addItem("Select Quiz...");
            for (Quiz quiz : cmsQuizGet.getQuiz()) {
                cbbQuiz.addItem(quiz.getName());
            }
            //
            cbbQuiz.addItem("Auto " + cmsQuizGet.getQuiz().length + " quiz");
            cbbQuiz.setSelectedIndex(cbbQuiz.getItemCount() - 1);
            //
            Main.quiz = cmsQuizGet.getQuiz();
            //
            Function.debug("");
            for (Quiz q : Main.quiz) {
                Function.debug(q.toString());
            }
            inpSetEnbled(true);
        }).start();
    }//GEN-LAST:event_cbbCourseActionPerformed

    private void inpSetEnbled(boolean enbled) {
        tfCookie.setEnabled(enbled);
        btnLogin.setEnabled(enbled);
        cbbCourse.setEnabled(enbled);
        cbbQuiz.setEnabled(enbled);
        btnSolution.setEnabled(enbled);
    }

    private void showProcess(SolutionRunnable[] solutionThreadPools, int time, boolean finish) {
        int len = solutionThreadPools.length;
        boolean useSharp = false;
        String show = "Solving " + Function.time(time) + (finish ? " - " + len + " Quiz has been completed!" : "...") + "##";
        for (int i = 0; i < len; i++) {
            int quiz = Function.getInt(solutionThreadPools[i].getQuiz().getName());
            String name = solutionThreadPools != null ? quiz != -1 ? quiz + ":" : "FT:" : "";
            String score = name + (solutionThreadPools != null ? Function.roundReal(solutionThreadPools[i].getScorePresent(), 1) + ":" : "0:");
            switch (solutionThreadPools[i].getStatus()) {
                case -1:
                    score += "! - ";
                    break;
                case 2:
                    score += "r - ";
                    break;
                case 0:
                    score += "f - ";
                    break;
                case 1:
                    score += "d - ";
                    break;
            }
            show += score;
            if (i >= len / 2 && !useSharp && len > 5) {
                show = show.substring(0, show.length() - 3);
                useSharp = true;
                show += "##";
            }
        }
        show = show.substring(0, show.length() - 3);
        showProcess(show);
    }

    private void showProcess(String s) {
        String line = ".....................................";
        String br = "<br>";
        String[] splLine = s.split("\\#\\#");
        String show = "<html><center>";
        if (splLine.length == 1) {
            show += line + br + splLine[0] + br + line;
        }
        if (splLine.length == 2) {
            show += splLine[0] + br + splLine[1] + br + line;
        }
        if (splLine.length == 3) {
            show += splLine[0] + br + splLine[1] + br + splLine[2];
        }
        show += "</center></html>";
        lbProcess.setText(show);
    }

    private void alertInf(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Info!!!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void alertWar(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Warning!!!", JOptionPane.WARNING_MESSAGE);
    }

    private void alertErr(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Error!!!", JOptionPane.ERROR_MESSAGE);
    }

    private void init() {
        setTitle(Main.APP_NAME + " v" + Main.APP_VER + " - " + Main.APP_SLOGAN);
        lbTitle.setText(Main.APP_NAME);
        lbSlogan.setText("Version " + Main.APP_VER + " - " + Main.APP_SLOGAN);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnContact;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSolution;
    private javax.swing.JComboBox<String> cbbCourse;
    private javax.swing.JComboBox<String> cbbQuiz;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbHello;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbProcess;
    private javax.swing.JLabel lbSlogan;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUserId;
    private javax.swing.JTextField tfCookie;
    // End of variables declaration//GEN-END:variables

}
