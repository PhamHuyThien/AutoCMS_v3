package frames;

import auto.getquiz.CMSGetQuiz;
import auto.getquiz.Exception.BuildQuizException;
import auto.solution.CMSSolution;
import auto.login.CMSLogin;
import auto.login.exception.LoginException;
import auto.solution.exception.BuildAnswerException;
import auto.solution.exception.EssayQuestionException;
import main.Main;
import function.Function;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import object.course.Course;
import object.course.quiz.Quiz;
import org.json.simple.parser.ParseException;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class FormMain extends javax.swing.JFrame {

    private int i;
    private int done;
    private int err;
    private int index;

    public FormMain() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        setTitle("AUTO CMS V3.1.0  - 10 Quiz 10 Điểm Easy!");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FPL@utoCMS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Version V3.1.0 - 10 Quiz 10 Point Easy!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addContainerGap(34, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Login with cookie:"));

        btnLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Solution:"));

        lbHello.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbHello.setText("Hello: ......................................");

        lbUserId.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbUserId.setText("User ID: ..................................");

        cbbCourse.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbbCourse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course..." }));
        cbbCourse.setEnabled(false);
        cbbCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbCourseActionPerformed(evt);
            }
        });

        btnSolution.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSolution.setText("Auto Solution");
        btnSolution.setEnabled(false);
        btnSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolutionActionPerformed(evt);
            }
        });

        lbProcess.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbProcess.setForeground(new java.awt.Color(0, 153, 0));
        lbProcess.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbProcess.setText("Processing...............................");

        cbbQuiz.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                    .addComponent(lbUserId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbProcess, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSolution, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbQuiz, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbCourse, 0, 154, Short.MAX_VALUE))
                .addContainerGap())
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
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSolution)
                    .addComponent(lbProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnSolution, cbbCourse, cbbQuiz, lbHello, lbUserId});

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Contact:"));

        lbInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbInfo.setForeground(new java.awt.Color(255, 0, 0));
        lbInfo.setText("AutoCMS V3.1.0  - Code By ThienDepZaii - SystemError");

        btnContact.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactActionPerformed
        new Thread(() -> {
            Function.contactMe();
        }).start();
    }//GEN-LAST:event_btnContactActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        new Thread(() -> {
            String cookie = tfCookie.getText();
            if (!cookie.equals("")) {
                showProcess("Đang đăng nhập...");
                inpSetEnbled(false);
                Main.cmsLogin = new CMSLogin(cookie);
                try {
                    Main.cmsLogin.login();
                } catch (LoginException ex) {
                    showProcess("Đăng nhập không thành công!");
                    tfCookie.setEnabled(true);
                    btnLogin.setEnabled(true);
                } catch (IOException ex) {
                    showProcess("Kết nối đăng nhập không thành công!");
                    tfCookie.setEnabled(true);
                    btnLogin.setEnabled(true);
                }

                Main.cmsAccount = Main.cmsLogin.getCmsAccount();
                Main.course = Main.cmsLogin.getCourse();
                lbHello.setText("Hello: " + Main.cmsAccount.getUserName().toUpperCase());
                lbUserId.setText("User ID: " + Main.cmsAccount.getUserId());
                cbbCourse.removeAllItems();
                cbbCourse.addItem("Select Course...");
                for (Course course : Main.course) {
                    cbbCourse.addItem(course.getNumber());
                }

                inpSetEnbled(true);
                cbbQuiz.setEnabled(false);
                btnSolution.setEnabled(false);
                showProcess("Đăng nhập thành công!");
                Function.debug(Main.cmsAccount.toString());
                Function.debug(Arrays.toString(Main.course));
            } else {
                alertWar("Bạn phải nhập Cookie!");
            }
        }).start();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolutionActionPerformed
        int id = cbbQuiz.getSelectedIndex();
        if (id < 1) {
            alertErr("Bạn phải chọn ít nhất 1 quiz để auto!");
            return;
        }
        new Thread(() -> {
            inpSetEnbled(false);
            int start = id - 1;
            int end = start;
            if (id - 1 == Main.quiz.length) {
                start = 0;
                end = id - 2;
            }

            done = 0;
            err = 0;
            showProcess("Đang giải... D: " + done + ", F: " + err + ", T: " + (end - start + 1));
            for (i = start; i <= end; i++) {
                new Thread(() -> {
                    CMSSolution cmsSolution = new CMSSolution();
                    cmsSolution.setCmsAccount(Main.cmsAccount);
                    cmsSolution.setCourse(Main.course[cbbCourse.getSelectedIndex() - 1]);
                    cmsSolution.setQuiz(Main.quiz[i]);
                    try {
                        cmsSolution.solution();
                    } catch (ParseException ex) {
                        Function.debug("name=" + Main.quiz[i].getName() + " => " + ex.toString());
                        err++;
                    } catch (IOException | EssayQuestionException ex) {
                        Function.debug("name=" + Main.quiz[i].getName() + " => " + ex.toString());
                        err++;
                    } catch (BuildAnswerException ex) {
                        Function.debug("name=" + Main.quiz[i].getName() + " => " + ex.toString());
                        err++;
                    }
                    if (cmsSolution.isDone()) {
                        done++;
                    }
                }).start();
                Function.sleep(100);
            }
            int time = 0;
            do {
                showProcess("Đang giải... D: " + done + ", F: " + err + ", T: " + (end - start + 1) + ", S: " + ++time);
                Function.sleep(1000);
            } while (err + done < end - start + 1);
            showProcess("Giải hoàn tất! D: " + done + ", F: " + err + ", T: " + (end - start + 1) + ", S: " + time);
            inpSetEnbled(true);
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

            try {
                showProcess("Đang lấy dữ liệu môn " + Main.course[id - 1].getNumber() + "...");
                cmsQuizGet.getRaw();
                cmsQuizGet.getStandard();

            } catch (IOException ex) {
                showProcess("Request lấy dữ liệu Quiz thất bại!");
                tfCookie.setEnabled(true);
                btnLogin.setEnabled(true);
                cbbCourse.setEnabled(true);
                return;
            } catch (BuildQuizException ex) {
                Logger.getLogger(FormMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            showProcess("Tìm thành công " + cmsQuizGet.getQuiz().length + " Quiz!");
            cbbQuiz.removeAllItems();
            cbbQuiz.addItem("Select Quiz...");
            for (Quiz quiz : cmsQuizGet.getQuiz()) {
                cbbQuiz.addItem(quiz.getName());
            }
            cbbQuiz.addItem("Auto " + cmsQuizGet.getQuiz().length + " quiz");
            cbbQuiz.setSelectedIndex(cbbQuiz.getItemCount() - 1);

            Main.quiz = cmsQuizGet.getQuiz();
            Function.debug("");
            for(Quiz q: Main.quiz){
                Function.debug(q.toString());
            }
            inpSetEnbled(true);
        }).start();

    }//GEN-LAST:event_cbbCourseActionPerformed

    public void inpSetEnbled(boolean enbled) {
        tfCookie.setEnabled(enbled);
        btnLogin.setEnabled(enbled);
        cbbCourse.setEnabled(enbled);
        cbbQuiz.setEnabled(enbled);
        btnSolution.setEnabled(enbled);
    }

    public void showProcess(String s) {
        lbProcess.setText(s);
    }

    public void alertInf(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Info!!!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertWar(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Warning!!!", JOptionPane.WARNING_MESSAGE);
    }

    public void alertErr(String s) {
        JOptionPane.showMessageDialog(this, s, "AutoCMS Error!!!", JOptionPane.ERROR_MESSAGE);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnContact;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSolution;
    private javax.swing.JComboBox<String> cbbCourse;
    private javax.swing.JComboBox<String> cbbQuiz;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbHello;
    private javax.swing.JLabel lbInfo;
    private javax.swing.JLabel lbProcess;
    private javax.swing.JLabel lbUserId;
    private javax.swing.JTextField tfCookie;
    // End of variables declaration//GEN-END:variables

}
