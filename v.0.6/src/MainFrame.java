
import java.awt.Color;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

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

    int diceResult; // 주사위 실행 결과
    Location[] locationList = new Location[24]; // 필드 생성
    Color[] color = {new Color(255, 0, 0), new Color(0, 255, 0)};
    // DB
    LocationDB LDB = new LocationDB();
    String loc_sql = "select * from location_table";

    Player player1 = new Player(1, 20, 800); // 1P 생성
    Player player2 = new Player(2, 20, 800); // 2P 생성
    int flag = 1; // 턴 확인 flag
    int turn = 40; // = 턴수 * 2
    int flag2 = 0; // 0 player1 / 1 player2 / 3 non

    // DB value 담는 변수
    String userId_value = null;
    String userPw_value = null;

    JLabel destination; // 세계여행 위치
    private JLabel[] playerMoneylblList; // 현금 자산 lbl 리스트
    private JLabel[] playerAssetlblList; // 총 자산 lbl 리스트

    private Player[] playerList; // player 리스트
    private JLabel[] playerlblList; // player lbl 리스트
    private static JLabel[] locationlblList; // location (장소) lbl 리스트

    public JLabel[] getPlayerMoneylblList() {
        return playerMoneylblList;
    }

    public void setPlayerMoneylblList(JLabel[] playerMoneylblList) {
        this.playerMoneylblList = playerMoneylblList;
    }

    public JLabel[] getPlayerAssetlblList() {
        return playerAssetlblList;
    }

    public void setPlayerAssetlblList(JLabel[] playerAssetlblList) {
        this.playerAssetlblList = playerAssetlblList;
    }

    public JLabel[] getLocationlblList() {
        return locationlblList;
    }

    public void setLocationlblList(JLabel[] locationlblList) {
        this.locationlblList = locationlblList;
    }

    public JLabel[] getPlayerlblList() {
        return playerlblList;
    }

    public void setPlayerlblList(JLabel[] playerlblList) {
        this.playerlblList = playerlblList;
    }

    public Player[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Player[] playerList) {
        this.playerList = playerList;
    }

    public MainFrame() {
        initComponents();
        JLabel[] locIdx = {location1, location2, location3, location4, location5, location6, location7, location8, location9, location10,
            location11, location12, location13, location14, location15, location16, location17, location18, location19, location20,
            location21, location22, location23, location24};
        JLabel[] playeridx = {lblPlayer1, lblPlayer2};
        JLabel[] moneyidx = {lblPlayer1money, lblPlayer2money};
        JLabel[] assetidx = {lblPlayer1Asset, lblPlayer2Asset};
        Player[] playerUser = {player1, player2};

        setPlayerlblList(playeridx);
        setLocationlblList(locIdx);
        setPlayerMoneylblList(moneyidx);
        setPlayerAssetlblList(assetidx);
        setPlayerList(playerUser);

        // 돈, 자산 초기값 표시
        for (int i = 0; i < playerUser.length; i++) {
            playerMoneylblList[i].setText(Integer.toString(playerUser[i].getMoney()));
            playerAssetlblList[i].setText(Integer.toString(playerUser[i].getAsset()));
        }

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
            getLoc_DBData(loc_sql);
            LDB.dbClose();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }

        // location 위치 배정
        for (int i = 0; i < 24; i++) {
            locIdx[i].setLocation(locationList[i].getX(), locationList[i].getY());
        }
    }

    public void getLoc_DBData(String sql) {
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

    /*
    * USER_Table DB 조건 통해 불러옴
     */
    public void getUser_DBData(String sql) {
        try {
            LDB.dbOpen();
            LDB.rs = LDB.stmt.executeQuery(sql);
            if (LDB.rs.next()) {
                lblerror.setText(String.valueOf(LDB.rs.getInt("user_idx")));
                userId_value = LDB.rs.getString("user_Id");
                userPw_value = LDB.rs.getString("user_Pw");
            } else {
                lblLoginErrorTitle.setText("불일치합니다.");
            }
            LDB.rs.close();
            LDB.dbClose();
        } catch (Exception e) {
            System.out.println("SQLException : " + e.getMessage());
        }
    }

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
        login1111 = new javax.swing.JOptionPane();
        loginFrame = new javax.swing.JFrame();
        txtLoginId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblLoginId = new javax.swing.JLabel();
        lblLoginPw = new javax.swing.JLabel();
        txtLoginPw = new javax.swing.JTextField();
        btnLoginGo = new javax.swing.JButton();
        lblLoginErrorTitle = new javax.swing.JLabel();
        bankRuptFrame = new javax.swing.JFrame();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        gameOverFrame = new javax.swing.JFrame();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnRestart = new javax.swing.JButton();
        btnGameover = new javax.swing.JButton();
        lblPlayer1TotalAsset = new javax.swing.JLabel();
        lblPlayer2TotalAsset = new javax.swing.JLabel();
        lblPlayer1TotalMoney = new javax.swing.JLabel();
        lblWinner = new javax.swing.JLabel();
        lblPlayer2TotalMoney = new javax.swing.JLabel();
        travelFrame = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        btnTravelGo = new javax.swing.JButton();
        lblDestination = new javax.swing.JLabel();
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
        lblPlayerName1 = new javax.swing.JLabel();
        lblPlayer1Asset = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblPlayer2money = new javax.swing.JLabel();
        lblPlayer2Name = new javax.swing.JLabel();
        lblPlayer2Asset = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

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

        costFrame.setBackground(new java.awt.Color(255, 51, 51));
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
                .addContainerGap()
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(costFrameLayout.createSequentialGroup()
                        .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalCost)
                            .addComponent(lblTotalMoney))
                        .addGap(18, 18, 18)
                        .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTotalMoneyValue)
                            .addComponent(lblTotalCostValue))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, costFrameLayout.createSequentialGroup()
                        .addGap(0, 82, Short.MAX_VALUE)
                        .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, costFrameLayout.createSequentialGroup()
                                .addComponent(lblCostTitle)
                                .addGap(104, 104, 104))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, costFrameLayout.createSequentialGroup()
                                .addComponent(btnCost)
                                .addGap(93, 93, 93))))))
        );
        costFrameLayout.setVerticalGroup(
            costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(costFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCostTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalMoney)
                    .addComponent(lblTotalMoneyValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(costFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalCost)
                    .addComponent(lblTotalCostValue))
                .addGap(54, 54, 54)
                .addComponent(btnCost)
                .addContainerGap())
        );

        btnStart.setText("게임시작");

        javax.swing.GroupLayout startFrameLayout = new javax.swing.GroupLayout(startFrame.getContentPane());
        startFrame.getContentPane().setLayout(startFrameLayout);
        startFrameLayout.setHorizontalGroup(
            startFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(startFrameLayout.createSequentialGroup()
                .addGroup(startFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(startFrameLayout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(btnStart))
                    .addGroup(startFrameLayout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(login1111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        startFrameLayout.setVerticalGroup(
            startFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, startFrameLayout.createSequentialGroup()
                .addContainerGap(110, Short.MAX_VALUE)
                .addComponent(login1111, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(btnStart)
                .addGap(58, 58, 58))
        );

        jLabel3.setText("Login");

        lblLoginId.setText("ID : ");

        lblLoginPw.setText("Password");

        btnLoginGo.setText("로그인하기");
        btnLoginGo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginGoMouseClicked(evt);
            }
        });

        lblLoginErrorTitle.setText("ㅁㅁㅁ");

        javax.swing.GroupLayout loginFrameLayout = new javax.swing.GroupLayout(loginFrame.getContentPane());
        loginFrame.getContentPane().setLayout(loginFrameLayout);
        loginFrameLayout.setHorizontalGroup(
            loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginFrameLayout.createSequentialGroup()
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginFrameLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLoginId)
                            .addComponent(lblLoginPw))
                        .addGap(10, 10, 10)
                        .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLoginGo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLoginPw, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(txtLoginId)))
                    .addGroup(loginFrameLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel3)
                        .addGap(41, 41, 41)
                        .addComponent(lblLoginErrorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 36, Short.MAX_VALUE))
        );
        loginFrameLayout.setVerticalGroup(
            loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginFrameLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblLoginErrorTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLoginId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLoginId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loginFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoginPw)
                    .addComponent(txtLoginPw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(btnLoginGo)
                .addContainerGap(27, Short.MAX_VALUE))
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

        gameOverFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                gameOverFrameComponentShown(evt);
            }
        });

        jLabel8.setText("게임 종료");

        jLabel9.setText("Player1");

        jLabel10.setText("Player2");

        btnRestart.setText("다시 플레이");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });

        btnGameover.setText("게임종료");
        btnGameover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGameoverActionPerformed(evt);
            }
        });

        lblPlayer1TotalAsset.setText("jLabel2");

        lblPlayer2TotalAsset.setText("jLabel2");

        lblPlayer1TotalMoney.setText("jLabel2");

        lblWinner.setText("000 Player Win");

        lblPlayer2TotalMoney.setText("jLabel2");

        javax.swing.GroupLayout gameOverFrameLayout = new javax.swing.GroupLayout(gameOverFrame.getContentPane());
        gameOverFrame.getContentPane().setLayout(gameOverFrameLayout);
        gameOverFrameLayout.setHorizontalGroup(
            gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameOverFrameLayout.createSequentialGroup()
                .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel9)
                        .addGap(34, 34, 34)
                        .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gameOverFrameLayout.createSequentialGroup()
                                .addComponent(lblPlayer1TotalAsset)
                                .addGap(52, 52, 52)
                                .addComponent(jLabel10)
                                .addGap(34, 34, 34)
                                .addComponent(lblPlayer2TotalAsset))
                            .addGroup(gameOverFrameLayout.createSequentialGroup()
                                .addComponent(lblPlayer1TotalMoney)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPlayer2TotalMoney))))
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(jLabel8))
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gameOverFrameLayout.createSequentialGroup()
                                .addGap(140, 140, 140)
                                .addComponent(lblWinner)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gameOverFrameLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnGameover)
                                .addGap(43, 43, 43)))
                        .addComponent(btnRestart)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        gameOverFrameLayout.setVerticalGroup(
            gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gameOverFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(43, 43, 43)
                .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblPlayer1TotalAsset))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlayer1TotalMoney))
                    .addGroup(gameOverFrameLayout.createSequentialGroup()
                        .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPlayer2TotalAsset)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPlayer2TotalMoney)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addComponent(lblWinner)
                .addGap(68, 68, 68)
                .addGroup(gameOverFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRestart)
                    .addComponent(btnGameover))
                .addContainerGap())
        );

        travelFrame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                travelFrameComponentShown(evt);
            }
        });

        jLabel1.setText("목적지 :");

        btnTravelGo.setText("jButton1");
        btnTravelGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTravelGoActionPerformed(evt);
            }
        });

        lblDestination.setText("jLabel2");

        javax.swing.GroupLayout travelFrameLayout = new javax.swing.GroupLayout(travelFrame.getContentPane());
        travelFrame.getContentPane().setLayout(travelFrameLayout);
        travelFrameLayout.setHorizontalGroup(
            travelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(travelFrameLayout.createSequentialGroup()
                .addGroup(travelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(travelFrameLayout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(btnTravelGo))
                    .addGroup(travelFrameLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDestination)))
                .addContainerGap(159, Short.MAX_VALUE))
        );
        travelFrameLayout.setVerticalGroup(
            travelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(travelFrameLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addGroup(travelFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblDestination))
                .addGap(99, 99, 99)
                .addComponent(btnTravelGo)
                .addGap(54, 54, 54))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        location20.setText("19");
        location20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location20MouseClicked(evt);
            }
        });
        jPanel1.add(location20);
        location20.setBounds(20, 150, 130, 130);

        location14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location14.setText("13");
        location14.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location14MouseClicked(evt);
            }
        });
        jPanel1.add(location14);
        location14.setBounds(670, 20, 130, 130);

        location6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location6.setText("5");
        location6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location6MouseClicked(evt);
            }
        });
        jPanel1.add(location6);
        location6.setBounds(670, 800, 130, 130);

        location1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location1.setText("시작1");
        location1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location1MouseClicked(evt);
            }
        });
        jPanel1.add(location1);
        location1.setBounds(20, 800, 130, 130);

        location22.setText("21");
        location22.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location22MouseClicked(evt);
            }
        });
        jPanel1.add(location22);
        location22.setBounds(20, 410, 130, 130);

        location21.setText("20");
        location21.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location21MouseClicked(evt);
            }
        });
        jPanel1.add(location21);
        location21.setBounds(20, 280, 130, 130);

        location23.setText("22");
        location23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location23MouseClicked(evt);
            }
        });
        jPanel1.add(location23);
        location23.setBounds(20, 540, 130, 130);

        location24.setText("23");
        location24.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location24MouseClicked(evt);
            }
        });
        jPanel1.add(location24);
        location24.setBounds(20, 670, 130, 130);

        location9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location9.setText("8");
        location9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location9MouseClicked(evt);
            }
        });
        jPanel1.add(location9);
        location9.setBounds(800, 540, 130, 130);

        location7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location7.setText("6");
        location7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location7MouseClicked(evt);
            }
        });
        jPanel1.add(location7);
        location7.setBounds(800, 800, 130, 130);

        location4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location4.setText("3");
        location4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location4MouseClicked(evt);
            }
        });
        jPanel1.add(location4);
        location4.setBounds(410, 800, 130, 130);

        location5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location5.setText("4");
        location5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location5MouseClicked(evt);
            }
        });
        jPanel1.add(location5);
        location5.setBounds(540, 800, 130, 130);

        location2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location2.setText("1");
        location2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location2MouseClicked(evt);
            }
        });
        jPanel1.add(location2);
        location2.setBounds(150, 800, 130, 130);

        location3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location3.setText("2");
        location3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        location3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location3MouseClicked(evt);
            }
        });
        jPanel1.add(location3);
        location3.setBounds(280, 800, 130, 130);

        location8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location8.setText("7");
        location8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location8MouseClicked(evt);
            }
        });
        jPanel1.add(location8);
        location8.setBounds(800, 670, 130, 130);

        location12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location12.setText("11");
        location12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location12MouseClicked(evt);
            }
        });
        jPanel1.add(location12);
        location12.setBounds(800, 150, 130, 130);

        location10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location10.setText("9");
        location10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location10MouseClicked(evt);
            }
        });
        jPanel1.add(location10);
        location10.setBounds(800, 410, 130, 130);

        location11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        location11.setText("10");
        location11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location11MouseClicked(evt);
            }
        });
        jPanel1.add(location11);
        location11.setBounds(800, 280, 130, 130);

        location13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location13.setText("12");
        location13.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location13MouseClicked(evt);
            }
        });
        jPanel1.add(location13);
        location13.setBounds(800, 20, 130, 130);

        location16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location16.setText("15");
        location16.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location16.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location16MouseClicked(evt);
            }
        });
        jPanel1.add(location16);
        location16.setBounds(410, 20, 130, 130);

        location15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location15.setText("14");
        location15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location15MouseClicked(evt);
            }
        });
        jPanel1.add(location15);
        location15.setBounds(540, 20, 130, 130);

        location19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location19.setText("18");
        location19.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location19.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location19MouseClicked(evt);
            }
        });
        jPanel1.add(location19);
        location19.setBounds(20, 20, 130, 130);

        location17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location17.setText("16");
        location17.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location17MouseClicked(evt);
            }
        });
        jPanel1.add(location17);
        location17.setBounds(280, 20, 130, 130);

        location18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        location18.setText("17");
        location18.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        location18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        location18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                location18MouseClicked(evt);
            }
        });
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

        lblPlayer1money.setText("0");
        jPanel2.add(lblPlayer1money);
        lblPlayer1money.setBounds(100, 80, 210, 18);

        lblPlayerName1.setText("Player1");
        jPanel2.add(lblPlayerName1);
        lblPlayerName1.setBounds(20, 20, 70, 18);

        lblPlayer1Asset.setText("0");
        jPanel2.add(lblPlayer1Asset);
        lblPlayer1Asset.setBounds(90, 50, 150, 18);

        jLabel2.setText("현금 자산:");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(20, 80, 70, 18);

        jLabel4.setText("총 자산: ");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(20, 50, 70, 18);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(426, 115));
        jPanel3.setLayout(null);

        lblPlayer2money.setText("0");
        jPanel3.add(lblPlayer2money);
        lblPlayer2money.setBounds(90, 90, 230, 18);

        lblPlayer2Name.setText("Player2");
        jPanel3.add(lblPlayer2Name);
        lblPlayer2Name.setBounds(30, 30, 120, 18);

        lblPlayer2Asset.setText("0");
        jPanel3.add(lblPlayer2Asset);
        lblPlayer2Asset.setBounds(90, 60, 190, 18);

        jLabel5.setText("총 자산: ");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(20, 60, 70, 18);

        jLabel11.setText("현금 자산:");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(20, 90, 70, 18);

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

    public void Buy() {
        buyFrame.setLocation(350, 350); // Model 대화상자 위치 지정
        buyFrame.setSize(430, 300); // Model 대화상자 크기 지정
        buyFrame.show();

        // 구매가
        JLabel[][] buylblList = {
            {buyGroundVal, buyVilaVal, buyBuildingVal, buyHotelVal},
            {sellGroundVal, sellVilaVal, sellBuildingVal, sellHotelVal},
            {costGroundVal, costVilaVal, costBuildingVal, costHotelVal}
        };
        int[] valueList = {locationList[playerList[flag2].getReferLoc()].getLoc_ground(),
            locationList[playerList[flag2].getReferLoc()].getLoc_vila(),
            locationList[playerList[flag2].getReferLoc()].getLoc_building(),
            locationList[playerList[flag2].getReferLoc()].getLoc_hotel()
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
    }

    public void Cost() {
        costFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        costFrame.setLocation(350, 350); // Model 대화상자 위치 지정
        costFrame.setSize(430, 300); // Model 대화상자 크기 지정
        costFrame.show();
    }

    public int SpecialArea(int judge) {

        if (judge == 6) {
            //무인도
            playerList[flag2].setIsland(0);
            flag++;
            turn--;
            gameOver();
            return -1;
        } else if (judge == 12) {
            // 복지 창;
            playerList[flag2].setMoney(playerList[flag2].getMoney() + 200000);
            playerList[flag2].setAsset(playerList[flag2].getAsset() + 200000);
            playerMoneylblList[flag2].setText(Integer.toString(playerList[flag2].getMoney()));
            playerAssetlblList[flag2].setText(Integer.toString(playerList[flag2].getAsset()));
            flag++;
            turn--;
            gameOver();
            return -1;
        } else if (judge == 18) {
            // 세계여행 창
            // gameOver() -====> 보류
            travelFrame.setLocation(350, 350); // Model 대화상자 위치 지정
            travelFrame.setSize(430, 300); // Model 대화상자 크기 지정
            travelFrame.show();
            flag++;
            turn--;
            return -1;
        }
        return 1;
    }

    public boolean isIsland() {
        if (playerList[flag2].getIsland() > 0) {
            playerList[flag2].setIsland(playerList[flag2].getIsland() - 1);
            flag++;
            turn--;
            gameOver();
            return true;
        }
        return false;
    }

    public void gameOver() {
        if (turn == 0) {
            gameOverFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            gameOverFrame.setLocation(350, 350); // Model 대화상자 위치 지정
            gameOverFrame.setSize(430, 300); // Model 대화상자 크기 지정
            gameOverFrame.show();
        }
    }

    private void DiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiceMouseClicked
        // trun이 영이 됬을 경우 처리 X

        Dice dice = new Dice();
        diceResult = dice.rollDice();

        diceNum.setText(String.valueOf(diceResult));

        if (flag % 2 != 0) {
            flag2 = 0;
        } else {
            flag2 = 1;
        }
        if (isIsland() == true) {
            return;
        }
        int judge = playerList[flag2].movement(diceResult, locationList, playerMoneylblList, playerAssetlblList);
        getPlayerlblList()[flag2].setLocation(getPlayerList()[flag2].getX(), getPlayerList()[flag2].getY());

        // 무인도, 복지, 세계여행
        // 구매 여부
        if (SpecialArea(judge) == 1) {
            if (judge == 3) {
                Buy();
                flag++;
                turn--;
            } else if (judge == flag2) {
                // 재구매 여부
                Buy();
                flag++;
                turn--;
            } else {
                // 지불 
                Cost();
                flag++;
                turn--;
            }
        }
        lblTurn.setText(String.valueOf("남은 턴 수 : " + turn));
    }//GEN-LAST:event_DiceMouseClicked
    private void form2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_form2ActionPerformed
        loginFrame.setVisible(true);
        loginFrame.setLocation(350, 350); // Model 대화상자 위치 지정
        loginFrame.setSize(430, 300); // Model 대화상자 크기 지정
        loginFrame.setTitle("로그인");
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
            // 땅을 구매할 현금이 부족합니다 알림 띄우고 history.go(-1)
        } else {
            int beforeMoney = playerList[flag2].getMoney(); // 유저의 돈을 불러옴
            beforeMoney -= totalPrice;
            int beforeAsset = playerList[flag2].getAsset(); // 유저의 돈을 불러옴
            beforeAsset -= totalPrice / 2;
            playerList[flag2].setAsset(beforeAsset);
            playerList[flag2].setMoney(beforeMoney); //플레이어의 moeny 업데이트
            playerMoneylblList[flag2].setText(Integer.toString(playerList[flag2].getMoney())); // 해당 플레이어의 자산을 수정
            playerAssetlblList[flag2].setText(Integer.toString(playerList[flag2].getAsset())); // 해당 플레이어의 자산을 수정
            // (path) enable(true)로 전환

            locationList[playerList[flag2].getReferLoc()].setValue(array); // 해당 플레이어의 구매정보
            locationList[playerList[flag2].getReferLoc()].setWho(flag2); // 해당 location의 필드의 소유자는 누구인지
            locationlblList[playerList[flag2].getReferLoc()].setBackground(color[flag2]); // color
        }

        totalPrice = 0;
        lblBuyTitle.setText(null);
        buyFrame.hide();
        gameOver();
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
        if (locationList[playerList[flag2].getReferLoc()].getWho() != flag2) {
            for (int i = 0; i < loc_array.length; i++) {
                check[i].setSelected(false);
                check[i].setEnabled(true);
            }
        }
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
        gameOver();
    }//GEN-LAST:event_btnBuyCancelActionPerformed

    private void btnCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostActionPerformed
        //        int playerMoney = Integer.parseInt(lblTotalMoneyValue.getText());
        int cost = Integer.parseInt(lblTotalCostValue.getText());
        int costResult1 = playerList[flag2].getMoney() - cost;
        int costResult2 = playerList[flag2].getAsset() - cost;

        if (costResult1 < 0) {
            lblTotalCostValue.setText("파산함");

        } else {
            playerList[flag2].setMoney(costResult1);
            lblPlayer2money.setText(Integer.toString(costResult1));
            playerList[flag2].setAsset(costResult2);
            costFrame.hide();
            gameOver();
        }
    }//GEN-LAST:event_btnCostActionPerformed

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

    private void btnLoginGoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginGoMouseClicked
        String txtIdValue = txtLoginId.getText();
        String txtPwValue = txtLoginPw.getText();

        login1111.hide();

        String userId_sql = "select * from user_table where user_Id = '" + txtIdValue + "' and user_Pw = '" + txtPwValue + "'";
        getUser_DBData(userId_sql);

        txtLoginId.setText(userId_value);
        txtLoginPw.setText(userPw_value);

        if (txtIdValue.equals(userId_value) && txtPwValue.equals(userPw_value)) {
            loginFrame.hide(); // 로그인 성공 후 폼 닫음
            login1111.showMessageDialog(null, "성공"); // 로그인 성공했다는 메세지 보여주기
//            startFrame.setVisible(true);
            startFrame.setLocation(350, 350); // Model 대화상자 위치 지정
            startFrame.setSize(430, 300); // Model 대화상자 크기 지정
            startFrame.setTitle("로그인");
            startFrame.show(); // 메세지 보여준 후 startFrame 창 열기
        } else {
            loginFrame.show(); // 로그인 실패 시 loginFrame 다시 보여 줌
        }
    }//GEN-LAST:event_btnLoginGoMouseClicked

    private void btnTravelGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTravelGoActionPerformed
//        // 이 버튼이 눌리면
        int index = 0;
        playerList[flag2].setX(destination.getX());
        playerList[flag2].setY(destination.getY());
        getPlayerlblList()[flag2].setLocation(getPlayerList()[flag2].getX(), getPlayerList()[flag2].getY());
//        for (int i = 0; i < locationlblList.length; i++) {
//            if (locationlblList[i] == destination) {
//                index = i;
//                lblerror.setText(Integer.toString(index));
//                break;
//            }
//        }
        playerList[flag2].setReferLoc(index); 
        flag++;
        turn--;
        if (index < 18) {
            playerList[flag2].setMoney(playerList[flag2].getMoney() + 20000);
            playerList[flag2].setAsset(playerList[flag2].getAsset() + 20000);
            playerMoneylblList[flag2].setText(Integer.toString(playerList[flag2].getMoney()));
            playerAssetlblList[flag2].setText(Integer.toString(playerList[flag2].getAsset()));
        }
        travelFrame.hide();
//        //destination에 저장된 label로  플레이어를 이동시킨다.


    }//GEN-LAST:event_btnTravelGoActionPerformed

    private void travelFrameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_travelFrameComponentShown
        lblDestination.setText("");
    }//GEN-LAST:event_travelFrameComponentShown

    private void gameOverFrameComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_gameOverFrameComponentShown
        // player 1, 2 자산 현금 비교 
        // 승리자 표출
        if (player1.getAsset() > player2.getAsset()) {
            lblWinner.setText("Player" + player1.getPlayerNum() + "님이 이겼습니다.");
        } else {
            lblWinner.setText("Player" + player2.getPlayerNum() + "님이 이겼습니다.");
        }

        lblPlayer1TotalAsset.setText(Integer.toString(player1.getAsset()));
        lblPlayer2TotalAsset.setText(Integer.toString(player2.getAsset()));
        lblPlayer1TotalMoney.setText(Integer.toString(player1.getMoney()));
        lblPlayer2TotalMoney.setText(Integer.toString(player2.getMoney()));

    }//GEN-LAST:event_gameOverFrameComponentShown

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        // 레이아웃 설정 후 다시
    }//GEN-LAST:event_btnRestartActionPerformed

    private void btnGameoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGameoverActionPerformed
        System.exit(-1);
    }//GEN-LAST:event_btnGameoverActionPerformed

    private void location1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location1MouseClicked
        destination = location1;
        lblDestination.setText(destination.getText());
    }//GEN-LAST:event_location1MouseClicked

    private void location2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location2MouseClicked
        destination = location2;
        lblDestination.setText(location2.getText());
    }//GEN-LAST:event_location2MouseClicked

    private void location3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location3MouseClicked
        destination = location3;
        lblDestination.setText(location3.getText());
    }//GEN-LAST:event_location3MouseClicked

    private void location4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location4MouseClicked
        destination = location4;
        lblDestination.setText(location4.getText());
    }//GEN-LAST:event_location4MouseClicked

    private void location5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location5MouseClicked
        destination = location5;
        lblDestination.setText(location5.getText());
    }//GEN-LAST:event_location5MouseClicked

    private void location6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location6MouseClicked
        destination = location6;
        lblDestination.setText(location6.getText());
    }//GEN-LAST:event_location6MouseClicked

    private void location7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location7MouseClicked
        destination = location7;
        lblDestination.setText(location7.getText());
    }//GEN-LAST:event_location7MouseClicked

    private void location8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location8MouseClicked
        destination = location8;
        lblDestination.setText(location8.getText());
    }//GEN-LAST:event_location8MouseClicked

    private void location9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location9MouseClicked
        destination = location9;
        lblDestination.setText(location9.getText());
    }//GEN-LAST:event_location9MouseClicked

    private void location10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location10MouseClicked
        destination = location10;
        lblDestination.setText(location10.getText());
    }//GEN-LAST:event_location10MouseClicked

    private void location11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location11MouseClicked
        destination = location11;
        lblDestination.setText(location11.getText());
    }//GEN-LAST:event_location11MouseClicked

    private void location12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location12MouseClicked
        destination = location12;
        lblDestination.setText(location12.getText());
    }//GEN-LAST:event_location12MouseClicked

    private void location13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location13MouseClicked
        destination = location13;
        lblDestination.setText(location13.getText());
    }//GEN-LAST:event_location13MouseClicked

    private void location14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location14MouseClicked
        destination = location14;
        lblDestination.setText(location14.getText());
    }//GEN-LAST:event_location14MouseClicked

    private void location15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location15MouseClicked
        destination = location15;
        lblDestination.setText(location15.getText());
    }//GEN-LAST:event_location15MouseClicked

    private void location16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location16MouseClicked
        destination = location16;
        lblDestination.setText(location16.getText());
    }//GEN-LAST:event_location16MouseClicked

    private void location17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location17MouseClicked
        destination = location17;
        lblDestination.setText(location17.getText());
    }//GEN-LAST:event_location17MouseClicked

    private void location18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location18MouseClicked
        destination = location18;
        lblDestination.setText(location18.getText());
    }//GEN-LAST:event_location18MouseClicked

    private void location19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location19MouseClicked
        destination = location19;
        lblDestination.setText(location19.getText());
    }//GEN-LAST:event_location19MouseClicked

    private void location20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location20MouseClicked
        destination = location20;
        lblDestination.setText(location20.getText());

    }//GEN-LAST:event_location20MouseClicked

    private void location21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location21MouseClicked
        destination = location21;
        lblDestination.setText(location21.getText());
    }//GEN-LAST:event_location21MouseClicked

    private void location22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location22MouseClicked
        destination = location22;
        lblDestination.setText(location22.getText());
    }//GEN-LAST:event_location22MouseClicked

    private void location23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location23MouseClicked
        destination = location23;
        lblDestination.setText(location23.getText());
    }//GEN-LAST:event_location23MouseClicked

    private void location24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_location24MouseClicked
        destination = location24;
        lblDestination.setText(location24.getText());
    }//GEN-LAST:event_location24MouseClicked

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
    private javax.swing.JButton btnGameover;
    private javax.swing.JButton btnLoginGo;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnTravelGo;
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
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel lblBuyPrice;
    private javax.swing.JLabel lblBuyTitle;
    private javax.swing.JLabel lblCostPrice;
    private javax.swing.JLabel lblCostTitle;
    private javax.swing.JLabel lblDestination;
    private javax.swing.JLabel lblLoginErrorTitle;
    private javax.swing.JLabel lblLoginId;
    private javax.swing.JLabel lblLoginPw;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblPlayer1Asset;
    private javax.swing.JLabel lblPlayer1TotalAsset;
    private javax.swing.JLabel lblPlayer1TotalMoney;
    private javax.swing.JLabel lblPlayer1money;
    private javax.swing.JLabel lblPlayer2;
    private javax.swing.JLabel lblPlayer2Asset;
    private javax.swing.JLabel lblPlayer2Name;
    private javax.swing.JLabel lblPlayer2TotalAsset;
    private javax.swing.JLabel lblPlayer2TotalMoney;
    private javax.swing.JLabel lblPlayer2money;
    private javax.swing.JLabel lblPlayerName1;
    private javax.swing.JLabel lblSellPrice;
    private javax.swing.JLabel lblTotalCost;
    private javax.swing.JLabel lblTotalCostValue;
    private javax.swing.JLabel lblTotalMoney;
    private javax.swing.JLabel lblTotalMoneyValue;
    private javax.swing.JLabel lblTurn;
    private javax.swing.JLabel lblWinner;
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
    private javax.swing.JOptionPane login1111;
    private javax.swing.JFrame loginFrame;
    private javax.swing.JLabel sellBuildingVal;
    private javax.swing.JLabel sellGroundVal;
    private javax.swing.JLabel sellHotelVal;
    private javax.swing.JLabel sellVilaVal;
    private javax.swing.JFrame startFrame;
    private javax.swing.JFrame travelFrame;
    private javax.swing.JTextField txtLoginId;
    private javax.swing.JTextField txtLoginPw;
    // End of variables declaration//GEN-END:variables
}
