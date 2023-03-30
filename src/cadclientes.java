import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class cadclientes {
    private JPanel Main;
    private JTextField txtNome;
    private JTextField txtEstado;
    private JTextField txtCidade;
    private JTextField txtBairro;
    private JTextField txtLogradouro;
    private JTextField txtComplemento;
    private JTextField txtTel;
    private JButton consultarButton;
    private JButton salvarButton;
    private JButton apagarButton;
    private JButton alterarButton;
    private JTextField txtid;
    private JTable table1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cadastro de Clientes");
        frame.setContentPane(new cadclientes().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void conexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/cadclientes", "root", "root");
            System.out.println("Conexão Ok");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from cadclientes");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public cadclientes() {
        conexao();
        table_load();

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Nome,Estado,Cidade, Bairro, Logradouro,Complemento,Telefone;

                Nome = txtNome.getText();
                Estado = txtEstado.getText();
                Cidade = txtCidade.getText();
                Bairro = txtBairro.getText();
                Logradouro = txtLogradouro.getText();
                Complemento = txtComplemento.getText();
                Telefone = txtTel.getText();

                try {
                    pst = con.prepareStatement("insert into cadClientes(Nome,Estado,Cidade,Bairro,Logradouro,Complemento,Telefone)values(?,?,?,?,?,?,?)");
                    pst.setString(1, Nome);
                    pst.setString(2, Estado);
                    pst.setString(3, Cidade);
                    pst.setString(4, Bairro);
                    pst.setString(5, Logradouro);
                    pst.setString(6, Complemento);
                    pst.setString(7, Telefone);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Salvar as informações inseridas?");
                    table_load();
                    txtNome.setText("");
                    txtEstado.setText("");
                    txtCidade.setText("");
                    txtBairro.setText("");
                    txtLogradouro.setText("");
                    txtComplemento.setText("");
                    txtTel.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String id = txtid.getText();

                    pst = con.prepareStatement("select Nome, Estado, Cidade,Bairro,Logradouro,Complemento,Telefone from cadClientes where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String Nome = rs.getString(1);
                        String Estado = rs.getString(2);
                        String Cidade = rs.getString(3);
                        String Bairro = rs.getString(4);
                        String Logradouro = rs.getString(5);
                        String Complemento = rs.getString(6);
                        String Telefone = rs.getString(7);

                        txtNome.setText(Nome);
                        txtEstado.setText(Estado);
                        txtCidade.setText(Cidade);
                        txtBairro.setText(Bairro);
                        txtLogradouro.setText(Logradouro);
                        txtComplemento.setText(Complemento);
                        txtTel.setText(Telefone);

                    } else {
                        txtNome.setText("");
                        txtEstado.setText("");
                        txtCidade.setText("");
                        txtBairro.setText("");
                        txtLogradouro.setText("");
                        txtComplemento.setText("");
                        txtTel.setText("");
                        JOptionPane.showMessageDialog(null, "Id nao encontrado!");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Nome, Estado, Cidade, Bairro, Logradouro, Complemento, Telefone, id;

                id = txtid.getText();
                Nome = txtNome.getText();
                Estado = txtEstado.getText();
                Cidade = txtCidade.getText();
                Bairro = txtBairro.getText();
                Logradouro = txtLogradouro.getText();
                Complemento = txtComplemento.getText();
                Telefone = txtTel.getText();

                try {
                    pst = con.prepareStatement("update cadClientes set Nome = ?,estado = ?, Cidade = ?, Bairro = ?, Logradouro = ?,Complemento = ?, Telefone = ? where id = ?");
                    pst.setString(1, Nome);
                    pst.setString(2, Estado);
                    pst.setString(3, Cidade);
                    pst.setString(4, Bairro);
                    pst.setString(5, Logradouro);
                    pst.setString(6, Complemento);
                    pst.setString(7, Telefone);
                    pst.setString(8,id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Atualizar alterações?");
                    table_load();
                    txtNome.setText("");
                    txtTel.setText("");
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String empid;
                empid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from cadClientes  where id = ?");

                    pst.setString(1, empid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Tem certeza que deseja apagar o registro?");
                    table_load();
                    txtNome.setText("");
                    txtTel.setText("");
                    txtEstado.getText();
                    txtCidade.getText();
                    txtBairro.getText();
                    txtLogradouro.getText();
                    txtComplemento.getText();
                    txtTel.getText();
                    txtNome.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }
}