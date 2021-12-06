
import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
// enable(false) 값 넘어가는 이슈
// 구매, 판매, 지불 처리 시 endable(false) 해제
// 한 바퀴 돌았을때의 경우 이슈
// 상대방 땅 자기땅 비교 안됨
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author scw
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    int diceResult; // 주사위 실행 결과
    Location[] locationList = new Location[24]; // 필드 생성
    Color[] color = {new Color(255, 0, 0), new Color(0, 255, 0)};
    // DB
    LocationDB LDB = new LocationDB();
    String sql = "select * from location_table";

    Player player1 = new Player(1, 20, 800); // 1P 생성
    Player player2 = new Player(2, 20, 800); // 2P 생성
    int flag = 1; // 턴 확인 flag
    int turn = 20; // = 턴수 * 2
    int flag2 = 0; // 0 player1 / 1 player2 / 3 non
    JLabel[] playerMoneyList;
    private String text;

    private JLabel[] locationlblList; // location List

    public JLabel[] getLocationlblList() {
        return locationlblList;
    }

    public void setLocationlblList(JLabel[] locationlblList) {
        this.locationlblList = locationlblList;
    }

    private JLabel[] playerlblList;

    public JLabel[] getPlayerlblList() {
        return playerlblList;
    }

    public void setPlayerlblList(JLabel[] playerlblList) {
        this.playerlblList = playerlblList;
    }

    private Player[] playerList;

    public Player[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Player[] playerList) {
        this.playerList = playerList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MainFrame() {

        initComponents();
        this.playerMoneyList = new JLabel[]{lblPlayer1money, lblPlayer2money}; // 플레이어 자산 label
        // DB
        JLabel[] locIdx = {location1, location2, location3, location4, location5, location6, location7, location8, location9, location10,
            location11, location12, location13, location14, location15, location16, location17, location18, location19, location20,
            location21, location22, location23, location24};
        setLocationlblList(locIdx);

        JLabel[] playeridx = {lblPlayer1, lblPlayer2};
        setPlayerlblList(playeridx);

        Player[] playerUser = {player1, player2};
        setPlayerList(playerUser);
        lblPlayer1money.setText(Integer.toString(player1.getMoney()));
        lblPlayer2money.setText(Integer.toString(player2.getMoney()));
        //location1의 시작 좌표 값
        int x = 20;
        int y = 800;

        for (int i = 0; i < playerlblList.length; i++) {
            playerlblList[i].setLocation(playerList[i].getX(), playerList[i].getY()); // 간편화
        }

        // location x / y값 할당
        for (int i = 0; i < 24; i++) {
            if (i < 6) {
                Location location = new Location(x, y);
                locationList[i] = location;
                x += 130;
            } else if (i < 12) {
                Location location = new Location(x, y);
                locationList[i] = location;
                y -= 130;
            } else if (i < 18) {
                Location location = new Location(x, y);
                locationList[i] = location;
                x -= 130;
            } else {
                Location location = new Location(x, y);
                locationList[i] = location;
                y += 130;
            }
        }
        try {
            LDB.dbOpen();
            getDBData(sql);
            LDB.dbClose();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }
        lblerror.setText(Integer.toString(locationList[1].getX()));
        lblerror2.setText(Integer.toString(locationList[23].getLoc_ground()));

        // location 위치 배정
        for (int i = 0; i < 24; i++) {
            locIdx[i].setLocation(locationList[i].getX(), locationList[i].getY());
        }
    }

    public void getDBData(String sql) {
        int idx = 0;

        try {
            LDB.rs = LDB.stmt.executeQuery(sql);
            while (LDB.rs.next()) {
                locationList[idx].setLoc_ground(LDB.rs.getInt("loc_ground"));
                locationList[idx].setLoc_vila(LDB.rs.getInt("loc_vila"));
                locationList[idx].setLoc_building(LDB.rs.getInt("loc_building"));
                locationList[idx].setLoc_hotel(LDB.rs.getInt("loc_hotel"));
                locationList[idx].setLoc_name(LDB.rs.getString("loc_name"));
                getLocationlblList()[idx].setText(LDB.rs.getString("loc_name"));
                idx++;
            }
            LDB.rs.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buyFrame = new javax.swing.JFrame();
        lblBuyTitle = new javax.swing.JLabel();
        btnBuyConfirm = new javax.swing.JButton();
        chkLoc_ground = new javax.swing.JCheckBox();
        chkLoc_vila = new javax.swing.JCheckBox();
        chkLoc_building = new javax.swing.JCheckBox();
        chkLoc_hotel = new javax.swing.JCheckBox();
        lblBuyPrice = new javax.swing.JLabel();
        lblSellPrice = new javax.swing.JLabel();
        lblCostPrice = new javax.swing.JLabel();
        buyGroundVal = new javax.swing.JLabel();
        sellGroundVal = new javax.swing.JLabel();
        costGroundVal = new javax.swing.JLabel();
        costVilaVal = new javax.swing.JLabel();
        sellVilaVal = new javax.swing.JLabel();
        buyVilaVal = new javax.swing.JLabel();
        costBuildingVal = new javax.swing.JLabel();
        sellBuildingVal = new javax.swing.JLabel();
        buyBuildingVal = new javax.swing.JLabel();
        costHotelVal = new javax.swing.JLabel();
        sellHotelVal = new javax.swing.JLabel();
        buyHotelVal = new javax.swing.JLabel();
        btnBuyCancel = new javax.swing.JButton();
        costFrame = new javax.swing.JFrame();
        lblCostTitle = new javax.swing.JLabel();
        lblTotalCost = new javax.swing.JLabel();
        btnCost = new javax.swing.JButton();
        lblTotalCostValue = new javax.swing.JLabel();
        lblTotalMoney = new javax.swing.JLabel();
        lblTotalMoneyValue = new javax.swing.JLabel();
        startFrame = new javax.swing.JFrame();
        btnStart = new javax.swing.JButton();
        loginFrame = new javax.swing.JFrame();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jFrame1 = new javax.swing.JFrame();
        bankRuptFrame = new javax.swing.JFrame();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        gameOverFrame = new javax.swing.JFrame();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        location20 = new javax.swing.JLabel();
        location14 = new javax.swing.JLabel();
        location6 = new javax.swing.JLabel();
        location1 = new javax.swing.JLabel();
        location22 = new javax.swing.JLabel();
        location21 = new javax.swing.JLabel();
        location23 = new javax.swing.JLabel();
        location24 = new javax.swing.JLabel();
        location9 = new javax.swing.JLabel();
        location7 = new javax.swing.JLabel();
        location4 = new javax.swing.JLabel();
        location5 = new javax.swing.JLabel();
        location2 = new javax.swing.JLabel();
        location3 = new javax.swing.JLabel();
        location8 = new javax.swing.JLabel();
        location12 = new javax.swing.JLabel();
        location10 = new javax.swing.JLabel();
        location11 = new javax.swing.JLabel();
        location13 = new javax.swing.JLabel();
        location16 = new javax.swing.JLabel();
        location15 = new javax.swing.JLabel();
        location19 = new javax.swing.JLabel();
        location17 = new javax.swing.JLabel();
        location18 = new javax.swing.JLabel();
        Dice = new javax.swing.JButton();
        lblPlayer1 = new javax.swing.JLabel();
        lblPlayer2 = new javax.swing.JLabel();
        diceNum = new javax.swing.JLabel();
        form2 = new javax.swing.JButton();
        lblerror = new javax.swing.JLabel();
        lblerror2 = new javax.swing.JLabel();
        lblTurn = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblPlayer1money = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblPlayer2money = new javax.swing.JLabel();

        buyFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                buyFrameComponentShown(evt);
            }
        });

        lblBuyTitle.setText("해당 지역 이름");

        btnBuyConfirm.setText("구매하기");
        btnBuyConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyConfirmActionPerformed(evt);
            }
        });

        chkLoc_ground.setText("토지");

        chkLoc_vila.setText("빌라");

        chkLoc_building.setText("빌딩");

        chkLoc_hotel.setText("호텔");

        lblBuyPrice.setText("구매가");

        lblSellPrice.setText("판매가");

        lblCostPrice.setText("통행료");

        buyGroundVal.setText("jLabel6");

        sellGroundVal.setText("jLabel6");

        costGroundVal.setText("jLabel6");

        costVilaVal.setText("jLabel6");

        sellVilaVal.setText("jLabel6");

        buyVilaVal.setText("jLabel6");

        costBuildingVal.setText("jLabel6");

        sellBuildingVal.setText("jLabel6");

        buyBuildingVal.setText("jLabel6");

        costHotelVal.setText("jLabel6");

        sellHotelVal.setText("jLabel6");

        buyHotelVal.setText("jLabel6");

        btnBuyCancel.setText("취소하기");
        btnBuyCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuyCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buyFrameLayout = new javax.swing.GroupLayout(buyFrame.getContentPane());
        buyFrame.getContentPane().setLayout(buyFrameLayout);
        buyFrameLayout.setHorizontalGroup(
            buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyFrameLayout.createSequentialGroup()
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnBuyConfirm)
                        .addGap(45, 45, 45)
                        .addComponent(btnBuyCancel))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSellPrice)
                            .addComponent(lblBuyPrice)
                            .addComponent(lblCostPrice))
                        .addGap(42, 42, 42)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_ground, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buyFrameLayout.createSequentialGroup()
                                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(buyGroundVal)
                                    .addComponent(sellGroundVal)
                                    .addComponent(costGroundVal))
                                .addGap(11, 11, 11)))
                        .addGap(22, 22, 22)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buyVilaVal)
                            .addComponent(sellVilaVal)
                            .addComponent(costVilaVal)
                            .addComponent(chkLoc_vila))
                        .addGap(18, 18, 18)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_building)
                            .addComponent(buyBuildingVal)
                            .addComponent(sellBuildingVal)
                            .addComponent(costBuildingVal))
                        .addGap(18, 18, 18)
                        .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkLoc_hotel)
                            .addComponent(buyHotelVal)
                            .addComponent(sellHotelVal)
                            .addComponent(costHotelVal)))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(lblBuyTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        buyFrameLayout.setVerticalGroup(
            buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buyFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBuyTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkLoc_ground)
                    .addComponent(chkLoc_vila)
                    .addComponent(chkLoc_building)
                    .addComponent(chkLoc_hotel))
                .addGap(24, 24, 24)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(lblBuyPrice)
                        .addGap(18, 18, 18)
                        .addComponent(lblSellPrice)
                        .addGap(18, 18, 18)
                        .addComponent(lblCostPrice))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyVilaVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellVilaVal)
                        .addGap(18, 18, 18)
                        .addComponent(costVilaVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyBuildingVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellBuildingVal)
                        .addGap(18, 18, 18)
                        .addComponent(costBuildingVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyHotelVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellHotelVal)
                        .addGap(18, 18, 18)
                        .addComponent(costHotelVal))
                    .addGroup(buyFrameLayout.createSequentialGroup()
                        .addComponent(buyGroundVal)
                        .addGap(18, 18, 18)
                        .addComponent(sellGroundVal)
                        .addGap(18, 18, 18)
                        .addComponent(costGroundVal)))
                .addGap(18, 18, 18)
                .addGroup(buyFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuyConfirm)
                    .addComponent(btnBuyCancel))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        costFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                costFrameComponentShown(evt);
            }
        });

        lblCostTitle.setText("지역 정보");

        lblTotalCost.setText("지불가");

        btnCost.setText("지불하기");
        btnCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostActionPerformed(evt);
            }
        });

        lblTotalCostValue.setText("jLabel3");

        lblTotalMoney.setText("자산");

        lblTotalMoneyValue.setText("jLabel3");

        javax.swing.GroupLayout costFrameLayout = new javax.swing.GroupLayout(costFrame.getContentPane());
        costFrame.getContentPane().setLayout(costFrameLayout);
        costFrameLayout.setHorizontalGroup(
            costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(costFrameLayout.createSequentialGroup()
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(costFrameLayout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCost)
                            .addComponent(lblCostTitle)))
                    .addGroup(costFrameLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(costFrameLayout.createSequentialGroup()
                                .addComponent(lblTotalMoney)
                                .addGap(32, 32, 32)
                                .addComponent(lblTotalMoneyValue))
                            .addGroup(costFrameLayout.createSequentialGroup()
                                .addComponent(lblTotalCost)
                                .addGap(18, 18, 18)
                                .addComponent(lblTotalCostValue)))))
                .addContainerGap(194, Short.MAX_VALUE))
        );
        costFrameLayout.setVerticalGroup(
            costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(costFrameLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblCostTitle)
                .addGap(35, 35, 35)
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalMoney)
                    .addComponent(lblTotalMoneyValue))
                .addGap(18, 18, 18)
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCost)
                    .addComponent(lblTotalCostValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(btnCost)
                .addContainerGap())
        );

        btnStart.setText("게임시작");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout startFrameLayout = new javax.swing.GroupLayout(startFrame.getContentPane());
        startFrame.getContentPane().setLayout(startFrameLayout);
        startFrameLayout.setHorizontalGroup(
            startFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startFrameLayout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(btnStart)
                .addContainerGap(208, Short.MAX_VALUE))
        );
        startFrameLayout.setVerticalGroup(
            startFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startFrameLayout.createSequentialGroup()
                .addContainerGap(263, Short.MAX_VALUE)
                .addComponent(btnStart)
                .addGap(58, 58, 58))
        );

        jLabel3.setText("Login");

        jLabel4.setText("ID : ");

        jLabel5.setText("Password");

        jButton1.setText("로그인하기");

        javax.swing.GroupLayout loginFrameLayout = new javax.swing.GroupLayout(loginFrame.getContentPane());
        loginFrame.getContentPane().setLayout(loginFrameLayout);
        loginFrameLayout.setHorizontalGroup(
            loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginFrameLayout.createSequentialGroup()
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(jTextField1)))
                    .addGroup(loginFrameLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel3)))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        loginFrameLayout.setVerticalGroup(
            loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginFrameLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jButton1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel6.setText("승리자");

        jLabel7.setText("패배자");

        jButton3.setText("게임 종료");

        javax.swing.GroupLayout bankRuptFrameLayout = new javax.swing.GroupLayout(bankRuptFrame.getContentPane());
        bankRuptFrame.getContentPane().setLayout(bankRuptFrameLayout);
        bankRuptFrameLayout.setHorizontalGroup(
            bankRuptFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankRuptFrameLayout.createSequentialGroup()
                .addGroup(bankRuptFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bankRuptFrameLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel7))
                    .addGroup(bankRuptFrameLayout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel6))
                    .addGroup(bankRuptFrameLayout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jButton3)))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        bankRuptFrameLayout.setVerticalGroup(
            bankRuptFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bankRuptFrameLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addGap(38, 38, 38)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(29, 29, 29))
        );

        jLabel8.setText("게임 종료");

        jLabel9.setText("1승리자");

        jLabel10.setText("2승리자");

        jButton4.setText("다시 플레이");

        jButton5.setText("게임종료");

        jButton6.setText("처음으로");

        javax.swing.GroupLayout gameOverFrameLayout = new javax.swing.GroupLayout(gameOverFrame.getContentPane());
        gameOverFrame.getContentPane().setLayout(gameOverFrameLayout);
        gameOverFrameLayout.setHorizontalGroup(
            gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameOverFrameLayout.createSequentialGroup()
                .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel8))
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)))
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        gameOverFrameLayout.setVerticalGroup(
            gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameOverFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(43, 43, 43)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        location20.setText("19");
        location20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location20);
        location20.setBounds(20, 150, 130, 130);

        location14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location14.setText("13");
        location14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location14);
        location14.setBounds(670, 20, 130, 130);

        location6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location6.setText("5");
        location6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location6);
        location6.setBounds(670, 800, 130, 130);

        location1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location1.setText("시작1");
        location1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location1);
        location1.setBounds(20, 800, 130, 130);

        location22.setText("21");
        location22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location22);
        location22.setBounds(20, 410, 130, 130);

        location21.setText("20");
        location21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location21);
        location21.setBounds(20, 280, 130, 130);

        location23.setText("22");
        location23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location23);
        location23.setBounds(20, 540, 130, 130);

        location24.setText("23");
        location24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location24);
        location24.setBounds(20, 670, 130, 130);

        location9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location9.setText("8");
        location9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location9);
        location9.setBounds(800, 540, 130, 130);

        location7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location7.setText("6");
        location7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location7);
        location7.setBounds(800, 800, 130, 130);

        location4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location4.setText("3");
        location4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location4);
        location4.setBounds(410, 800, 130, 130);

        location5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location5.setText("4");
        location5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location5);
        location5.setBounds(540, 800, 130, 130);

        location2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location2.setText("1");
        location2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location2);
        location2.setBounds(150, 800, 130, 130);

        location3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location3.setText("2");
        location3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location3);
        location3.setBounds(280, 800, 130, 130);

        location8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location8.setText("7");
        location8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location8);
        location8.setBounds(800, 670, 130, 130);

        location12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location12.setText("11");
        location12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location12);
        location12.setBounds(800, 150, 130, 130);

        location10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location10.setText("9");
        location10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location10);
        location10.setBounds(800, 410, 130, 130);

        location11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location11.setText("10");
        location11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location11);
        location11.setBounds(800, 280, 130, 130);

        location13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location13.setText("12");
        location13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location13);
        location13.setBounds(800, 20, 130, 130);

        location16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location16.setText("15");
        location16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location16);
        location16.setBounds(410, 20, 130, 130);

        location15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location15.setText("14");
        location15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location15);
        location15.setBounds(540, 20, 130, 130);

        location19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location19.setText("18");
        location19.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location19);
        location19.setBounds(20, 20, 130, 130);

        location17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location17.setText("16");
        location17.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location17);
        location17.setBounds(280, 20, 130, 130);

        location18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location18.setText("17");
        location18.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.add(location18);
        location18.setBounds(150, 20, 130, 130);

        Dice.setText("주사위");
        Dice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DiceMouseClicked(evt);
            }
        });
        jPanel1.add(Dice);
        Dice.setBounds(410, 640, 87, 27);

        lblPlayer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/playerImg1.png"))); // NOI18N
        jPanel1.add(lblPlayer1);
        lblPlayer1.setBounds(210, 690, 30, 20);

        lblPlayer2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/playerImg2.png"))); // NOI18N
        jPanel1.add(lblPlayer2);
        lblPlayer2.setBounds(250, 690, 30, 20);

        diceNum.setText("주사위 : ");
        jPanel1.add(diceNum);
        diceNum.setBounds(410, 450, 80, 40);

        form2.setText("jButton1");
        form2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                form2ActionPerformed(evt);
            }
        });
        jPanel1.add(form2);
        form2.setBounds(210, 440, 87, 27);

        lblerror.setText("jLabel1");
        jPanel1.add(lblerror);
        lblerror.setBounds(190, 210, 46, 18);

        lblerror2.setText("jLabel1");
        jPanel1.add(lblerror2);
        lblerror2.setBounds(190, 240, 560, 30);

        lblTurn.setText("남은 턴 수 : 20");
        jPanel1.add(lblTurn);
        lblTurn.setBounds(360, 500, 190, 18);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(null);

        lblPlayer1money.setText("jLabel5");
        jPanel2.add(lblPlayer1money);
        lblPlayer1money.setBounds(20, 40, 46, 18);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(426, 115));
        jPanel3.setLayout(null);

        lblPlayer2money.setText("jLabel3");
        jPanel3.add(lblPlayer2money);
        lblPlayer2money.setBounds(20, 30, 46, 18);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 941, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(562, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiceMouseClicked
        // trun이 영이 됬을 경우 처리 X

        Dice dice = new Dice();
        diceResult = dice.rollDice();

        diceNum.setText(String.valueOf(diceResult));

        if (flag % 2 != 0) {
            flag2 = 0;
            int judge = player1.movement(2, locationList);
            lblerror.setText(Integer.toString(judge));
            lblerror2.setText(Integer.toString(playerList[flag2].getPlayerNum()));
            getPlayerlblList()[flag2].setLocation(getPlayerList()[flag2].getX(), getPlayerList()[flag2].getY());
            location1.setText(Integer.toString(judge)); // 에러 검출코드

            // 구매 여부
            if (judge == 3) {
                buyFrame.setLocation(350, 350); // Model 대화상자 위치 지정
                buyFrame.setSize(430, 300); // Model 대화상자 크기 지정
                buyFrame.show();

                // 구매가
                JLabel[][] buylblList = {
                    {buyGroundVal, buyVilaVal, buyBuildingVal, buyHotelVal},
                    {sellGroundVal, sellVilaVal, sellBuildingVal, sellHotelVal},
                    {costGroundVal, costVilaVal, costBuildingVal, costHotelVal}
                };
                int[] valueList = {locationList[player1.getReferLoc()].getLoc_ground(),
                    locationList[player1.getReferLoc()].getLoc_vila(),
                    locationList[player1.getReferLoc()].getLoc_building(),
                    locationList[player1.getReferLoc()].getLoc_hotel()
                };
                for (int i = 0; i < 3; i++) {
                    for (int z = 0; z < 4; z++) {
                        if (i == 0) {
                            buylblList[i][z].setText(Integer.toString(valueList[z]));
                        } else if (i == 1) {
                            buylblList[i][z].setText(Integer.toString(valueList[z] / 2));
                        } else {
                            buylblList[i][z].setText(Integer.toString(valueList[z] * 2));
                        }
                    }
                }

            } else if (judge == player1.getPlayerNum()) {
                // 재구매 여부

            } else {
                // 통행료 지불
//                sellFrame.setLocation(350, 350); // Model 대화상자 위치 지정
//                sellFrame.setSize(430, 300); // Model 대화상자 크기 지정
//                sellFrame.show();

            }
            flag++;
            turn--;
            if (turn == 0) {
                System.exit(-1);
            }
        } else { // *** player2 ***
            flag2 = 1;
            int judge = player2.movement(2, locationList);
            getPlayerlblList()[flag2].setLocation(getPlayerList()[flag2].getX(), getPlayerList()[flag2].getY());
            if (judge == 3) {
                buyFrame.setLocation(350, 350); // Model 대화상자 위치 지정
                buyFrame.setSize(430, 300); // Model 대화상자 크기 지정
                buyFrame.show();

                // 구매가
                JLabel[][] buylblList = {
                    {buyGroundVal, buyVilaVal, buyBuildingVal, buyHotelVal},
                    {sellGroundVal, sellVilaVal, sellBuildingVal, sellHotelVal},
                    {costGroundVal, costVilaVal, costBuildingVal, costHotelVal}
                };
                int[] valueList = {locationList[player2.getReferLoc()].getLoc_ground(),
                    locationList[player2.getReferLoc()].getLoc_vila(),
                    locationList[player2.getReferLoc()].getLoc_building(),
                    locationList[player2.getReferLoc()].getLoc_hotel()
                };
                for (int i = 0; i < 3; i++) {
                    for (int z = 0; z < 4; z++) {
                        if (i == 0) {
                            buylblList[i][z].setText(Integer.toString(valueList[z]));
                        } else if (i == 1) {
                            buylblList[i][z].setText(Integer.toString(valueList[z] / 2));
                        } else {
                            buylblList[i][z].setText(Integer.toString(valueList[z] * 2));
                        }
                    }
                }

            } else if (judge == player2.getPlayerNum()) {
                // 재구매 여부

            } else {
                // 통행료 지불
                costFrame.setLocation(350, 350); // Model 대화상자 위치 지정
                costFrame.setSize(430, 300); // Model 대화상자 크기 지정
                costFrame.show();

            }
            flag++;
            turn--;
            if (turn == 0) {
                System.exit(-1);
            }
        }

        lblTurn.setText(String.valueOf("남은 턴 수 : " + turn));
    }//GEN-LAST:event_DiceMouseClicked

    private void form2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form2ActionPerformed
        int iWidth = 430;
        int iHeight = 300;

        loginFrame.setLocation(350, 350); // Model 대화상자 위치 지정
        loginFrame.setSize(iWidth, iHeight); // Model 대화상자 크기 지정
        loginFrame.show();
    }//GEN-LAST:event_form2ActionPerformed

    private void btnBuyConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyConfirmActionPerformed
        int[] array = {0, 0, 0, 0};
        int[] loc_buyArray = locationList[playerList[flag2].getReferLoc()].getValue();

        // enable(false) 된 땅 제외 하고 total 에 합치기
        int totalPrice = 0;

        if (chkLoc_ground.isSelected() && loc_buyArray[0] != 1) {
            array[0] = 1;
            totalPrice += locationList[playerList[flag2].getReferLoc()].getLoc_ground();
            chkLoc_ground.setSelected(false);
        }
        if (chkLoc_vila.isSelected() && loc_buyArray[1] != 1) {
            array[1] = 1;
            totalPrice += locationList[playerList[flag2].getReferLoc()].getLoc_vila();
            chkLoc_vila.setSelected(false);
        }
        if (chkLoc_building.isSelected() && loc_buyArray[2] != 1) {
            array[2] = 1;
            totalPrice += locationList[playerList[flag2].getReferLoc()].getLoc_building();
            chkLoc_building.setSelected(false);
        }
        if (chkLoc_hotel.isSelected() && loc_buyArray[3] != 1) {
            array[3] = 1;
            totalPrice += locationList[playerList[flag2].getReferLoc()].getLoc_hotel();
            chkLoc_hotel.setSelected(false);
        }

        // 자산을 초과할 경우 (예외처리)
        if (playerList[flag2].getMoney() < totalPrice) {
//            sellFrame.setLocation(350, 350);
//            sellFrame.setSize(430, 300);
//            sellFrame.show();
//
//            lblSellError.setText("자산이 부족합니다.");
        } else {
            int beforeMoney = playerList[flag2].getMoney(); // 유저의 돈을 불러옴
            beforeMoney -= totalPrice;
            playerList[flag2].setMoney(beforeMoney); //플레이어의 moeny 업데이트
            playerMoneyList[flag2].setText(Integer.toString(playerList[flag2].getMoney())); // 해당 플레이어의 자산을 수정
            // (path) enable(true)로 전환

            locationList[playerList[flag2].getReferLoc()].setValue(array); // 해당 플레이어의 구매정보
            locationList[playerList[flag2].getReferLoc()].setWho(flag2); // 해당 location의 필드의 소유자는 누구인지
            locationlblList[playerList[flag2].getReferLoc()].setBackground(color[flag2]); // color
//            JCheckBox[] check = {chkLoc_ground, chkLoc_vila, chkLoc_building, chkLoc_hotel};
//            for (int i = 0; i < check.length; i++) {
//                check[i].setSelected(false);
//            }

        }

        totalPrice = 0;
        lblBuyTitle.setText(null);
        buyFrame.hide();
    }//GEN-LAST:event_btnBuyConfirmActionPerformed

    private void buyFrameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_buyFrameComponentShown
        // 해당 location의 check된 땅 확인
        lblBuyTitle.setText(locationList[playerList[flag2].getReferLoc()].getLoc_name()); // title 지정

        int[] loc_array = new int[4];
        JCheckBox[] check = {chkLoc_ground, chkLoc_vila, chkLoc_building, chkLoc_hotel};

        for (int i = 0; i < loc_array.length; i++) {
            loc_array[i] = locationList[playerList[flag2].getReferLoc()].getValue()[i];
        }
        // loacation 클래스의 who 와 player 와 비교한 후

        for (int i = 0; i < loc_array.length; i++) {
            if (loc_array[i] == 1) {
                check[i].setSelected(true); // 배열의 true를 보여줌
                check[i].setEnabled(false); // checkbox 비활성화
            } else {
                check[i].setSelected(false);

            }
        }
    }//GEN-LAST:event_buyFrameComponentShown

    private void btnBuyCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuyCancelActionPerformed
        // buyForm btn버튼 취소 이벤트
        JCheckBox[] checkdelList = {chkLoc_ground, chkLoc_vila, chkLoc_building, chkLoc_hotel};

        for (int i = 0; i < checkdelList.length; i++) {
            checkdelList[i].setSelected(false);
        }
        buyFrame.hide();
    }//GEN-LAST:event_btnBuyCancelActionPerformed

    private void btnCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostActionPerformed
        //        int playerMoney = Integer.parseInt(lblTotalMoneyValue.getText());
        int cost = Integer.parseInt(lblTotalCostValue.getText());
        int costResult = playerList[flag2].getMoney() - cost;
        if (costResult < 0) {
            lblTotalCostValue.setText("파산함");
        } else {
            playerList[flag2].setMoney(costResult);
            lblPlayer2money.setText(Integer.toString(costResult));
            costFrame.hide();
        }
    }//GEN-LAST:event_btnCostActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStartActionPerformed

    private void costFrameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_costFrameComponentShown
        lblCostTitle.setText(locationList[playerList[flag2].getReferLoc()].getLoc_name());
        int totalCost = 0;
        int[] loc_array = new int[4];
        for (int i = 0; i < loc_array.length; i++) {
            loc_array[i] = locationList[playerList[flag2].getReferLoc()].getValue()[i];
        }
        int[] loc_value = {locationList[playerList[flag2].getReferLoc()].getLoc_ground(),
            locationList[playerList[flag2].getReferLoc()].getLoc_vila(),
            locationList[playerList[flag2].getReferLoc()].getLoc_building(),
            locationList[playerList[flag2].getReferLoc()].getLoc_hotel()};
        for (int i = 0; i < loc_array.length; i++) {
            if (loc_array[i] == 1) {
                totalCost += loc_value[i];
            }
        }
        lblTotalCostValue.setText(Integer.toString(totalCost));
        lblTotalMoneyValue.setText(Integer.toString(playerList[flag2].getMoney()));
    }//GEN-LAST:event_costFrameComponentShown

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dice;
    private javax.swing.JFrame bankRuptFrame;
    private javax.swing.JButton btnBuyCancel;
    private javax.swing.JButton btnBuyConfirm;
    private javax.swing.JButton btnCost;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel buyBuildingVal;
    private javax.swing.JFrame buyFrame;
    private javax.swing.JLabel buyGroundVal;
    private javax.swing.JLabel buyHotelVal;
    private javax.swing.JLabel buyVilaVal;
    private javax.swing.JCheckBox chkLoc_building;
    private javax.swing.JCheckBox chkLoc_ground;
    private javax.swing.JCheckBox chkLoc_hotel;
    private javax.swing.JCheckBox chkLoc_vila;
    private javax.swing.JLabel costBuildingVal;
    private javax.swing.JFrame costFrame;
    private javax.swing.JLabel costGroundVal;
    private javax.swing.JLabel costHotelVal;
    private javax.swing.JLabel costVilaVal;
    private javax.swing.JLabel diceNum;
    private javax.swing.JButton form2;
    private javax.swing.JFrame gameOverFrame;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lblBuyPrice;
    private javax.swing.JLabel lblBuyTitle;
    private javax.swing.JLabel lblCostPrice;
    private javax.swing.JLabel lblCostTitle;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblPlayer1money;
    private javax.swing.JLabel lblPlayer2;
    private javax.swing.JLabel lblPlayer2money;
    private javax.swing.JLabel lblSellPrice;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblTotalCostValue;
    private javax.swing.JLabel lblTotalMoney;
    private javax.swing.JLabel lblTotalMoneyValue;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JLabel lblerror;
    private javax.swing.JLabel lblerror2;
    private javax.swing.JLabel location1;
    private javax.swing.JLabel location10;
    private javax.swing.JLabel location11;
    private javax.swing.JLabel location12;
    private javax.swing.JLabel location13;
    private javax.swing.JLabel location14;
    private javax.swing.JLabel location15;
    private javax.swing.JLabel location16;
    private javax.swing.JLabel location17;
    private javax.swing.JLabel location18;
    private javax.swing.JLabel location19;
    private javax.swing.JLabel location2;
    private javax.swing.JLabel location20;
    private javax.swing.JLabel location21;
    private javax.swing.JLabel location22;
    private javax.swing.JLabel location23;
    private javax.swing.JLabel location24;
    private javax.swing.JLabel location3;
    private javax.swing.JLabel location4;
    private javax.swing.JLabel location5;
    private javax.swing.JLabel location6;
    private javax.swing.JLabel location7;
    private javax.swing.JLabel location8;
    private javax.swing.JLabel location9;
    private javax.swing.JFrame loginFrame;
    private javax.swing.JLabel sellBuildingVal;
    private javax.swing.JLabel sellGroundVal;
    private javax.swing.JLabel sellHotelVal;
    private javax.swing.JLabel sellVilaVal;
    private javax.swing.JFrame startFrame;
    // End of variables declaration//GEN-END:variables
}
