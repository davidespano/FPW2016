/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davide
 */
public class Db {

    private static final String dbConnectionUrl
            = "jdbc:derby://localhost:1527/davideSpano";
    private static final String dbConnectionUser = "davide";
    private static final String dbConnectionPsw = "password";

    private static Db singleton;

    private Db() {

    }

    public static Db getInstance() {
        if (singleton == null) {
            singleton = new Db();

            try {
                // caricamento esplicito del driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return singleton;
    }

    public boolean createUser(User usr){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "insert into appUser (id, username, password, firstname, surname) "
                  + "values (default, ?, ?, ?, ?)");
            stmt.setString(1, usr.getUsername());
            stmt.setString(2, usr.getPassword());
            stmt.setString(3, usr.getName());
            stmt.setString(4, usr.getSurname());
            

            int rows = stmt.executeUpdate();

            return rows == 1;

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }
    
    public boolean updateForm(Form f) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "update form set title= ? "
                    + "where id = ? ");
            stmt.setString(1, f.getTitle());
            stmt.setInt(2, f.getId());

            int rows = stmt.executeUpdate();

            return rows == 1;

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public boolean updateOption(Option o) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "update qOption set "
                    + "ovalue = ? where id = ? "
            );

            stmt.setString(1, o.getValue());
            stmt.setInt(2, o.getId());

            int rows = stmt.executeUpdate();

            return rows == 1;

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public boolean updateQuestion(Question q) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "update question set "
                    + "title = ?, qtype = ?, qorder= ? "
                    + "where id = ?"
            );

            stmt.setString(1, q.getTitle());
            stmt.setInt(2, q.getType());
            stmt.setInt(3, q.getOrder());
            stmt.setInt(4, q.getId());

            int rows = stmt.executeUpdate();

            return rows == 1;

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public boolean addDefaultQuestion(Form f) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);

            // faccio in modo che le due query vengano eseguite in 
            // modo atomico (tra la select e la insert non possono 
            // essere eseguite query da altri connessi ad db)
            conn.setAutoCommit(false);

            int nextOrder = -1;
            // recupero il valore massimo dell'ordine delle domande
            stmt = conn.prepareStatement(" select max(qOrder) +1 as nextOrder "
                    + "from question where form_id = ?");
            stmt.setInt(1, f.getId());
            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                nextOrder = set.getInt("nextOrder");
            }
            stmt.close();

            // creo la nuova domanda
            boolean isOk = generateDefaultQuestion(conn, f.getId(), nextOrder);
            conn.commit();
            
            return isOk;

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }
    
    public boolean addOption(Question q, Option o){
        Connection conn = null;
        PreparedStatement stmt = null;
        try { 
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            stmt = conn.prepareStatement(
                "insert into qoption (id, ovalue, question_id) " +
                 "values (default, ?, ?)");
            stmt.setString(1, o.getValue());
            stmt.setInt(2, q.getId());
            
            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public int createNewForm(User usr) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);
            String[] generatedId = new String[1];
            generatedId[0] = "id";
            stmt = conn.prepareStatement(
                    "insert into form (id, title, user_id)"
                    + "values (default, 'Nuovo form', ?)", generatedId);
            stmt.setInt(1, usr.getId());

            // in rows ho il numero delle righe modificate
            int rows = stmt.executeUpdate();

            // leggo l'id generato dal database
            if (rows == 1) {
                ResultSet keys = stmt.getGeneratedKeys();
                while (keys.next()) {
                    int formId = keys.getInt(1);
                    if (generateDefaultQuestion(conn, formId, 1)) {
                        return formId;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public Form getFormById(int id) {
        Form form = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);

            stmt = conn.prepareStatement(
                    "select form.id as formId, form.title as formTitle, "
                    + "       form.USER_ID as formUserId, "
                    + "       question.id as questionId, question.TITLE as questionTitle,"
                    + "       question.FORM_ID as questionFormId, question.QTYPE as questionType,"
                    + "       question.QORDER as questionOrder, qOption.id as optionId,"
                    + "       qOption.OVALUE as optionValue, "
                    + "       qOption.QUESTION_ID as optionQuestionId "
                    + "from form \n"
                    + "join question on form.id = question.FORM_ID "
                    + "join qOption on qOption.QUESTION_ID = question.id "
                    + "where form.id = ? "
                    + "order by question.qOrder, qoption.id");
            stmt.setInt(1, id);
            List<Form> forms = getFormBySql(stmt, conn);

            if (forms.size() == 1) {
                return forms.get(0);
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return form;
    }

    public List<Form> getFormsByUser(User usr) {
        List<Form> forms = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(
                    dbConnectionUrl,
                    dbConnectionUser,
                    dbConnectionPsw);

            stmt = conn.prepareStatement(
                    "select form.id as formId, form.title as formTitle, "
                    + "       form.USER_ID as formUserId, "
                    + "       question.id as questionId, question.TITLE as questionTitle,"
                    + "       question.FORM_ID as questionFormId, question.QTYPE as questionType,"
                    + "       question.QORDER as questionOrder, qOption.id as optionId,"
                    + "       qOption.OVALUE as optionValue, "
                    + "       qOption.QUESTION_ID as optionQuestionId "
                    + "from form \n"
                    + "join question on form.id = question.FORM_ID "
                    + "join qOption on qOption.QUESTION_ID = question.id "
                    + "where form.USER_ID = ?");
            stmt.setInt(1, usr.getId());

            forms = this.getFormBySql(stmt, conn);

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return forms;
    }

    public User getUserVer0(String username, String password) {
        String usrFalso = "davide";
        String pswFalso = "FPW";

        String usrFalso2 = "giovanni";
        String pswFalso2 = "nuovo";

        if (username.equals(usrFalso)
                && password.equals(pswFalso)) {
            User usr = new User();
            usr.setId(1);
            usr.setUsername(username);
            usr.setPassword(password);
            return usr;
        }

        if (username.equals(usrFalso2)
                && password.equals(pswFalso2)) {
            User usr = new User();
            usr.setId(2);
            usr.setUsername(username);
            usr.setPassword(password);
            return usr;
        }

        return null;
    }

    /**
     * Ricerca un utente sul db tramite username e password N.B. Permette la sql
     * injection
     *
     * @param username lo username specificato dall'utente
     * @param password la password specificata dall'utente
     * @return l'utente corrispondente allo username e password specificati,
     * null in caso non esista
     */
    public User getUserVer1(String username, String password) {
        Connection conn = null;
        User user = null;
        Statement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            dbConnectionUrl,
                            dbConnectionUser,
                            dbConnectionPsw);
            stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery(
                    "select * from appUser where username='"
                    + username + "' "
                    + "and password='"
                    + password + "'");
            while (set.next()) {
                user = new User();
                user.setUsername(set.getString("username"));
                user.setPassword(set.getString("password"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return user;

    }

    /**
     * Ricerca un utente sul db tramite username e password N.B. Non permette la
     * sql injection
     *
     * @param username lo username specificato dall'utente
     * @param password la password specificata dall'utente
     * @return l'utente corrispondente allo username e password specificati,
     * null in caso non esista
     */
    public User getUserVer2(String username, String password) {
        Connection conn = null;
        User user = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            dbConnectionUrl,
                            dbConnectionUser,
                            dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "select * from appUser where "
                    + "username = ? and "
                    + "password = ?"
            );

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                user = new User();
                user.setId(set.getInt("id"));
                user.setUsername(set.getString("username"));
                user.setPassword(set.getString("password"));
                user.setName(set.getString("firstname"));
                user.setSurname(set.getString("surname"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return user;

    }

    public User getUserByUsername(String username) {
        Connection conn = null;
        User user = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager
                    .getConnection(
                            dbConnectionUrl,
                            dbConnectionUser,
                            dbConnectionPsw);
            stmt = conn.prepareStatement(
                    "select * from appUser where "
                    + "username = ?"
            );

            stmt.setString(1, username);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                user = new User();
                user.setId(set.getInt("id"));
                user.setUsername(set.getString("username"));
                user.setPassword(set.getString("password"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Db.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Db.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        return user;

    }

    private List<Form> getFormBySql(PreparedStatement stmt, Connection conn) throws SQLException {
        List<Form> forms = new ArrayList<>();

        ResultSet set = stmt.executeQuery();

        Form form = null;
        Question question = null;
        Option option = null;

        while (set.next()) {
            if (form == null || set.getInt("formId") != form.getId()) {
                // creo un nuovo form
                form = new Form();
                form.setId(set.getInt("formId"));
                form.setTitle(set.getString("formTitle"));
                forms.add(form);
            }

            if (question == null || set.getInt("questionId") != question.getId()) {
                question = new Question();
                question.setId(set.getInt("questionId"));
                question.setTitle(set.getString("questionTitle"));
                question.setType(set.getInt("questionType"));
                question.setOrder(set.getInt("questionOrder"));
                form.getQuestions().add(question);
            }

            option = new Option();
            option.setId(set.getInt("optionId"));
            option.setValue(set.getString("optionValue"));
            question.getOptions().add(option);
        }

        return forms;
    }

    private boolean generateDefaultQuestion(
            Connection conn,
            int formId,
            int order)
            throws SQLException {
        PreparedStatement stmt2 = null, stmt3 = null;

        try {
            String[] generatedId = new String[1];
            generatedId[0] = "id";
            // inserisco una riga nella tabella option
            stmt2 = conn.prepareStatement(
                    "insert into question (id, title, qType, qOrder, form_id)"
                    + "values (default, 'Domanda di default', 1, ?, ?)", generatedId);

            stmt2.setInt(1, order);
            stmt2.setInt(2, formId);

            int rows = stmt2.executeUpdate();

            if (rows == 1) {
                ResultSet keys2 = stmt2.getGeneratedKeys();
                while (keys2.next()) {
                    int questionId = keys2.getInt(1);

                    stmt3 = conn.prepareStatement(
                            "insert into qOption (id, oValue, question_id) "
                            + "values (default, 'Opzione di default', ?)",
                            generatedId);

                    stmt3.setInt(1, questionId);

                    rows = stmt3.executeUpdate();

                    if (rows == 1) {
                        return true;
                    }

                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt2 != null) {
                stmt2.close();

            }
            if (stmt3 != null) {
                stmt3.close();
            }
        }

        return false;
    }
    
}
