package main;

import cms.getquiz.CmsQuizBase;
import cms.getquiz.CmsQuizReal;
import cms.login.CmsLogin;
import cms.solution.CmsSolution;
import exception.CmsException;
import util.Client;
import util.Console;
import util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Account;
import model.Course;
import model.Quiz;
import model.QuizQuestion;
import util.MsgBox;
import util.OS;
import util.PoolExec;

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
        btnSolution = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        lbProcess = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lbHello = new javax.swing.JLabel();
        lbUserId = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        cbbCourse = new javax.swing.JComboBox<>();
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
        lbTitle.setText("FPL@utoCMS");

        lbSlogan.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbSlogan.setForeground(new java.awt.Color(0, 51, 255));
        lbSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSlogan.setText("Version v0.0.0.0 - 10 Quiz 10 Point Easy!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSlogan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbSlogan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(tfCookie, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCookie, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnLogin, tfCookie});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Solution:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 11))); // NOI18N

        btnSolution.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        btnSolution.setText("Auto Solution");
        btnSolution.setEnabled(false);
        btnSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolutionActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbProcess.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbProcess.setForeground(new java.awt.Color(0, 153, 0));
        lbProcess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProcess.setText("<html><center>.....................................<br>Thiên Đẹp Traii is the best!<br>.....................................<html>");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbProcess, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbHello.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbHello.setText("Hello:.................");

        lbUserId.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lbUserId.setText("User ID:..............");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(lbHello, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbHello)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbUserId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lbHello, lbUserId});

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cbbCourse.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        cbbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course..." }));
        cbbCourse.setEnabled(false);
        cbbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCourseActionPerformed(evt);
            }
        });

        cbbQuiz.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        cbbQuiz.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Quiz..." }));
        cbbQuiz.setEnabled(false);
        cbbQuiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbQuizActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbQuiz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbbCourse, cbbQuiz});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbbQuiz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(btnSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contact:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 0, 11))); // NOI18N

        lbInfo.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactActionPerformed
        contactButton();
    }//GEN-LAST:event_btnContactActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        loginButton();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolutionActionPerformed
        solutionButton();
    }//GEN-LAST:event_btnSolutionActionPerformed

    private void cbbCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbCourseActionPerformed
        selectCourseCombobox();
    }//GEN-LAST:event_cbbCourseActionPerformed

    private void cbbQuizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbQuizActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbQuizActionPerformed

    private void loginButton() {
        String cookie = tfCookie.getText().trim();
        if (cookie.equals("")) {
            tfCookie.setText("");
            MsgBox.alertWar(this, "You must enter Cookie!");
            return;
        }
        new Thread(() -> {
            showProcess("Loging...");
            inpSetEnbled(false);
            try {
                Object[] objects = CmsLogin.start(cookie);
                Main.account = (Account) objects[0];
                Main.courses = (Course[]) objects[1];
            } catch (CmsException ex) {
                Console.debug("loginButton", FormMain.class, ex);
                showProcess("Login Fail!");
                tfCookie.setEnabled(true);
                tfCookie.setText("");
                btnLogin.setEnabled(true);
                return;
            }
            //
            Console.debug(Main.account);
            Console.debug(Main.courses);
            //
            lbHello.setText("Hello: " + Main.account.getUserName().toUpperCase());
            lbUserId.setText("User ID: " + Main.account.getUserId());
            //
            cbbCourse.removeAllItems();
            cbbCourse.addItem("Select Course...");
            for (Course course : Main.courses) {
                cbbCourse.addItem(course.getName());
            }
            inpSetEnbled(true);
            cbbQuiz.setEnabled(false);
            btnSolution.setEnabled(false);
            showProcess("Login done!");
            Client.pushAnalysis(Main.account);
        }).start();
    }

    private void selectCourseCombobox() {
        int id = cbbCourse.getSelectedIndex();
        if (id < 1) {
            return;
        }
        new Thread(() -> {
            inpSetEnbled(false);
            Course courseSelect = Main.courses[id - 1];
            if (courseSelect.getQuizs() == null) {
                Quiz[] quizNews = null;
                try {
                    showProcess("Get Quiz Base...");
                    quizNews = CmsQuizBase.parse(Main.account, courseSelect);
                    showProcess("Retrieving subject " + courseSelect.getNumber() + " data...");
                    quizNews = poolExecCmsQuizReal(Main.account, quizNews);
                } catch (CmsException ex) {
                    Console.debug("selectCourseCombobox", FormMain.class, ex);
                    showProcess("Get data subject error!");
                    tfCookie.setEnabled(true);
                    btnLogin.setEnabled(true);
                    cbbCourse.setEnabled(true);
                    return;
                }
                Main.courses[id - 1].setQuiz(quizNews);
                courseSelect.setQuiz(quizNews);
                //
                Client.pushCourse(courseSelect);
                int[] safetyQuiz = Client.getCourse(courseSelect);
                //
                if (safetyQuiz != null) { //lấy được dữ liệu trên server
                    if (safetyQuiz[1] < Main.ADMIN_QUIZ_SAFETY) { //chưa đủ độ an toàn
                        MsgBox.alertWar(this, "Số lương quiz môn trên server chưa đủ độ an toàn\nSố lượng quiz tìm thấy có thể bị thiếu do mạng lag...");
                    } else { //đã đủ độ an toàn
                        //không đủ số lượng quiz
                        if (courseSelect.getQuizs().length < safetyQuiz[0]) {
                            cbbCourse.setSelectedIndex(0);
                            tfCookie.setEnabled(true);
                            btnLogin.setEnabled(true);
                            cbbCourse.setEnabled(true);
                            MsgBox.alertWar(this, "Không đủ số lượng quiz vui lòng chọn lại môn cần giải bài!");
                            return;
                        }
                    }
                } else {
                    MsgBox.alertWar(this, "Không thấy dữ liệu từ server!\nSố lượng quiz tìm thấy có thể bị thiếu do mạng lag...");
                }
            }
            //
            int quizsLength = courseSelect.getQuizs().length;
            showProcess("Find " + quizsLength + " quiz success!");
            cbbQuiz.removeAllItems();
            cbbQuiz.addItem("Select Quiz...");
            for (Quiz quiz : courseSelect.getQuizs()) {
                if (Util.getInt(quiz.getName()) == -1) {
                    quiz.setName("Final Test");
                }
                cbbQuiz.addItem(quiz.getName() + " - " + ((int) quiz.getScore()) + "/" + ((int) quiz.getScorePossible()) + " point");
            }
            cbbQuiz.addItem("Auto all " + quizsLength + " quiz");
            cbbQuiz.setSelectedIndex(cbbQuiz.getItemCount() - 1);
            inpSetEnbled(true);
        }).start();
    }

    private void solutionButton() {
        int idCourse = cbbCourse.getSelectedIndex();
        int idQuiz = cbbQuiz.getSelectedIndex();
        if (idQuiz < 1) {
            MsgBox.alertErr(this, "You must choose at least 1 quiz!");
            return;
        }
        new Thread(() -> {
            inpSetEnbled(false);
            showProcess("Solving....");
            //
            int start = idQuiz - 1;
            int end = start;
            if (idQuiz - 1 == Main.courses[idCourse - 1].getQuizs().length) {
                start = 0;
                end = idQuiz - 2;
            }
            //
            CmsSolution[] cmsSolutions = new CmsSolution[end - start + 1];
            int j = 0;
            for (int i = start; i <= end; i++) {
                cmsSolutions[j++] = new CmsSolution(
                        Main.account,
                        Main.courses[idCourse - 1],
                        Main.courses[idCourse - 1].getQuizs()[i]
                );
            }
            PoolExec poolExec = new PoolExec(cmsSolutions);
            poolExec.execute();
            //
            int time = 0;
            do {
                showProcess(cmsSolutions, ++time, false);
                Util.sleep(1000);
            } while (poolExec.isTerminating());
            //
            showProcess(cmsSolutions, time, true);
            inpSetEnbled(true);
        }).start();
    }

    private void contactButton() {
        new Thread(() -> {
            OS.openTabBrowser(Main.APP_CONTACT);
        }).start();
    }

    private String buildNameQuizQuestion(Quiz quiz) {
        int totalType = quiz.getQuizQuestion().length;
        int typeText = 0;
        for (QuizQuestion quizQuestion : quiz.getQuizQuestion()) {
            if (quizQuestion.getType().equals("text")) {
                typeText++;
            }
        }
        return "";

    }

    private static Quiz[] poolExecCmsQuizReal(Account account, Quiz[] quizs) {
        CmsQuizReal[] cmsQuizReals = new CmsQuizReal[quizs.length];
        for (int i = 0; i < quizs.length; i++) {
            cmsQuizReals[i] = new CmsQuizReal(account, quizs[i]);
        }
        PoolExec poolExec = new PoolExec(cmsQuizReals);
        poolExec.execute();
        do {
            Util.sleep(1000);
        } while (poolExec.isTerminating());
        List<Quiz> lQuiz = new ArrayList<>();
        for (CmsQuizReal cmsQuizReal : cmsQuizReals) {
            if (cmsQuizReal.getQuiz() != null) {
                lQuiz.add(cmsQuizReal.getQuiz());
            }
        }
        Collections.sort(lQuiz);
        Quiz[] quizResult = new Quiz[lQuiz.size()];
        for (int i = 0; i < quizResult.length; i++) {
            quizResult[i] = lQuiz.get(i);
        }
        return quizResult;
    }

    private void inpSetEnbled(boolean enbled) {
        tfCookie.setEnabled(enbled);
        btnLogin.setEnabled(enbled);
        cbbCourse.setEnabled(enbled);
        cbbQuiz.setEnabled(enbled);
        btnSolution.setEnabled(enbled);
    }

    private void showProcess(CmsSolution[] cmsSolutions, int time, boolean finish) {
        int len = cmsSolutions.length;
        boolean useSharp = false;
        String show = "Solving " + Util.toStringDate(time) + (finish ? " - " + len + " Quiz has been completed!" : "...") + "##";
        for (int i = 0; i < len; i++) {
            int quizNum = Util.getInt(cmsSolutions[i].getQuiz().getName());
            String name = cmsSolutions != null ? (quizNum != -1 ? quizNum + ":" : "FT:") : "";
            String score = name + (cmsSolutions != null ? Util.roundReal(cmsSolutions[i].getScorePresent(), 1) + ":" : "0:");
            switch (cmsSolutions[i].getStatus()) {
                case -1:
                    score += "f - ";
                    break;
                case 0:
                    score += "r - ";
                    break;
                case 1:
                    score += "d - ";
                    break;
            }
            show += score;
            if (i >= len / 2 && !useSharp && len > 5) {
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
            show += splLine[0] + br + splLine[1] + splLine[2];
        }
        show += "</center></html>";
        lbProcess.setText(show);
    }

    private void init() {
        setTitle(Main.APP_NAME + " v" + Main.APP_VER + " - " + Main.APP_SLOGAN);
        lbTitle.setText(Main.APP_NAME);
        lbSlogan.setText("Version " + Main.APP_VER + " - " + Main.APP_SLOGAN);
        lbInfo.setText(Main.APP_NAME + " - Code By " + Main.APP_AUTHOR + " - " + Main.APP_NICKNAME);
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lbHello;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbProcess;
    private javax.swing.JLabel lbSlogan;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUserId;
    private javax.swing.JTextField tfCookie;
    // End of variables declaration//GEN-END:variables

}
